package dao;

import entity.Reservation;
import entity.ReservationTable;
import entity.Restaurant;
import entity.Tabla;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(ReservationTableDao.class)
public class ReservationTableDaoBean extends GenericDaoBean<ReservationTable, Integer> implements ReservationTableDao {



}
