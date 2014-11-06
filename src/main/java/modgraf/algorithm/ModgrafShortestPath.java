package modgraf.algorithm;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

/**
 * Klasa rozwiązuje problem najkrótsza ścieżka.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ModgrafAbstractAlgorithm
 * @see BellmanFordShortestPath
 * 
 */
public class ModgrafShortestPath extends ModgrafAbstractAlgorithm
{
	public enum Algorithm
	{
		BellmanFord,
		Dijkstra,
		FloydWarshall;
	}
	
	private Algorithm algorithm;
	
	public ModgrafShortestPath(Editor e, Algorithm alg)
	{
		super(e);
		algorithm = alg;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (editor.getGraphT() instanceof WeightedGraph)
			openParamsWindow();
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-wrong-graph-type")+
				    lang.getProperty("alg-sp-graph-type"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
	}
	
	private void createGraphicalResult(List<ModgrafEdge> result)
	{
		int width = 4;
		changeVertexStrokeWidth(startVertex, width);
		changeVertexStrokeWidth(endVertex, width);
		for (ModgrafEdge edge : result)
			changeEdgeStrokeWidth(edge, width);
		editor.getGraphComponent().refresh();
	}

	private void createTextResult(List<ModgrafEdge> result)
	{
		StringBuilder sb = new StringBuilder();
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		sb.append(lang.getProperty("alg-sp-message-1"));
		sb.append(startVertexComboBox.getSelectedItem());
		sb.append(lang.getProperty("alg-sp-message-2"));
		sb.append(endVertexComboBox.getSelectedItem());
		sb.append(lang.getProperty("alg-sp-message-3"));
		if (result.size() == 1)
			sb.append(lang.getProperty("alg-sp-message-4"));
		else
		{
			sb.append(result.size());
			if (result.size() > 1 && result.size() < 5)
				sb.append(lang.getProperty("alg-sp-message-5"));
			if (result.size() > 4 )
				sb.append(lang.getProperty("alg-sp-message-6"));
			sb.append(lang.getProperty("alg-sp-message-7"));
			String start = (String)startVertexComboBox.getSelectedItem().toString();
			sb.append(start);
			ArrayList<String> vertexIdList = new ArrayList<>();
			vertexIdList.add(editor.getVertexId(start));
			for (int i = 0; i < result.size(); ++i)
			{
				sb.append(", ");
				
				Vertex vertex = result.get(i).getTarget();
				if (vertexIdList.contains(vertex.getId()))
					vertex = result.get(i).getSource();
				vertexIdList.add(vertex.getId());
				mxCell vertexCell = (mxCell)model.getCell(vertex.getId());
				vertexIdList.add(vertexCell.getValue().toString());
				sb.append(vertexCell.getValue().toString());
			}
			sb.append(".");
		}
		editor.setText(sb.toString());
	}

	@Override
	public String getName()
	{
		return lang.getProperty("menu-algorithm-shortest-path");
	}

	@Override
	protected void findAndShowResult()
	{
		List<ModgrafEdge> result = createResult();
		if (result != null)
		{
			createTextResult(result);
			createGraphicalResult(result);
		}
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("message-no-solution"),
					lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
	}

	private List<ModgrafEdge> createResult()
	{
		List<ModgrafEdge> result = null;
		if (algorithm == Algorithm.BellmanFord)
			result = BellmanFordShortestPath.findPathBetween(
					editor.getGraphT(), startVertex, endVertex);
		if (algorithm == Algorithm.Dijkstra)
			result = DijkstraShortestPath.findPathBetween(
					editor.getGraphT(), startVertex, endVertex);
		if (algorithm == Algorithm.FloydWarshall)
		{
			FloydWarshallShortestPaths<Vertex, ModgrafEdge> fwsp = 
					new FloydWarshallShortestPaths<Vertex, ModgrafEdge>(editor.getGraphT());
			GraphPath<Vertex, ModgrafEdge> path = fwsp.getShortestPath(startVertex, endVertex);
			result = path.getEdgeList();
		}
		return result;
	}
}
