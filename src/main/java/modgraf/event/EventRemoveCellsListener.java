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
 * Zdarzenie <code>mxEvent.REMOVE_CELLS</code> na obiekcie {@link mxGraph}
 * pojawia się w momencie usunięcia wierzchołka lub krawędzi. <br>
 * Jeżeli w warstwie wizualnej usunięty został wierzchołek lub krawędź, 
 * to w warstwie matematycznej także zostanie usunięty odpowiadający 
 * mu wierzchołek lub krawędź i zostanie zaktualizowana mapa z nazwami 
 * wierzchołków lub mapa krawędzi.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see mxIEventListener
 *
 */
public class EventRemoveCellsListener implements mxIEventListener
{
	private Editor editor;

	public EventRemoveCellsListener(Editor e)
	{
		editor = e;
	}
	
	@Override
	public void invoke(Object sender, mxEventObject evt)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		Object[] cells = (Object[])evt.getProperties().get("cells");
		if (cells != null)
		{
			for (int i = 0; i < cells.length; ++i)
			{
				if (cells[i] instanceof mxCell)
				{
					mxCell cell = (mxCell)cells[i];
					if (cell.isVertex())
					{
						graphT.removeVertex(new Vertex(cell));
						editor.removeVertexId(cell.getValue().toString());
					}
					if (cell.isEdge())
					{
						graphT.removeEdge(new Vertex(cell.getSource()), new Vertex(cell.getTarget()));
						editor.removeEdgeId(cell.getSource().getId(), cell.getTarget().getId());
						if (graphT instanceof UndirectedGraph)
							editor.removeEdgeId(cell.getTarget().getId(), cell.getSource().getId());
					}
				}
			}
		}
	}
}
