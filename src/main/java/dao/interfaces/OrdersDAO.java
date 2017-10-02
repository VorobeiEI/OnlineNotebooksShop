package dao.interfaces;


import entity.order.Order;
import entity.order.Status;
import entity.users.User;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jacksparrow on 20.09.17.
 */
public interface OrdersDAO {
    public void createOrder(Order order);

    public ArrayList<Order> getAllOrders();

    public ArrayList<Order> getAllUsersOrders(int user_id);

    public void changeStatus(int order_id);

    public void orderComlete(int order_id, Status status);

    public Order getOrderById(int order_id);

    public ArrayList<Integer> getAllProductFromOrder(int order_id);

}
