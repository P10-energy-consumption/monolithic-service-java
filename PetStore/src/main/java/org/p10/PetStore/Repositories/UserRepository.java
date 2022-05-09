package org.p10.PetStore.Repositories;

import org.p10.PetStore.Models.User;
import org.p10.PetStore.Models.UserStatus;
import org.p10.PetStore.Repositories.Interfaces.IUserRepositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends Repository implements IUserRepositories {

    @Override
    public int insertUser(User user) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into users.user (id, username, firstname, lastname, email, passwordhash, salt, phone, status, created, createdby) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp, 'PetStore.User.Api');"
            );
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUserName());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPasswordHash());
            stmt.setString(7, user.getSalt());
            stmt.setString(8, user.getPhone());
            stmt.setInt(9, user.getStatus().ordinal());
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    public int insertUserGuzzler(User user) {
        return user.getId();
    }

    @Override
    public User getUser(String userName) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement("select u.id, u.username, u.status, u.firstname, u.lastname, " +
                    "u.email, u.phone, u.PasswordHash, u.salt " +
                    "from users.user u where u.UserName = ?");
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            User user = null;
            while (rs.next()) {
                user = getUserFromResultSet(rs);
            }
            stmt.close();
            connection.close();

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "update users.user set FirstName = ?, LastName = ?, " +
                            "Email = ?, PasswordHash = ?, " +
                            "Salt = ?, Phone = ?, " +
                            "Status = ?, Modified = current_timestamp, " +
                            "ModifiedBy = 'PetStore.User.Api'  where UserName = ?"
            );
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPasswordHash());
            stmt.setString(5, user.getSalt());
            stmt.setString(6, user.getPhone());
            stmt.setInt(7, user.getStatus().ordinal());
            stmt.setString(8, user.getUserName());
            stmt.executeUpdate();

            stmt.close();
            connection.close();

            return user;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    @Override
    public String deleteUser(String userName) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "delete from users.user where username = ?"
            );
            stmt.setString(1, userName);
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            if (affectedRows > 0) {
                return userName;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    public String deleteUserGuzzler(String userName) {
        return userName;
    }

    @Override
    public List<User> getNewestUsers(int limit) {
        try (Connection connection = openConnection()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "select u.id, u.username, u.status, u.firstname, u.lastname, " +
                            "u.email, u.phone, u.PasswordHash, u.salt, u.created " +
                            "from users.user u order by u.created desc limit ?"
            );
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(getUserFromResultSet(rs));
            }

            stmt.close();
            connection.close();

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    public List<User> getNewestUsersGuzzler(int limit) {
        List<User> users = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < limit; i++) {
            users.add(new User(counter, String.valueOf(counter), "test", "string", "string", "string", "string", "string", UserStatus.Active));
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet rs) {
        try {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("username"));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            user.setEmail(rs.getString("email"));
            user.setPasswordHash(rs.getString("passwordHash"));
            user.setSalt(rs.getString("salt"));
            user.setPhone(rs.getString("phone"));
            user.setStatus(UserStatus.values()[rs.getInt("status")]);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }
}
