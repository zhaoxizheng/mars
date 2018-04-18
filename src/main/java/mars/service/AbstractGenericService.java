package mars.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public abstract class AbstractGenericService<T> implements GenericService<T> {
	
	@Override
	public List<T> search(double latitude, double longitude, String term) {
		throw new UnsupportedOperationException("search()");
	}
}

