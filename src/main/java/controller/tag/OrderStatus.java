package controller.tag;

import entity.order.Status;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class OrderStatus
{
    public static String statusOrder(Object ob) {
    String res = null;
    if (ob.equals(Status.PAID)) {
        res = "In process";
    } else {
        res = "Order completed";
    }
    return res;
}
}
