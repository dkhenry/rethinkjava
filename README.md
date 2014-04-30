rethinkjava
===========

Java/JVM Drivers for RethinkDB ( rethinkdb.com )


# Status

This driver is working with API version 1.0 and 2.0 ( RethinkDB 1.5, 1.6, 1.7 .. 1.11) 

## Changelog 

* 0.1 (June 2013) Initial push of project and first support for Maven
* 0.2 (June 2013) Added the rest of the Rql language to driver and provided method to deconstruct responses.
* 0.3 (July 2013) Fixed Errors in Datum Encoding and RqlCursors. Added More testing
* 0.4 (July 2013) Added the instance methods so API looks much like the python API. Further testing added. 
* 0.5 (September 2013) Modified the license to conform to the rest of the RethinkDB driver licenses
* 0.6 (September 2013) Updated to latest version of The query language Protobuf. Added javadocs and source jar's to
deployment
* 0.7 (December 2013) Fixed error encoding NULL. Updated protobuf to most recent version ( 1.11.2 )
* 0.8 (Februrary 2014) Full support for v2 of the connection protocol including authentication
* 0.9 (April 2014) Added support for optional arguments. Additional testing

## Using rethinkjava 
Add the following to your pom.xml
```xml
<repository>
    <id>rethinkjava-mvn-repo</id>
    <url>https://raw.github.com/dkhenry/rethinkjava/mvn-repo/</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
</repository>

```

Then in your dependencies section add the following
```xml
<dependency>
    <groupId>com.dkhenry</groupId>
    <artifactId>rethinkjava</artifactId>
    <version>0.7</version>
  </dependency>
```

## Whats Working 
Almost Everything

##Whats not working

Formal Examples. 

# How you can Help 
Make the testing better ( This is actually really important ) 

Fork the repo and make pull requests 

Submit defects and feature request 

Create some kind of documentation ( java docs on all the instance methods would be awesome ) 

Converting the instance methods to actually take in parameters ( this will help in finding compile time defects, and documentation )

# Building From Source 
Rethink Java uses Maven as its build system. If you want to build from source make sure you have 
maven installed as well as the procol buffer compiler for java. On fedora this should get you the required packages 

    yum install maven protobuf protobuf-java
    
Once you have the required packages and the source checked out you can build Rethink Java and install it to your local maven cache by running  

    mvn clean install 
    
If you get a new ql2.proto file you _must_ add the package definition to the top of it ( package com.rethinkdb;)

# Testing
Rethink Java also ships with a test suite which can be ran with the test target. You must be running three instances of rethink-db on localhost to run the tests
    rethinkdb --directory rethinkdb_data&
    rethinkdb --port-offset 1 --directory rethinkdb_data1&
    rethinkdb --port-offset 2 --directory rethinkdb_data2&
    echo "set auth RETHUNK" | rethinkdb admin --join localhost:29017
    sleep 5
    mvn test 

If your interested in the code coverage jacoco is configured to run a code coverage report after testing its located here 

    target/site/jacoco/index.html
 
# API
The API should stay as close to the official API's as it can be. This is an example of what the current API looks like.


    RethinkDB r = RethinkDB.connect(hostname,port);
    // Any use of db set the default db
    r.db("test").table_create("characters");
    // A simple Insert
    r.db("test").table("characters").insert(Arrays.asList(
				    new HashMap() {{ put("name","Worf");put("show","Star Trek TNG"); }},
				    new HashMap() {{ put("name","Data");put("show","Star Trek TNG"); }},
				    new HashMap() {{ put("name","William Adama");put("show","Battlestar Galactica"); }}, 
				    new HashMap() {{ put("name","Homer Simpson");put("show","The Simpsons"); }}
				));

    // Then a Simple Query
    r.table("tv_shows")
	  .filter(new HashMap() {{ put("name","Star Trek TNG"); }});
    // Returns Array(HashMap( ("name","Worf") , ("show","Star Trek TNG") ),HashMap( ("name","Data") , ("show","Star Trek TNG") ))

# License 
rethinkjava - Java Drivers for RethinkDB

    Copyright (C) 2013  D.K.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this product except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
