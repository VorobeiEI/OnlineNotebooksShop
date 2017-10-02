package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.GoodDaoImplementation;
import dao.interfaces.ProductsDAO;
import entity.Product.Good;
import entity.Product.GoodList;
import entity.order.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class GoodsPageCommand implements ActionCommand {
    private static final String PARAM_SHOW_PRODUCTS = "show";
    private static final String PARAM_PRODUCT_ID = "product";
    private static final String PARAM_PROD_CATEGORY = "category";
    private static final String PARAM_ADD_PRODUCT = "add";
    private static final String PARAM_CART = "cart";
    private static final String PARAM_SORT_BY_NAME = "sortByName";
    private static final String PARAM_SORT_BY_PRICE = "sortByPrice";
    private static final String PARAM_VIEW_AMOUNT_PRODUCT = "view";


    public static final class SortedByName  implements Comparator<Good> {
        @Override
        public int compare(Good product1, Good product2) {
            String str1 = product1.getName();
            String str2 = product2.getName();
            return str1.compareTo(str2);
        }
    }

    public static final class SortedByPrice  implements Comparator<Good> {
        @Override
        public int compare(Good product1, Good product2) {
            Double price1 = product1.getPrice();
            Double price2 = product2.getPrice();
            if(price1 > price2) {
                return 1;
            } else if(price1 < price2) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    @Override
    public String execute(HttpServletRequest request) {
        ProductsDAO pmd = new GoodDaoImplementation();
        String page = null;

        // extract values from user request
        String productID = request.getParameter(PARAM_PRODUCT_ID);
        String categoryProd = request.getParameter(PARAM_PROD_CATEGORY);
        String sortByName = request.getParameter(PARAM_SORT_BY_NAME);
        String sortByPrice = request.getParameter(PARAM_SORT_BY_PRICE);
        String viewAmountProduct = request.getParameter(PARAM_VIEW_AMOUNT_PRODUCT);

        //set initial amount of product on 1 page
        if(request.getParameter(PARAM_VIEW_AMOUNT_PRODUCT) != null){
            GoodList newProductsList = new GoodList();
            String currentCategoryProd = (String) request.getSession().getAttribute("currentCategoryProduct");
            GoodList allProducts = pmd.getProductByProducer(Integer.valueOf(currentCategoryProd));
            int maxProduct = Integer.valueOf(viewAmountProduct);
            if(maxProduct > allProducts.getSize()) {
                maxProduct = allProducts.getSize();
            }

            for(Good product : allProducts.getGoods().subList(0, maxProduct)) {
                newProductsList.addGood(product);
            }
            request.getSession().setAttribute("maxProductOnPage", maxProduct);
            request.getSession().setAttribute("productCategList", newProductsList);
            return ConfigurationManager.getProperty("path.page.goods");
        }

        // get all products from db and show it on web page
        if (request.getParameter(PARAM_SHOW_PRODUCTS) != null) {
            Order order = (Order) request.getSession().getAttribute("order");
            if (order != null) {
                double orderAmount = 0;
                for (Integer idProduct : order.getProdacts()) {
                    orderAmount += pmd.getPoductById(idProduct).getPrice();
                }
                request.getSession().setAttribute("orderAmount", orderAmount);
            }

            //set standard amount of product on web page
            int maxProductsOnPage = 25;
            GoodList allProducts = pmd.getProductByProducer(Integer.valueOf(categoryProd));
            if(maxProductsOnPage > allProducts.getSize()) {
                maxProductsOnPage = allProducts.getSize();
            }
            GoodList newProductsList = new GoodList();
            for(Good product : allProducts.getGoods().subList(0, Integer.valueOf(maxProductsOnPage))) {
                newProductsList.addGood(product);
            }
            request.getSession().setAttribute("currentCategoryProduct", categoryProd);
            request.getSession().setAttribute("amountofproducts", allProducts.getSize());
            request.getSession().setAttribute("productCategList", newProductsList);
            return ConfigurationManager.getProperty("path.page.goods");
        }

        //sort our product by name or price and show it on web page
        if (sortByName != null || sortByPrice != null) {
            GoodList listProduct = (GoodList) request.getSession().getAttribute("productCategList");
            if(sortByName != null) {
                Collections.sort(listProduct.getGoods(), new SortedByName());
            } else {
                Collections.sort(listProduct.getGoods(), new SortedByPrice());
            }
            request.getSession().setAttribute("productCategList", listProduct);
            return ConfigurationManager.getProperty("path.page.goods");
        }

        // add product to oder
        if (request.getParameter(PARAM_ADD_PRODUCT) != null) {
            if(request.getSession().getAttribute("user") == null) {
                request.setAttribute("ErrorUserNotLogin", MessagesManager.getProperty("message.mustlogin"));
                return ConfigurationManager.getProperty("path.page.goods");
            }
            Order order;
            // if session doesn't contain oder then create a new oder
            if (request.getSession().getAttribute("order") == null) {
                order = new Order();
            } else {
                order = (Order) request.getSession().getAttribute("order");
                request.getSession().setMaxInactiveInterval(3000);
            }
            // check the availability of the product in the database
            // if product not available send message to main page
            Good product = pmd.getPoductById(Integer.valueOf(productID));
            if (product.getQuantity() == 0) {
                request.setAttribute("ErrorNotInStockMessage", MessagesManager.getProperty("message.notinstock"));
                return ConfigurationManager.getProperty("path.page.goods");
            }
            // if "ok" add product to order
            order.addProducts(Integer.valueOf(productID));
            request.getSession().setAttribute("order", order);
            return ConfigurationManager.getProperty("path.page.goods");
        }

        // shows a list of all the goods that the customer has chosen
        if (request.getParameter(PARAM_CART) != null) {
            GoodList cartProduct = new GoodList();
            Order order = (Order) request.getSession().getAttribute("order");
            if (order == null) {
                request.setAttribute("cartIsEmptyMessage", MessagesManager.getProperty("message.cartisempty"));
                return ConfigurationManager.getProperty("path.page.goods");
            }

            double orderAmount = 0;
            for (Integer idProduct : order.getProdacts()) {
                Good product = pmd.getPoductById(idProduct);
                cartProduct.addGood(product);
                orderAmount += product.getPrice();
            }
            request.getSession().setAttribute("cart", cartProduct.getGoods());
            request.getSession().setAttribute("orderAmount", orderAmount);
            return ConfigurationManager.getProperty("path.page.cart");
        }
        return page;
    }
}
