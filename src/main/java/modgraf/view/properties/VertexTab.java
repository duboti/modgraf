package modgraf.view.properties;

import static com.mxgraph.util.mxConstants.SHAPE_ACTOR;
import static com.mxgraph.util.mxConstants.SHAPE_CLOUD;
import static com.mxgraph.util.mxConstants.SHAPE_CYLINDER;
import static com.mxgraph.util.mxConstants.SHAPE_ELLIPSE;
import static com.mxgraph.util.mxConstants.SHAPE_HEXAGON;
import static com.mxgraph.util.mxConstants.SHAPE_RECTANGLE;
import static com.mxgraph.util.mxConstants.SHAPE_RHOMBUS;
import static com.mxgraph.util.mxConstants.SHAPE_TRIANGLE;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import modgraf.view.Editor;

import com.mxgraph.util.mxUtils;

public class VertexTab extends PreferencesTab
{
	private static final long serialVersionUID = -1343713811059005780L;
	public static final int VERTEX_MINIMUM_SIZE = 1;
	public static final int VERTEX_MAXIMUM_SIZE = 1000;
	public static final int BORDER_MINIMUM_WIDTH = 0; 
	public static final int BORDER_MAXIMUM_WIDTH = 100;
	private JComboBox<String> shape;
	private JFormattedTextField height;
	private JFormattedTextField width;
	private JFormattedTextField borderWidth;
	private ChangeColorListener fillColorListener;
	private ChangeColorListener borderColorListener;
	private JComboBox<Object> fontFamily;
	private JFormattedTextField fontSize;
	private ChangeColorListener fontColorListener;

	public VertexTab(Editor e)
	{
		super(e);
		createVertexLabelsColumn();
		createVertexParamsColumn();
	}

	private void createVertexLabelsColumn()
	{
		addLabel(0, "pref-vertex-shape");
		addLabel(1, "pref-vertex-height");
		addLabel(2, "pref-vertex-width");
		addLabel(3, "pref-vertex-border-width");
		addLabel(4, "pref-vertex-fill-color");
		addLabel(5, "pref-vertex-border-color");
		addLabel(6, "pref-vertex-font-family");
		addLabel(7, "pref-vertex-font-size");
		addLabel(8, "pref-vertex-font-color");
	}

	private void createVertexParamsColumn()
	{
		addComponent(0, createShapeComboBox());
		addComponent(1, createHeightField());
		addComponent(2, createWidthField());
		addComponent(3, createBorderWidthField());
		addComponent(4, createFillColorChooser());
		addComponent(5, createBorderColorChooser());
		addComponent(6, createFontFamilyField());
		addComponent(7, createFontSizeField());
		addComponent(8, createFontColorChooser());
	}

	private JComponent createShapeComboBox()
	{
		Vector<String> vector = new Vector<>(8);
		vector.add(lang.getProperty("menu-vertex-shape-circle"));
		vector.add(lang.getProperty("menu-vertex-shape-square"));
		vector.add(lang.getProperty("menu-vertex-shape-rhombus"));
		vector.add(lang.getProperty("menu-vertex-shape-cloud"));
		vector.add(lang.getProperty("menu-vertex-shape-hexagon"));
		vector.add(lang.getProperty("menu-vertex-shape-triangle"));
		vector.add(lang.getProperty("menu-vertex-shape-actor"));
		vector.add(lang.getProperty("menu-vertex-shape-cylinder"));
		shape = new JComboBox<>(vector);
		shape.setSelectedItem(prop.getProperty("default-vertex-shape"));
		return shape;
	}
	
	private JComponent createHeightField()
	{
		height = new JFormattedTextField(
				createNumberFormatter(VERTEX_MINIMUM_SIZE, VERTEX_MAXIMUM_SIZE));
		height.setColumns(FIELD_SIZE);
		height.setToolTipText(createHint(VERTEX_MINIMUM_SIZE, VERTEX_MAXIMUM_SIZE));
		height.setValue(new Integer(prop.getProperty("default-vertex-height")));
		return height;
	}
	
