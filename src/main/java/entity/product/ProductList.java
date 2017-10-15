package entity.product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksparrow on 13.09.17.
 */
public class ProductList {

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void addGood(Product product){
        products.add(product);
    }

    public void removeGood(Product product){
        products.remove(product);
    }

    public int getSize() {
        return products.size();
    }

}
