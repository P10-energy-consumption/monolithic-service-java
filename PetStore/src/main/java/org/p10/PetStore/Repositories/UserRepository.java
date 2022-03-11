package org.p10.PetStore.Repositories;

import org.p10.PetStore.Database.ConnectionFactory;
import org.p10.PetStore.Models.User;
import org.p10.PetStore.Models.UserStatus;
import org.p10.PetStore.Repositories.Interfaces.IUserRepositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepositories {

    private final Connection connection;

    public UserRepository() {
        connection = new ConnectionFactory().createDBConnection();
    }

    @Override
    public int insertUser(User user) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
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

    @Override
    public User getUser(String userName) {
        User user = null;
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("select u.id, u.username, u.status, u.firstname, u.lastname, " +
                    "u.email, u.phone, u.PasswordHash, u.salt " +
                    "from users.user u where u.UserName = ?");
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("passwordHash"));
                user.setSalt(rs.getString("salt"));
                user.setPhone(rs.getString("phone"));
                user.setStatus(UserStatus.values()[rs.getInt("status")]);
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
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
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
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "delete from users.user where username = ?"
            );
            stmt.setString(1, userName);
            stmt.executeUpdate();

            stmt.close();
            connection.close();

            return userName;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }
}
