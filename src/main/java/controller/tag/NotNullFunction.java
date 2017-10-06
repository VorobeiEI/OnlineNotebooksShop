package controller.tag;

/**
 * Created by jacksparrow on 30.09.17.
 */
public class NotNullFunction {
    public static String notNull(Object ob){
        String res = null;
        if(ob == null || ob.toString().isEmpty()){
            res = "Guest";
        }else {
            res = ob.toString();
        }
        return res;
    }

    public static String notNullorderAmount (Object ob){
        String res = null;
        if(ob == null || ob.toString().isEmpty()){
            res = null;
        }else {
            res = "Amount of order: " + ob.toString() + " USD!";
        }
        return res;
    }
}
