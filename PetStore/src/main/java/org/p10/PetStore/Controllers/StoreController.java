package org.p10.PetStore.Controllers;

import org.p10.PetStore.Models.InventoryLine;
import org.p10.PetStore.Models.Order;
import org.p10.PetStore.Models.Pojo.OrderPojo;
import org.p10.PetStore.Models.OrderStatus;
import org.p10.PetStore.Repositories.StoreRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1")
public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController() {
        this.storeRepository = new StoreRepository();
    }

    @GET
    @Path("/store/inventory")
    @Produces("text/plain")
    public Response getInventory() {
        List<InventoryLine> inventory = storeRepository.getInventory();
        return Response.ok(inventory).build();
    }

    @GET
    @Path("/store/order/{id}")
    @Produces("text/plain")
    public Response getOrder(@PathParam("id") int orderId) {
        Order order = storeRepository.getOrders(orderId);
        return Response.ok(order).build();
    }

    @POST
    @Path("/store/order")
    @Produces("text/plain")
    public Response placeOrder(OrderPojo orderPojo) {
        Order order = new Order(orderPojo.getId(), orderPojo.getPetId(),
                orderPojo.getQuantity(), orderPojo.getShipDate(),
                OrderStatus.values()[orderPojo.getStatus()], orderPojo.isComplete());
        order = storeRepository.postOrder(order);
        return Response.ok(order).build();
    }

    @DELETE
    @Path("/store/order/{id}")
    @Produces("text/plain")
    public Response deleteOrder(@PathParam("id") int orderId) {
        int affectedRows = storeRepository.deleteOrder(orderId);
        if (affectedRows > 0) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
}
