package dao.xmlParser;

/**
 * Created by jacksparrow on 02.10.17.
 */
public enum ProductTagForXML {
    PRODUCTS("products"),
    PRODUCT("product"),
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    QUANTITY("quantity"),
    CATEGORY("category");

    private String value;

    private ProductTagForXML(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}