package cs5.puida.db;

import cs5.puida.User;
import cs5.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MockUserDao implements UserDao{
    private long id = 0;
    private Map<Long,User> users = new HashMap<>();
    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }
    public Collection find(String firstName, String lastName) throws DatabaseException {
        Collection<User> foundUsers = new LinkedList<>();
        for (Map.Entry<Long, User> user : users.entrySet()) {
            if (user.getValue().getFirstName().equals(firstName) && user.getValue().getLastName().equals(lastName)) {
                foundUsers.add(user.getValue());
            }
        }
        return foundUsers;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
