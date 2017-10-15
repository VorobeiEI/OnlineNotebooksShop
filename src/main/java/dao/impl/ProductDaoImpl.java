package dao.impl;

import dao.connectionpool.ConnectionPool;
import dao.exception.DatabaseException;
import dao.interfaces.ProductDAO;
import entity.product.Product;
import entity.product.ProductList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Created by jacksparrow on 26.09.17.
 */
public class ProductDaoImpl implements ProductDAO {

    public static final String SQL_COUNT_QOUANTITY_BY_PRODUCER = "SELECT COUNT(*) AS total FROM products WHERE producer_id = ?";
    public static final String SQL_COUNT_QUANTITY_OF_PRODUCT = "SELECT COUNT(*) AS total FROM products";
    public static final String SQL_INSERT_OR_UPDATE = "INSERT INTO products (id,name, description, price, cpu,ram, memory, producer_id,qantity) VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE qantity=qantity+?;";
    public static final String SQL_CREATE_PRODUCT = "INSERT INTO products (name, description, price, cpu," +
            "ram, memory,producer_id, qantity) values (?,?, ?, ?, ?, ?, ?,?)";
    public static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM products INNER JOIN producers ON products.producer_id = id_producer";
    public static final String SQL_SELECT_ALL_PRODUCTS_With_Pagination = "SELECT * FROM products INNER JOIN producers ON products.producer_id = id_producer LIMIT ?,?";

    public static final String SQL_SELECT_PRODUCT_BY_Producer = "SELECT * FROM products WHERE producer_id = ?";
    public static final String SQL_SELECT_PRODUCT_BY_Producer_With_Pagination = "SELECT * FROM products WHERE producer_id = ? LIMIT ?,?";

    public static final String SQL_UPDATE_QUANTITY = "UPDATE products SET qantity=? WHERE id=?";

    public static final String SQL_SELECT_PRODUCER_ID = "SELECT id_producer FROM producers WHERE name = ?";

    public static final String SQL_SELECT_PRODUCT = "SELECT * FROM products WHERE name = ? OR description = ? OR producer_id = ?";

    public static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT id,name, description, price, cpu,ram, memory, producer_id,qantity FROM products WHERE id = ?";

