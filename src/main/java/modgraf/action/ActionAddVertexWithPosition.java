package modgraf.action;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import modgraf.view.Editor;

import com.mxgraph.view.mxGraph;

/**
 * Klasa odpowiada za dodanie nowego wierzchołka w wybranym miejscu.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionAddVertexWithPosition implements ActionListener 
{
	private Editor editor;
	private Point point;
	
	/**
	 * 
	 * @param e obiekt klasy {@link Editor}
	 * @param p punkt w którym znajdzie lewy górny róg nowego wierzchołka
	 */
	public ActionAddVertexWithPosition(Editor e, Point p)
	{
		editor = e;
		point = p;
	}
	
	/**
	 * Metoda doadaje nowy wierzchołek w miejscu podanym w konstruktorze.<br>
	 * Metoda jest wywoływana z menu kontekstowego <i>Dodaj wierzchołek</i>.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		Properties prop = editor.getProperties();
		int width = Integer.parseInt(prop.getProperty("default-vertex-width"));
		int height = Integer.parseInt(prop.getProperty("default-vertex-height"));
		Object parent = graph.getDefaultParent();
		graph.insertVertex(parent, null, editor.incrementAndGetNewVertexCounter(), 
				point.x, point.y, width, height, "vertexStyle");
	}

}

