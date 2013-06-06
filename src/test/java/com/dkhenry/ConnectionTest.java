package com.dkhenry;

import org.junit.Test;
import java.io.PrintStream;

import com.dkhenry.RethinkDB; 
import com.dkhenry.errors.RqlDriverException;

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
}
