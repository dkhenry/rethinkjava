rethinkjava
===========

Java/JVM Drivers for RethinkDB ( rethinkdb.com )


# Status 
This drivers are being worked on now and is currently in progress. I make no claims about the usability, stability or saftly of this code. 

## Whats Working 
Right now communicating with the Server works. Also If you pull in this artifact you should have access to the entire Protobuf API so if you're in a hurry you can just construct raw querys and send them using the RqlConneciton class. 

Also The Database administration querys should be working ( db_create, db_drop, db_list, table_create, table_drop, table_list )

There _is_ support for inserting right now, but Its not well tested. 

##Whats not working 
Any for of reads or updates 

Actualy testing of everything

Formal Examples. 

# How you can Help 
Make the testing better ( This is actually really important going forwards ) 

Fork the repo and make pull requests 

Submit defects and feature request 

Create some kind of documentation 

Give me a place to host the maven artifacts

# Planned API 
I want to keep this API as close to the official API's as I can. This is an example of what I will try to accomplish


    RethinkDB r = RethinkDB.connect(hostname,port);
    // Any use of db set the default db
    r.db("test").table_create("characters");
    // A simple Insert
    r.db("test").table("characters").insert(Arrays.asList(
				    new HashMap() {{ put("name","Worf");put("show","Star Trek TNG") }},
				    new HashMap() {{ put("name","Data");put("show","Star Trek TNG") }},
				    new HashMap() {{ put("name","William Adama");put("show","Battlestar Galactica") }}, 
				    new HashMap() {{ put("name","Homer Simpson");put("show","The Simpsons") }}
				));

    // Then a Simple Query
    r.table("tv_shows")
	  .filter(new HashMap() {{ put("name","Star Trek TNG"); }});
    // Returns Array(HashMap( ("name","Worf") , ("show","Star Trek TNG") ),HashMap( ("name","Data") , ("show","Star Trek TNG") ))
