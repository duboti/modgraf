package modgraf.event;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import modgraf.jgrapht.DoubleWeightedGraph;
import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;

/**
 * Zdarzenie <code>mxEvent.LABEL_CHANGED</code> na obiekcie {@link mxGraphComponent} 
 * pojawia się w momencie zmiany nazwy wierzchołka lub wagi krawędzi. <br>
 * Na początku wykonywane jest sprawdzenie czy zmiana została dokonana na 
 * wierzchołku czy krawędzi. Jeżeli zmiana dotyczyła krawędzi, to w zależności
 * od typu grafu (ważony, podwójnie ważony) zostaje wywołana odpowiednia metoda
 * z klasy {@link LabelChangedUtils} lub zostaje wyświetlony komunikat 
 * "Nie wolno ustawiać wag ani nazywać krawędzi w grafie nieważonym!". <br>
 * Jeżeli zmiana dotyczyła wierzchołka, to najpierw wykonywane jest sprawdzenie 
 * czy nazwa została zmieniona na inną. Następnie sprawdzane jest czy nowa nazwa 
 * nie występuje już w grafie. Jeżeli nazwa się powtarza, to użytkownik proszony 
 * jest o podanie nowej nazwy. Jeżeli podana zostanie odpowiednia nazwa, to 
 * zostanie ona zapisana. Jeśli nie to jako nazwa zostanie ustawiony kolejny 
 * numer wierzchołka. Na koniec wykonywana jest aktualizacja mapy nazw wierzchołków.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see mxIEventListener
 * @see LabelChangedUtils
 *
 */
public class EventLabelChangedListener extends LabelChangedUtils implements mxIEventListener
{
	public EventLabelChangedListener(Editor e)
	{
		super(e);
	}
	
	@Override
	public void invoke(Object sender, mxEventObject evt)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		mxCell cell = (mxCell)evt.getProperties().get("cell");
		if (cell.isEdge())
		{
			if (graphT instanceof WeightedGraph)
				changeValueForWeightedEdge(cell);
			else
			{
				if (graphT instanceof DoubleWeightedGraph)
					changeValueForDoubleWeightedEdge(cell);
				else
				{
					cell.setValue("");
					JOptionPane.showMessageDialog(editor.getGraphComponent(),
							lang.getProperty("warning-unweighted-edge"),
						    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if (cell.isVertex())
		{
			if (checkIfValueIsReallyChanged(cell))
				changeValueForVertex(cell);
		}
	}

	private boolean checkIfValueIsReallyChanged(mxCell cell)
	{
		String id = cell.getId();
		String value = (String)cell.getValue();
		return !id.equals(editor.getVertexId(value));
	}

	private void changeValueForVertex(mxCell cell)
	{
		String value = (String)cell.getValue();
		value = value.trim();
		while (value != null && (editor.getVertexValuesSet().contains(value) || value.equals("")))
		{
			value = JOptionPane.showInputDialog(editor.getGraphComponent(),
					lang.getProperty("warning-name-exists"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			if (value != null)
				value = value.trim();
		}
		if (value == null)
			cell.setValue(Integer.toString(editor.incrementAndGetNewVertexCounter()));
		else
			cell.setValue(value);
		updateVertexIdMap(cell);
	}

	private void updateVertexIdMap(mxCell cell)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		editor.setVertexId((String) cell.getValue(), cell.getId());
		if (graphT.vertexSet().size() < editor.getVertexValuesSet().size())
		{
			mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
			ArrayList<String> vertexValuesList = new ArrayList<>(editor.getVertexValuesSet());
			for (Vertex vertexId : graphT.vertexSet())
			{
				mxCell vertex = (mxCell)model.getCell(vertexId.getId());
				if (editor.getVertexValuesSet().contains(vertex.getValue().toString()))
					vertexValuesList.remove(vertex.getValue().toString());
			}
			editor.removeVertexId(vertexValuesList.get(0));
		}
        for (Vertex vertex : graphT.vertexSet())
        {
            if (vertex.getId().equals(cell.getId()))
            {
                vertex.setName(cell.getValue().toString());
                break;
            }
        }
	}
}
