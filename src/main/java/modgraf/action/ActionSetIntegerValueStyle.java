package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JOptionPane;

import modgraf.view.Editor;

/**
 * Klasa odpowiada za zmianę grubości krawędzi, grubości obramowania wierzchołka i rozmiaru czcionki.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionSetIntegerValueStyle extends ActionSetStyle implements ActionListener
{
	private Properties lang;
	private int minWidth;
	private int maxWidth;
	private String frameName;
	
	public ActionSetIntegerValueStyle(Editor e, String styleName, String frameName, int minWidth, int maxWidth, boolean isVertex)
	{
		super(e, styleName, isVertex);
		this.lang = editor.getLanguage();
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.frameName = frameName;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String stringValue = (String) JOptionPane.showInputDialog(
				editor.getGraphComponent(),
				lang.getProperty("message-range-tip")+" "+
						minWidth+" "+lang.getProperty("to")+" "+maxWidth,
				lang.getProperty(frameName),
				JOptionPane.PLAIN_MESSAGE, null, null, "1");
		if (stringValue != null)
		{
			int intValue;
			try
			{
				intValue = Integer.parseInt(stringValue);
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("warning-not-number"),
						lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (intValue  >= minWidth && intValue <= maxWidth)
				setStyle(Integer.toString(intValue));
			else
			{
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("warning-not-proper-number"),
						lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
