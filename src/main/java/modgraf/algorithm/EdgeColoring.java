package modgraf.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ColoredEdge;
import modgraf.jgrapht.edge.ModgrafEdge;

import org.jgrapht.EdgeFactory;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ChromaticNumber;
import org.jgrapht.alg.util.VertexDegreeComparator;
import org.jgrapht.graph.SimpleGraph;

public class EdgeColoring
{
	private Map<String, List<Integer>> availableColorsForAllVertex;
	
	public Map<Integer, Set<ModgrafEdge>> findColoredEgdeGroups(UndirectedGraph<Vertex, ModgrafEdge> undirectedGraph)
	{
		VertexDegreeComparator<Vertex, ModgrafEdge> comp =
	            new VertexDegreeComparator<Vertex, ModgrafEdge>(undirectedGraph);
		int maxVertexDegree = undirectedGraph.degreeOf(Collections.max(undirectedGraph.vertexSet(),comp));
		if (maxVertexDegree < 3 )
			return algNC(undirectedGraph);
		else
			return algNTL(undirectedGraph, maxVertexDegree);
	}
	
	private Map<Integer, Set<ModgrafEdge>> algNC(UndirectedGraph<Vertex, ModgrafEdge> undirectedGraph)
	{
		Map<Integer, Set<ModgrafEdge>> result = new HashMap<>();
		Map<String, Set<Integer>> vertexColors = new HashMap<>();
		for (ModgrafEdge edge:undirectedGraph.edgeSet())
		{
			Vertex source = undirectedGraph.getEdgeSource(edge);
			Vertex target = undirectedGraph.getEdgeTarget(edge);
			Set<Integer> sourceColorsSet = vertexColors.get(source.getId());
			Set<Integer> targetColorsSet = vertexColors.get(target.getId());
			boolean sourceColorBusy = true;
			boolean targetColorBusy = true;
			Integer currentColor = new Integer(-1);
			while ( sourceColorBusy || targetColorBusy)
			{
				++currentColor;
				if (sourceColorsSet == null)
				{
					sourceColorsSet = new HashSet<>();
					vertexColors.put(source.getId(), sourceColorsSet);
					sourceColorBusy = false;
				}
				else
					sourceColorBusy = sourceColorsSet.contains(currentColor);
				if (targetColorsSet == null)
				{
					targetColorsSet = new HashSet<>();
					vertexColors.put(target.getId(), targetColorsSet);
					targetColorBusy = false;
				}
				else
					targetColorBusy = targetColorsSet.contains(currentColor);
			}
			Set<ModgrafEdge> currentColorEdgeSet = result.get(currentColor);
			if (currentColorEdgeSet == null)
			{
				currentColorEdgeSet = new HashSet<>();
				result.put(currentColor, currentColorEdgeSet);
			}
			currentColorEdgeSet.add(edge);
			sourceColorsSet.add(currentColor);
			targetColorsSet.add(currentColor);
		}
		return result;
	}

	//Algorytm NTL
	
