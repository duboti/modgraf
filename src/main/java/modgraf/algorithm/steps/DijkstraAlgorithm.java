package modgraf.algorithm.steps;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.jgrapht.edge.WeightedEdge;
import modgraf.view.Editor;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

public class DijkstraAlgorithm extends ModgrafAbstractStepsAlgorithm
{
	private boolean first;
	private Map<String, Double> distances;
	private Set<Vertex> vertexSet;
	private PriorityQueue<DistanceVertex> queue;
	private ArrayList<ModgrafEdge> edgesList;
	private DistanceVertex vertexU;
	private Vertex vertexV;
	private ModgrafEdge edgeV;
	private boolean edgeVchecked;
	private Set<Vertex> markedVertexSet;

	public DijkstraAlgorithm(Editor e)
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		first = true;
		if (editor.getGraphT() instanceof WeightedGraph)
			openParamsWindow();
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-wrong-graph-type")+
				    lang.getProperty("alg-sp-graph-type"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
	}
	
	@Override
	public String getName()
	{
		return "Alg. Dijksrty";
	}
	
	@Override
	protected void runOneStep()
	{
		if (first)
			firstStep();
		else
		{
			if (!endVertex.equals(vertexU)) 
			{
				if (edgesList.isEmpty() && edgeVchecked)
					selectNextVertex();
				else
				{
					if (edgeVchecked)
						selectNextEdge();
					else
						checkDistance();
				}
			}
			else{
				createTextResult();
				changeState(State.End);
			}
		}
	}

	private void selectNextVertex()
	{
		if (edgeV != null && vertexV != null)
		{
			changeEdgeStrokeWidth(edgeV, 1);
			if (!markedVertexSet.contains(vertexV))
				changeVertexStrokeWidth(vertexV, 1);
			edgeV = null;
			vertexV = null;
		}
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		vertexU = queue.poll();
		Set<ModgrafEdge> edgesSet = null;
		if (graphT instanceof DirectedGraph)
			edgesSet = ((DirectedGraph<Vertex, ModgrafEdge>)graphT).outgoingEdgesOf(vertexU);
		else
			edgesSet = graphT.edgesOf(vertexU);
		edgesList = new ArrayList<>(edgesSet);
		changeVertexStrokeWidth(vertexU, 4);
		markedVertexSet.add(vertexU);
		if (vertexU.getPrevious() != null)
			changeEdgeStrokeWidth(vertexU.getPrevious(), 4);
		edgeVchecked = true;
	}
	
	private void selectNextEdge()
	{
		if (edgeV != null && vertexV != null)
		{
			changeEdgeStrokeWidth(edgeV, 1);
			if (!markedVertexSet.contains(vertexV))
				changeVertexStrokeWidth(vertexV, 1);
		}
		edgeV = edgesList.get(0);
		edgesList.remove(0);
		vertexV = edgeV.getOtherVertex(vertexU);
		changeEdgeStrokeWidth(edgeV, 2);
		if (!markedVertexSet.contains(vertexV))
		{
			changeVertexStrokeWidth(vertexV, 2);
			edgeVchecked = false;
		}
		else
			edgeVchecked = true;
	}
	
	private void checkDistance()
	{
		double distanceThroughU = distances.get(vertexU.getId()) + ((WeightedEdge)edgeV).getWeight();
		if (distanceThroughU < distances.get(vertexV.getId()))
		{
			queue.remove(new DistanceVertex(vertexV, 0.0, null));
			distances.put(vertexV.getId(), distanceThroughU);
			queue.add(new DistanceVertex(vertexV, distanceThroughU, edgeV));
			showDistances();
		}
		edgeVchecked = true;
	}
	
	class DistanceVertex extends Vertex implements Comparable<DistanceVertex>
	{
		private static final long serialVersionUID = -1504310465327879043L;
		private double distance;
		private ModgrafEdge previous;
		
		public DistanceVertex(Vertex v, double d, ModgrafEdge e) 
		{
			super(v.getId(), v.getName());
			previous = e;
			setDistance(d);
		}
		
		public double getDistance() 
		{
			return distance;
		}
		
		public void setDistance(double distance) 
		{
			this.distance = distance;
		}
		
		public ModgrafEdge getPrevious()
		{
			return previous;
		}
		
		public void setPrevious(ModgrafEdge previous)
		{
			this.previous = previous;
		}
		
		@Override
		public int compareTo(DistanceVertex o) {
			return Double.compare(distance, o.getDistance());
		}
		
	}
	
	private void firstStep()
	{
		vertexSet = editor.getGraphT().vertexSet();
		distances = new Hashtable<>();
		edgesList = new ArrayList<>();
		markedVertexSet = new HashSet<>();
		for(Vertex vertex : vertexSet)
			distances.put(vertex.getId(), Double.MAX_VALUE);
		distances.put(startVertex.getId(), 0.0);
		queue = new PriorityQueue<>();
		queue.add(new DistanceVertex(startVertex, 0.0, null));
		showDistances();
		vertexU = null;
		vertexV = null;
		edgeV = null;
		first = false;
		edgeVchecked = true;
	}

	private void showDistances() 
	{
		mxGraphModel model = (mxGraphModel) editor.getGraphComponent().getGraph().getModel();
		for(Vertex vertex : vertexSet)
		{
			mxCell cell = (mxCell) model.getCell(vertex.getId());
			String value = null;
			Double d = distances.get(vertex.getId());
			if (d.equals(Double.MAX_VALUE))
				value = vertex.getName() + "\n" + Character.toString('\u221E');
			else
				value = vertex.getName() + "\n" + d;
			cell.setValue(value);
		}
	}

	private void createTextResult()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(lang.getProperty("alg-sp-message-1"));
		sb.append(startVertexComboBox.getSelectedItem());
		sb.append(lang.getProperty("alg-sp-message-2"));
		sb.append(endVertexComboBox.getSelectedItem());
		sb.append(lang.getProperty("alg-sp-message-8"));
		sb.append(distances.get(endVertex.getId()));
		sb.append(".");
		editor.setText(sb.toString());
	}
}
