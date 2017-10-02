import dao.impl.GoodDaoImplementation;
import dao.impl.OrderDAOImplementation;
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
import entity.users.UserType;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacksparrow on 22.09.17.
 */
public class Domain {
    void a(Integer...s){

    }
    public static void main(String[] args) throws SQLException {

//        User client3 = new User();
//
//        client3.setName("petysdsda");
//        client3.setEmail("test@tesdfsfdt");
//        client3.setPasswordHash("323dsdfs423");
//        client3.setStatus(UserStatus.STATUS_OK);
//        client3.setPhone("453dfsd4534");
//        client3.setRole("ADMIN");

        Good good = new Good();
        //good.setId(5);
        good.setName("Delfdglfy");
        good.setDescription("Megddogo");
        good.setPrice(10000);
        good.setCpu(2.2);
        good.setRam(10246565);
        good.setMemory(120.0);
        good.setProducerId(2);
        good.setQuantity(4);

        //ProductsDAO productsDAO = new GoodDaoImplementation();
        //productsDAO.createProduct(good);
        // productsDAO.insertOrUpdateProduct(good);
        //productsDAO.updateQuantity(5667,3);
        //GoodList goodList = productsDAO.getAllPRoducts();
        //GoodList goodList = productsDAO.getProductByProducer(2);
        // GoodList goodList = productsDAO.resultSearchByRequest("Dellfy");

//        Good good1 = productsDAO.getPoductById(2);
//        System.out.println(good1);

//        UserDAO test = new UserDAOImpl();

        // test.createUser(client3);

        //System.out.println(test.getUserByEmail("test@test"));

        //test.deleteUserByEmail("test@test");

        // test.updatePassword("test@test", "44434");

        // System.out.println(test.getUserByElement("role","ADMIN"));

        // test.changeStatus("test@test");

        Order order = new Order();

        order.setSum(5.5);
        order.setUserId(10);
        order.setStatus(Status.CREATED);
        order.addProducts(2);
        OrdersDAO ord = new OrderDAOImplementation();

       // ord.createOrder(order);

        ArrayList<Order> test =  ord.getAllOrders();
        for(Order a : test){
            System.out.println(a);
        }
    }
}
