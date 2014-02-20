package com.dkhenry;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.dkhenry.RethinkDB.RqlConnection;
import com.dkhenry.RethinkDB.RqlCursor;
import com.dkhenry.RethinkDB.errors.RqlDriverException;


public class ConnectionTest {
	static int PRIMARY_PORT=28015;
    static int SECONDARY_PORT=28016;
    static int SECURED_PORT=28016;
    
	@Test
    public void testConnection(){
		boolean rvalue = false;
		try { 
			RqlConnection r = RqlConnection.connect("localhost",PRIMARY_PORT);
			r.close();
		} catch (RqlDriverException e) {
			rvalue = true; 
		}
		AssertJUnit.assertFalse("Error Connecting", rvalue);
	}

    @Test
    public void testSecuredConnection() {
        boolean rvalue = false;
        try {
            RqlConnection r = RqlConnection.connect("localhost",SECURED_PORT,"RETHUNK");
            r.close();
        } catch (RqlDriverException e) {
            rvalue = true;
        }
        AssertJUnit.assertFalse("Error connecting with secured connection",rvalue);
    }
	
	@Test
	public void testHostname() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			AssertJUnit.assertEquals("localhost", r.get_hostname());
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Connecting", rvalue);
	}
	
	@Test
	public void testPort() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			AssertJUnit.assertEquals(PRIMARY_PORT, r.get_port());
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Connecting", rvalue);
	}
	
	@Test
	public void testReconnect() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			r.set_port(SECONDARY_PORT);
			r.set_hostname(InetAddress.getLocalHost().getCanonicalHostName());
			r.close();
		} 		
		catch (RqlDriverException e) {
			rvalue = true;
		}
                catch (UnknownHostException e) {
                        rvalue = true;
                }
		AssertJUnit.assertTrue("Error Connecting", rvalue);
	}
	
	/* Test the functionality of the ten minute Introduction */
	@Test
	public void testDatabaseCreate() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			//r.db_create('superheroes').run(conn)
			RqlCursor cursor = r.run(r.db_create("superheroes"));
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Creating Database", rvalue);
	}
	
	@Test
	public void testDatabaseList() { 
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			//r.db_list().run(conn)
			r.run(r.db_list());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}	
		AssertJUnit.assertFalse("Error Listing Databases", rvalue);
	}
	
	@Test
	public void testDatabaseDrop() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			//r.db_drop('superheroes').run(conn)
			r.run(r.db_drop("superheroes")).toString();			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue =  true;
		}	
		AssertJUnit.assertFalse("Error Droping Databases", rvalue);
	}
	
	@Test
	public void testTableCreate() {
		boolean rvalue = false; 
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			// r.db('test').table_create('dc_universe').run(conn)
			r.run(r.db_create("test12345"));
			r.run(r.db("test12345").table_create("dc_universe"));
			r.run(r.db_drop("test12345"));
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Creating Table", rvalue);
	}
	
	@Test
	public void testTableList() {
		boolean rvalue= false ;	
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			// r.db('test').table_list().run(conn)
			r.run(r.db_create("test12345"));
			r.run(r.db("test12345").table_list());
			r.run(r.db_drop("test12345"));
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Listing Tables", rvalue);
	}
	
	@Test
	public void testTableDrop() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			// r.db('test').table_drop('dc_universe').run(conn)
			r.run(r.db_create("test12345"));
			r.run(r.db("test12345").table_create("dc_universe"));
			r.run(r.db("test12345").table_drop("dc_universe"));
			r.run(r.db_drop("test12345"));
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Droping Table", rvalue);
	}	
	
	@Test
	public void testTable() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",PRIMARY_PORT);
			r.run(r.db_create("test123456"));
			r.run(r.db("test123456").table_create("dc_universe"));
			r.table("dc_universe");
			r.run(r.db("test123456").table_drop("dc_universe"));
			r.run(r.db_drop("test123456"));
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Connecting", rvalue);
	}
}
