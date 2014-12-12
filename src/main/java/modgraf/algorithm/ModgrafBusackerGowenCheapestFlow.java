package modgraf.algorithm;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import modgraf.jgrapht.DoubleWeightedGraph;
import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.DirectedDoubleWeightedEdge;
import modgraf.jgrapht.edge.DirectedTripleWeightedEdge;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.EdmondsKarpMaximumFlow;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;

/**
 * 
 *
 * @see ModgrafAbstractAlgorithm
 * 
 */
public class ModgrafBusackerGowenCheapestFlow extends ModgrafAbstractAlgorithm
{

	public ModgrafBusackerGowenCheapestFlow(Editor e)
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (editor.getGraphT() instanceof DirectedGraph && editor.getGraphT() instanceof DoubleWeightedGraph)
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
		return lang.getProperty("menu-algorithm-cheapest-flow");
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
		int flow = 5;
		DirectedGraph<Vertex, ModgrafEdge> graph = (DirectedGraph<Vertex, ModgrafEdge>)editor.getGraphT();
		
		SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> newGraph = new SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge>(DirectedTripleWeightedEdge.class);
		
		for(Vertex vertex : graph.vertexSet()) 
		{
			newGraph.addVertex(new Vertex(vertex.getId(),vertex.getName()));
		}
		
		for(ModgrafEdge e:  graph.edgeSet()) 
		{
			DirectedTripleWeightedEdge newEdge = new DirectedTripleWeightedEdge(e.getSource(), e.getTarget());
			newEdge.setFlow(0);
			double capacity = ((DirectedDoubleWeightedEdge)e).getCapacity();
			double cost = ((DirectedDoubleWeightedEdge)e).getCost();
			newEdge.setCapacity(capacity);
			newEdge.setCost(cost);
			newGraph.addEdge(e.getSource(), e.getTarget(),newEdge);
		}
		
		int W = 0;
		
		
		while (W < flow) {
			System.out.println("GRAF");
			System.out.println(newGraph);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> residualGraph =
					this.getResidualNetwork(newGraph);
			System.out.println("RESIDUAL GRAPH");
			System.out.println(residualGraph);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			List<Vertex> bellmanFordList = this.getBellmanFordPath(residualGraph);
			
			
			double min = this.findSmallestNumberInPath(bellmanFordList, residualGraph);
			
			double delta = (W + min > flow) ? flow - W : min;
			
			this.updateGraph(bellmanFordList, residualGraph, newGraph, delta);
			
			W += delta;
		}

		System.out.println("GRAF");
		System.out.println(newGraph);
		
		
		
