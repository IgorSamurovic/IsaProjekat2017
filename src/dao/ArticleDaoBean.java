package dao;

import entity.Article;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(ArticleDao.class)
public class ArticleDaoBean extends GenericDaoBean<Article, Integer> implements ArticleDao {

}
