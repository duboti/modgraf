package modgraf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Klasa wyświetla okno "O programie".
 * 
 * @author Daniel Pogrebniak
 *
 */
public class AboutModgraf implements ActionListener 
{
	private Properties lang;
	private Properties prop;
	
	public AboutModgraf(Editor e)
	{
		lang = e.getLanguage();
		prop = e.getProperties();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		JOptionPane.showMessageDialog(null,
				"<html><center>" +
				lang.getProperty("label-program-name") +
				": Modgraf<br><br>" +
				lang.getProperty("label-version") +
				": " +
				prop.getProperty("program-version") +
				"<br><br>" +
				lang.getProperty("label-author") +
				": Daniel Pogrebniak<br><br>" +
				lang.getProperty("message-send-notes") +
				":<br><a>daniel.pogrebniak@gmail.com</a></center></html>",
				lang.getProperty("menu-help-about"), JOptionPane.PLAIN_MESSAGE, null);	
	}
}
