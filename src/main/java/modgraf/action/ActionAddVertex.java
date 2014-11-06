package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import modgraf.view.Editor;

import com.mxgraph.view.mxGraph;

/**
 * Klasa odpowiada za dodanie nowego wierzchołka w lewym górnym rogu.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionAddVertex implements ActionListener 
{
	private Editor editor;
	
	public ActionAddVertex(Editor e)
	{
		editor = e;
	}
	
	/**
	 * Metoda odpowiada za dodanie nowego wierzchołka w lewym górnym rogu.<br>
	 * Metoda jest wywoływana z menu <i>Wierzchołek --> Dodaj wierzchołek</i>.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		Properties prop = editor.getProperties();
		int width = Integer.parseInt(prop.getProperty("default-vertex-width"));
		int height = Integer.parseInt(prop.getProperty("default-vertex-height"));
		Object parent = graph.getDefaultParent();
		int vertexPosition = editor.getVertexCounter() * 5;
		graph.insertVertex(parent, null, editor.incrementAndGetNewVertexCounter(), 
				vertexPosition, vertexPosition, width, height, "vertexStyle");
	}

}

