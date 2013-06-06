package com.dkhenry;

import java.io.IOException;

import com.rethinkdb.Ql2.*;

public class RethinkCommand {
	private RethinkDB _db;
	private Query _query;
	private Long _token; 
	
	public RethinkCommand() {
				
	}
	public RethinkCommand(RethinkDB db) { 
		_db = db;
	}
	
	public RethinkDB get_db() {
		return _db;
	}



	public void set_db(RethinkDB _db) {
		this._db = _db;
	}	
	
	/**
	 * Run this command
	 * @throws IOException 
	 */
	public Response run() throws IOException { 
		_db.send_raw(_query.toByteArray());
		return _db.recv_raw();
	}
	
	public static RethinkCommand db_list(RethinkDB db) { 
		RethinkCommand r = new RethinkCommand(db);
		r._token = db.nextToken(); 
		// Build the Protocol Buffer ( create table )
		Query.Builder q =  com.rethinkdb.Ql2.Query.newBuilder();
		Term.Builder t =  com.rethinkdb.Ql2.Term.newBuilder();
		
		t.setType(Term.TermType.DB_LIST);
		q.setType(Query.QueryType.START);
		q.setToken(r._token); 
		q.setQuery(t.build());
		r._query = q.build();
		return r; 
	}
}
