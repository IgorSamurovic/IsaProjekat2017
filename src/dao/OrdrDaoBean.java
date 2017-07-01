package dao;

import entity.Ordr;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(OrdrDao.class)
public class OrdrDaoBean extends GenericDaoBean<Ordr, Integer> implements OrdrDao {
	
	@Override
	public boolean cancel(Integer userId, Integer reservationId) {
		Query q = em.createQuery("SELECT o FROM Ordr o WHERE o.user=:userId AND o.reservation=:reservationId");
		q.setParameter("userId", userId);
		q.setParameter("reservationId", reservationId);
		Ordr o = (Ordr) q.getSingleResult();
		o.setCancelled(true);
		em.merge(o);
		return true;
	}
}
