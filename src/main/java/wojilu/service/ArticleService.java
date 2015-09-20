package wojilu.service;

import org.springframework.stereotype.Service;
import wojilu.domain.Article;

import java.util.List;

/**
 * Created by shenyuyang
 */
public interface ArticleService {

    String getName();

    Article getById(long id);

    List<Article> getAll();

    void insert(Article x);
}
