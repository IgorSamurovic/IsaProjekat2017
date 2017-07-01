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

import dao.ArticleOrderDao;
import dao.InvitationDao;
import dao.RestaurantDao;
import dao.UserDao;
import dao.VisitDao;
import dao.OrdrDao;
import dao.ReservationDao;
import entity.ArticleOrder;
import entity.Invitation;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;
import entity.User;
import entity.Visit;
import entity.Ordr;
import utils.Mailer;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersResource {
	
	@EJB
	OrdrDao orderDao;
	
	@EJB
	RestaurantDao restaurantDao;
	
	@EJB
	ReservationDao reservationDao;
	
	@EJB
	ArticleOrderDao articleOrderDao;
	
	@EJB
	VisitDao visitDao;
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Ordr findById(@PathParam("id") long id) {
		Ordr r = orderDao.findById(id);
		return r;
	}
	
	@GET
	@Path("/restaurant")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant restaurant(@QueryParam("id") long id) {
		return restaurantDao.findById(
			   reservationDao.findById(
			   orderDao.findById(id).getReservation()).getRestaurant());
	}
	
	@POST
	@Path("/create")
	public Ordr create(Ordr order) {
		orderDao.persist(order);
		return order;
	}
	
	@POST
	@Path("/setarticles")
	public ArticleOrder[] setarticles(ArticleOrder[] aos) {
		for (ArticleOrder ao : aos) {
			System.out.println(ao);
			articleOrderDao.persist(ao);
		}
		return aos;
	}
	
	@PUT
	@Path("/{id}")
	public void update(Ordr order) {
		orderDao.merge(order);
	}
}