		calculateCost(newGraph);
		
		
		
		
		
		
		
		
		
		
		
//			createTextResult(ekmf);
//			createGraphicalResult(ekmf);
//		else
//			JOptionPane.showMessageDialog(editor.getGraphComponent(),
//					lang.getProperty("message-no-solution"),
//					lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
//	}
	}

	private void updateGraph(List<Vertex> bellmanFordList, 
			SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> residualGraph,
			SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> newGraph,
			double delta) {
		
		List<DirectedTripleWeightedEdge> edges = new LinkedList<>();
		
		for(int i = 0; i < bellmanFordList.size()-1; ++i) {
			edges.add(residualGraph.getEdge(bellmanFordList.get(i), bellmanFordList.get(i+1)));
		}
		
		for(DirectedTripleWeightedEdge e : edges) {
			Vertex v1 = e.isConsistentWithFlow() ? residualGraph.getEdgeSource(e) : residualGraph.getEdgeTarget(e);
			Vertex v2 = e.isConsistentWithFlow() ? residualGraph.getEdgeTarget(e) : residualGraph.getEdgeSource(e);
			
			DirectedTripleWeightedEdge edge = newGraph.getEdge(v1, v2);
			
			edge.setFlow(edge.getFlow() + (e.isConsistentWithFlow() ? delta : -delta));
		}
		
		
	}

	private SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> getResidualNetwork(SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> graph) {
		SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> residualGraph = 
				new SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge>(DirectedTripleWeightedEdge.class);
		
		for(Vertex vertex : graph.vertexSet()) {
			Vertex newVertex = new Vertex(vertex.getId(),vertex.getName());
			residualGraph.addVertex(newVertex);
		}
		for(DirectedTripleWeightedEdge edge : graph.edgeSet()) {
			if(edge.getFlow() < edge.getCapacity()) {
				Vertex newEdgeSource = this.findVertexInResidualGraph(residualGraph, edge.getSource().getId());
				Vertex newEdgeTarget = this.findVertexInResidualGraph(residualGraph, edge.getTarget().getId());
				DirectedTripleWeightedEdge newEdge = new DirectedTripleWeightedEdge(newEdgeSource, newEdgeTarget);
				newEdge.setCost(edge.getCost());
				newEdge.setCapacity(edge.getCapacity() - edge.getFlow());
				newEdge.setFlow(edge.getFlow());
				newEdge.setConsistentWithFlow(true);
				residualGraph.addEdge(newEdgeSource, newEdgeTarget,newEdge);
			}
		}
		 
		for(DirectedTripleWeightedEdge edge : graph.edgeSet()) {
			if(edge.getFlow() > 0) {
				Vertex newEdgeSource = this.findVertexInResidualGraph(residualGraph, edge.getSource().getId());
				Vertex newEdgeTarget = this.findVertexInResidualGraph(residualGraph, edge.getTarget().getId());
				DirectedTripleWeightedEdge newEdge = new DirectedTripleWeightedEdge(newEdgeTarget, newEdgeSource);
				newEdge.setCost(edge.getCost());
				newEdge.setCapacity(edge.getFlow());
				newEdge.setFlow(edge.getFlow());
				newEdge.setConsistentWithFlow(false);
				residualGraph.addEdge(newEdgeTarget, newEdgeSource,newEdge);
			}
		}
		return residualGraph;
	}
	
	
	private Vertex findVertexInResidualGraph(SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> newGraph, String id) {
		for(Vertex vertex : newGraph.vertexSet()) {
			if(vertex.getId().equals(id))
				return vertex;
		}
		return null;
	}
	
	
	@SuppressWarnings("static-access")
	private List<Vertex> getBellmanFordPath(SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> graph) {
		BellmanFordShortestPath<Vertex,DefaultWeightedEdge> bf;
		
		SimpleDirectedGraph<Vertex, DefaultWeightedEdge> g = 
				new SimpleDirectedGraph<Vertex, DefaultWeightedEdge>
				(DefaultWeightedEdge.class);
		
		for(Vertex vertex : graph.vertexSet())
			g.addVertex(vertex);
		
		for(DirectedTripleWeightedEdge e : graph.edgeSet()) {
			DefaultWeightedEdge defaultWeightedEdge = new DefaultWeightedEdge();
			g.addEdge(e.getSource(), e.getTarget(), defaultWeightedEdge);
			g.setEdgeWeight(defaultWeightedEdge, e.getCost());
		}
		
		bf = new BellmanFordShortestPath<Vertex, DefaultWeightedEdge>(g,startVertex);
		List<DefaultWeightedEdge> list = bf.findPathBetween(g, startVertex, endVertex);
		System.out.println("Ścieżka");
		System.out.println(bf.findPathBetween(g, startVertex, endVertex));
		System.out.println("KOSZT ŚCIEŻKI");
		System.out.println(bf.getCost(endVertex));
		
		
		List<Vertex> vertices = new LinkedList<>();
		for(DefaultWeightedEdge e : list)
			vertices.add(g.getEdgeSource(e));
		
		vertices.add(endVertex);
		return vertices;
	}

	
	private double findSmallestNumberInPath(
			List<Vertex> bellmanFordList,
			SimpleDirectedWeightedGraph<Vertex, DirectedTripleWeightedEdge> residualGraph) {
		double min = Double.MAX_VALUE;
		for(int i = 0; i < bellmanFordList.size()-1; ++i) {
			DirectedTripleWeightedEdge edge = residualGraph.getEdge(bellmanFordList.get(i), bellmanFordList.get(i+1));
			if(edge.getCapacity() < min)
				min = edge.getCapacity();
		}
		return min;
	}
	
	private int calculateCost(SimpleDirectedWeightedGraph<Vertex,DirectedTripleWeightedEdge> graph) {
		int cost = 0;
		for(DirectedTripleWeightedEdge e : graph.edgeSet()) {
			cost += e.getCost() * e.getFlow();
		}
		System.out.println("KOSZT");
		System.out.println(cost);
		return cost;
	}
}

