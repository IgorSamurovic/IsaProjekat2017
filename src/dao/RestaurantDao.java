package dao;

import java.util.List;

import entity.Article;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;

public interface RestaurantDao extends GenericDao<Restaurant, Integer> {

	public List<Restaurant> search(String name, String type);
	public List<Tabla> getTables(Reservation r);
	public List<Article> getArticles(Integer id);
}
