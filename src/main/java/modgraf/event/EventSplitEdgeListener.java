package modgraf.event;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

/**
 * Zdarzenie <code>mxEvent.SPLIT_EDGE</code> na obiekcie {@link mxGraph} 
 * pojawia się w momencie wstawienia wierzchołka w środek istniejącej 
 * krawędzi. Po tej operacji ów wierzchołek jest od razu połączony z 
 * dwoma krawędziami. <br>
 * Z punktu widzenia warstwy matematycznej ta operacja składa się z 
 * usunięcia jednej krawędzi i dodania dwóch nowych. 
 * 
 * @author Daniel Pogrebniak
 * 
 * @see mxIEventListener
 * @see EventAddCellsListener
 * @see EventRemoveCellsListener
 *
 */
public class EventSplitEdgeListener implements mxIEventListener
{
	private Editor editor;

	public EventSplitEdgeListener(Editor e)
	{
		editor = e;
	}
	
	@Override
	public void invoke(Object sender, mxEventObject evt)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		mxCell edge = (mxCell) evt.getProperties().get("edge");
		mxCell newEdge = (mxCell) evt.getProperties().get("newEdge");
		ModgrafEdge removedEdge = graphT.removeEdge(new Vertex(newEdge.getSource()),new Vertex(edge.getTarget()));
		graphT.addEdge(new Vertex(edge.getSource()),new Vertex(edge.getTarget()), removedEdge);
		editor.removeEdgeId(newEdge.getSource().getId(), edge.getTarget().getId());
		editor.setEdgeId(edge.getSource().getId(), edge.getTarget().getId(), edge.getId());
		if (graphT instanceof UndirectedGraph)
		{
			editor.removeEdgeId(edge.getTarget().getId(), newEdge.getSource().getId());
			editor.setEdgeId(edge.getTarget().getId(), edge.getSource().getId(), edge.getId());
		}
	}
}
