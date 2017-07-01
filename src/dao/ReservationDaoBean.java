package dao;

import entity.Reservation;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(ReservationDao.class)
public class ReservationDaoBean extends GenericDaoBean<Reservation, Integer> implements ReservationDao {
}
