package org.p10.PetStore.Database.Interfaces;

import java.sql.Connection;

public interface IConnectionFactory {
    public Connection createDBConnection();
}
