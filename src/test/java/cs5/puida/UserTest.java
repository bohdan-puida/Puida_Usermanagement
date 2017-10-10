package cs5.puida;
import junit.framework.TestCase;
import java.util.Calendar;
import java.util.Date;

public class UserTest extends TestCase {

    private User user;
    private Date dateOfBirth;
    private static final int AGE = 19;
    private static final int DAY_OF_BIRTH = 6;
    private static final int YEAR_OF_BIRTH = 1998;
    private static final int IRRELEVANT_DATE = 5;

    public void setUp() throws Exception {
        super.setUp();
        user = new User();
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, Calendar.JUNE, DAY_OF_BIRTH);
        dateOfBirth = calendar.getTime();
    }

    public void testGetFullName() {
        user.setFirstName("John");
        user.setLastName("Doe");
        assertEquals("John, Doe", user.getFullName());
    }

    public void testGetFullNameThrowsException() {
        user.setFirstName("Bogdan");
        try {
            user.getFullName();
            fail("something is undefined, ergo IllegalStateException is expected");
        } catch (IllegalStateException e) {

        }
    }

    public void testGetAge() {
        user.setDateOfBirth(dateOfBirth);
        assertEquals(AGE, user.getAge());
    }

    public void testGetAgeWithIncorrectData() {
        user.setDateOfBirth(dateOfBirth);
        assertFalse(AGE + IRRELEVANT_DATE == user.getAge());
    }
}