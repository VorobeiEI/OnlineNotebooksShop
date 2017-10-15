package dao.xmlparser;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class ValidatorXML {
    private static final Logger logger = Logger.getLogger(ValidatorXML.class);

    public static boolean validation(String fileName, String SchemaPath){
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(SchemaPath);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            validator.validate(source);
            logger.info(fileName + " is valid.");
        } catch (SAXException e) {
            logger.error("validation " + fileName + " is not valid because " + e.getMessage());
            return false;
        } catch (IOException e) {
            logger.error(fileName + " is not valid because " + e.getMessage());
            return false;
        }
        return true;
    }
}
