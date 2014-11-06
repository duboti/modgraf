package modgraf;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import modgraf.action.ActionNewGraph;
import modgraf.view.Editor;

/**
 * Klasa startowa. Tworzy instancję klasy Editor i główne okno programu.
 * 
 * @author Daniel Pogrebniak
 *
 */
public class Main 
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e1)
		{
			JOptionPane.showMessageDialog(null,
					"Nie udało się ustawić systemowego stylu okien!\n" +
					"Został użyty styl domyślny",
					"Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
		}
		Editor editor = new Editor();
		editor.createFrame();
		if (editor.getProperties().getProperty("show-new-graph-window-on-startup").equals("true"))
			new ActionNewGraph(editor).actionPerformed(null);
	}
}
