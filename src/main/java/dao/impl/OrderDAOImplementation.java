package dao.impl;

import dao.connectionPool.ConnectionPool;
import dao.exception.DatabaseException;
import dao.interfaces.OrdersDAO;
import entity.order.Order;
import entity.order.Status;
import entity.users.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by jacksparrow on 26.09.17.
 */
public class OrderDAOImplementation implements OrdersDAO {

    public static final String SQL_CREATE_ODER = "INSERT INTO orders (order_amount, id_user, status, date) values (?, ?, ?, ?)";
    public static final String SQL_CREATE_ODER_PRODUCT = "INSERT INTO items_orders (id_order, id_item) values (?, ?)";
    public static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders GROUP BY status, id";
    public static final String SQL_SELECT_USER_ORDERS = "SELECT * FROM orders WHERE id_user = ? GROUP BY status, id";
    public static final String SQL_UPDATE_STATUS = "UPDATE orders SET status=? WHERE id=?";
    public static final String SQL_SELECT_ORDER_BY_ID = "SELECT id, order_amount, status, date FROM orders WHERE id = ?";
    public static final String SQL_SELECT_PRODUCT_FROM_ORDER = "SELECT id_item FROM items_orders WHERE id_order = ?";

    private static final Logger logger = Logger.getLogger(OrderDAOImplementation.class);

    public static final class OrderNotFoundEException extends RuntimeException{
        public OrderNotFoundEException(final int orderId){
            super("Order with id: " + orderId + " hasn't been found");
        }
    }
    @Override
    public void createOrder(Order order) {
        Connection conn = null;
        PreparedStatement psCreateIrder = null;
        PreparedStatement psCreateProductForOrder = null;

        try{
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            psCreateIrder = conn.prepareStatement(SQL_CREATE_ODER, Statement.RETURN_GENERATED_KEYS);

            psCreateIrder.setDouble(1, order.getSum());
            psCreateIrder.setInt(2,order.getUserId());
            psCreateIrder.setString(3, order.getStatus().toString());
            psCreateIrder.setDate(4,new Date(System.currentTimeMillis()));
            psCreateIrder.execute();
            ResultSet rs = psCreateIrder.getGeneratedKeys();

            int idOrder = 0;
            if(rs.next()){
                idOrder = rs.getInt(1);
            }

            psCreateProductForOrder = conn.prepareCall(SQL_CREATE_ODER_PRODUCT);
            for(Integer productId : order.getProdacts()){
                psCreateProductForOrder.setInt(1,idOrder);
                psCreateProductForOrder.setInt(2,productId);
                psCreateProductForOrder.execute();
            }

            conn.commit();


        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }

    }

    @Override
    public ArrayList<Order> getAllOrders() {
        Connection conn = null;
        PreparedStatement ps = null;

        ArrayList<Order> orderList = new ArrayList<>();
        try{
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL_ORDERS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setSum(rs.getDouble(2));
                order.setUserId(rs.getInt(3));
                order.setStatus(Status.valueOf(rs.getString(4)));
                order.setDate(rs.getDate(5));
                orderList.add(order);
            }


        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return orderList;
    }

    @Override
    public ArrayList<Order> getAllUsersOrders(int user_id) {
        Connection conn = null;
        PreparedStatement ps = null;

        ArrayList<Order> orderList = new ArrayList<>();
        try{
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_USER_ORDERS);
            ps.setInt(1,user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setSum(rs.getDouble(2));
                order.setUserId(rs.getInt(3));
                order.setStatus(Status.valueOf(rs.getString(4)));
                order.setDate(rs.getDate(5));
                orderList.add(order);
            }

        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return orderList;
    }

    @Override
    public void changeStatus(int order_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        Status status = null;

    }

    @Override
    public void orderComlete(int order_id, Status status) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_STATUS);
            ps.setString(1,status.name());
            ps.setInt(2,order_id);
            ps.execute();
            if(status.equals("CANCELED")||status.equals("CREATED")){
                logger.info("Order with id: " + order_id + " uncompleted");
            }else{
                logger.info("Order with id: " + order_id + " completed");
            }
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }

    }

    @Override
    public Order getOrderById(int order_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        Order order = null;
        try{
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ORDER_BY_ID);
            ps.setInt(1,order_id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                order = new Order();
                order.setId(rs.getInt(1));
                order.setSum(rs.getDouble(2));
                order.setUserId(rs.getInt(3));
                order.setStatus(Status.valueOf(rs.getString(4)));
                order.setDate(rs.getDate(5));

            }else {
                throw new OrderNotFoundEException(order_id);
            }

        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return order;
    }

    @Override
    public ArrayList<Integer> getAllProductFromOrder(int order_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ArrayList<Integer> listIdProduct = new ArrayList<>();
        try {
            conn = ConnectionPool.getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PRODUCT_FROM_ORDER);
            ps.setInt(1,order_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                listIdProduct.add(rs.getInt(2));
            }

        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return listIdProduct;
    }
}
