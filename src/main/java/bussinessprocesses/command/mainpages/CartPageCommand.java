package bussinessprocesses.command.mainpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.GoodDaoImplementation;
import dao.impl.OrderDAOImplementation;
import dao.impl.TransactionalGoodImplementation;
import dao.impl.UserDAOImpl;
import dao.interfaces.OrdersDAO;
import dao.interfaces.ProductsDAO;
import dao.interfaces.UserDAO;
import entity.Product.Good;
import entity.Product.GoodList;
import entity.order.Order;
import entity.order.Status;
import entity.users.User;
import entity.users.UserStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class CartPageCommand implements ActionCommand {
    private static final String PARAM_PRODUCT_ID = "product";
    private static final String PARAM_CART = "cart";
    private static final String PARAM_REMOVE_FROM_CART = "remove";
    private static final String PARAM_PAY_ODER = "pay";
    private static final Lock lock = new ReentrantLock();
    @Override
    public String execute(HttpServletRequest request) {
        UserDAO userDAO = new UserDAOImpl();
        ProductsDAO productsDAO = new GoodDaoImplementation();
        OrdersDAO ordersDAO = new OrderDAOImplementation();
        String page = null;

        // extract values from user request
        String productID = request.getParameter(PARAM_PRODUCT_ID);

        // shows a list of all the goods that the customer has chosen
        if (request.getParameter(PARAM_CART) != null) {
            if(request.getParameter(PARAM_CART).isEmpty()) {
                return ConfigurationManager.getProperty("path.page.cart");
            }
            Order order = (Order) request.getSession().getAttribute("order");
            if (order == null) {
                request.setAttribute("cartIsEmptyMessage", MessagesManager.getProperty("message.cartisempty"));
                return ConfigurationManager.getProperty("path.page.cart");
            }
        }

        // delete the selected item from the cart
        if (request.getParameter(PARAM_REMOVE_FROM_CART) != null) {
            GoodList cartProduct = new GoodList();
            Order order = (Order) request.getSession().getAttribute("order");
            order.removeProducts(productID);

            double orderAmount = 0;
            for (Integer idProduct : order.getProdacts()) {
                Good product = productsDAO.getPoductById(idProduct);
                cartProduct.addGood(product);
                orderAmount += product.getPrice();
            }
            request.getSession().setAttribute("order", order);
            request.getSession().setAttribute("cart", cartProduct.getGoods());
            request.getSession().setAttribute("orderAmount", orderAmount);
            return ConfigurationManager.getProperty("path.page.cart");
        }

        // customer pays for an order, the order is put in database with status 0,
        // then the administrator must change the status in the order of 1 to complete order.
        if (request.getParameter(PARAM_PAY_ODER) != null) {
            Order order = (Order) request.getSession().getAttribute("order");
            if (order == null) {
                request.setAttribute("cartIsEmptyMessage", MessagesManager.getProperty("message.cartisempty"));
                return ConfigurationManager.getProperty("path.page.main");
            }

            String email = (String) request.getSession().getAttribute("user");
            User user = userDAO.getUserByEmail(email);
            // if user's account blocked, order is not paid
            if (user.getStatus().equals(UserStatus.STATUS_BLOCKED)) {
                request.setAttribute("ErrorAccountBlockedMessage", MessagesManager.getProperty("message.accountblocked"));
                return ConfigurationManager.getProperty("path.page.cart");
            }
            lock.lock();
            TransactionalGoodImplementation transaction = null;
            try {
                transaction = productsDAO.startTransaction();

                // before payment checking quantity of product in db and update
                // it value
                for (Integer prodID : order.getProdacts()) {
                    Good product = transaction.getPoductById(prodID);
                    // check the availability of the product in the database
                    if (product.getQuantity() == 0) {
                        transaction.rollback();
                        request.setAttribute("ErrorNotInStockMessage",
                                MessagesManager.getProperty("message.notinstock"));
                        return ConfigurationManager.getProperty("path.page.cart");
                    }
                    transaction.updateQuantity(Integer.valueOf(prodID),
                            (product.getQuantity() - 1));
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
            } finally {
                lock.unlock();
            }

            // if "ok" put put order into db
            int userId = user.getId();
            double orderAmount = (double) request.getSession().getAttribute("orderAmount");
            order.setStatus(Status.PAID);
            order.setUserId(userId);
            order.setSum(orderAmount);
            order.setDate(new Date(System.currentTimeMillis()));
            ordersDAO.createOrder(order);

            request.getSession().setAttribute("order", null);
            request.getSession().setAttribute("orderAmount", null);
            request.getSession().setAttribute("cart", null);
            request.setAttribute("OrderInProcessed", MessagesManager.getProperty("message.orderinprocess"));
            return ConfigurationManager.getProperty("path.page.cart");
        }
        return page;
    }
}
