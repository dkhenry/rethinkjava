package com.dkhenry.RethinkDB;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.dkhenry.RethinkDB.errors.RqlDriverException;
import com.rethinkdb.Ql2.Response;

public class RqlCursor implements Iterable<RqlObject> ,Iterator<RqlObject> {
	
	private RqlConnection _connection;
	private Response _response; 
	private int _index; 
	
	public String toString() { 
		return _response.toString();
	}
	
	public RqlCursor(RqlConnection conn) {
		_connection = conn;
		_index = 0;
	}
	
	public RqlCursor(RqlConnection conn, Response rsp) {
		_connection = conn;
		_response = rsp; 
		_index = 0;
	}
	
	@Override
	public Iterator<RqlObject> iterator() {
		return this; 		
	}

	@Override
	public boolean hasNext() {
		if (_index < _response.getResponseCount()) {
			return true;
		} else if (_response.getType() == Response.ResponseType.SUCCESS_PARTIAL) {
			_response = _connection.get_more(_response.getToken());
			_index = 0;
			return _index < _response.getResponseCount();
		}
		return false;
	}
   
	@Override
	public RqlObject next() {
		if( _index < _response.getResponseCount()) { 
			return new RqlObject(_response.getResponse(_index++));
		} else if( _response.getType() == Response.ResponseType.SUCCESS_PARTIAL){ 			
			try {
				_response = _connection.get_more(_response.getToken());
				_index = 0;
				return next();
			} catch (RqlDriverException e) {
				throw new NoSuchElementException(e.getMessage());
			}			
			
		}
		throw new NoSuchElementException("The RqlCursor has no more elements");		
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Removing rows from a RqlCursor is not currently supported");		
	}

}
