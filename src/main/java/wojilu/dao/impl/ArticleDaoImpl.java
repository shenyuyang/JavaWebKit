package wojilu.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wojilu.dao.ArticleDao;
import wojilu.domain.Article;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shenyuyang
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {

    @PersistenceContext
    private EntityManager db;

    @Override
    public String getName() {
        return "=>name from dao";
    }


    public Article getById(long id) {
        return db.find(Article.class, id);
    }

    public List<Article> getAll() {
        return db
                .createQuery("select x from Article x", Article.class)
                .getResultList();
    }

    @Transactional
    public void insert(Article x) {
        db.persist(x);
    }
}
