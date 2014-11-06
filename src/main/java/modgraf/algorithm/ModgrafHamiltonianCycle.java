package modgraf.algorithm;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

/**
 * Klasa rozwiązuje problem komiwojażera.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ModgrafAbstractAlgorithm
 * @see HamiltonianCycle
 * 
 */
public class ModgrafHamiltonianCycle extends ModgrafAbstractAlgorithm
{
	public ModgrafHamiltonianCycle(Editor e)
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (editor.getGraphT() instanceof SimpleWeightedGraph)
		{
			startAlgorithmWithoutParams();
		}
		else
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-wrong-graph-type")+
				    lang.getProperty("alg-cn-graph-type"),
				    "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void createGraphicalResult(List<Vertex> result)
	{
		int width = 4;
		for (int i = 0; i < result.size()-1; ++i)
		{
			changeEdgeStrokeWidth(result.get(i), result.get(i+1), width);
		}
		changeEdgeStrokeWidth(result.get(0), result.get(result.size()-1), width);
		editor.getGraphComponent().refresh();
	}

	private void createTextResult(List<Vertex> result)
	{
		StringBuilder sb = new StringBuilder();
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		sb.append(lang.getProperty("alg-hc-message-1"));
		Vertex vertexId = result.get(0);
		mxCell vertex = (mxCell)model.getCell(vertexId.getId());
		sb.append(vertex.getValue().toString());
		for (int i = 1; i < result.size(); ++i)
		{
			sb.append(", ");
			vertexId = result.get(i);
			vertex = (mxCell)model.getCell(vertexId.getId());
			sb.append(vertex.getValue().toString());
		}
		sb.append(".");
		editor.setText(sb.toString());
	}

	@Override
	public String getName()
	{
		return lang.getProperty("menu-algorithm-hamiltonian-cycle");
	}

	@Override
	protected void findAndShowResult()
	{
		List<Vertex> result = HamiltonianCycle.getApproximateOptimalForCompleteGraph(
				(SimpleWeightedGraph<Vertex, ModgrafEdge>)editor.getGraphT());
		if (result != null)
		{
			createTextResult(result);
			createGraphicalResult(result);
		}
		else
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("message-not-complete-graph"),
					lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
