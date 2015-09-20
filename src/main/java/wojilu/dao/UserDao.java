package wojilu.dao;

import wojilu.domain.User;

/**
 * Created by shenyuyang
 */
public interface UserDao {

    User getById(long id);

    User checkByNameAndPwd(String name, String pwd);

    User checkByEmailAndPwd(String email, String pwd);

    void insert(User x);
}
