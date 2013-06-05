package com.dkhenry;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import com.rethinkdb.*;
import java.util.Arrays;
import java.nio.charset.Charset;


public class ConnectionTest {

	public static SocketChannel simpleConnection(String hostname, Integer port) throws IOException { 
		SocketChannel socketChannel = SocketChannel.open();

		// We will be blocking for this test
		socketChannel.configureBlocking(true);

		socketChannel.connect(new InetSocketAddress(hostname,port));

		ByteBuffer buf = ByteBuffer.allocate(4);
		buf.order(ByteOrder.LITTLE_ENDIAN);
		buf.clear();
		buf.putInt(com.rethinkdb.Ql2.VersionDummy.Version.V0_1_VALUE);
		System.out.println("Connecting with version: " + Integer.toString(com.rethinkdb.Ql2.VersionDummy.Version.V0_1_VALUE,16));
		System.out.println(Arrays.toString(buf.array()));
		buf.flip();
		while(buf.hasRemaining()) { 
			socketChannel.write(buf);
		}	   

		return socketChannel; 
	}
	public static void simpleSend(SocketChannel sc, byte[] data) throws IOException { 
		// Construct the message to send 
		ByteBuffer buffer = ByteBuffer.allocate(4+data.length); 
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(data.length); 
		buffer.put(data); 
		buffer.flip();

		while(buffer.hasRemaining()) { 
			sc.write(buffer); 
		}
	}

	public static com.rethinkdb.Ql2.Response simpleRecv(SocketChannel sc) throws IOException {
		ByteBuffer datalen = ByteBuffer.allocate(4); 
		datalen.order(ByteOrder.LITTLE_ENDIAN);
		int bytesRead = sc.read(datalen); 
		if( bytesRead != 4 ) { 
			throw new IOException("Incorrect amount of data read " + (new Integer(bytesRead)).toString() + " (expected 4) ");
		}
		datalen.flip();
		int len = datalen.getInt();
		System.out.println("Data is " + (new Integer(len)).toString() + " Bytes Long"); 
		ByteBuffer buf = ByteBuffer.allocate(len);
		bytesRead = 0;
		while( bytesRead != len ){ 
			 bytesRead += sc.read(buf);		
		}
		buf.flip();
		com.rethinkdb.Ql2.Response r = com.rethinkdb.Ql2.Response.parseFrom(buf.array()); 
		return r;
	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for ( int j = 0; j < bytes.length; j++ ) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	@Test
    public void testConnection(){
		boolean rvalue = false;
		try { 
			SocketChannel sc = simpleConnection("localhost",28015); 
			sc.close(); 
		} catch (IOException e) {
			rvalue = true; 
		}
		org.junit.Assert.assertFalse("Error Connecting", rvalue);
	}

	@Test
	public void testCreateTable() {
		// Connect We Know this works 
		boolean rvalue = false; 
		try {
			SocketChannel sc = simpleConnection("localhost",28015); 

		// Build the Protocol Buffer ( create table )
		com.rethinkdb.Ql2.Query.Builder q =  com.rethinkdb.Ql2.Query.newBuilder();
		com.rethinkdb.Ql2.Term.Builder t =  com.rethinkdb.Ql2.Term.newBuilder();
		
		t.setType(com.rethinkdb.Ql2.Term.TermType.DB_LIST);
		q.setType(com.rethinkdb.Ql2.Query.QueryType.START);
		q.setToken(1L); 
		q.setQuery(t.build());

		System.out.println("Sending Query");
		simpleSend(sc,q.build().toByteArray());

		System.out.println("Getting response"); 
		com.rethinkdb.Ql2.Response r = simpleRecv(sc);
		System.out.println(r.toString()); 
		sc.close();
		} catch (Exception e) { 
			e.printStackTrace(new PrintStream(System.out));
			rvalue = true; 
		}
		org.junit.Assert.assertFalse("Error Sending DBList Query", rvalue);		
	}
}
