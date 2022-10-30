package Lab_2.parser;

import Lab_2.model.Computer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentParserStAX {
    public Computer parseXml(String xmlPath) throws XMLStreamException, FileNotFoundException {
        var xml = new File(xmlPath);

        var xmlInputFactory = XMLInputFactory.newInstance();

        var deviceHandler = new DeviceHandler();
        var reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xml));

        while (reader.hasNext()) {
            var nextXMLEvent = reader.nextEvent();

            if (nextXMLEvent.isStartElement()) {
                var startElement = nextXMLEvent.asStartElement();
                nextXMLEvent = reader.nextEvent();

                String name = startElement.getName().getLocalPart();
                if (nextXMLEvent.isCharacters()) {
                    var attributesList = new ArrayList<Attribute>();
                    var iter = startElement.getAttributes();

                    while (iter.hasNext()) {
                        attributesList.add(iter.next());
                    }

                    var attributeMap = new HashMap<String, String>();

                    for (var attribute : attributesList) {
                        attributeMap.put(
                                attribute.getName().getLocalPart(),
                                attribute.getValue()
                        );
                    }

                    deviceHandler.setField(name, nextXMLEvent.asCharacters().getData(), attributeMap);
                }
            }
        }
        return new Computer(deviceHandler.getDeviceList());
    }
}
