package com.dkhenry.RethinkDB;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicLong;

import com.dkhenry.RethinkDB.errors.RqlDriverException;
import com.rethinkdb.Ql2.Response;

public class RqlConnection {
	private SocketChannel _sc;    
	private String _hostname;
	private int _port; 
	private boolean _connected;


 
	//! A global counter for the request tokens; 
	private static AtomicLong counter = new AtomicLong(0);

	public RqlConnection() { 
		_connected = false; 
	}

	public String get_hostname() { return _hostname; }
	public void set_hostname(String hostname) throws RqlDriverException {
		String ohostname = _hostname;
		_hostname = hostname; 
		if(_connected && hostname != ohostname) { 
			reconnect();
		}
	}

	public int get_port() { return _port; }
	public void set_port(int port) throws RqlDriverException { 
		int oport = _port; 
		_port = port; 
		if(_connected && oport != port) { 
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
			buffer.putInt(com.rethinkdb.Ql2.VersionDummy.Version.V0_1_VALUE);

			buffer.flip();
			while(buffer.hasRemaining()) { 
				_sc.write(buffer);
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
		RqlConnection r = new RqlConnection(); 
		r.set_hostname(hostname);
		r.set_port(port);		
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
