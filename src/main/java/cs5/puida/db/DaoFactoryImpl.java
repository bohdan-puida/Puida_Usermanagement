package cs5.puida.db;

public class DaoFactoryImpl extends DaoFactory {


        @Override
        public UserDao getUserDAO() {
            UserDao result = null;
            try {
                Class clazz = Class.forName(properties.getProperty(USER_DAO));
                HsqldbUserDao userDao = (HsqldbUserDao) clazz.newInstance();
                userDao.setConnectionFactory(getConnectionFactory());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return result;
        }

}
