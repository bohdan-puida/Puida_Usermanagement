package cs5.puida.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.sql.Date;

import cs5.puida.User;


 class HsqldbUserDao implements UserDao {

    private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users(firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private ConnectionFactory connectionFactory;
    private static final String FIND_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id=?";
    private static final String UPDATE_QUERY = "UPDATE users SET firstname=?, lastname=?, dateofbirth=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";

    public HsqldbUserDao() {
        //super();
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }


    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public User create(User user) throws DatabaseException {
        try {Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2,user.getLastName() );
            statement.setDate(3,new Date(user.getDateOfBirth().getTime()) );
            int n = statement.executeUpdate();
            if(n!=1){
                throw new DatabaseException("Number of the inserted rows: "+ n);

            }
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            if(keys.next()){
                user.setId(keys.getLong(1));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return user;
        } catch(DatabaseException e){
            throw e;
        }catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }


    public User update(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection
                    .prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the updated rows: " + n);
            }
            statement.close();
            connection.close();
            return user;
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    public User delete(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection
                    .prepareStatement(DELETE_QUERY);
            statement.setLong(1, user.getId());
            int n = statement.executeUpdate();
            if (n != 1) {
                throw new DatabaseException("Number of the deleted rows: " + n);
            }
            statement.close();
            connection.close();
            return user;
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public User find(Long id) throws DatabaseException {
        User result = null;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DatabaseException("Could not find the user with id="
                        + id);
            }
            result = new User();
            result.setId(resultSet.getLong(1));
            result.setFirstName(resultSet.getString(2));
            result.setLastName(resultSet.getString(3));
            result.setDateOfBirth(resultSet.getDate(4));
            resultSet.close();
            statement.close();
            connection.close();
        } catch (DatabaseException e) {
            throw e;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    public Collection findAll() throws DatabaseException {
        Collection<User> result = new  LinkedList<>();
        try {
            Connection connection = connectionFactory.createConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setDateOfBirth(resultSet.getDate(4));
                result.add(user);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch(DatabaseException e){
            throw e;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }






}