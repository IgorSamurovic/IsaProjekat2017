package rest;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import dao.UserDao;
import entity.User;
import entity.Visit;
import utils.Mailer;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {
	
	@EJB
	UserDao userDao;
	
	protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 120) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	@GET
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request, @QueryParam("username") String username, @QueryParam("password") String password) {
		System.out.println(username + password);
		HttpSession session = request.getSession(true);
		User u = userDao.login(username, password);
		if (u == null) {
			return null;
		}
		u.setPassword(null);
		session.setAttribute("user", u);
		
		return u;
	}
	
	@GET
	@Path("/freqs")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getFriendRequests(@Context HttpServletRequest request) {
		return userDao.getFriendRequests(User.getLoggedUser(request));
	}
	
	@GET
	@Path("/friends")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getFriends(@Context HttpServletRequest request) {
		return userDao.getFriends(User.getLoggedUser(request));
	}
	
	@GET
	@Path("/visits")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Visit> getVisits(@Context HttpServletRequest request) {
		return userDao.getVisits(User.getLoggedUser(request));
	}
	
	@PUT
	@Path("/freqs")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void decideFriendRequest(@Context HttpServletRequest request, @QueryParam("friendId") long friendId, @QueryParam("accepted") boolean accepted) {
		userDao.decideFriendRequest(User.getLoggedUser(request), friendId, accepted);
	}
	
	@DELETE
	@Path("/friends")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeFriend(@Context HttpServletRequest request, @QueryParam("friendId") long friendId) {
		userDao.removeFriend(User.getLoggedUser(request), friendId);
	}
	
	@POST
	@Path("/friends")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void addFriend(@Context HttpServletRequest request, @QueryParam("friendId") long friendId) {
		userDao.addFriend(User.getLoggedUser(request), friendId);
	}
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User findById(@PathParam("id") long id) {
		User u = userDao.findById(id);
		u.setPassword("");
		return u;
	}
	

	
	@PUT
	@Path("/{id}")
	public void edit(User u) {
		System.out.println("LEDITING");
		userDao.merge(u);
	}
	
	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<User> search(@Context HttpServletRequest request) {
		return userDao.search(User.getLoggedUser(request), request.getParameter("firstName"),
				request.getParameter("lastName"), request.getParameter("searchFriends").equals("true"));
	}
	
	@GET
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void logout(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("user", null);
			session.invalidate();
			System.out.println("session desutroyd");
		}
		try {
			response.sendRedirect("isamrs/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return;
	}
	
	@POST
	@Path("/register")
	public User register(User u) {
		if (userDao.findByEmail(u.getEmail()) == null && userDao.findByUsername(u.getUsername()) == null) {
			u.setActivationString(getRandomString());
			User user = userDao.register(u);
			System.err.println(user);
			new Mailer().sendFromGMail(u.getEmail(), "Welcome to our site!",
				"<html><body>We are very happy that you're here, but you'll have to <a href='" + "http://localhost:8084/isamrs/activation.jsp?activationString=" + u.getActivationString() + "'>activate your account by clicking this link before you log in to our site.</body></html>");
			return user;
		} else {
			return null;
		}
	}
	
	@PUT
	@Path("/activate")
	public User activate(User u) {
		return userDao.activate(u.getActivationString());
	}

}
