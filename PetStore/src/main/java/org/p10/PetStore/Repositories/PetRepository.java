package org.p10.PetStore.Repositories;

import org.p10.PetStore.Database.ConnectionFactory;
import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Repositories.Interfaces.IPetRepositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PetRepository implements IPetRepositories {

    private final Connection connection;

    public PetRepository() {
        connection = new ConnectionFactory().createDBConnection();
    }

    @Override
    public Pet getPet(int petId) {
        Pet pet = null;
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("SELECT * FROM pets.pet WHERE id=?");
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setCategory(rs.getInt("category"));
                pet.setName(rs.getString("name"));
                pet.setPhotoUrls(null);
                pet.setTags(rs.getString("tags"));
                pet.setStatus(rs.getInt("status"));
            }
            stmt.close();
            connection.close();

            return pet;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }

        return pet;
    }
}
