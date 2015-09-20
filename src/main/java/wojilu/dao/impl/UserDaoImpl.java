package wojilu.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wojilu.dao.UserDao;
import wojilu.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by shenyuyang
 */
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager db;

    @Override
    public User getById(long id) {
        return db.find(User.class, id);
    }

    @Override
    public User checkByNameAndPwd(String name, String pwd) {
        List<User> list = db.createQuery("from User where Name=:name and Pwd=:pwd")
                .setParameter("name", name)
                .setParameter("pwd", pwd)
                .getResultList();

        if (list.size() == 0) return null;
        return list.get(0);
    }

    @Override
    public User checkByEmailAndPwd(String email, String pwd) {
        List<User> list = db.createQuery("from User where Email=:email and Pwd=:pwd")
                .setParameter("email", email)
                .setParameter("pwd", pwd)
                .getResultList();

        if (list.size() == 0) return null;
        return list.get(0);
    }

    @Override
    @Transactional
    public void insert(User x) {
        db.persist(x);
    }

}
