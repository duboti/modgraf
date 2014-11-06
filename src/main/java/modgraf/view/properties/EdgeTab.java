package modgraf.view.properties;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import com.mxgraph.util.mxUtils;

import modgraf.view.Editor;

public class EdgeTab extends PreferencesTab 
{

	private static final long serialVersionUID = -4175778718725440149L;
	private JFormattedTextField width;
	private ChangeColorListener edgeColorListener;
	private JFormattedTextField weight;
	private JFormattedTextField cost;
	private JFormattedTextField capacity;
	private JFormattedTextField fontSize;
	private ChangeColorListener fontColorListener;
	private DecimalFormat doubleFormat;
	
	public EdgeTab(Editor e) 
	{
		super(e);
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
		symbols.setDecimalSeparator('.');
		doubleFormat = new DecimalFormat("#.######", symbols);
		createVertexLabelsColumn();
		createVertexParamsColumn();
	}

	private void createVertexLabelsColumn() 
	{
		addLabel(0, "pref-edge-width");
		addLabel(1, "pref-edge-color");
		addLabel(2, "pref-edge-weight");
		addLabel(3, "pref-edge-capacity");
		addLabel(4, "pref-edge-cost");
		addLabel(5, "pref-edge-font-size");
		addLabel(6, "pref-edge-font-color");
	}

	private void createVertexParamsColumn() 
	{
		addComponent(0, createWidthField());
		addComponent(1, createEdgeColorChooser());
		addComponent(2, createWeightField());
		addComponent(3, createCapacityField());
		addComponent(4, createCostField());
		addComponent(5, createFontSizeField());
		addComponent(6, createFontColorChooser());
	}

	private JComponent createWidthField() 
	{
		width = new JFormattedTextField(NumberFormat.getIntegerInstance());
		width.setColumns(FIELD_SIZE);
		width.setValue(new Integer(prop.getProperty("default-edge-width")));
		return width;
	}

	private JComponent createEdgeColorChooser()
	{
		Color fillColor = mxUtils.parseColor(prop.getProperty("default-edge-color"));
		edgeColorListener = new ChangeColorListener(editor, fillColor);
		JPanel colorChooser = createColorChooser(fillColor, edgeColorListener);
		return colorChooser;
	}
	
	private JComponent createWeightField() 
	{
		weight = new JFormattedTextField(doubleFormat);
		weight.setColumns(FIELD_SIZE);
		weight.setValue(new Double(prop.getProperty("default-edge-weight")));
		return weight;
	}
	
	private JComponent createCapacityField() 
	{
		capacity = new JFormattedTextField(doubleFormat);
		capacity.setColumns(FIELD_SIZE);
		capacity.setValue(new Double(prop.getProperty("default-edge-capacity")));
		return capacity;
	}
	
	private JComponent createCostField() 
	{
		cost = new JFormattedTextField(doubleFormat);
		cost.setColumns(FIELD_SIZE);
		cost.setValue(new Double(prop.getProperty("default-edge-cost")));
		return cost;
	}
	
	private JComponent createFontSizeField()
	{
		fontSize = new JFormattedTextField(
				createNumberFormatter(FONT_MINIMUM_SIZE, FONT_MAXIMUM_SIZE));
		fontSize.setColumns(FIELD_SIZE);
		fontSize.setToolTipText(createHint(FONT_MINIMUM_SIZE, FONT_MAXIMUM_SIZE));
		fontSize.setValue(new Integer(prop.getProperty("default-edge-font-size")));
		return fontSize;
	}

	private JComponent createFontColorChooser()
	{
		Color fontColor = mxUtils.parseColor(prop.getProperty("default-edge-font-color"));
		fontColorListener = new ChangeColorListener(editor, fontColor);
		JPanel colorChooser = createColorChooser(fontColor, fontColorListener);
		return colorChooser;
	}
	
	public Properties getEdgeProperties() 
	{
		Properties edge = new Properties();
		edge.setProperty("default-edge-width", width.getText());
		edge.setProperty("default-edge-color", mxUtils.hexString(edgeColorListener.getColor()));
		edge.setProperty("default-edge-weight", weight.getText());
		edge.setProperty("default-edge-capacity", capacity.getText());
		edge.setProperty("default-edge-cost", cost.getText());
		edge.setProperty("default-edge-font-size", fontSize.getText());
		edge.setProperty("default-edge-font-color", mxUtils.hexString(fontColorListener.getColor()));
		return edge;
	}

}
