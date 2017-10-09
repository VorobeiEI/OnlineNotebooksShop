package dao.impl;

import dao.connectionPool.ConnectionPool;
import dao.exception.DatabaseException;
import dao.interfaces.ProductsDAO;
import entity.Product.Good;
import entity.Product.GoodList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * Created by jacksparrow on 26.09.17.
 */
public class    GoodDaoImplementation implements ProductsDAO {
    public static final String SQL_INSERT_OR_UPDATE = "INSERT INTO products (id,name, description, price, cpu,ram, memory, producer_id,qantity) VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE qantity=qantity+?;";
    public static final String SQL_CREATE_PRODUCT = "INSERT INTO products (name, description, price, cpu," +
            "ram, memory,producer_id, qantity) values (?,?, ?, ?, ?, ?, ?,?)";
    public static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM products INNER JOIN producers ON products.producer_id = id_producer";

    public static final String SQL_SELECT_PRODUCT_BY_Producer = "SELECT * FROM products WHERE producer_id = ?";

    public static final String SQL_UPDATE_QUANTITY = "UPDATE products SET qantity=? WHERE id=?";

    public static final String SQL_SELECT_PRODUCER_ID = "SELECT id_producer FROM producers WHERE name = ?";

    public static final String SQL_SELECT_PRODUCT = "SELECT * FROM products WHERE name = ? OR description = ? OR producer_id = ?";

    public static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT id,name, description, price, cpu,ram, memory, producer_id,qantity FROM products WHERE id = ?";

    private static final Logger logger = Logger.getLogger(GoodDaoImplementation.class);

    public static final class GoodNotFoundException extends RuntimeException{
        public GoodNotFoundException(final Integer productId){
            super("Product with id: " + productId + " hasn't been found");
        }
    }
    @Override
    public void insertOrUpdateProduct(Good good) {

        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = getConnection();
            ps = conn.prepareStatement(SQL_INSERT_OR_UPDATE);

            ps.setInt(1, good.getId());
            ps.setString(2, good.getName());
            ps.setString(3,good.getDescription());
            ps.setDouble(4,good.getPrice() );
            ps.setDouble(5,good.getCpu());
            ps.setInt(6, good.getRam());
            ps.setDouble(7, good.getMemory());
            ps.setInt(8, good.getProducerId());
            ps.setInt(9, good.getQuantity());
            ps.setInt(10,good.getQuantity());
            ps.execute();

        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }

    }

    @Override
    public void createProduct(Good good) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = getConnection();
            ps = conn.prepareStatement(SQL_CREATE_PRODUCT);
            ps.setString(1, good.getName());
            ps.setString(2,good.getDescription());
            ps.setDouble(3,good.getPrice() );
            ps.setDouble(4,good.getCpu());
            ps.setInt(5, good.getRam());
            ps.setDouble(6, good.getMemory());
            ps.setInt(7, good.getProducerId());
            ps.setInt(8, good.getQuantity());
            ps.execute();
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public GoodList getAllPRoducts() {
        Connection conn = null;
        PreparedStatement ps = null;
        GoodList goodList = new GoodList();

        try{
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Good good = new Good();
                good.setId(resultSet.getInt(1));
                good.setName(resultSet.getString(2));
                good.setDescription(resultSet.getString(11)+
                        ". Description: "+resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                good.setCpu(resultSet.getDouble(5));
                good.setRam(resultSet.getInt(6));
                good.setMemory(resultSet.getDouble(7));
                good.setProducerId(resultSet.getInt(8));
                good.setQuantity(resultSet.getInt(9));

                goodList.addGood(good);
            }
            return goodList;
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public Good getPoductById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_ID);
            ps.setInt(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                Good good = new Good();
                good.setId(resultSet.getInt(1));
                good.setName(resultSet.getString(2));
                good.setDescription(resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                good.setCpu(resultSet.getDouble(5));
                good.setRam(resultSet.getInt(6));
                good.setMemory(resultSet.getDouble(7));
                good.setProducerId(resultSet.getInt(8));
                good.setQuantity(resultSet.getInt(9));
                return good;
            }else {
                throw new GoodNotFoundException(id);
            }
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public GoodList getProductByProducer(int producerId) {
        Connection conn = null;
        PreparedStatement ps = null;

        GoodList goodList = new GoodList();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_Producer);
            ps.setInt(1, producerId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                Good good = new Good();
                good.setId(resultSet.getInt(1));
                good.setName(resultSet.getString(2));
                good.setDescription(resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                good.setCpu(resultSet.getDouble(5));
                good.setRam(resultSet.getInt(6));
                good.setMemory(resultSet.getDouble(7));
                good.setProducerId(resultSet.getInt(8));
                good.setQuantity(resultSet.getInt(9));

                goodList.addGood(good);

            }
            return goodList;
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }

    }

    @Override
    public void updateQuantity(int id, int quantity) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_QUANTITY);

            ps.setInt(1,quantity);
            ps.setInt(2,id);
            ps.execute();
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public TransactionalGoodImplementation startTransaction() {

        Connection conn = getConnection();

        try{
            conn.setAutoCommit(false);
        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return new TransactionalGoodImplementation(conn);
    }

    @Override
    public GoodList resultSearchByRequest(String searchRequest) {

        Connection conn = null;
        PreparedStatement psForProducerName = null;
        PreparedStatement psForSaarchProduct = null;
        GoodList goodList = new GoodList();
        int producerId = 0;

        try{
            conn = getConnection();
            psForProducerName = conn.prepareStatement(SQL_SELECT_PRODUCER_ID);
            psForProducerName.setString(1,searchRequest);
            ResultSet rs = psForProducerName.executeQuery();
            if(rs.next()){
                producerId = rs.getInt(1);
            }

            psForSaarchProduct = conn.prepareStatement(SQL_SELECT_PRODUCT);
            psForSaarchProduct.setString(1,searchRequest);
            psForSaarchProduct.setString(2,searchRequest);
            psForSaarchProduct.setInt(3,producerId);

            rs = psForSaarchProduct.executeQuery();

            while (rs.next()){
                Good good = new Good();
                good.setId(rs.getInt(1));
                good.setName(rs.getString(2));
                good.setDescription(rs.getString(3));
                good.setPrice(rs.getDouble(4));
                good.setCpu(rs.getDouble(5));
                good.setRam(rs.getInt(6));
                good.setMemory(rs.getDouble(7));
                good.setProducerId(rs.getInt(8));
                good.setQuantity(rs.getInt(9));
                goodList.addGood(good);

            }

        }catch (SQLException e){
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }finally {
            ConnectionPool.close(psForProducerName);
            ConnectionPool.close(psForSaarchProduct);
            ConnectionPool.close(conn);
        }

        return goodList;
    }

    protected Connection getConnection(){
        try {
                 return ConnectionPool.getConnection();
            }catch (SQLException e){
                logger.error(e.getMessage(), e);
                throw new DatabaseException(e);
            }

    }
}