	private Map<Integer, Set<ModgrafEdge>> algNTL(UndirectedGraph<Vertex, ModgrafEdge> undirectedGraph, int maxVertexDegree)
	{
		UndirectedGraph<String, ColoredEdge> newGraph = new SimpleGraph<>(new EdgeFactory<String, ColoredEdge>() {
			@Override
			public ColoredEdge createEdge(String sourceVertex, String targetVertex) {
				return new ColoredEdge(new Vertex(sourceVertex, sourceVertex), new Vertex(targetVertex, targetVertex));
			}
		});
		Set<Integer> availableColors = new HashSet<>();
		boolean bipartite = checkIfGraphIsBipartite(undirectedGraph);
		availableColorsForAllVertex = new HashMap<>();
		int chromaticIndex;
		if (bipartite)
			chromaticIndex = maxVertexDegree;
		else
			chromaticIndex = maxVertexDegree + 1;
		for (int i = 0; i < chromaticIndex; ++i)
			availableColors.add(new Integer(i));
		for (Vertex vertex:undirectedGraph.vertexSet())
		{
			newGraph.addVertex(vertex.getId());
			availableColorsForAllVertex.put(vertex.getId(), new ArrayList<>(availableColors));
		}
		for (ModgrafEdge edge:undirectedGraph.edgeSet())
		{
			Vertex source = undirectedGraph.getEdgeSource(edge);
			Vertex target = undirectedGraph.getEdgeTarget(edge);
			ColoredEdge coloredEdge = newGraph.addEdge(source.getId(), target.getId());
			List<Integer> availableColorsForSource = availableColorsForAllVertex.get(source.getId());
			List<Integer> availableColorsForTarget = availableColorsForAllVertex.get(target.getId());
			List<Integer> intersectionAvailableColors = getIntersectionList
					(availableColorsForSource, availableColorsForTarget);
			Integer color = null;
			if (intersectionAvailableColors.size() == 0)
			{
				if (bipartite)
					color = bipartiteRecolor(newGraph, source.getId(), target.getId());
				else
					color = recolor(newGraph, source.getId(), target.getId());
			}
			else
				color = intersectionAvailableColors.get(0);
			if (color == null) //na wypadek błędów w implementacji
			{
				color = availableColors.size();
				availableColors.add(color);
				for (String vertex:newGraph.vertexSet())
					availableColorsForAllVertex.get(vertex).add(color);
			}
			availableColorsForSource.remove(color);
			availableColorsForTarget.remove(color);
			coloredEdge.setColor(color);
		}
		return createResult(newGraph.edgeSet());
	}
		
	private boolean checkIfGraphIsBipartite(UndirectedGraph<Vertex,ModgrafEdge> undirectedGraph) 
	{
		Map<Integer, Set<Vertex>> coloredGroups = ChromaticNumber.
				findGreedyColoredGroups(undirectedGraph);
		return coloredGroups.size() == 2;
	}

	private Map<Integer, Set<ModgrafEdge>> createResult(Set<ColoredEdge> edgeSet)
	{
		Map<Integer, Set<ModgrafEdge>> result = new HashMap<>();
		for(ColoredEdge edge:edgeSet)
		{
			Set<ModgrafEdge> edgeSetForColor = result.get(edge.getColor());
			if (edgeSetForColor == null)
			{
				edgeSetForColor = new HashSet<>();
				result.put(edge.getColor(), edgeSetForColor);
			}
			edgeSetForColor.add(edge);
		}
		return result;
	}

	class EdgeForColoring 
	{
		private Integer newColor;
		private ColoredEdge edge;

		public EdgeForColoring(ColoredEdge edge, Integer newColor) 
		{
			this.setEdge(edge);
			this.newColor = newColor;
		}
		
		public Integer getNewColor() 
		{
			return newColor;
		}

		public void setNewColor(Integer newColor) 
		{
			this.newColor = newColor;
		}

		public ColoredEdge getEdge() 
		{
			return edge;
		}

		public void setEdge(ColoredEdge edge) 
		{
			this.edge = edge;
		}
		
		public String toString()
		{
			return "{e:"+edge+",nc:"+newColor+"}";
		}
	}
	
	//Procedura Recolor dla grafów dwudzielnych
	
	private Integer bipartiteRecolor(UndirectedGraph<String,ColoredEdge> graph, String vertexU, 
			String vertexV) 
	{
		List<Integer> availableColorsForV = availableColorsForAllVertex.get(vertexV);
		List<Integer> availableColorsForU = availableColorsForAllVertex.get(vertexU);
		Integer firstColor = availableColorsForV.get(0);
		Integer secondColor = availableColorsForU.get(0);
		List<ColoredEdge> path = findPath(graph, firstColor, secondColor, vertexU);
		recolorPath(firstColor, secondColor, path, vertexU);
		return firstColor;
	}

	private List<ColoredEdge> findPath(UndirectedGraph<String, ColoredEdge> graph, 
			Integer firstColor, Integer secondColor, String startPathVertex)
	{
		List<ColoredEdge> path = new ArrayList<>();
		String vertex = startPathVertex;
		ColoredEdge edge = findSelectedColorEdgeForVertex(vertex, firstColor, graph);
		for (int i = 1; edge != null; ++i)
		{
			path.add(edge);
			vertex = edge.getOtherVertex(vertex);
			if (i % 2 == 1)
				edge = findSelectedColorEdgeForVertex(vertex, secondColor, graph);
			else
				edge = findSelectedColorEdgeForVertex(vertex, firstColor, graph);
		}
		return path;
	}

