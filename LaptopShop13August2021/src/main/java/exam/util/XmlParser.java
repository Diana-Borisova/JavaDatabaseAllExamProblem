package exam.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    public <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException;
}
