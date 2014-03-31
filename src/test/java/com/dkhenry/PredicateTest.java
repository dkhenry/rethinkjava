package com.dkhenry;

import com.dkhenry.RethinkDB.*;
import com.dkhenry.RethinkDB.errors.RqlDriverException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class PredicateTest {

    @Test
    public void testFunction1() throws RqlDriverException {
        RqlConnection r = RqlConnection.connect("localhost",28015);

        RqlQuery query = RqlQuery.eval(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).map(new Function() {


            @Override
            public RqlQuery apply(RqlQuery.Var var1) {
                return var1.add(1);
            }
        });


        RqlCursor cursor = r.run(query);
        AssertJUnit.assertEquals(Arrays.asList(2,3,4,5,6,7,8,9,10,11), cursor.next().getList());


    }
}


