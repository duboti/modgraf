package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.mxgraph.view.mxGraph;

import modgraf.view.Editor;

/**
 * Klasa odpowiada za usunięcie zaznaczonych elementów grafu (wierzchołków i krawędzi).
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionRemoveSelected implements ActionListener 
{
	Editor editor;
	
	public ActionRemoveSelected(Editor e)
	{
		editor = e;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.getModel().beginUpdate();
		try
		{
			graph.removeCells();
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		editor.getGraphComponent().refresh();
	}

}
