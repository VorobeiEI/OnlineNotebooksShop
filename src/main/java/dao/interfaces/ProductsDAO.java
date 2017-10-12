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

    public GoodList getAllPRoducts();

    public Good getPoductById(int id);

    public GoodList getProductByProducer(int id);

    public void updateQuantity(int id, int quantity);

    public TransactionalGoodImplementation startTransaction();

    public GoodList resultSearchByRequest (String searchRequest);

    public GoodList getProductByProducerWithPagination(int producerId);

    }
