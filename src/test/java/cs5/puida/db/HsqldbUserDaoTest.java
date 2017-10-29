package cs5.puida.db;

import java.util.Calendar;
import java.util.Date;
import cs5.puida.User;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private HsqldbUserDao dao;
    private ConnectionFactory connectionFactory;

    protected void setUp() throws Exception {
        super.setUp();
        dao = new HsqldbUserDao(connectionFactory);

    }


    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setDateOfBirth(new Date(1907,5,3));
            assertNull(user.getId());
            user = dao.create(user);
            assertNotNull(user);
            assertNotNull(user.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }


    }
    public void testFindAll() throws Exception {
        assertNotNull(dao.findAll());
        int AMOUNT_OF_ENTRIES_IN_DB = 2;
        assertEquals("Collection size: ", AMOUNT_OF_ENTRIES_IN_DB, dao.findAll().size());
    }
    public void testUpdate() throws Exception {
        try {
            User temporaryUser = dao.find(1000L);
            assertNotNull(temporaryUser);
            temporaryUser.setFirstName("John");
            dao.update(temporaryUser);
            User updatedUser = dao.find(1000L);
            assertEquals(temporaryUser.getFirstName(), updatedUser.getFirstName());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    public void testDelete() throws Exception {
        try {
            User userToCheck = dao.find(1001L);
            assertNotNull(userToCheck);
            dao.delete(userToCheck);
            userToCheck = dao.find(1001L);
            assertEquals(null, userToCheck);
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }


    public void testFind() throws Exception {
        User bush = new User();
        bush.setId(1001L);
        bush.setFirstName("George");
        bush.setLastName("Bush");
        Calendar calendar = Calendar.getInstance();
        calendar.set(1949, Calendar.AUGUST, 17, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        bush.setDateOfBirth(calendar.getTime());

        User result = dao.find(1001L);

        assertEquals("Wrong first name",bush.getFirstName(),result.getFirstName());
        assertEquals("Wrong last name",bush.getLastName(),result.getLastName());
        assertEquals("Wrong id",bush.getId(),result.getId());
        assertEquals("Wrong date of birth",bush.getDateOfBirth(),result.getDateOfBirth());
    }



    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory=new ConnectionFactoryImpl(
                "org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:file:db/usermanagement",
                "sa",
                "");
        return new DatabaseConnection(connectionFactory.createConnection()) ;
    }


    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass()
                .getClassLoader()
                .getResourceAsStream("UsersDataSet.xml"));
    }



}