	private void recolorPath(Integer firstColor, Integer secondColor, List<ColoredEdge> path, String firstVertex)
	{
		String lastVertex = firstVertex;
		for (int i = 0; i < path.size(); ++i)
		{
			ColoredEdge edge = path.get(i);
			lastVertex = edge.getOtherVertex(lastVertex);
			if (edge.getColor() == firstColor)
				edge.setColor(secondColor);
			else
				edge.setColor(firstColor);
			if (i == 0)
				changeAvailableColors(firstColor, secondColor, firstVertex);
			if (i == path.size()-1)
				changeAvailableColors(firstColor, secondColor, lastVertex);
		}
	}

	//Procedura Recolor dla zwykłych grafów
	
	private Integer recolor (UndirectedGraph<String,ColoredEdge> graph, String vertexU, 
			String vertexV)
	{
		Set<ColoredEdge> fan = new HashSet<>(graph.edgesOf(vertexV));
		ColoredEdge euv = graph.getEdge(vertexU, vertexV);
		fan.remove(euv);
		List<EdgeForColoring> edgeForColoringList = new ArrayList<>();
		String vertexWs = tryRecolorInFan(vertexV, vertexU, fan, 
				edgeForColoringList, euv);
		if (vertexWs != null)
			doRecolorOnPath(graph, vertexV, vertexWs, edgeForColoringList);
		Integer newColor = changeColors(edgeForColoringList, vertexV);
		return newColor;
	}

	private String tryRecolorInFan(String vertexV, String vertexU, Set<ColoredEdge> fan,
			List<EdgeForColoring> edgeForColoringList, ColoredEdge oldEdge)
	{
		List<Integer> availableColorsForV = availableColorsForAllVertex.get(vertexV);
		String vertexWs = null;
		boolean endLoop = false;
		while (!endLoop && fan.size() > 0)
		{
			List<Integer> availableColorsForU = availableColorsForAllVertex.get(vertexU);
			ColoredEdge newEdge = findEdge(fan, availableColorsForU);
			if (newEdge != null)
			{
				edgeForColoringList.add(new EdgeForColoring(oldEdge, newEdge.getColor()));
				String vertexW = newEdge.getOtherVertex(vertexV);
				List<Integer> availableColorsForW = availableColorsForAllVertex.get(vertexW);
				List<Integer> intersection = getIntersectionList(availableColorsForW, availableColorsForV);
				if (intersection.size() > 0)
				{
					Integer color = intersection.get(0);
					edgeForColoringList.add(new EdgeForColoring(newEdge, color));
					endLoop = true;
				}
				else
				{
					vertexU = vertexW;
					oldEdge = newEdge;
					fan.remove(newEdge);
					if (fan.size() == 0)
						vertexWs = vertexW;
				}
			}
			else
			{
				endLoop = true;
				vertexWs = vertexU;
			}
		}
		return vertexWs;
	}

	private void doRecolorOnPath(UndirectedGraph<String, ColoredEdge> graph,
			String vertexV, String vertexWs,
			List<EdgeForColoring> edgeForColoringList)
	{
		List<Integer> availableColorsForV = availableColorsForAllVertex.get(vertexV);
		List<Integer> availableColorsForWs = availableColorsForAllVertex.get(vertexWs);
		Integer firstColor = availableColorsForV.get(0);
		Integer secondColor = availableColorsForWs.get(0);
		List<ColoredEdge> path = findPath(graph, firstColor, secondColor, vertexWs);
		String lastVertex = getLastVertexInPath(path, vertexWs);
		if (lastVertex.equals(vertexV))
			cutEdgeForColoringList(edgeForColoringList, path.get(path.size()-1));
		else
		{
			Set<String> vertexFan = createVertexFan(edgeForColoringList, vertexV);
			List<Integer> availableColorsForWi = availableColorsForAllVertex.get(lastVertex);
			if (vertexFan.contains(lastVertex) && availableColorsForWi.contains(secondColor))
			{
				ColoredEdge edge = graph.getEdge(vertexV, lastVertex);
				cutEdgeForColoringList(edgeForColoringList, edge);
				edgeForColoringList.add(new EdgeForColoring(edge, firstColor));
			}
			else
			{
				ColoredEdge edge = graph.getEdge(vertexV, vertexWs);
				edgeForColoringList.add(new EdgeForColoring(edge, firstColor));
			}
		}
		recolorPath(firstColor, secondColor, path, vertexWs);
	}

