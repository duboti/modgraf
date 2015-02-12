package modgraf.view.properties;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import layout.TableLayout;
import modgraf.view.Editor;

/**
 * Klasa bazowa dla klas odpowiadających za wyświetlanie zakładek.
 *
 * @author Daniel Pogrebniak
 */
public class PreferencesTab extends JPanel
{
	private static final long serialVersionUID = 5091363112463196059L;
	private static final double PARAMS_COLUMN_WIDTH = 0.37;
	private static final double SPACE_COLUMN_WIDTH = 0.03;
	private static final double LABEL_COLUMN_WIDTH = 0.6;
	private static final int ROW_COUNT = 9;
	private static final double ROW_HEIGHT = 30;
	public static final int FIELD_SIZE = 5;
	public static final int FONT_MINIMUM_SIZE = 1;
	public static final int FONT_MAXIMUM_SIZE = 100;
	protected Editor editor;
	protected Properties lang;
	protected Properties prop;

	protected PreferencesTab(Editor e)
	{
		editor = e;
		lang = e.getLanguage();
		prop = e.getProperties();
		createLayout();
	}
	
	public PreferencesTab(Editor e, double[][] size)
	{
		editor = e;
		lang = e.getLanguage();
		prop = e.getProperties();
		TableLayout tableLayout = new TableLayout(size);
		setLayout(tableLayout);
	}
	
	protected void createLayout()
	{
		double[] columns = { LABEL_COLUMN_WIDTH, SPACE_COLUMN_WIDTH, 
				PARAMS_COLUMN_WIDTH}; 
		double[] rows = new double[ROW_COUNT];
		for (int i = 0; i < ROW_COUNT; ++i)
			rows[i] = ROW_HEIGHT;
		TableLayout tableLayout = new TableLayout();
		tableLayout.setColumn(columns);
		tableLayout.setRow(rows);
		setLayout(tableLayout);
	}

	protected JPanel createColorChooser(Color color, ChangeColorListener ccl)
	{
		JPanel panel = new JPanel();
		FlowLayout layout = (FlowLayout) panel.getLayout();
		layout.setHgap(0);
		JTextField field = new JTextField();
		field.setColumns(FIELD_SIZE);
		field.setEnabled(false);
		field.setBackground(color);
		panel.add(field);
		ccl.setField(field);
		JButton change = new JButton(lang.getProperty("button-change"));
		change.addActionListener(ccl);
		panel.add(change);
		return panel;
	}
	
	public void addLabel(int row, String propertyName)
	{
		add(new JLabel(lang.getProperty(propertyName)), "0 "+row+" r c");
	}
	
	public void addComponent(int row, JComponent component)
	{
		add(component, "2 "+row+" l c");
	}

	public NumberFormatter createNumberFormatter(int min, int max)
	{
		NumberFormatter nf = new NumberFormatter();  
		nf.setMinimum(min);
		nf.setMaximum(max);
		return nf;
	}
	
	public String createHint(int from, int to)
	{
        return lang.getProperty("message-range-tip") + " "
				+ from + " " + lang.getProperty("to") + " "
				+ to + ".";
	}
	
}
