package com.dkhenry.RethinkDB;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.dkhenry.RethinkDB.errors.RqlDriverException;
import com.rethinkdb.Ql2;
import com.rethinkdb.Ql2.Query;
import com.rethinkdb.Ql2.Response;

public class RqlConnection {
	private SocketChannel _sc;    
	private String _hostname;
	private int _port; 
	private boolean _connected;

    //! Flag to indicate if this is a secured connection
    private boolean _secured;

    //! The authorization key for this connection
    private String _authKey;
 
	//! A global counter for the request tokens; 
	private static AtomicLong counter = new AtomicLong(0);

	public RqlConnection() { 
		_connected = false;
        _secured = false;
	}

	public String get_hostname() { return _hostname; }
	public void set_hostname(String hostname) throws RqlDriverException {
        set_hostname2(hostname,true);
    }
    public void set_hostname2(String hostname, boolean reconnect) throws RqlDriverException {
		String ohostname = _hostname;
		_hostname = hostname; 
		if(reconnect && _connected && hostname != ohostname) {
			reconnect();
		}
	}

	public int get_port() { return _port; }
	public void set_port(int port) throws RqlDriverException {
        set_port2(port,true);
    }
    public void set_port2(int port, boolean reconnect) throws RqlDriverException {
		int oport = _port; 
		_port = port; 
		if(reconnect && _connected && oport != port) {
			reconnect();
		}
	}

    public String get_authKey() { return _authKey; }
    public void set_authKey(String authKey) throws RqlDriverException {
        set_authKey2(authKey, true);
    }
    public void set_authKey2(String authKey, boolean reconnect) throws RqlDriverException {
        String okey = _authKey;
        _authKey = authKey;
        _secured = (authKey != null) ;

        if(reconnect && _connected && okey != authKey) {
            reconnect();
        }
    }

	public void close() throws RqlDriverException { 
		if( _connected ) {
			try { 
				_sc.close();
			} catch (IOException ex) { 
				throw new RqlDriverException(ex.getMessage());				
			}
			_connected = false; 
		}
	}
    public RqlCursor run(RqlQuery query) throws RqlDriverException {
        return run(query,null);
    }

    public RqlCursor run(RqlQuery query, HashMap<String,Object> optargs) throws RqlDriverException {
		Query.Builder q =  com.rethinkdb.Ql2.Query.newBuilder();		
		q.setType(Query.QueryType.START);
		q.setToken(nextToken()); 
		q.setQuery(query.build());
        if( null != optargs ) {
            for(Map.Entry<String,Object> e: optargs.entrySet()) {
                q.addGlobalOptargs(
                    Query.AssocPair.newBuilder()
                            .setKey(e.getKey())
                            .setVal(RqlQuery.eval(e.getValue()).build())
                            .build()
                );
            }
        }
		try {
			send_raw(q.build().toByteArray());
		} catch (IOException ex) { 
			throw new RqlDriverException(ex.getMessage());
		}
		Response rsp = get(); 

		// For this version we only support success :-(
		switch(rsp.getType()) {
		case SUCCESS_ATOM:
		case SUCCESS_SEQUENCE:
		case SUCCESS_PARTIAL:
			return new RqlCursor(this,rsp);
		case CLIENT_ERROR:
		case COMPILE_ERROR:
		case RUNTIME_ERROR:
		default:
			throw new RqlDriverException(rsp.toString());							
		}							
	}
	
	public Response get() throws RqlDriverException {
		try {
			return recv_raw();
		} catch (IOException ex) { 
			throw new RqlDriverException(ex.getMessage());
		}
	}
	
	public Response get_more(long token) throws RqlDriverException {
		// Send the [CONTINUE] query 
		Query.Builder q = com.rethinkdb.Ql2.Query.newBuilder()
					.setType(Query.QueryType.CONTINUE)
					.setToken(token);
		try { 
			send_raw(q.build().toByteArray());
			return recv_raw();
		} catch (IOException ex) {
			throw new RqlDriverException(ex.getMessage());
		}
	}
	
	/* Utility functions to make a pretty API */
	public RqlQuery.Table table(Object... args) { 
		RqlQuery.Table rvalue =  new RqlQuery.Table(args);
		return rvalue; 
	}
	
	public RqlTopLevelQuery.DB db(Object... args) {
		RqlTopLevelQuery.DB rvalue = new RqlTopLevelQuery.DB(args);
		return rvalue;
	}
	
