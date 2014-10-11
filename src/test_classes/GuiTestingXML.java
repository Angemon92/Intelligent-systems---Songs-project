package test_classes;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import wikia.*;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class GuiTestingXML extends JFrame {

	private JPanel contentPane;
	private JTextField txFieldArtistName;
	private JTextField txFieldSongName;
	private JButton btnFind;
	private JTextArea textAreaMain;
	private JScrollPane scrollPane;
	private JLabel lblArtistName;
	private JLabel lblSongName;
	private JButton btnClear;
	private JButton btnGetLyrics;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiTestingXML frame = new GuiTestingXML();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiTestingXML() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTxFieldArtistName());
		contentPane.add(getTxFieldSongName());
		contentPane.add(getBtnFind());
		contentPane.add(getScrollPane());
		contentPane.add(getLblArtistName());
		contentPane.add(getLblSongName());
		contentPane.add(getBtnClear());
		contentPane.add(getBtnGetLyrics());
	}
	private JTextField getTxFieldArtistName() {
		if (txFieldArtistName == null) {
			txFieldArtistName = new JTextField();
			txFieldArtistName.setText("Eminem");
			txFieldArtistName.setBounds(10, 42, 86, 20);
			txFieldArtistName.setColumns(10);
		}
		return txFieldArtistName;
	}
	private JTextField getTxFieldSongName() {
		if (txFieldSongName == null) {
			txFieldSongName = new JTextField();
			txFieldSongName.setText("Not afraid");
			txFieldSongName.setBounds(10, 106, 86, 20);
			txFieldSongName.setColumns(10);
		}
		return txFieldSongName;
	}
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton("Find");
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					WikiaXMLHandler xmlHandler = new WikiaXMLHandler();
					
					String artistName = txFieldArtistName.getText();
					String songName = txFieldSongName.getText();
					
					if(songName.equals("") && !artistName.equals("")){
						List<String> songs = xmlHandler.getAllSongs(txFieldArtistName.getText());
						for (String song : songs) textAreaMain.append(song+"\n");
					}
					else textAreaMain.append( xmlHandler.getLyricsSnippet(artistName, songName)+"\n" );
					
				}
			});
			btnFind.setBounds(10, 188, 89, 23);
		}
		return btnFind;
	}
	private JTextArea getTextAreaMain() {
		if (textAreaMain == null) {
			textAreaMain = new JTextArea();
		}
		return textAreaMain;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(124, 11, 300, 200);
			scrollPane.setViewportView(getTextAreaMain());
		}
		return scrollPane;
	}
	private JLabel getLblArtistName() {
		if (lblArtistName == null) {
			lblArtistName = new JLabel("Artist name :");
			lblArtistName.setBounds(10, 17, 86, 14);
		}
		return lblArtistName;
	}
	private JLabel getLblSongName() {
		if (lblSongName == null) {
			lblSongName = new JLabel("Song name :");
			lblSongName.setBounds(10, 81, 86, 14);
		}
		return lblSongName;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("Clear");
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					textAreaMain.setText("");
				}
			});
			btnClear.setBounds(10, 222, 89, 23);
		}
		return btnClear;
	}
	private JButton getBtnGetLyrics() {
		if (btnGetLyrics == null) {
			btnGetLyrics = new JButton("Get lyrics");
			btnGetLyrics.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					WikiaXMLHandler xmlHandler = new WikiaXMLHandler();
					SongFinder songFinder = new SongFinder();
					
					String artistName = txFieldArtistName.getText();
					String songName = txFieldSongName.getText();
					
					String[] urlAndLyrics = xmlHandler.getUriAndLyricsSnippet(artistName, songName);
					
					
					if(urlAndLyrics!=null){
						String lyrics = songFinder.findAndExtractSong(urlAndLyrics[0],urlAndLyrics[1]);
						textAreaMain.append(lyrics+"\n");
					}
					else textAreaMain.append("Cant find songs lyrics, mabey Song or artist name are not right...\n");
				}
			});
			btnGetLyrics.setBounds(124, 222, 89, 23);
		}
		return btnGetLyrics;
	}
}
