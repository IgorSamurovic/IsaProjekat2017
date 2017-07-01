package dao;

import entity.Invitation;
import entity.Reservation;
import entity.Restaurant;
import entity.Tabla;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(InvitationDao.class)
public class InvitationDaoBean extends GenericDaoBean<Invitation, Integer> implements InvitationDao {



}
