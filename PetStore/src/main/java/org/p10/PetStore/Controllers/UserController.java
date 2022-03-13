package org.p10.PetStore.Controllers;

import org.p10.PetStore.Models.User;
import org.p10.PetStore.Models.Pojo.UserPojo;
import org.p10.PetStore.Models.UserStatus;
import org.p10.PetStore.Repositories.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/v1")
public class UserController {

    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    @POST
    @Path("/user")
    @Produces("text/plain")
    public Response insertUser(UserPojo userPojo) {
        User user = new User(userPojo.getId(), userPojo.getUserName(),
                userPojo.getFirstName(), userPojo.getLastName(),
                userPojo.getEmail(), userPojo.getPasswordHash(),
                userPojo.getSalt(), userPojo.getPhone(),
                UserStatus.values()[userPojo.getStatus()]);
        int affectedRows = userRepository.insertUser(user);
        if (affectedRows > 0) {
            return Response.ok(affectedRows).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/user")
    @Produces("text/plain")
    public Response updateUser(UserPojo userPojo) {
        User user = new User(userPojo.getId(), userPojo.getUserName(),
                userPojo.getFirstName(), userPojo.getLastName(),
                userPojo.getEmail(), userPojo.getPasswordHash(),
                userPojo.getSalt(), userPojo.getPhone(),
                UserStatus.values()[userPojo.getStatus()]);
        user = userRepository.updateUser(user);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/user/{username}")
    @Produces("text/plain")
    public Response deleteUser(@PathParam("username") String username) {
        String name = userRepository.deleteUser(username);
        if (name != null) {
            return Response.ok(name).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/user/{username}")
    @Produces("text/plain")
    public Response getUser(@PathParam("username") String username) {
        User user = userRepository.getUser(username);
        return Response.ok(user).build();
    }
}
