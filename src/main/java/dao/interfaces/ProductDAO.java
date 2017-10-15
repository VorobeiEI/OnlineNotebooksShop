package dao.interfaces;

import dao.impl.TransactionalProductImpl;
import entity.product.Product;
import entity.product.ProductList;

/**
 * Created by jacksparrow on 20.09.17.
 */
public interface ProductDAO {

    public void insertOrUpdateProduct(Product product);

    public void createProduct(Product product);

    int countAllProducts();

    int countProductsByProducers(int producerId);

    public ProductList getAllPRoducts();

    ProductList getAllPRoductsWithPagination(int beginIndex, int amountToShow);

    public Product getPoductById(int id);

    public ProductList getProductByProducer(int id);

    ProductList getProductByProducerWithPagination(int producerId, int beginIndex, int amountToShow);

    public void updateQuantity(int id, int quantity);

    public TransactionalProductImpl startTransaction();

    public ProductList resultSearchByRequest (String searchRequest);

    }
