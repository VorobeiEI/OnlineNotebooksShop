package bussinessprocesses.command;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by jacksparrow on 01.10.17.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request) throws UnsupportedEncodingException;
}
