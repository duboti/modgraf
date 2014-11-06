package modgraf.event;

import java.util.Properties;

import javax.swing.JOptionPane;

import modgraf.jgrapht.DoubleWeightedGraph;
import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

/**
 * Zdarzenie <code>mxEvent.CONNECT_CELL</code> na obiekcie {@link mxGraph} 
 * pojawia się w momencie przełożenia krawędzi między wierzchołkami. <br>
 * Jeżeli w warstwie wizualnej krawędź zostanie przeniesiona, to wykonywane 
 * jest sprawdzenie czy taka czynność jest dozwolona. Jeżeli tak to w warstwie 
 * matematycznej usuwana jest stara krawędź i dodawana jest w nowym miejscu. 
 * Analogiczne zmiany dokonywane są w mapie krawędzi. Jeśli zmiana nie jest 
 * dozwolona, to przeniesiona krawędź w warstwie wizualnej jest usuwana i 
 * zostaje wyświetlony komunikat "Nie można dodać krawędzi! Prawdopodobnie 
 * istniała już wcześniej".
 * 
 * @author Daniel Pogrebniak
 * 
 * @see mxIEventListener
 *
 */
public class EventConnectCellListener implements mxIEventListener
{
	private Editor editor;
	private Graph<Vertex, ModgrafEdge> graphT;

	public EventConnectCellListener(Editor e)
	{
		editor = e;
	}
	
	@Override
	public void invoke(Object sender, mxEventObject evt)
	{
		mxCell previous = (mxCell)evt.getProperties().get("previous");
		mxCell edge = (mxCell)evt.getProperties().get("edge");
		Boolean source = (Boolean)evt.getProperties().get("source");
		if (previous != null && edge != null && source != null)
		{
			String edgeId = editor.getEdgeId(edge.getSource().getId(), edge.getTarget().getId());
			if (edgeId == null)
				changeEdgeInGraphT(edge, previous.getId(), source);
			else
				removeEdgeAndShowWarning(edge);
		}
	}

	private void removeEdgeAndShowWarning(mxCell edge)
	{
		editor.getGraphComponent().getGraph().getModel().remove(edge);
		Properties lang = editor.getLanguage();
		JOptionPane.showMessageDialog(editor.getGraphComponent(),
				lang.getProperty("warning-not-add-edge"),
			    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
	}

	private void changeEdgeInGraphT(mxCell edge, String previousId, boolean source)
	{
		graphT = editor.getGraphT();
		addEdge(edge);
		
		if (source)
			removeEdge(previousId, edge.getTarget().getId());
		else
			removeEdge(edge.getSource().getId(), previousId);
		
		LabelChangedUtils lcu = new LabelChangedUtils(editor);
		if (graphT instanceof WeightedGraph)
			lcu.changeValueForWeightedEdge(edge);
		if (graphT instanceof DoubleWeightedGraph)
			lcu.changeValueForDoubleWeightedEdge(edge);
	}

	private void removeEdge(String source, String target)
	{
		Vertex sourceVertex = new Vertex(source, null);
		Vertex targetVertex = new Vertex(target, null);
		graphT.removeEdge(sourceVertex, targetVertex);
		editor.removeEdgeId(source, target);
		if (graphT instanceof UndirectedGraph)
			editor.removeEdgeId(target, source);
	}
	
	private void addEdge(mxCell edge)
	{
		Vertex source = new Vertex(edge.getSource());
		Vertex target = new Vertex(edge.getTarget());
		graphT.addEdge(source, target);
		String id = edge.getId();
		graphT.addEdge(source, target);
		editor.setEdgeId(source.getId(), target.getId(), id);
		if (graphT instanceof UndirectedGraph)
			editor.setEdgeId(target.getId(), source.getId(), id);
	}
}
