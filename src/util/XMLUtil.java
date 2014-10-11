package util;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLUtil {
	
	public static void printDocument(Document doc) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			XMLWriter writer = new XMLWriter(System.out, format);
			writer.write(doc);
		} catch (Exception e) {
			System.err.println("Error while trying to print: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(Document document, String fileName) throws IOException {
        // lets write to a file
		OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(
            new FileWriter( fileName ), format);
        writer.write( document );
        writer.close();

	}

}