	//Metody pomocnicze:
	
	private Set<String> createVertexFan(List<EdgeForColoring> edgeForColoringList, String vertexV)
	{
		Set<String> vertexFan = new HashSet<>();
		for (EdgeForColoring efc : edgeForColoringList)
			vertexFan.add(efc.getEdge().getOtherVertex(vertexV));
		return vertexFan;
	}

	private void cutEdgeForColoringList(List<EdgeForColoring> edgeForColoringList,
			ColoredEdge lastEdgeOnPath)
	{
		List<EdgeForColoring> tempList = new ArrayList<>(edgeForColoringList);
		boolean appeared = false;
		for (EdgeForColoring e:tempList)
		{
			if (e.getEdge().equals(lastEdgeOnPath))
				appeared = true;
			if (appeared)
				edgeForColoringList.remove(e);
		}
	}

	private Integer changeColors(List<EdgeForColoring> edgeForColoringList, String vertexV)
	{
		for (int i = 1; i < edgeForColoringList.size(); ++i)
		{
			EdgeForColoring e = edgeForColoringList.get(i);
			ColoredEdge edge = e.getEdge();
			int oldColor = edge.getColor();
			edge.setColor(e.getNewColor());
			List<Integer> availableColorsForV = availableColorsForAllVertex.get(vertexV);
			availableColorsForV.remove(e.getNewColor());
			String vertexW = edge.getOtherVertex(vertexV);
			List<Integer> availableColorsForW = availableColorsForAllVertex.get(vertexW);
			availableColorsForW.remove(e.getNewColor());
			availableColorsForW.add(new Integer(oldColor));
		}
		return edgeForColoringList.get(0).getNewColor();
	}

	private ColoredEdge findEdge(Set<ColoredEdge> edges, List<Integer> availableColors)
	{
		for (ColoredEdge edge:edges)
		{
			if (availableColors.contains(edge.getColor()))
				return edge;
		}
		return null;
	}

	//TODO Nieoptymalna implementacja
	private String getLastVertexInPath(List<ColoredEdge> path, String firstVertex)
	{
		String lastVertex = null;
		if (path.size() == 1)
			lastVertex = path.get(0).getOtherVertex(firstVertex);
		else
		{
			String tempVertex = firstVertex;
			for(ColoredEdge edge:path)
				tempVertex = edge.getOtherVertex(tempVertex);
			lastVertex = tempVertex;
		}
		return lastVertex;
	}

	private void changeAvailableColors(Integer firstColor, Integer secondColor, String vertex)
	{
		List<Integer> list = availableColorsForAllVertex.get(vertex);
		if (list.contains(firstColor))
		{
			list.remove(firstColor);
			list.add(secondColor);
		}
		else
		{
			list.remove(secondColor);
			list.add(firstColor);
		}
	}
	
	private ColoredEdge findSelectedColorEdgeForVertex(String vetex, Integer color, UndirectedGraph<String, ColoredEdge> graph)
	{
		Set<ColoredEdge> fan = graph.edgesOf(vetex);
		for(ColoredEdge edge:fan)
		{
			Integer edgeColor = edge.getColor();
			if (edgeColor.equals(color))
				return edge;
		}
		return null;
	}

	private List<Integer> getIntersectionList(Collection<Integer> first, Collection<Integer> second)
	{
		List<Integer> intersection = new ArrayList<>(first);
		intersection.retainAll(second);
		return intersection;
	}
}
