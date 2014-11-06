package modgraf.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Klasa wyświetla okno "Pomoc".
 * 
 * @author Daniel Pogrebniak
 *
 */
public class Help implements ActionListener 
{
	private JFrame frame;
	private Properties lang;
	boolean useClassLoader;
	
	public Help(Editor e)
	{
		lang = e.getLanguage();
		String useClassLoaderString = e.getProperties().getProperty("use-class-loader");
		useClassLoader = Boolean.parseBoolean(useClassLoaderString);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		openWindow();
	}
	
	private void openWindow()
	{
		JScrollPane editorScrollPane = createTextPanel();
		JPanel buttonPanel = createButtonPanel();
		frame = new JFrame(lang.getProperty("menu-help-help"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.add(editorScrollPane, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	private JScrollPane createTextPanel()
	{
		JEditorPane editorPane = new JEditorPane();
		File helpFile = new File("help/help.html");
		try
		{
			URL helpFileURL = null;
			if (useClassLoader)
				helpFileURL = getClass().getClassLoader().getResource("help/help.html");
			else
				helpFileURL = helpFile.toURI().toURL();
			editorPane.setPage(helpFileURL);
		}
		catch (IOException e)
		{
			editorPane.setText(lang.getProperty("error-not-open-help-file")+
					helpFile.getAbsolutePath());
		}
		editorPane.setEditable(false);
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		return editorScrollPane;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		JButton close = new JButton(lang.getProperty("button-close"));
		close.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
			}
		});
		buttonPanel.add(close);
		return buttonPanel;
	}
}
