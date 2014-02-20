package com.dkhenry;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rethinkdb.Ql2.Term;

public class CompletnessTest {
	
	@DataProvider(name="termTypeNames")
	public Iterator<Object[]> termTypes() {
		ArrayList<Object[]> types = new ArrayList<Object[]>();
		for(Term.TermType type: Term.TermType.values()) {
			
			String name = type.name();
			String finalName = "";
			for(String part : name.split("_")) { 
				finalName += (part.substring(0,1).toUpperCase() + part.substring(1).toLowerCase()); 
			}
			
			// Some last minute Sanitization
			finalName = finalName.replace("by","By")
							.replaceAll("Db$","DB")
							.replaceAll("^Error$","UserError")
							.replace("script","Script")
							.replace("each","Each")
							.replace("map","Map")
							.replace("of","Of")
							.replace("call","Call");									
			types.add( new String[] { finalName });
		}
		return types.iterator();
	}
	
	@Test(dataProvider="termTypeNames",groups={"acceptance"})
	public void testTermImplementation(String type) throws ClassNotFoundException{ 			
		try {
			Class.forName("com.dkhenry.RethinkDB.RqlQuery$"+type);
		} catch (ClassNotFoundException ex) { 
			try {
				Class.forName("com.dkhenry.RethinkDB.RqlTopLevelQuery$"+type);
			} catch (ClassNotFoundException ex1) {
				try {
					Class.forName("com.dkhenry.RethinkDB.RqlMethodQuery$"+type);
				} catch (ClassNotFoundException ex2) {
					Class.forName("com.dkhenry.RethinkDB.RqlBiOperQuery$"+type);
				}
			}
		}
	}
}
