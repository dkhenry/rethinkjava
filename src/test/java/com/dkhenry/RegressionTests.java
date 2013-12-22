package com.dkhenry;

import com.dkhenry.RethinkDB.RqlConnection;
import com.dkhenry.RethinkDB.RqlCursor;
import com.dkhenry.RethinkDB.RqlObject;
import com.dkhenry.RethinkDB.errors.RqlDriverException;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;

public class RegressionTests {
    @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
    @Test
    public void insertNullAsValueParamater() throws RqlDriverException {
        SecureRandom random = new SecureRandom();
        String database = new BigInteger(130, random).toString(32);
        String table = new BigInteger(130, random).toString(32);
        RqlConnection r = RqlConnection.connect("localhost",28015);
        RqlCursor cursor = r.run(r.db_create(database));
        RqlObject obj = cursor.next();
        assert Double.valueOf(1.0).equals(obj.getAs("created")) : "Database was not created successfully ";

        cursor = r.run(r.db(database).table(table).insert( Arrays.asList(
                new HashMap() {{
                    put("TestForNullInsert", null);
                }}
        )));
        assert Double.valueOf(1.0).equals(cursor.next().getAs("inserted")) : "Error inserting null value into Database";

        cursor = r.run(r.db(database).table(table).get());
        obj = cursor.next();
        assert obj.getAs("TestForNullInsert") == null : "Error Getting null value out of database";

        cursor = r.run(r.db(database).table_drop(table));
        assert Double.valueOf(1.0).equals(cursor.next().getAs("dropped")) : "Table was not dropped successfully ";

        r.run(r.db_drop(database));
        r.close();
    }
}
