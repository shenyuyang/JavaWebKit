package wojilu.dao;

import org.springframework.stereotype.Repository;
import wojilu.domain.Article;

import java.util.List;

/**
 * Created by shenyuyang
 */

public interface ArticleDao {


    String getName();

    Article getById(long id);

    List<Article> getAll();

    void insert(Article x);

}
