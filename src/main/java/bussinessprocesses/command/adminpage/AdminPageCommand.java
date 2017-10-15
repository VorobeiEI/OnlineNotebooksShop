package bussinessprocesses.command.adminpage;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.ProductDaoImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.UserDAOImpl;
import dao.interfaces.OrderDAO;
import dao.interfaces.ProductDAO;
import dao.interfaces.UserDAO;
import dao.xmlparser.CannotReadXMLException;
import dao.xmlparser.STAXMLParser;
import dao.xmlparser.ValidatorXML;
import entity.product.Product;
import entity.product.ProductList;
import entity.order.Status;
import entity.users.UserType;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class AdminPageCommand implements ActionCommand {
    private static final String PARAM_XML_PATH = "xmlPath";
    private static final String PARAM_PARSE_XML = "create";
    private static final String PARAM_SHOW_PRODUCTS = "showProducts";
    private static final String PARAM_SHOW_CUSTOMERS = "showCustomers";
    private static final String PARAM_SHOW_ORDERS = "showOrders";
    private static final String PARAM_CLIENT_Email = "email";
    private static final String PARAM_CHANGE_USER_STATUS = "changeUserStatus";
    private static final String PARAM_ORDERID = "orderID";
    private static final String PARAM_COMPLETE = "complete";

    private static final String XSD_SCHEMA_PATH = "/home/jacksparrow/Java/Projects/Project/src/main/products.xsd";

    private static final Lock lock = new ReentrantLock();
    @Override
    public String execute(HttpServletRequest request) {
        ProductDAO productDAO = new ProductDaoImpl();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        String page = null;
        ProductList productList;

        // extract values from user request
        String orderID = request.getParameter(PARAM_ORDERID);
        String xmlPath = request.getParameter(PARAM_XML_PATH);
        String clientEmail = request.getParameter(PARAM_CLIENT_Email);
        //additional check for administration role
        if(request.getSession().getAttribute("role") != (String.valueOf(UserType.ADMINISTRATOR))){
            return ConfigurationManager.getProperty("path.page.login");
        }

        //Parse xml file with product and add this product to data base
        if(request.getParameter(PARAM_PARSE_XML) != null){

            if (!ValidatorXML.validation(xmlPath, XSD_SCHEMA_PATH)){
                request.setAttribute("errorXMLnotValid", MessagesManager.getProperty("message.xmlvalidation"));
                return page = ConfigurationManager.getProperty("path.page.admin");
            }

            lock.lock();
            STAXMLParser parser = new STAXMLParser();
            try {
                productList = parser.doParse(xmlPath);
                for (Product product : productList.getProducts()){
                    productDAO.insertOrUpdateProduct(product);
                }
            } catch (CannotReadXMLException e) {
                request.setAttribute("errorParseXMLMessage", MessagesManager.getProperty("message.readxmlerror"));
                return page = ConfigurationManager.getProperty("path.page.admin");

            } catch (FileNotFoundException e) {
                request.setAttribute("errorsSearchXMLMessage", MessagesManager.getProperty("message.xmlnotfound"));
                return page = ConfigurationManager.getProperty("path.page.admin");
            } finally {
                lock.unlock();
            }
            request.setAttribute("productCreatedMessage", MessagesManager.getProperty("message.productcreated"));
            page = ConfigurationManager.getProperty("path.page.admin");
        }

        //get all products from db and show it on web page
        if(request.getParameter(PARAM_SHOW_PRODUCTS) != null) {
            productList = productDAO.getAllPRoducts();
            request.setAttribute("productList", productList);
            return ConfigurationManager.getProperty("path.page.admin");
        }

        //get all customers from db and show it on web page
        if(request.getParameter(PARAM_SHOW_CUSTOMERS) !=null) {
            request.setAttribute("userList", userDAO.getAllUSers());
            return ConfigurationManager.getProperty("path.page.admin");
        }

        //change status of client to block or unblock
        if(request.getParameter(PARAM_CHANGE_USER_STATUS) != null) {
            try {
                lock.lock();
                userDAO.changeStatus(clientEmail);
                request.setAttribute("chageStatusSuccesMessage", MessagesManager.getProperty("message.changestatussucces"));
                page = ConfigurationManager.getProperty("path.page.admin");
            } finally {
                lock.unlock();
            }
        }

        //get all orders from db and show it on web page
        if(request.getParameter(PARAM_SHOW_ORDERS) !=null) {
            request.setAttribute("oderList", orderDAO.getAllOrders());
            return ConfigurationManager.getProperty("path.page.admin");
        }

        //change status of order to complete
        if(request.getParameter(PARAM_COMPLETE) !=null) {
            orderDAO.orderComlete(Integer.valueOf(orderID), Status.COMPLETED);
            request.setAttribute("orderCompleteMessage", MessagesManager.getProperty("message.ordercomplete"));
            page = ConfigurationManager.getProperty("path.page.admin");
        }

        return page;
}
}
