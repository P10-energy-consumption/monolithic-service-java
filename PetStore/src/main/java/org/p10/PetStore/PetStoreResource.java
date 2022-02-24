package org.p10.PetStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Repositories.PetRepository;

@Path("/v1")
public class PetStoreResource {

    private final PetRepository petRepository;

    public PetStoreResource() {
        this.petRepository = new PetRepository();
    }

    @Path("/hello-world")
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/pet/{id}")
    @Produces("text/plain")
    public String pet(@PathParam("id") Integer petId) {
        Pet pet = petRepository.getPet(petId);
        System.out.println("Got: " + pet);

        return "Hello, World!";
    }
}