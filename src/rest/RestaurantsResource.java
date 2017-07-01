package rest;

import java.io.IOException;
import java.sql.Timestamp;
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

import dao.RestaurantDao;
import dao.UserDao;
import entity.Article;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;
import entity.User;
import utils.Mailer;

@Path("/restaurant")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantsResource {
	
	@EJB
	RestaurantDao restaurantDao;
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant findById(@PathParam("id") long id) {
		Restaurant r = restaurantDao.findById(id);
		return r;
	}
	
	@GET
	@Path("/articles")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Article> getArticles(@QueryParam("id") Integer restaurantId) {
		return restaurantDao.getArticles(restaurantId);
	}
	
	@GET
	@Path("/tables")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Tabla> getTables(@QueryParam("user") Integer user, @QueryParam("restaurant") long restaurant,
		@QueryParam("timeFrom") Timestamp timeFrom, @QueryParam("timeTo") Timestamp timeTo) {
		
		Reservation r = new Reservation();
		r.setUser(user);
		r.setRestaurant(restaurant);
		r.setTimeFrom(timeFrom);
		r.setTimeTo(timeTo);
		
		List<Tabla> t = restaurantDao.getTables(r);
		return t;
	}
	
	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<Restaurant> search(@Context HttpServletRequest request) {
		return restaurantDao.search(request.getParameter("name"),
				request.getParameter("type"));
	}

}
