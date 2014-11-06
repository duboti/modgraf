package modgraf.algorithm;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.EdmondsKarpMaximumFlow;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

/**
 * Klasa rozwiązuje problem maksymalnego przepływu.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ModgrafAbstractAlgorithm
 * @see EdmondsKarpMaximumFlow
 * 
 */
public class ModgrafEdmondsKarpMaximumFlow extends ModgrafAbstractAlgorithm
{

	public ModgrafEdmondsKarpMaximumFlow(Editor e)
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (editor.getGraphT() instanceof DirectedGraph && editor.getGraphT() instanceof WeightedGraph)
			openParamsWindow();
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-wrong-graph-type")+
				    lang.getProperty("alg-mf-graph-type"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public String getName()
	{
		return lang.getProperty("menu-algorithm-maximum-flow");
	}

	private void createTextResult(EdmondsKarpMaximumFlow<Vertex,ModgrafEdge> ekmf)
	{
		StringBuilder sb = new StringBuilder();
		String newLine = "\n";
		sb.append(lang.getProperty("alg-mf-message-1"));
		sb.append(ekmf.getMaximumFlowValue());
		sb.append(newLine);
		sb.append(lang.getProperty("alg-mf-message-2"));
		sb.append(newLine);
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		Map<ModgrafEdge, Double> resultMap = ekmf.getMaximumFlow();
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		for (Entry<ModgrafEdge, Double> entry : resultMap.entrySet())
		{
			double flow = entry.getValue().doubleValue();
			if (flow > 0)
			{
				ModgrafEdge edge = entry.getKey();
				double capacity =  graphT.getEdgeWeight(edge);
				Vertex sourceId = graphT.getEdgeSource(edge);
				Vertex targetId = graphT.getEdgeTarget(edge);
				mxCell cellSource = (mxCell) model.getCell(sourceId.getId());
				mxCell cellTarget = (mxCell) model.getCell(targetId.getId());
				String source = cellSource.getValue().toString();
				String target = cellTarget.getValue().toString();
				sb.append(source);
				sb.append(" : ");
				sb.append(target);
				sb.append(" --> ");
				sb.append(flow);
				sb.append("/");
				sb.append(capacity);
				sb.append(newLine);
			}
		}
		editor.setText(sb.toString());
	}

	private void createGraphicalResult(EdmondsKarpMaximumFlow<Vertex,ModgrafEdge> ekmf)
	{
		int width = 4;
		int halfWidth = 2;
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		changeVertexStrokeWidth(startVertex, width);
		changeVertexStrokeWidth(endVertex, width);
		Map<ModgrafEdge, Double> resultMap = ekmf.getMaximumFlow();
		for (Entry<ModgrafEdge, Double> entry : resultMap.entrySet())
		{
			double flow = entry.getValue().doubleValue();
			if (flow > 0)
			{
				ModgrafEdge edge = entry.getKey();
				double capacity =  graphT.getEdgeWeight(edge);
				if (flow == capacity)
					changeEdgeStrokeWidth(edge, width);
				if (flow < capacity)
					changeEdgeStrokeWidth(edge, halfWidth);
			}
		}
		editor.getGraphComponent().refresh();
	}

	@Override
	protected void findAndShowResult()
	{
		EdmondsKarpMaximumFlow<Vertex, ModgrafEdge> ekmf = 
				new EdmondsKarpMaximumFlow<>((DirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT());
		ekmf.calculateMaximumFlow(startVertex, endVertex);
		if (ekmf.getMaximumFlowValue() > 0)
		{
			createTextResult(ekmf);
			createGraphicalResult(ekmf);
		}
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("message-no-solution"),
					lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
	}

}
