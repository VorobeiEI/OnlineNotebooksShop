package dao.interfaces;

import dao.impl.TransactionalGoodImplementation;
import entity.Product.Good;
import entity.Product.GoodList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksparrow on 20.09.17.
 */
public interface ProductsDAO {

    public void insertOrUpdateProduct(Good good);

    public void createProduct(Good good);

    int countAllProducts();

    int countProductsByProducers(int producerId);

    public GoodList getAllPRoducts();

    GoodList getAllPRoductsWithPagination(int beginIndex, int amountToShow);

    public Good getPoductById(int id);

    public GoodList getProductByProducer(int id);

    GoodList getProductByProducerWithPagination(int producerId, int beginIndex, int amountToShow);

    public void updateQuantity(int id, int quantity);

    public TransactionalGoodImplementation startTransaction();

    public GoodList resultSearchByRequest (String searchRequest);

    }
