package dao.xmlparser;

import entity.product.Product;
import entity.product.ProductList;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class STAXMLParser {
    private final Logger logger = Logger.getLogger(STAXMLParser.class);

    public ProductList doParse(String fileName) throws CannotReadXMLException, FileNotFoundException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = null;
        ProductList productList = null;
        Product product = null;
        String tagContent = null;

        try {
            reader = factory.createXMLStreamReader(new FileInputStream(fileName));

            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        ProductTagForXML name = ProductTagForXML.valueOf(reader.getLocalName().toUpperCase());
                        if (name == ProductTagForXML.PRODUCT) {
                            product = new Product();
                            break;
                        }
                        if (name == ProductTagForXML.PRODUCTS) {
                            productList = new ProductList();
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        tagContent = reader.getText().trim();
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        switch (ProductTagForXML.valueOf(reader.getLocalName().toUpperCase())) {
                            case PRODUCT:
                                productList.addGood(product);
                                break;
                            case ID:
                                product.setId(Integer.parseInt(tagContent));
                                break;
                            case NAME:
                                product.setName(tagContent);
                                break;
                            case DESCRIPTION:
                                product.setDescription(tagContent);
                                break;
                            case PRODUCERID:
                                product.setProducerId(Integer.parseInt(tagContent));
                                break;
                            case CPU:
                                product.setCpu(Double.parseDouble(tagContent));
                                break;
                            case RAM:
                                product.setRam(Integer.parseInt(tagContent));
                                break;
                            case MEMORY:
                                product.setMemory(Double.parseDouble(tagContent));
                                break;
                            case PRICE:
                                product.setPrice(Double.parseDouble(tagContent));
                                break;
                            case QUANTITY:
                                product.setQuantity(Integer.parseInt(tagContent));
                                break;
                            default:
                                break;
                        }
                }
            }
        } catch (Exception e) {
            logger.error("Error in reading XML ", e);
            throw new CannotReadXMLException(e, "Error in reading XML ");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    logger.error(e);
                }
            }
        }
        return productList;
    }
}
