package com.dkhenry;

import org.testng.annotations.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import com.dkhenry.RethinkDB.*; 
import com.dkhenry.RethinkDB.errors.RqlDriverException;

public class IntegrationTest {
	@Test
	public void createAndListDb() throws RqlDriverException {		 
		SecureRandom random = new SecureRandom();
		String database = new BigInteger(130, random).toString(32);
		RqlConnection r = RqlConnection.connect("localhost",28015);
		RqlCursor cursor = r.run(r.db_create(database));
		RqlObject obj = cursor.next();					
		assert Double.valueOf(1.0).equals(obj.getAs("created")) : "Database was not created successfully ";
		cursor = r.run(r.db_list());
		obj = cursor.next();
		boolean found = false;
		for(Object o: obj.getList()) {
			if( database.equals(o)) { 
				found = true;
				break; 
			}				
		}
		assert found == true : "Databse was not able to be listed";			
		cursor = r.run(r.db_drop(database));
		obj = cursor.next(); 
		assert Double.valueOf(1.0).equals(obj.getAs("dropped")) : "Database was not dropped successfully ";
		r.close();
	}

	@Test
	public void createAndListTable() throws RqlDriverException { 
		SecureRandom random = new SecureRandom();
		String database = new BigInteger(130, random).toString(32);
		String table = new BigInteger(130, random).toString(32);
		RqlConnection r = RqlConnection.connect("localhost",28015);
		r.run(r.db_create(database));
		RqlCursor cursor = r.run(r.db(database).table_create(table));
		assert Double.valueOf(1.0).equals(cursor.next().getAs("created")) : "Table was not created successfully ";		
		cursor = r.run(r.db(database).table_list());
		boolean found = false; 
		for(Object o: cursor.next().getList()) { 
			if(table.equals(o)) {
				found = true; 
				break; 
			}
		}
		assert found == true : "Table was not able to be listed";
		cursor = r.run(r.db(database).table_drop(table));
		assert Double.valueOf(1.0).equals(cursor.next().getAs("dropped")) : "Table was not dropped successfully ";		
		r.run(r.db_drop(database)); 
		r.close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	@Test
	public void insertAndRetrieveData() throws RqlDriverException {
		SecureRandom random = new SecureRandom();
		String database = new BigInteger(130, random).toString(32);
		String table = new BigInteger(130, random).toString(32);
		RqlConnection r = RqlConnection.connect("localhost",28015);
		r.run(r.db_create(database));
		r.run(r.db(database).table_create(table));
		
		RqlCursor cursor = r.run(r.db(database).table(table).insert( Arrays.asList(				
				new HashMap() {{ put("name","Worf");put("show","Star Trek TNG"); }},
			    new HashMap() {{ put("name","Data");put("show","Star Trek TNG"); }},
			    new HashMap() {{ put("name","William Adama");put("show","Battlestar Galactica"); }}, 
			    new HashMap() {{ put("name","Homer Simpson");put("show","The Simpsons"); }}
		)));
		assert Double.valueOf(4.0).equals(cursor.next().getAs("inserted")) : "Error inserting Data into Database";	
		cursor = r.run(r.db(database).table(table).filter(new HashMap() {{ put("show","Star Trek TNG"); }}));
		// We Expect Two results 
		int count = 0; 		
		for(RqlObject o: cursor) {
			Map<String,Object> m = o.getMap(); 
			assert m.containsKey("name") : "Data that came back was malformed (missing \"name\")";
			assert m.containsKey("show") : "Data that came back was malformed (missing \"show\")";
			assert "Star Trek TNG".equals(m.get("show")): "Data that came back was just plain wrong (\"show\" was not \"Star Trek TNG\");" +								
			count++; 			
		}
		
		cursor = r.run(r.db(database).table(table).count(new HashMap() {{ put("show","Star Trek TNG"); }}));
		double rowCount = 0;
		for(RqlObject o: cursor) {
			rowCount = o.getNumber();
		}		
		assert rowCount == count : "Failed at getting the correct row count.";
		
		cursor = r.run(r.db(database).table(table).count());
		for(RqlObject o: cursor) {
			rowCount = o.getNumber();
		}		
		assert rowCount == 4.0 : "Failed at getting the correct row count.";		
		
		r.run(r.db(database).table_drop(table));				
		r.run(r.db_drop(database)); 
		r.close();
	}

    @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
    @Test
    public void largeDataSetTest() throws RqlDriverException {
        final SecureRandom random = new SecureRandom();
        String database = new BigInteger(130, random).toString(32);
        String table = new BigInteger(130, random).toString(32);
        RqlConnection r = RqlConnection.connect("localhost",28015);
        r.run(r.db_create(database));
        r.run(r.db(database).table_create(table));

        List<Object> l = new ArrayList<Object>();
        for( int i = 0 ; i < 8192 ; i++ ) {
            l.add(
                    new HashMap() {{
                        put("id",new BigInteger(32,random).toString(32) );
                        put("name",new BigInteger(64,random).toString(32));
                    }}
            );
        }
        RqlCursor cursor = r.run(r.db(database).table(table).insert( l ));
        assert Double.valueOf(8192.0).equals(cursor.next().getAs("inserted")) : "Error inserting Data into Database";
        cursor = r.run(r.db(database).table(table).count());
        assert Double.valueOf(8192.0).equals(cursor.next().getNumber()) : "Error getting large row cound";

        cursor = r.run(r.db(database).table(table));
        long rowCount = 0;
        for(RqlObject o: cursor) {
            rowCount++;
        }
        assert rowCount == 8192 : "Error fetching all rows in large dataset";
        System.out.println("We got " + rowCount + " results back ");

        r.run(r.db(database).table_drop(table));
        r.run(r.db_drop(database));
        r.close();

    }
}
