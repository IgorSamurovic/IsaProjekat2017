package dao;

import entity.ArticleOrder;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(ArticleOrderDao.class)
public class ArticleOrderDaoBean extends GenericDaoBean<ArticleOrder, Integer> implements ArticleOrderDao {

}
