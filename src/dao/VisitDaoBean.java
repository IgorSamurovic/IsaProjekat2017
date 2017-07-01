package dao;

import entity.Visit;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(VisitDao.class)
public class VisitDaoBean extends GenericDaoBean<Visit, Integer> implements VisitDao {

}
