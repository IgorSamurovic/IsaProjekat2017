package dao;

import entity.Article;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(RestaurantDao.class)
public class RestaurantDaoBean extends GenericDaoBean<Restaurant, Integer> implements RestaurantDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getArticles(Integer restaurantId) {
		Query q = em.createQuery("SELECT a FROM Article a WHERE a.restaurant=:restaurantId ORDER BY a.type");
		q.setParameter("restaurantId", restaurantId);
		System.out.println(restaurantId);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Restaurant> search(String name, String type) {
		Query q = em.createQuery("SELECT r FROM Restaurant r WHERE r.name LIKE :name AND r.type LIKE :type");
		q.setParameter("name", "%" + name + "%");
		q.setParameter("type", "%" + type + "%");
		List<Restaurant> list = q.getResultList();
		if (list.size() == 0) {
			return null;
		} else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tabla> getTables(Reservation r) {
		Query q = em.createQuery("SELECT t FROM Tabla t WHERE t.restaurant=:id");
		q.setParameter("id", r.getRestaurant());
		List<Tabla> allTables = q.getResultList();
		
		Query q2 = em.createQuery("SELECT t FROM Tabla t, ReservationTable rt, Reservation r "
		    + "WHERE rt.tabla = t.id AND rt.reservation = r.id AND r.timeFrom <= :timeTo AND r.timeTo >= :timeFrom");
		q2.setParameter("timeFrom", r.getTimeFrom());
		q2.setParameter("timeTo", r.getTimeTo());
		List<Tabla> allUsedTables = q2.getResultList();
		
		for (Tabla t : allTables) {
			for (Tabla b : allUsedTables) {
				if (t.getId() == b.getId()) {
					t.setUsed(true);
				}
			}
		}
		return allTables;
	}

}
