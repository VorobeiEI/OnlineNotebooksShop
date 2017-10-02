package controller.tag;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class OrderStatus
{	public static String statusOrder(Object ob) {
    String res = null;
    if ((int) ob == 0) {
        res = "In process";
    } else {
        res = "Order complete";
    }
    return res;
}
}
