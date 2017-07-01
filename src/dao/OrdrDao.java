package dao;
import entity.Ordr;
public interface OrdrDao extends GenericDao<Ordr, Integer> {
	public boolean cancel(Integer userId, Integer reservationId);
}
