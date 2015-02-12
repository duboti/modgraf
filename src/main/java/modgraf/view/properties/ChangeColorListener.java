package modgraf.view.properties;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JTextField;

import modgraf.view.Editor;

/**
 * Klasa obsługuje wszystkie przyciski służące do zmiany kolorów, które występują opcjach.
 *
 * @author Daniel Pogrebniak
 */
public class ChangeColorListener implements ActionListener
{
	
	private Editor editor;
	private Color color;
	private JTextField field;

	public ChangeColorListener(Editor e, Color color)
	{
		editor = e;
		this.color = color;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Color newColor = JColorChooser.showDialog(editor.getGraphComponent(),
				editor.getLanguage().getProperty("frame-select-color"), null);
		if (newColor != null)
		{
			color = newColor;
			if (field != null)
				field.setBackground(newColor);
		}
	}

	public Color getColor()
	{
		return color;
	}
	
	public void setField(JTextField field)
	{
		this.field = field;
	}
}
