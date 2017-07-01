package rest;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
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
import dao.ReservationDao;
import dao.ReservationTableDao;
import dao.RestaurantDao;
import dao.UserDao;
import dao.VisitDao;
import entity.Invitation;
import entity.Reservation;
import entity.ReservationTable;
import entity.Restaurant;
import entity.Tabla;
import entity.User;
import entity.Visit;
import utils.Mailer;

@Path("/reservation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationsResource {
	
	@EJB UserDao userDao;
	@EJB RestaurantDao restaurantDao;
	@EJB ReservationDao reservationDao;
	@EJB ReservationTableDao reservationTableDao;
	@EJB InvitationDao invitationDao;
	@EJB VisitDao visitDao;
	
	@GET
	@Path("/frominv")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Reservation fromInv(@QueryParam("invId") Integer invId) {
		Invitation inv = invitationDao.findById(invId);
		return reservationDao.findById(inv.getReservation());
	}
	
	@POST
	@Path("/create")
	public Reservation create(Reservation r) {
		List<Tabla> allTables = restaurantDao.getTables(r);
		for (Long table : r.getTables()) {
			for (Tabla t : allTables) {
				if (t.isUsed() && t.getId() == table) {
					return null;
				}
			}
		}
		
		reservationDao.persist(r);
		
		ReservationTable rt;
		
		for (Long table : r.getTables()) {
			rt = new ReservationTable();
			rt.setReservation(r.getId());
			rt.setTabla(table);
			reservationTableDao.persist(rt);
		}
		
		Visit visit = new Visit();
		visit.setComing(true);
		visit.setReservation(r.getId());
		visit.setUser(r.getUser());
		visitDao.persist(visit);
		
		return r;
	}
	
	@POST
	@Path("/invite")
	public void invite(Reservation r) {
		Mailer m = new Mailer(); 
		for (Long id : r.getInvites()) {
			User u = userDao.findById(id);
			Invitation inv = new Invitation();
			inv.setAccepted(false);
			inv.setUser(u.getId());
			inv.setReservation(r.getId());
			invitationDao.persist(inv);
			
			m.sendFromGMail(u.getEmail(), "You have been invited to a feast!",
				"<html><body>For more information, click <a href='http://localhost:8084/isamrs/invitation.jsp?id=" + inv.getId() + "'>here</a>.</body></html>");
		}
	}

}
