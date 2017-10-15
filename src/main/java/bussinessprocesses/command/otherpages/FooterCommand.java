package bussinessprocesses.command.otherpages;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.resource.ConfigurationManager;
import bussinessprocesses.resource.MessagesManager;
import dao.impl.ProductDaoImpl;
import dao.interfaces.ProductDAO;
import entity.product.ProductList;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class FooterCommand implements ActionCommand {

    private static final String PARAM_NAME_SEARCH_REQUEST = "searchRequest";
    private static final String PARAM_NAME_SEARCH_BUTTON = "searchButton";

    @Override
    public String execute(HttpServletRequest request) {
        ProductDAO pmd = new ProductDaoImpl();
        String page = null;

        //search result
        if (request.getParameter(PARAM_NAME_SEARCH_BUTTON) != null) {
            ProductList productList = pmd.resultSearchByRequest((request.getParameter(PARAM_NAME_SEARCH_REQUEST)));
            if(productList.getProducts().isEmpty()) {
                request.setAttribute("noResultMessage", MessagesManager.getProperty("message.noresult"));
            }
            request.getSession().setAttribute("productList", productList);
            return ConfigurationManager.getProperty("path.page.searchresult");
        }

        return page;
    }
}
