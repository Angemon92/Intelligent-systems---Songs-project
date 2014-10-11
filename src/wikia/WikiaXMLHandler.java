package wikia;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.XMLUtil;

public class WikiaXMLHandler {

	private static String WIKIA_API_URL = "http://lyrics.wikia.com/";
	
	private SAXReader reader = new SAXReader();
	
	private String buildUrl(String artistName,String songName){
		
		if(artistName==null) artistName="";
		if(songName==null) songName="";
		
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(WIKIA_API_URL).append("api.php?func=");
		urlBuilder.append("getSong&artist=").append(artistName);
		urlBuilder.append("&song=").append(songName);
		urlBuilder.append("&fmt=xml");
		
		return urlBuilder.toString();
	}
	/**
	 * 
	 * @param artistName
	 * @param songName
	 * @return url where official lyrics are.
	 */
	public String getLyricsWebPage(String artistName,String songName){
		try {
			
			if (artistName.equals(null) || artistName.equals("") || songName.equals(null) || songName.equals(""))
				return null;
			
			String url = buildUrl(artistName,songName);

			Document document = reader.read(url);
//			XMLUtil.writeToFile(document, artistName + " - " + songName + ".xml");
		
			Element root = document.getRootElement(); //LyricsResult element
		
			Element pageIdElement = root.element("page_id"); // page_id element
			String pageId = pageIdElement.getText();
			
			if(pageId.equals("")){ //there is NOT lyrics page
				System.out.println("No lyrics page.");
				return null;
			}
			else
				return root.element("url").getText();		
			
		} catch (Exception e) {
			return null;
			//e.printStackTrace();
		}
	}
	
	
public String getLyricsSnippet(String artistName,String songName){
		
	if (artistName.equals(null) || artistName.equals("") || songName.equals(null) || songName.equals(""))
		return null;
	
	String url = buildUrl(artistName,songName);

	try {
		Document document = reader.read(url);
//		XMLUtil.writeToFile(document, artistName + " - " + songName + ".xml");
	
		Element root = document.getRootElement(); //LyricsResult element
		String pageId = root.element("page_id").getText();
	
		if(pageId.equals("")){ //there is NOT lyrics page
			System.out.println("No lyrics page.");
			return null;
		}
		else
			return root.element("lyrics").getText();
			
		} catch (Exception e) {
			return null;
			//e.printStackTrace();
		}
	}

/**
 * Method is used to find full lyrics, by SongFinder class.
 * @param artistName
 * @param songName
 * @return
 */
public String[] getUriAndLyricsSnippet(String artistName,String songName){
	
	if (artistName.equals(null) || artistName.equals("") || songName.equals(null) || songName.equals(""))
		return null;
	
	String url = buildUrl(artistName,songName);

	try {
		Document document = reader.read(url);
//		XMLUtil.writeToFile(document, artistName + " - " + songName + ".xml");
	
		Element root = document.getRootElement(); //LyricsResult element
	
		Element pageIdElement = root.element("page_id"); // page_id element
		String pageId = pageIdElement.getText();

		if(pageId.equals("")){ //there is NOT lyrics page (Song or Artist name is wrong)
			System.out.println("No lyrics page.");
			return null;
		}
		else
			return new String[]{root.element("url").getText(),root.element("lyrics").getText()};
		
	} catch (Exception e) {
		return null;
		//e.printStackTrace();
	}
}
	
	/**
	 * 
	 * @param artistName
	 * @return null if there isnt any song found.
	 */
	public List<String> getAllSongs(String artistName){
		
		if(artistName.equals(null) || artistName.equals("")) return null;
		
		List<String> allSongs = null;
	
		String url = buildUrl(artistName,null);
		
		try {
			Document document = reader.read(url);
//			XMLUtil.writeToFile(document, artistName + " - songs.xml");
		
			Element root = document.getRootElement(); //getArtistResponse element
			Element albumElement = root.element("albums"); // albums element
			List<Element> songsElement = albumElement.elements("songs");
		
			allSongs = new ArrayList<String>();
			
			for (Element songs : songsElement) {
				List<Element> items = songs.elements();
				for (Element item : items) 
					if(!allSongs.contains(item.getText())) allSongs.add(item.getText());
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return allSongs;
	}
	

}
