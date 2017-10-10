package cs5.puida;

import java.util.Calendar;
import java.util.Date;


public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    public String getFullName() throws IllegalStateException {
        if (getFirstName() == null || getLastName() == null) {
            throw new IllegalStateException("firstName or lastName is null");
        }
        return getFirstName()+ ", " + getLastName();
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance();


        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(getDateOfBirth());
        int yearOfBirth = calendar.get(Calendar.YEAR);
        int birthDay = calendar.get(Calendar.DAY_OF_YEAR);


        int age = currentYear - yearOfBirth;

        return currentDayOfYear < birthDay ?  --age : age;
    }

    }

