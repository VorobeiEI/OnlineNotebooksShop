package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import dao.impl.GoodDaoImplementation;
import dao.impl.OrderDAOImplementation;
import dao.impl.UserDAOImpl;
import dao.interfaces.OrdersDAO;
import dao.interfaces.ProductsDAO;
import dao.interfaces.UserDAO;
import entity.Product.Good;
import entity.order.Order;
import entity.users.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class CabinetPageCommand implements ActionCommand {
    private static final String PARAM_USER_ORDER = "myOders";
    private static final String PARAM_SHOW_ORDER = "show";
    private static final String PARAM_ORDERID= "orderID";
    private static final String PARAM_USER_INFO = "myInfo";
    private static final String PARAM_WRITE_TO_ADMINISTRATION = "write";
    private static final String PARAM_EDIT_PROFILE = "edit";

    @Override
    public String execute(HttpServletRequest request) {
        OrdersDAO ordersDAO = new OrderDAOImplementation();
        UserDAO userDAO = new UserDAOImpl();
        ProductsDAO productsDAO = new GoodDaoImplementation();
        String page = null;
        User user;

        String orderId = request.getParameter(PARAM_ORDERID);

        //show user's orders
        if(request.getParameter(PARAM_USER_ORDER) != null) {
            String email = (String) request.getSession().getAttribute("user");
            user = userDAO.getUserByEmail(email);
            request.setAttribute("userOderList", ordersDAO.getAllUsersOrders(user.getId()));
            return ConfigurationManager.getProperty("path.page.cabinet");
        }

        //show info about user
        if(request.getParameter(PARAM_USER_INFO) != null) {
            String email = (String) request.getSession().getAttribute("user");
            user = userDAO.getUserByEmail(email);
            List<User> users = new LinkedList<>();
            users.add(user);
            request.setAttribute("userInfo", users);
            return ConfigurationManager.getProperty("path.page.cabinet");
        }

        //show order details
        if(request.getParameter(PARAM_SHOW_ORDER) !=null) {
            ArrayList<Order> userOrder = new ArrayList<>();
            ArrayList<Integer> productIdList;
            ArrayList<Good> productList = new ArrayList<>();
            //view only one selected order on web page
            userOrder.add(ordersDAO.getOrderById(Integer.valueOf(orderId)));
            request.setAttribute("userOderList", userOrder);
            //view list of product from selected order
            productIdList = ordersDAO.getAllProductFromOrder(Integer.valueOf(orderId));
            for(Integer p : productIdList) {
                productList.add(productsDAO.getPoductById(p));
            }
            request.setAttribute("productInOrder", productList);;
            return ConfigurationManager.getProperty("path.page.cabinet");
        }

        //redirect to mail page
        if(request.getParameter(PARAM_WRITE_TO_ADMINISTRATION) != null) {
            return ConfigurationManager.getProperty("path.page.mail");
        }

        //redirect to edit profile page
        if(request.getParameter(PARAM_EDIT_PROFILE) != null) {
            User currentUser = userDAO.getUserByEmail(String.valueOf(request.getSession().getAttribute("user")));
            request.getSession().setAttribute("userName", currentUser.getName());
            request.getSession().setAttribute("userPhone", currentUser.getPhone());
            return ConfigurationManager.getProperty("path.page.editprofile");
        }
        return page;
    }
}
