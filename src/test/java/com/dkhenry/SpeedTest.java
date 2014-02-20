package com.dkhenry;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.dkhenry.RethinkDB.*; 
import com.dkhenry.RethinkDB.errors.RqlDriverException;

public class SpeedTest {
	public static String randomString(Random rng, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
	}
		
	@DataProvider(name="insertSizeCounters")
	public Iterator<Object[]> termTypes() {
		ArrayList<Object[]> counts = new ArrayList<Object[]>();
		for(int i =0; i < 16 ; i++) {
			counts.add(new Integer[] { Integer.valueOf(Double.valueOf(Math.pow(2.0,i)).intValue()) } );
		}
		return counts.iterator();
	}
	
	@SuppressWarnings("serial")
	@Test(dataProvider="insertSizeCounters", groups = { "benchmark" })
	public void smallInserts(int count) throws RqlDriverException { 
		SecureRandom random = new SecureRandom();
		String database = new BigInteger(130, random).toString(32);
		String table = new BigInteger(130, random).toString(32);
		RqlConnection r = RqlConnection.connect("localhost",28015);
		r.run(r.db_create(database));
		r.run(r.db(database).table_create(table));
		ArrayList<HashMap<String,String>> values = new ArrayList<HashMap<String,String>>();
		for(int i = 0; i < count; i++) {
            final String index = String.valueOf(i) ;
			final String rkey = randomString(random,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",512);
			final String rvalue = randomString(random,"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",512);
			values.add( new HashMap<String,String>() {{
                put("id",index);
                put(rkey,rvalue);
            }} );
		}
		try {
			long start = System.nanoTime();
			RqlCursor cursor = r.run(r.db(database).table(table).insert( values ));
			long end = System.nanoTime();
			System.out.println("Insert of " + count + " rows took " + (end-start)/1000000000.0 + "s");
			assert Double.valueOf(count).equals(cursor.next().getAs("inserted")): "Wrong number of rows inserted";
		} catch (RqlDriverException ex) { 
			System.out.println(ex.getMessage());
			throw ex;
		}
								
		r.run(r.db(database).table_drop(table));				
		r.run(r.db_drop(database)); 
		r.close();
		
	}
}
