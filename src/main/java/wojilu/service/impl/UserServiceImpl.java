package wojilu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wojilu.dao.UserDao;
import wojilu.domain.User;
import wojilu.service.UserService;

/**
 * Created by shenyuyang
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    public User checkByNameAndPwd(String name, String pwd) {
        return userDao.checkByNameAndPwd(name,pwd);
    }

    @Override
    public User checkByEmailAndPwd(String email, String pwd) {
        return userDao.checkByEmailAndPwd(email,pwd);
    }

    @Override
    @Transactional
    public void insert(User x) {
        userDao.insert(x);
    }
}
