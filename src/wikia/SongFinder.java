package wikia;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

public class SongFinder {

	/**
	 * Method is using Jsoup to connect and take web page.
	 * @param url of page where lyrics are on internet
	 * @param inputLyricsSnippet lyrics snippet taken from wiki
	 * @return String full fong with "\n" formated
	 */
	public String findAndExtractSong(String url,String inputLyricsSnippet){

//Problem: Try to find optimal length of lyricsSnippet, because sometime there is problem to find where Lyrics starts.
		int startIndex = -1;
		int n = 31; 

		String lyricsLine = "";
		
		do{
			n = n-1;
			
			while(n+5>=inputLyricsSnippet.length()) n=n-1;
			
			String lyricsSnippet = inputLyricsSnippet.substring(0, n);
//			System.out.println("startIndex: "+startIndex+" - n:"+n+" - lyricsSnippet:"+lyricsSnippet);
			
			Document document = null;
			try {
				document = Jsoup.connect(url).get();
			} catch (IOException e){ 
				e.printStackTrace();
			}

			Cleaner cleaner = new Cleaner(new Whitelist().simpleText()); // remove all tags from html
	
			Document cleanedDocument = cleaner.clean(document);
	
			String allText = cleanedDocument.toString();
			String[] allTextArray = allText.split("\n");
			
//			System.out.println("ALL TEXT: "+cleanedDocument.toString());
//			System.out.println("SNIPPET: "+lyricsSnippet);
		
			for (String line : allTextArray) {
				if(line.contains(lyricsSnippet)){
					lyricsLine = line;
					break;
				}
			}
		
//			System.out.println("LYRICS LINE: "+lyricsLine);
			startIndex = lyricsLine.indexOf(lyricsSnippet);

		}while(startIndex==-1);
		
//Problem: There isnt "\n" in official lyrics, so lets make it using regular expressions.
		String fullLyrics = lyricsLine.substring(startIndex); // String to be scanned to find the pattern.			
			
		String pattern = "[a-z][A-Z]";
		Pattern r = Pattern.compile(pattern); // Create a Pattern object
		Matcher m = r.matcher(fullLyrics); // Now create matcher object.
		
		for(int i=0;i<50;i++)
			if ( m.find()){ 
				//System.out.println(i + ": Found value from pattern: " + m.group(0) );
				fullLyrics = fullLyrics .replace(m.group(0),  m.group(0).charAt(0)+"\n"+m.group(0).charAt(1) );
		}
	
//Problem: Sometime after official lyrics stays some unuseful text, so remove it.
		if(fullLyrics.contains("  ")){
			String[] strangeLyrics = fullLyrics.split("  ");
			System.out.println(strangeLyrics[1]);
			fullLyrics = strangeLyrics[0];
		}
		
//Problem: Some lyrics are formatted different in html, so i cant recognise them.
		if(fullLyrics.length()<100) fullLyrics = "Some problem ocur, cant find lyrics. Not your fault.";
	return fullLyrics;
	}
	
}