	private JComponent createWidthField()
	{
		width = new JFormattedTextField(
				createNumberFormatter(VERTEX_MINIMUM_SIZE, VERTEX_MAXIMUM_SIZE));
		width.setColumns(FIELD_SIZE);
		width.setToolTipText(createHint(VERTEX_MINIMUM_SIZE, VERTEX_MAXIMUM_SIZE));
		width.setValue(new Integer(prop.getProperty("default-vertex-width")));
		return width;
	}

	private JComponent createBorderWidthField()
	{
		borderWidth = new JFormattedTextField(
				createNumberFormatter(BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH));
		borderWidth.setColumns(FIELD_SIZE);
		borderWidth.setToolTipText(createHint(BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH));
		borderWidth.setValue(new Integer(prop.getProperty("default-vertex-border-width")));
		return borderWidth;
	}

	private JComponent createFillColorChooser()
	{
		Color fillColor = mxUtils.parseColor(prop.getProperty("default-vertex-fill-color"));
		fillColorListener = new ChangeColorListener(editor, fillColor);
		JPanel colorChooser = createColorChooser(fillColor, fillColorListener);
		return colorChooser;
	}

	private JComponent createBorderColorChooser()
	{
		Color borderColor = mxUtils.parseColor(prop.getProperty("default-vertex-border-color"));
		borderColorListener = new ChangeColorListener(editor, borderColor);
		JPanel colorChooser = createColorChooser(borderColor, borderColorListener);
		return colorChooser;
	}
	
	private JComponent createFontFamilyField()
	{
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		List<String> fonts = new ArrayList<String>();
		fonts.addAll(Arrays.asList(new String[] { "Helvetica", "Verdana",
				"Times New Roman", "Garamond", "Courier New", "Arial", "---" }));
		fonts.addAll(Arrays.asList(env.getAvailableFontFamilyNames()));
		fontFamily = new JComboBox<>(fonts.toArray());
		fontFamily.setSelectedItem(prop.getProperty("default-vertex-font-family"));
		return fontFamily;
	}
	
	private JComponent createFontSizeField()
	{
		fontSize = new JFormattedTextField(
				createNumberFormatter(FONT_MINIMUM_SIZE, FONT_MAXIMUM_SIZE));
		fontSize.setColumns(FIELD_SIZE);
		fontSize.setToolTipText(createHint(FONT_MINIMUM_SIZE, FONT_MAXIMUM_SIZE));
		fontSize.setValue(new Integer(prop.getProperty("default-vertex-font-size")));
		return fontSize;
	}

	private JComponent createFontColorChooser()
	{
		Color fontColor = mxUtils.parseColor(prop.getProperty("default-vertex-font-color"));
		fontColorListener = new ChangeColorListener(editor, fontColor);
		JPanel colorChooser = createColorChooser(fontColor, fontColorListener);
		return colorChooser;
	}
	
	public Properties getVertexProperties()
	{
		Properties vertex = new Properties();
		vertex.setProperty("default-vertex-shape", getSelectedShape());
		vertex.setProperty("default-vertex-height", height.getText());
		vertex.setProperty("default-vertex-width", width.getText());
		vertex.setProperty("default-vertex-border-width", borderWidth.getText());
		vertex.setProperty("default-vertex-fill-color", mxUtils.hexString(fillColorListener.getColor()));
		vertex.setProperty("default-vertex-border-color", mxUtils.hexString(borderColorListener.getColor()));
		vertex.setProperty("default-vertex-font-family", fontFamily.getSelectedItem().toString());
		vertex.setProperty("default-vertex-font-size", fontSize.getText());
		vertex.setProperty("default-vertex-font-color", mxUtils.hexString(fontColorListener.getColor()));
		return vertex;
	}
	
	private String getSelectedShape()
	{
		switch (shape.getSelectedIndex())
		{
			case 0: return SHAPE_ELLIPSE;
			case 1: return SHAPE_RECTANGLE;
			case 2: return SHAPE_RHOMBUS;
			case 3: return SHAPE_CLOUD;
			case 4: return SHAPE_HEXAGON;
			case 5: return SHAPE_TRIANGLE;
			case 6: return SHAPE_ACTOR;
			case 7: return SHAPE_CYLINDER;
			default: return SHAPE_ELLIPSE;
		}
	}
}
