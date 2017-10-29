package cs5.puida.db;
import cs5.puida.User;

import java.util.Collection;

public interface UserDao {
    User create(User user) throws DatabaseException;
    User update(User user) throws DatabaseException;
    User delete(User user) throws DatabaseException;
    User find(Long id) throws DatabaseException;
    Collection findAll() throws DatabaseException;
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