	public RqlTopLevelQuery.DbCreate db_create(Object... args) { 
		RqlTopLevelQuery.DbCreate rvalue = new RqlTopLevelQuery.DbCreate(args);
		return rvalue;
	}
	
	public RqlTopLevelQuery.DbDrop db_drop(Object... args) { 
		RqlTopLevelQuery.DbDrop rvalue = new RqlTopLevelQuery.DbDrop(args);
		return rvalue;
	}
	
	public RqlTopLevelQuery.DbList db_list(Object... args) {
		RqlTopLevelQuery.DbList rvalue = new RqlTopLevelQuery.DbList(args);
		return rvalue;
	}

	public RqlTopLevelQuery.Branch branch(Object... args) {
		RqlTopLevelQuery.Branch rvalue = new RqlTopLevelQuery.Branch(args);
		return rvalue;
	}
		
	/* Private methods */ 
	private void reconnect() throws RqlDriverException{ 
		try {
			if( _connected ) {
				_sc.close();
			}
			_sc = SocketChannel.open(); 
			_sc.configureBlocking( true );
			_sc.connect(new InetSocketAddress(_hostname,_port));

			ByteBuffer buffer = ByteBuffer.allocate(4); 
			buffer.order(ByteOrder.LITTLE_ENDIAN);
            if( _secured ) {
                buffer = ByteBuffer.allocate(4 + 4 + _authKey.length());
                buffer.order(ByteOrder.LITTLE_ENDIAN);
			    buffer.putInt(com.rethinkdb.Ql2.VersionDummy.Version.V0_2_VALUE);
                buffer.putInt(_authKey.length());
                buffer.put(_authKey.getBytes());
            } else {
                buffer.putInt(com.rethinkdb.Ql2.VersionDummy.Version.V0_1_VALUE);
            }

			buffer.flip();
			while(buffer.hasRemaining()) { 
				_sc.write(buffer);
			}

            // If we are working with a v2 connection then we need to get the response string
            if( _secured ) {
                ByteBuffer response = ByteBuffer.allocate(4096);
                _sc.read(response);
                String s = new String(response.array(), "UTF-8");
                if(s.equals("SUCCESS")) {
                    throw new RqlDriverException(s);
                }
            }

			_connected = true;
		} catch (IOException ex) { 
			throw new RqlDriverException(ex.getMessage());				
		}

	}

	public long nextToken() { 
		return counter.incrementAndGet();
	}
	
	public void send_raw( byte[] data ) throws IOException { 
		rethink_send(_sc,data); 
	}

	public Response recv_raw() throws IOException { 
		return rethink_recv(_sc); 
	}
	
	public static RqlConnection connect(String hostname, int port) throws RqlDriverException { 
		return connect2(hostname,port,null);
	}

    public static RqlConnection connect(String hostname, int port, String key) throws RqlDriverException {
        return connect2(hostname,port,key);
    }

    private static RqlConnection connect2(String hostname, int port, String key) throws RqlDriverException {
        RqlConnection r = new RqlConnection();
        r.set_hostname2(hostname, false);
        r.set_port2(port, false);
        if( null != key ) {
            r.set_authKey2(key, false);
        }
        r.reconnect();
        return r;
    }

	public static void rethink_send(SocketChannel sc, byte[] data) throws IOException { 
		ByteBuffer buffer = ByteBuffer.allocate(4+data.length); 
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(data.length); 
		buffer.put(data); 
		buffer.flip();

		while(buffer.hasRemaining()) { 
			sc.write(buffer); 
		}		
	}

	public static Response rethink_recv(SocketChannel sc) throws IOException {
		ByteBuffer datalen = ByteBuffer.allocate(4); 
		datalen.order(ByteOrder.LITTLE_ENDIAN);
		int bytesRead = sc.read(datalen); 
		if( bytesRead != 4 ) { 
			throw new IOException("Incorrect amount of data read " + (new Integer(bytesRead)).toString() + " (expected 4) ");
		}
		datalen.flip();
		int len = datalen.getInt();

		ByteBuffer buf = ByteBuffer.allocate(len);
		bytesRead = 0;
		while( bytesRead != len ){ 
			 bytesRead += sc.read(buf);		
		}
		buf.flip();
		com.rethinkdb.Ql2.Response r = com.rethinkdb.Ql2.Response.parseFrom(buf.array()); 
		return r;
	}
}
