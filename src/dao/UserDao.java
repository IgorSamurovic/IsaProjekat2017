package dao;

import java.util.List;

import entity.User;
import entity.Visit;

public interface UserDao extends GenericDao<User, Integer> {

	public User findByUsername(String username);
	public User findByEmail(String email);
	public List<User> search(User u, String firstName, String lastName, boolean friends);
	
	public User login(String username, String password);
	public User register(User u);
	public User activate(String activationString);
	
	public List<User> getFriendRequests(User u);
	public List<User> getFriends(User u);
	public List<Visit> getVisits(User u);
	public void addFriend(User u, long friendId);
	public void removeFriend(User u, long friendId);
	
	public void decideFriendRequest(User u, long friendId, boolean accepted);
}
