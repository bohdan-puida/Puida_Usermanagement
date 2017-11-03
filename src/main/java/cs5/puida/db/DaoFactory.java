package cs5.puida.db;
import java.io.IOException;
import java.util.Properties;


public abstract class DaoFactory {
    protected static final String USER_DAO = "cs5.puida.db.UserDao";
    protected static Properties properties;
    private static final String DAO_FACTORY = "dao.factory";
    private static DaoFactory INSTANCE;

    protected DaoFactory() {
    }

    static {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static synchronized DaoFactory getInstance() {
        if (INSTANCE == null) {
            try {
                Class<?> factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                INSTANCE = (DaoFactory) factoryClass.newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return INSTANCE;
    }

    public static void init(Properties properties) {
        DaoFactory.properties = properties;
        INSTANCE = null;
    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }

    public abstract UserDao getUserDao();
}