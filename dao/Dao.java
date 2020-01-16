package idv.peter.publishers.dao;

import java.util.List;


public interface Dao<T> {
	
	List<T> getAll();
	
	
	public int update(T obj);
	
	public int delete(T obj);
	
}
