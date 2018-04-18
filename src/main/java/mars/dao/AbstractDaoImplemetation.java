package mars.dao;

import java.util.List;
import java.util.Set;

import mars.entity.Item;

public abstract class AbstractDaoImplemetation implements AbstractDao {

	@Override
	public void close() {
		
	}

	@Override
	public void setFavoriteItems(String userId, List<String> itemIds) {
		
	}

	@Override
	public void unsetFavoriteItems(String userId, List<String> itemIds) {
		
	}

	@Override
	public Set<String> getFavoriteItemIds(String userId) {
		return null;
	}

	@Override
	public Set<Item> getFavoriteItems(String userId) {
		return null;
	}

	@Override
	public Set<String> getCategories(String itemId) {
		return null;
	}

	@Override
	public List<Item> searchItems(String userId, double lat, double lon, String term) {
		return null;
	}

	@Override
	public void saveItem(Item item) {
	}

	@Override
	public String getFullname(String userId) {
		return null;
	}

	@Override
	public boolean verifyLogin(String userId, String password) {
		return false;
	}
}