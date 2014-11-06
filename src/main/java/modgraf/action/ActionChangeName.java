package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;

import modgraf.event.EventLabelChangedListener;
import modgraf.view.Editor;

/**
 * Klasa odpowiada za zmianę nazwy wierzchołka lub wagi krawędzi.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionChangeName implements ActionListener 
{
	private Editor editor;
	private Properties lang;
	
	public ActionChangeName(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
	}
	
	/**
	 * Metoda zmienia nazwę wierzchołka lub wagę krawędzi.<br>
	 * Metoda jest wywoływana z menu kontekstowego <i>Zmień nazwę</i>.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		int selectionCount = editor.getGraphComponent().getGraph().getSelectionCount();
		if (selectionCount != 1)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-only-one-vertex"),
					lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			mxCell cell = (mxCell)editor.getGraphComponent().getGraph().getSelectionCell();
			String message = null;
			String title = null;
			if (cell.isVertex())
			{
				message = lang.getProperty("message-new-vertex-name");
				title = lang.getProperty("frame-change-name");
			}
			else
			{
				message = lang.getProperty("message-new-edge-name");
				title = lang.getProperty("frame-change-weight");
			}
			String value = (String) JOptionPane.showInputDialog(editor.getGraphComponent(), 
					message, title,	JOptionPane.PLAIN_MESSAGE, null, null, cell.getValue());
			if (value != null)
			{
				cell.setValue(value);
				new EventLabelChangedListener(editor).invoke(null, new mxEventObject(mxEvent.LABEL_CHANGED, "cell", cell));
				editor.getGraphComponent().refresh();
			}
		}
	}
}
