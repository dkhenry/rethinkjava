package com.dkhenry;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.Arrays;
import java.util.HashMap;

import com.dkhenry.RethinkDB.*; 
import com.dkhenry.RethinkDB.errors.RqlDriverException;


public class ConnectionTest {
	
	@Test
    public void testConnection(){
		boolean rvalue = false;
		try { 
			RqlConnection r = RqlConnection.connect("localhost",28015);
			r.close();
		} catch (RqlDriverException e) {
			rvalue = true; 
		}
		AssertJUnit.assertFalse("Error Connecting", rvalue);
	}
	
	/* Test the functionality of the ten minute Introduction */
	@Test
	public void testDatabaseCreate() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			//r.db_create('superheroes').run(conn)
			RqlCursor cursor = r.run(r.db_create("superheroes"));
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		AssertJUnit.assertFalse("Error Creating Datrabase", rvalue);
	}
	
	@Test
	public void testDatabaseList() { 
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
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
			r = RqlConnection.connect("localhost",28015);
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
			r = RqlConnection.connect("localhost",28015);
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
			r = RqlConnection.connect("localhost",28015);
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
			r = RqlConnection.connect("localhost",28015);
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
}
