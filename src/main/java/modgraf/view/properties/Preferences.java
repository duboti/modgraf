package modgraf.view.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import modgraf.view.Editor;

/**
 * Klasa zajmuje się obsługą mechanizmu zmiany opcji.
 *
 * @author Daniel Pogrebniak
 */
public class Preferences implements ActionListener
{
	private static final int FRAME_HEIGHT = 400;
	private static final int FRAME_WIDTH = 550;
	private Editor editor;
	private Properties lang;
	private JFrame frame;
	private Properties prop;
	private GeneralTab generalTab;
	private VertexTab vertexTab;
	private EdgeTab edgeTab;
	
	public Preferences(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
		prop = e.getProperties();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		openParamsWindow();
	}

	private void openParamsWindow()
	{
		JTabbedPane tabbedPane = createTabbedPane();
		JPanel buttonPanel = createButtonPanel();
		frame = new JFrame(lang.getProperty("menu-preferences"));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.add(tabbedPane, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setLocationRelativeTo(editor.getGraphComponent());
		frame.setVisible(true);
	}
	
	private JTabbedPane createTabbedPane()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		generalTab = new GeneralTab(editor);
		vertexTab = new VertexTab(editor);
		edgeTab = new EdgeTab(editor);
		tabbedPane.addTab(lang.getProperty("pref-generalTab-name"), generalTab);
		tabbedPane.addTab(lang.getProperty("pref-vertexTab-name"), vertexTab);
		tabbedPane.addTab(lang.getProperty("pref-edgeTab-name"), edgeTab);
		return tabbedPane;
	}

	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		JButton save = new JButton(lang.getProperty("button-save"));
		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String oldLanguage = prop.getProperty("language");
				savePreferences();
				applyPreferences(oldLanguage);
				frame.dispose();
			}
		});
		JButton cancel = new JButton(lang.getProperty("button-cancel"));
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
			}
		});
		buttonPanel.add(save);
		buttonPanel.add(cancel);
		return buttonPanel;
	}
	
	protected void applyPreferences(String oldLanguage)
	{
		editor.loadPropertiesFile();
		if (!prop.getProperty("language").equals(oldLanguage))
			editor.updateFrameLanguage();
	}

	private void savePreferences()
	{
		Properties userPreferences = new Properties();
		userPreferences.putAll(generalTab.getGeneralProperties());
		userPreferences.putAll(vertexTab.getVertexProperties());
		userPreferences.putAll(edgeTab.getEdgeProperties());
		try
		{
			FileWriter writer = new FileWriter(prop.getProperty("properties-file"));
			userPreferences.store(writer, "Modgraf properties file");
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-not-save-properties-file"),
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
		}
	}	
	
}
