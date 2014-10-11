package test_classes;

import java.util.List;

import wikia.SongFinder;
import wikia.WikiaXMLHandler;

public class WikiaTest {

//		private static String WIKIA_API_KEY = "d3c8cffb1a3fb4d2274f2c03c14ec1c24644a02a";
	
	public static void main(String[] args){
		
		WikiaXMLHandler xmlHandler = new WikiaXMLHandler();
		SongFinder songFinder = new SongFinder();
		
		
		try {
		
		String[] urlAndLyrics = xmlHandler.getUriAndLyricsSnippet("Adele", "Rolling in the deep");
		
		
		String lyrics = songFinder.findAndExtractSong(urlAndLyrics[0],urlAndLyrics[1]);
		
		System.out.println(lyrics);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
