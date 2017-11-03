package cs5.puida.db;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DaoFactory {
    protected static final String USER_DAO = "user.dao";
    private static String DAO_FACTORY = "dao.factory";
    protected static Properties properties;
    private static DaoFactory instance;

    static {
        InputStream inputStream;
        properties = new Properties();
        try {
            inputStream = new FileInputStream("src/main/resources/settings.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Class factoryClass = null;
            try {
                factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = (DaoFactory) factoryClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    protected DaoFactory() {

    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }

    public static void init(Properties prop) {
        properties = prop;
        instance = null;
    }

    public abstract UserDao getUserDAO();



}