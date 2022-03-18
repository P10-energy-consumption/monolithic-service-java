package org.p10.PetStore.Repositories;

import org.p10.PetStore.Database.ConnectionFactory;

import java.sql.Connection;

public class Repository {
    public Connection connection;

    public void openConnection() {
        connection = new ConnectionFactory().createDBConnection();
    }
}