    private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);

    public static final class GoodNotFoundException extends RuntimeException {
        public GoodNotFoundException(final Integer productId) {
            super("product with id: " + productId + " hasn't been found");
        }
    }

    @Override
    public void insertOrUpdateProduct(Product product) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_INSERT_OR_UPDATE);

            ps.setInt(1, product.getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setDouble(5, product.getCpu());
            ps.setInt(6, product.getRam());
            ps.setDouble(7, product.getMemory());
            ps.setInt(8, product.getProducerId());
            ps.setInt(9, product.getQuantity());
            ps.setInt(10, product.getQuantity());
            ps.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }

    }

    @Override
    public void createProduct(Product product) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = getConnection();
            ps = conn.prepareStatement(SQL_CREATE_PRODUCT);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setDouble(4, product.getCpu());
            ps.setInt(5, product.getRam());
            ps.setDouble(6, product.getMemory());
            ps.setInt(7, product.getProducerId());
            ps.setInt(8, product.getQuantity());
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public int countAllProducts() {
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_COUNT_QUANTITY_OF_PRODUCT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
            return count;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public int countProductsByProducers(int producerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_COUNT_QOUANTITY_BY_PRODUCER);
            ps.setInt(1, producerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
            return count;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public ProductList getAllPRoducts() {
        Connection conn = null;
        PreparedStatement ps = null;
        ProductList productList = new ProductList();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription("Producer: " + resultSet.getString(11) +
                        ". Description: " + resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setCpu(resultSet.getDouble(5));
                product.setRam(resultSet.getInt(6));
                product.setMemory(resultSet.getDouble(7));
                product.setProducerId(resultSet.getInt(8));
                product.setQuantity(resultSet.getInt(9));

                productList.addGood(product);
            }
            return productList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public ProductList getAllPRoductsWithPagination(int beginIndex, int amountToShow) {
        Connection conn = null;
        PreparedStatement ps = null;
        ProductList productList = new ProductList();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_ALL_PRODUCTS_With_Pagination);
            ps.setInt(1, beginIndex);
            ps.setInt(2, amountToShow);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription("Producer: " + resultSet.getString(11) +
                        ". Description: " + resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setCpu(resultSet.getDouble(5));
                product.setRam(resultSet.getInt(6));
                product.setMemory(resultSet.getDouble(7));
                product.setProducerId(resultSet.getInt(8));
                product.setQuantity(resultSet.getInt(9));

                productList.addGood(product);
            }
            return productList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public Product getPoductById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_ID);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setCpu(resultSet.getDouble(5));
                product.setRam(resultSet.getInt(6));
                product.setMemory(resultSet.getDouble(7));
                product.setProducerId(resultSet.getInt(8));
                product.setQuantity(resultSet.getInt(9));
                return product;
            } else {
                throw new GoodNotFoundException(id);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public ProductList getProductByProducer(int producerId) {
        Connection conn = null;
        PreparedStatement ps = null;

        ProductList productList = new ProductList();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_Producer);
            ps.setInt(1, producerId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setCpu(resultSet.getDouble(5));
                product.setRam(resultSet.getInt(6));
                product.setMemory(resultSet.getDouble(7));
                product.setProducerId(resultSet.getInt(8));
                product.setQuantity(resultSet.getInt(9));

                productList.addGood(product);

            }
            return productList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }

    }

    @Override
    public ProductList getProductByProducerWithPagination(int producerId, int beginIndex, int amountToShow) {
        Connection conn = null;
        PreparedStatement ps = null;

        ProductList productList = new ProductList();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(SQL_SELECT_PRODUCT_BY_Producer_With_Pagination);
            ps.setInt(1, producerId);
            ps.setInt(2, beginIndex);
            ps.setInt(3, amountToShow);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setPrice(resultSet.getDouble(4));
                product.setCpu(resultSet.getDouble(5));
                product.setRam(resultSet.getInt(6));
                product.setMemory(resultSet.getDouble(7));
                product.setProducerId(resultSet.getInt(8));
                product.setQuantity(resultSet.getInt(9));

                productList.addGood(product);

            }
            return productList;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
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

            ps.setInt(1, quantity);
            ps.setInt(2, id);
            ps.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(ps);
            ConnectionPool.close(conn);
        }
    }

    @Override
    public TransactionalProductImpl startTransaction() {

        Connection conn = getConnection();

        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }
        return new TransactionalProductImpl(conn);
    }

    @Override
    public ProductList resultSearchByRequest(String searchRequest) {

        Connection conn = null;
        PreparedStatement psForProducerName = null;
        PreparedStatement psForSaarchProduct = null;
        ProductList productList = new ProductList();
        int producerId = 0;

        try {
            conn = getConnection();
            psForProducerName = conn.prepareStatement(SQL_SELECT_PRODUCER_ID);
            psForProducerName.setString(1, searchRequest);
            ResultSet rs = psForProducerName.executeQuery();
            if (rs.next()) {
                producerId = rs.getInt(1);
            }

            psForSaarchProduct = conn.prepareStatement(SQL_SELECT_PRODUCT);
            psForSaarchProduct.setString(1, searchRequest);
            psForSaarchProduct.setString(2, searchRequest);
            psForSaarchProduct.setInt(3, producerId);

            rs = psForSaarchProduct.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3));
                product.setPrice(rs.getDouble(4));
                product.setCpu(rs.getDouble(5));
                product.setRam(rs.getInt(6));
                product.setMemory(rs.getDouble(7));
                product.setProducerId(rs.getInt(8));
                product.setQuantity(rs.getInt(9));
                productList.addGood(product);

            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        } finally {
            ConnectionPool.close(psForProducerName);
            ConnectionPool.close(psForSaarchProduct);
            ConnectionPool.close(conn);
        }

        return productList;
    }

    protected Connection getConnection() {
        try {
            return ConnectionPool.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e);
        }

    }
}
