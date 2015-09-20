package wojilu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wojilu.dao.ArticleDao;
import wojilu.domain.Article;
import wojilu.service.ArticleService;

import java.util.List;

/**
 * Created by shenyuyang
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public String getName() {
        return "test service name " + articleDao.getName();
    }

    @Override
    public Article getById(long id) {
        return articleDao.getById(id);
    }

    @Override
    public List<Article> getAll() {
        return articleDao.getAll();
    }

    @Transactional
    @Override
    public void insert(Article x) {
        articleDao.insert(x);
    }
}
