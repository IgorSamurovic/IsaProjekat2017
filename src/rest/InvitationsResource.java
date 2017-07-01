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
import dao.RestaurantDao;
import dao.UserDao;
import dao.InvitationDao;
import entity.Invitation;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;
import entity.User;
import entity.Invitation;
import utils.Mailer;

@Path("/invitation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvitationsResource {
	
	@EJB
	InvitationDao invitationDao;
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Invitation findById(@PathParam("id") long id) {
		Invitation r = invitationDao.findById(id);
		return r;
	}
	
	@POST
	@Path("/create")
	public Invitation create(Invitation invitation) {
		invitationDao.persist(invitation);
		return invitation;
	}
	
	@PUT
	@Path("/{id}")
	public void update(Invitation invitation) {
		invitationDao.merge(invitation);
	}
}
