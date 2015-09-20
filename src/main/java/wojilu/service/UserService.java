package wojilu.service;

import wojilu.domain.User;

/**
 * Created by shenyuyang
 */
public interface UserService {

    User getById(long id);

    User checkByNameAndPwd(String name, String pwd);

    User checkByEmailAndPwd(String email, String pwd);

    void insert(User x);
}
