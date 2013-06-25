package com.dkhenry;

import org.junit.Test;
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
		org.junit.Assert.assertFalse("Error Connecting", rvalue);
	}
	
	/* Test the functionality of the ten minute Introduction */
	@Test
	public void testDatabaseCreate() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			//r.db_create('superheroes').run(conn)
			System.out.println(r.run(r.db_create("superheroes")).toString());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		org.junit.Assert.assertFalse("Error Creating Datrabase", rvalue);
	}
	
	@Test
	public void testDatabaseList() { 
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			//r.db_list().run(conn)
			System.out.println(r.run(r.db_list()).toString());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}	
		org.junit.Assert.assertFalse("Error Listing Databases", rvalue);
	}
	
	@Test
	public void testDatabaseDrop() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			//r.db_drop('superheroes').run(conn)
			System.out.println(r.run(r.db_drop("superheroes")).toString());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue =  true;
		}	
		org.junit.Assert.assertFalse("Error Droping Databases", rvalue);
	}
	
	@Test
	public void testTableCreate() {
		boolean rvalue = false; 
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			// r.db('test').table_create('dc_universe').run(conn)
			System.out.println(r.run(r.db("test").table_create("dc_universe")).toString());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		org.junit.Assert.assertFalse("Error Creating Table", rvalue);
	}
	
	@Test
	public void testTableList() {
		boolean rvalue= false ;	
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			// r.db('test').table_list().run(conn)
			System.out.println(r.run(r.db("test").table_list()).toString());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		org.junit.Assert.assertFalse("Error Listing Tables", rvalue);
	}
	
	@Test
	public void testTableDrop() {
		boolean rvalue = false;
		RqlConnection r;
		try {
			r = RqlConnection.connect("localhost",28015);
			// r.db('test').table_drop('dc_universe').run(conn)
			System.out.println(r.run(r.db("test").table_drop("dc_universe")).toString());			
			r.close();
		} 		
		catch (RqlDriverException e) {
			e.printStackTrace();
			rvalue = true;
		}
		org.junit.Assert.assertFalse("Error Droping Table", rvalue);
	}	
}
