package Lab_2.parser;

import Lab_2.model.Computer;
import Lab_2.model.Device;
import Lab_2.model.Types;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Lab_2.util.Constants.*;

public class DocumentParserDOM {

    public Computer parseXml(String xmlPath, String xsdPath) throws ParserConfigurationException, IOException, SAXException {
        var dbf = DocumentBuilderFactory.newInstance();
        var db = dbf.newDocumentBuilder();

        var doc = db.parse(new File(xmlPath));
        var deviceNodeList = doc.getElementsByTagName(DEVICE);

        var devices = new ArrayList<Device>();

        for (int i = 0; i < deviceNodeList.getLength(); i++) {
            var node = deviceNodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;

            var element = (Element) node;
            devices.add(createDevice(element));
        }

        return new Computer(devices);
    }

    private Device createDevice(Element element) {
        int id = Integer.parseInt(element.getAttribute(ID));
        String name = getByTag(element, NAME);
        String origin = getByTag(element, ORIGIN);
        int price = Integer.parseInt(getByTag(element, PRICE));
        boolean critical = Boolean.parseBoolean(getByTag(element, CRITICAL));

        var typesElement = (Element) element.getElementsByTagName(TYPES).item(0);

        return new Device(id, name, origin, price, critical, createTypes(typesElement));
    }

    private Types createTypes(Element element) {
        boolean peripheral = Boolean.parseBoolean(getByTag(element, PERIPHERAL));
        int energyConsumption = Integer.parseInt(getByTag(element, ENERGY_CONSUMPTION));
        boolean cooler = Boolean.parseBoolean(getByTag(element, COOLER));
        String type = getByTag(element, TYPE);
        String port = getByTag(element, PORT);

        return new Types(peripheral, energyConsumption, cooler, type, port);
    }

    private String getByTag(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }


}
