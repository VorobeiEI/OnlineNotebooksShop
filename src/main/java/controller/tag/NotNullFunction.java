package controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by jacksparrow on 30.09.17.
 */
public class NotNullFunction extends TagSupport{
    @Override
    public int doStartTag() throws JspException {

        return SKIP_BODY;
    }

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
