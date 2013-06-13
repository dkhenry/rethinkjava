package com.dkhenry;

import org.junit.Test;
import java.io.PrintStream;

import com.dkhenry.RethinkDB; 
import com.dkhenry.errors.RqlDriverException;
import com.rethinkdb.Ql2.*;
public class ConnectionTest {

	@Test
    public void testConnection(){
		boolean rvalue = false;
		try { 
			RethinkDB r = RethinkDB.connect("localhost",28015);
			r.close();
		} catch (RqlDriverException e) {
			rvalue = true; 
		}
		org.junit.Assert.assertFalse("Error Connecting", rvalue);
	}

	@Test
	public void testCreateTable() {
		// Connect We Know this works 
		boolean rvalue = false; 
		try {
			RethinkDB r = RethinkDB.connect("localhost",28015); 
			com.rethinkdb.Ql2.Response resp = r.db_list().run();
			System.out.println(resp.toString()); 
			r.close();
		} catch (Exception e) { 
			e.printStackTrace(new PrintStream(System.out));
			rvalue = true; 
		}
		org.junit.Assert.assertFalse("Error Sending DBList Query", rvalue);		
	}
	
	@Test
	public void testSimpleGet() {
		boolean rvalue = false;
		try {
			RethinkDB  r = RethinkDB.connect("localhost", 28015);
			
			// Build the Get Request
			Long token = r.nextToken(); 
			// Build the Protocol Buffer ( Insert Data )
			Query.Builder q =  com.rethinkdb.Ql2.Query.newBuilder();
			Term.Builder t =  com.rethinkdb.Ql2.Term.newBuilder();
			
			t.setType(Term.TermType.INSERT);
			t.addArgs(
					Term.newBuilder()
						.setType(Term.TermType.TABLE)
						.addArgs(
								Term.newBuilder()
									.setType(Term.TermType.DATUM)
									.setDatum(RethinkDB.stringDatum("first"))									
						)
			).addArgs(
					Term.newBuilder()
						.setType(Term.TermType.MAKE_ARRAY)
						.addArgs(
								Term.newBuilder()
									.setType(Term.TermType.DATUM)
									.setDatum(RethinkDB.objectDatum("id",1.0))
						)
			);
			
			// Set the query parameters
			q.setType(Query.QueryType.START);
			q.setToken(token); 
			q.setQuery(t.build());
			r.send_raw(q.build().toByteArray());
			com.rethinkdb.Ql2.Response resp = r.recv_raw();
			System.out.println(resp.toString()); 
		} catch (Exception ex) {
			ex.printStackTrace(new PrintStream(System.out));			
			rvalue = true; 
		}
		org.junit.Assert.assertFalse("Error Sending Insert to Server", rvalue);									
	}
}
