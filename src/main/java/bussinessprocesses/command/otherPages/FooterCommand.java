package bussinessprocesses.command.otherPages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.GoodDaoImplementation;
import dao.interfaces.ProductsDAO;
import entity.Product.GoodList;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class FooterCommand implements ActionCommand {

    private static final String PARAM_NAME_SEARCH_REQUEST = "searchRequest";
    private static final String PARAM_NAME_SEARCH_BUTTON = "searchButton";

    @Override
    public String execute(HttpServletRequest request) {
        ProductsDAO pmd = new GoodDaoImplementation();
        String page = null;

        //search result
        if (request.getParameter(PARAM_NAME_SEARCH_BUTTON) != null) {
            GoodList productList = pmd.resultSearchByRequest((request.getParameter(PARAM_NAME_SEARCH_REQUEST)));
            if(productList.getGoods().isEmpty()) {
                request.setAttribute("noResultMessage", MessagesManager.getProperty("message.noresult"));
            }
            request.getSession().setAttribute("productList", productList);
            return ConfigurationManager.getProperty("path.page.searchresult");
        }

        return page;
    }
}
