package test_classes;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;






public class HtmlParserTest {	
	
	public static void main(String[] args){
		
		Document doc = null;
		
		try {
			doc = Jsoup.connect("http://lyrics.wikia.com/Adele:Rolling_In_The_Deep").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Cleaner cleaner = new Cleaner(new Whitelist().simpleText());
		
		Document cleanDoc = cleaner.clean(doc);
		
		String songLine="";
		
		String allTextS = cleanDoc.toString();
		String[] allText = allTextS.split("\n");
		
		for (String line : allText) {
			if(line.contains("They keep me thinking")) songLine = line;
		}
		
		
		
		
		String liryctSnippet = "There's a fire starting in my heartReaching";
		
		int startindex = songLine.indexOf(liryctSnippet);
		
		// String to be scanned to find the pattern.
		String fullLyrics = songLine.substring(startindex);
			
		String pattern = "[a-z][A-Z]";
		
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		
		// Now create matcher object.
		Matcher m = r.matcher(fullLyrics);
		
		for(int i=0;i<30;i++)
			if ( m.find()){ 
				System.out.println("Found value from pattern: " + m.group(0) );
				
				fullLyrics = fullLyrics.replace(m.group(0),  m.group(0).charAt(0)+"\n"+m.group(0).charAt(1) );
			}
		
		
		System.out.println(fullLyrics);
		



		
		
		
		
		
		
		
	}
		
}
