package bussinessprocesses.command.actionfactory;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.command.enumcommand.CommandEnum;
import bussinessprocesses.command.otherPages.EmptyCommand;
import bussinessprocesses.resource.MessagesManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 01.10.17.
 */
public class ActionFactory {
    public ActionCommand defineComand(HttpServletRequest request){
        ActionCommand current = new EmptyCommand();

        // Extract the command name from the request
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            //if the command is not specified in the current request
            return current;
        }
        // receiving object corresponding to the command
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction",
                    action + MessagesManager.getProperty("message.wrongaction"));
        }
        return current;

    }
}
