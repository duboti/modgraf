package modgraf.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import modgraf.view.Editor;

import com.mxgraph.util.mxUtils;

/**
 * Klasa odpowiada za zmianę parametru wierzchołka związanego z kolorem.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionSetStyle
 * @see ActionListener
 */
public class ActionSetColor extends ActionSetStyle implements ActionListener
{
	private String frameName;
	
	public ActionSetColor(Editor e, String styleName, String frameName, boolean isVertex)
	{
		super(e, styleName, isVertex);
		this.frameName = frameName;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		Color newColor = JColorChooser.showDialog(editor.getGraphComponent(),
				editor.getLanguage().getProperty(frameName), null);
		if (newColor != null)
			setStyle(mxUtils.hexString(newColor));
	}
}
