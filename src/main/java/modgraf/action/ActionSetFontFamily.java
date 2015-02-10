package modgraf.action;

import static com.mxgraph.util.mxConstants.STYLE_FONTFAMILY;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import modgraf.view.Editor;

/**
 * Klasa odpowiada za zmianę parametru wierzchołka związanego z kolorem.
 *
 * @author Daniel Pogrebniak
 *
 * @see ActionSetStyle
 * @see ActionListener
 */
public class ActionSetFontFamily extends ActionSetStyle implements ActionListener
{
	private Properties lang;
	private Properties prop;
	
	public ActionSetFontFamily(Editor e, boolean isVertex)
	{
		super(e, STYLE_FONTFAMILY, isVertex);
		lang = editor.getLanguage();
		prop = e.getProperties();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		List<String> fonts = new ArrayList<>();
		fonts.addAll(Arrays.asList("Helvetica", "Verdana",
                "Times New Roman", "Garamond", "Courier New", "Arial", "---"));
		fonts.addAll(Arrays.asList(env.getAvailableFontFamilyNames()));
		String newValue = (String)JOptionPane.showInputDialog(
				editor.getGraphComponent(),
				null,
				lang.getProperty("frame-select-font-family"),
				JOptionPane.PLAIN_MESSAGE,
				null, fonts.toArray(),
				prop.getProperty("default-vertex-font-family"));
		if (newValue != null)
			setStyle(newValue);
	}
}

