package org.p10.PetStore.Repositories;

import org.p10.PetStore.Models.InventoryLine;
import org.p10.PetStore.Models.Order;
import org.p10.PetStore.Models.OrderStatus;
import org.p10.PetStore.Models.PetStatus;
import org.p10.PetStore.Repositories.Interfaces.IStoreRepositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StoreRepository extends Repository implements IStoreRepositories {

    @Override
    public List<InventoryLine> getInventory() {
        openConnection();
        List<InventoryLine> inventoryLineList = new ArrayList<>();
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "select Status, count(Id) from pets.pet " +
                            "where IsDelete = FALSE group by Status"
            );
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InventoryLine inventoryLine = new InventoryLine();
                inventoryLine.setStatus(PetStatus.values()[rs.getInt("Status")]);
                inventoryLine.setCount(rs.getInt("Count"));

                inventoryLineList.add(inventoryLine);
            }
            stmt.close();
            connection.close();

            return inventoryLineList;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    @Override
    public Order getOrders(int orderId) {
        openConnection();
        Order order = null;
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("select Id, Status, PetId, Quantity, " +
                    "ShipDate, Complete " +
                    "from orders.order where IsDelete = FALSE and id = ?");
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                order = getOrderFromResultSet(rs);
            }
            stmt.close();
            connection.close();

            return order;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    @Override
    public Order postOrder(Order order) {
        openConnection();
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "insert into orders.order (id, petid, quantity, shipdate, status, complete, created, createdby) " +
                            "values (?, ?, ?, ?, ?, ?, current_timestamp, 'PetStore.Store.Api');"
            );
            stmt.setInt(1, order.getId());
            stmt.setInt(2, order.getPetId());
            stmt.setInt(3, order.getQuantity());
            stmt.setDate(4, java.sql.Date.valueOf(order.getShipDate().toLocalDate()));
            stmt.setInt(5, order.getStatus().ordinal());
            stmt.setBoolean(6, order.isComplete());
            stmt.executeUpdate();

            stmt.close();
            connection.close();

            return order;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    public Order postOrderGuzzler(Order order) {
        return new Order(1, 1, 1, LocalDateTime.now(), OrderStatus.Placed, false);
    }

    @Override
    public int deleteOrder(int orderId) {
        openConnection();
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM orders.order where id=?"
            );
            stmt.setInt(1, orderId);
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

    public int deleteOrderGuzzler(int orderId) {
        return 1;
    }

    @Override
    public List<Order> getNewestOrders(int limit) {
        openConnection();
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("select Id, Status, PetId, Quantity, " +
                    "ShipDate, Complete, Created " +
                    "from orders.order where IsDelete = FALSE order by Created desc limit ?");
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(getOrderFromResultSet(rs));
            }

            stmt.close();
            connection.close();

            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }

    public List<Order> getNewestOrdersGuzzler(int limit) {
        List<Order> orders = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < limit; i++) {
            orders.add(new Order(counter, counter, 1, LocalDateTime.now(), OrderStatus.Placed, false));
        }
        return orders;
    }

    private Order getOrderFromResultSet(ResultSet rs) {
        try {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setPetId(rs.getInt("petId"));
            order.setQuantity(rs.getInt("quantity"));
            order.setShipDate(rs.getDate("shipDate"));
            order.setStatus(OrderStatus.values()[rs.getInt("status")]);
            order.setComplete(rs.getBoolean("complete"));
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }
}
