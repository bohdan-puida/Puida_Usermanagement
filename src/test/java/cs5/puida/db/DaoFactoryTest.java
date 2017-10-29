package cs5.puida.db;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetUserDao() {
        try {
            DaoFactory daoFactory = DaoFactory.getInstance();
            assertNotNull("DaoFactory instance is null", daoFactory);
            UserDao userDao = daoFactory.getUserDao();
            assertNotNull("userDao instance is null", userDao);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

}
