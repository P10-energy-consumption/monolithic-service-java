package org.p10.PetStore.Models;

public class InventoryLine {
    private PetStatus status;
    private int count;

    public InventoryLine() {
    }

    public InventoryLine(PetStatus status, int count) {
        this.status = status;
        this.count = count;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
