package cs5.puida.db;

import com.mockobjects.dynamic.Mock;

public abstract class MockDaoFactory extends DaoFactory {
    private Mock mockUserDao;

    public MockDaoFactory() {
        super();
        mockUserDao = new Mock(UserDao.class);


    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public UserDao getUserDao() {
        return  (UserDao) mockUserDao.proxy();
    }

}
