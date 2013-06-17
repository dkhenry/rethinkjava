rethinkjava
===========

Java/JVM Drivers for RethinkDB ( rethinkdb.com )


# Status 
This drivers are being worked on now, You should start to see pushes comming to this repository shortly. 

# Planned API 
I want to keep this API as close to the official API's as I can. This is an example of what I will try to accomplish


    RethinkDB r = RethinkDB.connect(hostname,port);
    // Any use of db set the default db
    r.db("test").tableCreate("characters");
    // A simple Insert
    r.table("characters").insert(Arrays.asList(
				    new HashMap() {{ put("name","Worf");put("show","Star Trek TNG") }},
				    new HashMap() {{ put("name","Data");put("show","Star Trek TNG") }},
				    new HashMap() {{ put("name","William Adama");put("show","Battlestar Galactica") }}, 
				    new HashMap() {{ put("name","Homer Simpson");put("show","The Simpsons") }}
				));

    // Then a Simple Query
    r.table("tv_shows")
	  .filter(new HashMap() {{ put("name","Star Trek TNG"); }});
    // Returns Array(HashMap( ("name","Worf") , ("show","Star Trek TNG") ),HashMap( ("name","Data") , ("show","Star Trek TNG") ))
