#!/usr/bin/python
import rethinkdb as r
import random
import time 

conn = r.connect("localhost")

for c in range(0,12): 
    values = [] 
    for i in range(0,pow(2,c)):
        rkey = '%0512x' %random.randrange(16**512)
        rvalue = '%0512x' %random.randrange(16**512)
        values.append({rkey : rvalue})
    #end for
    d = '%030x' %random.randrange(16**30)
    t = '%030x' %random.randrange(16**30)
    r.db_create(d).run(conn)
    r.db(d).table_create(t).run(conn)
    start = time.time()
    r.db(d).table(t).insert(values).run(conn)    
    end = time.time()
    print "Inserting " + str(pow(2,c)) + " rows took " + str(end-start) + "s" 
    r.db_drop(d)
#end for



