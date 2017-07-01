package dao;

import entity.Friend;
import entity.FriendRequest;
import entity.Reservation;
import entity.Restaurant;
import entity.User;
import entity.Visit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
@Local(UserDao.class)
public class UserDaoBean extends GenericDaoBean<User, Integer> implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public User login(String username, String password) {
		TypedQuery<User> q = em.createQuery(
				"SELECT u FROM User u WHERE u.username=:username AND u.password=:password", User.class);
		q.setParameter("username", username);
		q.setParameter("password", password);
		List<User> list = q.getResultList();
		if (list.size() == 0)
			return null;
		else
			return list.get(0);
	}

	public void decideFriendRequest(User u, long friendId, boolean accepted) {
		Query q = em.createQuery(
			"DELETE FROM FriendRequest freq WHERE (freq.requester=:requester AND freq.requested=:requested) "
				+ "OR (freq.requested=:requester AND freq.requester=:requested)");
		
		long userId = u.getId();
		q.setParameter("requested", userId);
		q.setParameter("requester", friendId);
		System.out.println(u.getId() + " " + friendId);
		Friend fr1 = new Friend();
		fr1.setUser1(userId);
		fr1.setUser2(friendId);
		Friend fr2 = new Friend();
		fr2.setUser1(friendId);
		fr2.setUser2(userId);
		em.persist(fr1);
		em.persist(fr2);
		q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFriendRequests(User u) {
		Query q = em.createQuery("SELECT req FROM FriendRequest req WHERE req.requested =:id");
		q.setParameter("id", u.getId());
		List<FriendRequest> freqs = q.getResultList();
		List<User> list = new ArrayList<User>();
		for (FriendRequest fr : freqs) {
			list.add(findById(fr.getRequester()));
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getFriends(User u) {
		long userId = u.getId();
		Query q = em.createQuery("SELECT u FROM User u, Friend fr "
							   + "WHERE fr.user1=:userId AND fr.user2=u.id");
		q.setParameter("userId", userId);
		return q.getResultList();
	}
	
	@EJB ReservationDao reservationDao;
	@EJB RestaurantDao restaurantDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Visit> getVisits(User u) {
		long userId = u.getId();
		Query q = em.createQuery("SELECT v FROM User u, Visit v, Reservation r "
							   + "WHERE v.coming=TRUE AND u.id=:userId AND v.user = u.id AND v.reservation = r.id ORDER BY r.timeFrom DESC");
		q.setParameter("userId", userId);
		List<Visit> visits = new ArrayList<Visit>();
		
		visits = q.getResultList();
		for (Visit v : visits) {
			Reservation reservation = reservationDao.findById(v.getReservation());
			Restaurant restaurant = restaurantDao.findById(reservation.getRestaurant());
			Long currentTime = new java.util.Date().getTime();
			Long startingTime = reservation.getTimeFrom().getTime();
			
			v.setDateFrom(reservation.getTimeFrom());
			v.setDateTo(reservation.getTimeTo());
			v.setRestaurantName(restaurant.getName());
			v.setCanCancel(startingTime - currentTime >= 1800000);
		}
		
		return visits;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addFriend(User u, long friendId) {
		long userId = u.getId();
		if (userId == friendId) {
			return;
		}
		Query q = em.createQuery("SELECT freq FROM FriendRequest freq WHERE freq.requester=:userId AND freq.requested=:friendId");
		q.setParameter("userId", userId);
		q.setParameter("friendId", friendId);
		if (q.getResultList().size() < 1) {
			Query q2 = em.createQuery("SELECT freq FROM Friend freq WHERE freq.user1=:userId AND freq.user2=:friendId");
			q2.setParameter("userId", userId);
			q2.setParameter("friendId", friendId);
			if (q2.getResultList().size() < 1) {
				FriendRequest frq = new FriendRequest();
				frq.setRequester(userId);
				frq.setRequested(friendId);
				em.persist(frq);
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void removeFriend(User u, long friendId) {
		long userId = u.getId();
		Query q = em.createQuery(
			"DELETE FROM Friend fr WHERE (fr.user1=:userId AND fr.user2=:friendId) "
								   + "OR (fr.user1=:friendId AND fr.user2=:userId)");
		q.setParameter("userId", userId);
		q.setParameter("friendId", friendId);
		q.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User findByUsername(String username) {
		Query q = em.createQuery("SELECT u FROM User u WHERE u.username=:username");
		q.setParameter("username", username);
		List<User> list = q.getResultList();
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public User findByEmail(String email) {
		Query q = em.createQuery("SELECT u FROM User u WHERE u.email=:email");
		q.setParameter("email", email);
		List<User> list = q.getResultList();
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> search(User u, String firstName, String lastName, boolean friends) {
		if (friends) {
			System.out.println(firstName + " " + lastName + " " + friends);
			Query q = em.createQuery("SELECT u FROM User u, Friend fr "
					+ "WHERE fr.user1=:userId AND fr.user2=u.id "
					+ "AND u.firstName LIKE :firstName AND u.lastName LIKE :lastName");
			q.setParameter("firstName",  "%" +firstName + "%");
			q.setParameter("lastName",  "%" +lastName + "%")  ;
			q.setParameter("userId", u.getId());
			List<User> list = q.getResultList();
			if (list.size() == 0) {
				return null;
			} else {
				return list;
			}
		} else {
			Query q = em.createQuery("SELECT u FROM User u WHERE u.firstName LIKE :firstName AND u.lastName LIKE :lastName");
			q.setParameter("firstName",  "%" +firstName + "%");
			q.setParameter("lastName", "%" +lastName + "%");
			List<User> list = q.getResultList();
			if (list.size() == 0) {
				return null;
			} else {
				return list;
			}
		}
	}
	
	@Override
	public User register(User u) {
		em.persist(u);
		return u;
	}

	@Override
	public User activate(String activationString) {
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.activationString LIKE :activationString", User.class);
		try {
			User u = q.setParameter("activationString", activationString).getSingleResult();

			if (u != null) {
				Query q2 = em.createQuery("UPDATE User u SET u.active=true, u.activationString=null WHERE u.active=FALSE AND u.activationString LIKE :activationString");
				q2.setParameter("activationString", activationString);
				q2.executeUpdate();
				return u;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}
