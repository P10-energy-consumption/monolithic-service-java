package org.p10.PetStore.Models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Order {
    private int id;
    private int petId;
    private int quantity;
    private LocalDateTime shipDate;
    private OrderStatus status;
    private boolean complete;

    public Order() {
    }

    public Order(int id, int petId, int quantity, LocalDateTime shipDate, OrderStatus status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public void setShipDate(Date date) {
        // Time is not stored in database.
        // As such, we just store it as the start of the day in the object return from the database.
        this.shipDate = date.toLocalDate().atStartOfDay();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
