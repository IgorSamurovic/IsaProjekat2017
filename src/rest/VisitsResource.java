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

import dao.InvitationDao;
import dao.OrdrDao;
import dao.ReservationDao;
import dao.RestaurantDao;
import dao.UserDao;
import dao.VisitDao;
import entity.Invitation;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;
import entity.User;
import entity.Visit;
import utils.Mailer;

@Path("/visit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VisitsResource {
	
	@EJB VisitDao visitDao;
	@EJB ReservationDao reservationDao;
	@EJB OrdrDao ordrDao;
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Visit findById(@PathParam("id") long id) {
		Visit r = visitDao.findById(id);
		return r;
	}
	
	@POST
	@Path("/create")
	public Visit create(Visit visit) {
		visitDao.persist(visit);
		return visit;
	}
	
	@PUT
	@Path("/{id}")
	public boolean update(Visit visit) {
		Long currentTime = new java.util.Date().getTime();
		Long startingTime = reservationDao.findById(visit.getReservation()).getTimeFrom().getTime();
		if (startingTime - currentTime >= 1800000) {
			visit.setComing(false);
			visitDao.merge(visit);
			ordrDao.cancel(visit.getUser(), visit.getReservation());
			return true;
		} else {
			return false;
		}
	}
	
}
