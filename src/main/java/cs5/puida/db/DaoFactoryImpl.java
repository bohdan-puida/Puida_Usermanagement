package cs5.puida.db;
public class DaoFactoryImpl extends DaoFactory {


    @Override
    public UserDao getUserDao() {
        UserDao userDao;
        try {
            Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
            userDao = (UserDao) clazz.newInstance();
            ConnectionFactory factory = getConnectionFactory();
            userDao.setConnectionFactory(factory);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userDao;


    }

}
