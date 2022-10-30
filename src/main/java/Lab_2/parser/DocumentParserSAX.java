package Lab_2.parser;


import Lab_2.model.Computer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class DocumentParserSAX {

    public Computer parseXml(String xmlPath) throws SAXException, IOException, ParserConfigurationException {
        var xml = new File(xmlPath);

        var saxParserFactory = SAXParserFactory.newInstance();
        var saxParser = saxParserFactory.newSAXParser();

        var deviceHandler = new DeviceHandler();
        saxParser.parse(xml, deviceHandler);

        return new Computer(deviceHandler.getDeviceList());
    }
}