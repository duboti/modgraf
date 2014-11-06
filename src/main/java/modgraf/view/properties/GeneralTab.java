package modgraf.view.properties;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import modgraf.view.Editor;

import com.mxgraph.util.mxUtils;

public class GeneralTab extends PreferencesTab
{
	private static final long serialVersionUID = -1956455976702453830L;
	private JComboBox<String> language;
	private JComboBox<String> startup;
	private JComboBox<String> graphType;
	private JComboBox<Integer> edgeType;
	private JFormattedTextField graphPaneHeight;
	private JFormattedTextField textPaneHeight;
	private JComboBox<String> fileFormat;
	private JComboBox<String> fileEncoding;
	private ChangeColorListener backgroundColorListener;

	public GeneralTab(Editor e)
	{
		super(e);
		createGeneralLabelsColumn();
		createGeneralParamsColumn();
	}

	private void createGeneralLabelsColumn()
	{
		addLabel(0, "pref-general-language");
		addLabel(1, "pref-general-startup");
		addLabel(2, "pref-general-graph-type");
		addLabel(3, "pref-general-edge-type");
		addLabel(4, "pref-general-graphPane-height");
		addLabel(5, "pref-general-textPane-height");
		addLabel(6, "pref-general-file-format");
		addLabel(7, "pref-general-file-encoding");
		addLabel(8, "pref-general-background-color");
	}

	private void createGeneralParamsColumn()
	{
		addComponent(0, createLanguageComboBox());
		addComponent(1, createYesNoComboBox());
		addComponent(2, createGraphTypeComboBox());
		addComponent(3, createEdgeTypeComboBox());
		addComponent(4, createGraphPaneHeightField());
		addComponent(5, createTextPaneHeightField());
		addComponent(6, createFileTypeComboBox());
		addComponent(7, createFileEncodingComboBox());
		addComponent(8, createBackgroundColorChooser());
	}

	private JComboBox<String> createLanguageComboBox()
	{
		Vector<String> vector = new Vector<>(2);
		vector.add("polski");
		vector.add("english");
		language = new JComboBox<>(vector);
		language.setSelectedItem(prop.getProperty("language"));
		return language;
	}

	private JComboBox<String> createYesNoComboBox()
	{
		Vector<String> vector = new Vector<>(2);
		vector.add(lang.getProperty("yes"));
		vector.add(lang.getProperty("no"));
		startup = new JComboBox<>(vector);
		String startupProp = prop.getProperty("show-new-graph-window-on-startup");
		if (startupProp.equals("true"))
			startup.setSelectedIndex(0);
		if (startupProp.equals("false"))
			startup.setSelectedIndex(1);
		return startup;
	}

	public JComboBox<String> createGraphTypeComboBox()
	{
		Vector<String> vector = new Vector<>(2);
		vector.add(lang.getProperty("graph-type-undirected"));
		vector.add(lang.getProperty("graph-type-directed"));
		graphType = new JComboBox<>(vector);
		String graphTypeProp = prop.getProperty("default-graph-type");
		if (graphTypeProp.equals("undirected"))
			graphType.setSelectedIndex(0);
		if (graphTypeProp.equals("directed"))
			graphType.setSelectedIndex(1);
		return graphType;
	}

	public JComboBox<Integer> createEdgeTypeComboBox()
	{
		Vector<Integer> edgeTypes = new Vector<>(3);
		edgeTypes.add(new Integer(0));
		edgeTypes.add(new Integer(1));
		edgeTypes.add(new Integer(2));
		edgeType = new JComboBox<>(edgeTypes);
		String edgeTypeProp = prop.getProperty("default-edge-type");
		if (edgeTypeProp.equals("0"))
			edgeType.setSelectedIndex(0);
		if (edgeTypeProp.equals("1"))
			edgeType.setSelectedIndex(1);
		if (edgeTypeProp.equals("2"))
			edgeType.setSelectedIndex(2);
		return edgeType;
	}

	private JFormattedTextField createGraphPaneHeightField()
	{
		graphPaneHeight = new JFormattedTextField(NumberFormat.getIntegerInstance());
		graphPaneHeight.setColumns(FIELD_SIZE);
		graphPaneHeight.setValue(new Integer(prop.getProperty("graphComponent-height")));
		return graphPaneHeight;
	}
	
	private JFormattedTextField createTextPaneHeightField()
	{
		textPaneHeight = new JFormattedTextField(NumberFormat.getIntegerInstance());
		textPaneHeight.setColumns(FIELD_SIZE);
		textPaneHeight.setValue(new Integer(prop.getProperty("textPane-height")));
		return textPaneHeight;
	}

	private JComboBox<String> createFileTypeComboBox()
	{
		Vector<String> vector = new Vector<>(3);
		vector.add("xml");
		vector.add("grf");
		vector.add("png");
		fileFormat = new JComboBox<>(vector);
		fileFormat.setSelectedItem(prop.getProperty("default-file-format"));
		return fileFormat;
	}
	
	private JComboBox<String> createFileEncodingComboBox()
	{
		Vector<String> vector = new Vector<>(3);
		vector.add("UTF-8");
		vector.add("windows-1250");
		vector.add("ISO-8859-1");
		vector.add("ISO-8859-2");
		fileEncoding = new JComboBox<>(vector);
		fileEncoding.setSelectedItem(prop.getProperty("file-encoding"));
		return fileEncoding;
	}
	
	private JPanel createBackgroundColorChooser()
	{
		Color backgroundColor = editor.getGraphComponent().getViewport().getBackground();
		backgroundColorListener = new ChangeColorListener(editor, backgroundColor);
		JPanel colorChooser = createColorChooser(backgroundColor, backgroundColorListener);
		return colorChooser;
	}

	public Properties getGeneralProperties()
	{
		Properties general = new Properties();
		general.setProperty("language", language.getSelectedItem().toString());
		if (startup.getSelectedIndex() == 0)
			general.setProperty("show-new-graph-window-on-startup", "true");
		else
			general.setProperty("show-new-graph-window-on-startup", "false");
		if (graphType.getSelectedIndex() == 0)
			general.setProperty("default-graph-type", "undirected");
		else
			general.setProperty("default-graph-type", "directed");
		general.setProperty("default-edge-type", edgeType.getSelectedItem().toString());
		general.setProperty("graphComponent-height", graphPaneHeight.getText());
		general.setProperty("textPane-height", textPaneHeight.getText());
		general.setProperty("default-file-format", fileFormat.getSelectedItem().toString());
		general.setProperty("file-encoding", fileEncoding.getSelectedItem().toString());
		general.setProperty("background-color", mxUtils.hexString(backgroundColorListener.getColor()));
		return general;
	}
}
