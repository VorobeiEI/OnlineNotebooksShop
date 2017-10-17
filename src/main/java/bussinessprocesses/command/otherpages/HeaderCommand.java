package bussinessprocesses.command.otherpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.ProductDaoImpl;
import dao.interfaces.ProductDAO;
import entity.product.Product;
import entity.product.ProductList;
import entity.order.Order;
import entity.users.UserType;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class HeaderCommand implements ActionCommand {
    private static final String PARAM_NAME_MAIN = "main";
    private static final String PARAM_NAME_GOODS = "goods";
    private static final String PARAM_NAME_CABINET = "cabinet";
    private static final String PARAM_NAME_CONTACTS = "contacts";
    private static final String PARAM_NAME_CALLBACK = "callback";
    private static final String PARAM_NAME_CART = "cart";
    private static final String PARAM_NAME_LOGIN = "logIn";
    private static final String PARAM_NAME_REGISTRATION = "registration";
    private static final String PARAM_NAME_ADMIN = "admin";
    @Override
    public String execute(HttpServletRequest request) {
        ProductDAO pmd = new ProductDaoImpl();
        String page = null;

        // redirect to goods page
        if (request.getParameter(PARAM_NAME_GOODS) != null) {
            return ConfigurationManager.getProperty("path.page.goods");
        }
        // redirect to cabinet page
        if (request.getParameter(PARAM_NAME_CABINET) != null) {
            if(request.getSession().getAttribute("user") == null) {
                return ConfigurationManager.getProperty("path.page.index");
            }
            return ConfigurationManager.getProperty("path.page.cabinet");
        }
        // redirect to contacts page
        if (request.getParameter(PARAM_NAME_CONTACTS) != null) {
            return ConfigurationManager.getProperty("path.page.contacts");
        }
        // redirect to callback page
        if (request.getParameter(PARAM_NAME_CALLBACK) != null) {
            return ConfigurationManager.getProperty("path.page.callback");
        }

        // redirect to admin page
        if (request.getParameter(PARAM_NAME_ADMIN) != null) {
            if(request.getSession().getAttribute("role")==null) {
                request.getSession().setAttribute("role", UserType.GUEST);}
                else if(request.getSession().getAttribute("role").equals(UserType.ADMINISTRATOR)){
                return ConfigurationManager.getProperty("path.page.admin");
            } else {
                return ConfigurationManager.getProperty("path.page.index");
            }
        }

        // redirect to cart page
        if (request.getParameter(PARAM_NAME_CART) != null) {
            ProductList cartProduct = new ProductList();
            Order order = (Order) request.getSession().getAttribute("order");
            if (order == null) {
                request.setAttribute("cartIsEmptyMessage", MessagesManager.getProperty("message.cartisempty"));
                return ConfigurationManager.getProperty("path.page.goods");
            }

            double orderAmount = 0;
            for (Integer idProduct : order.getProdacts()) {
                Product product = pmd.getPoductById(idProduct);
                cartProduct.addGood(product);
                orderAmount += product.getPrice();
            }
            request.getSession().setAttribute("cart", cartProduct.getProducts());
            request.getSession().setAttribute("orderAmount", orderAmount);
            return ConfigurationManager.getProperty("path.page.cart");
        }

        //redirect to login page
        if (request.getParameter(PARAM_NAME_LOGIN) != null) {
            //checking of logIn current user or not. If logIn redirect user to main page.
            if(request.getSession().getAttribute("user") != null) {
                return ConfigurationManager.getProperty("path.page.index");
            } else {
                return ConfigurationManager.getProperty("path.page.login");
            }
        }

        //redirect to registration page
        if (request.getParameter(PARAM_NAME_REGISTRATION) != null) {
            //checking of logIn current user or not. If logIn redirect user to main page.
            if(request.getSession().getAttribute("user") != null) {
                return ConfigurationManager.getProperty("path.page.index");
            } else {
                return ConfigurationManager.getProperty("path.page.registration");
            }
        }
        return page;
    }
}
