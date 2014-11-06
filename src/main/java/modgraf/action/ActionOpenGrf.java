package modgraf.action;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.view.mxGraph;

public class ActionOpenGrf 
{
	private Editor editor;
	
	public ActionOpenGrf(Editor e)
	{
		editor = e;
	}
	
	/**
	 * Metoda wczytuje pliki grf.
	 * @param chooser
	 * @throws IOException
	 */
	public void openGrf(JFileChooser chooser) throws IOException
	{
		String grfFile = new ActionOpen(editor).readFile(chooser);
		grfFileValidationTesting(grfFile);
		createGraphFromGrfFile(grfFile);
		if (existInformationAboutLocation(grfFile))
			setMxGeometryFromGrfFile(grfFile);
		else
			setMxGeometryOnCircle();
	}

	/**
	 * Metoda sprawdza poprawność pliku grf.
	 * @param grfFile - zawartość pliku grf
	 * @throws IllegalArgumentException
	 */
	private void grfFileValidationTesting(String grfFile) throws IllegalArgumentException
	{
		String[] lines = grfFile.split("\n");
		if (lines.length < 5)
			throw new IllegalArgumentException("Not enough lines in the file!");
		boolean directed = lines[2].startsWith("s") || lines[2].startsWith("S");
		StringTokenizer st = new StringTokenizer(lines[4]);
		int edgeWeightDegree = st.countTokens() - 2;
		if (edgeWeightDegree < 0 || edgeWeightDegree > 2)
			throw new IllegalArgumentException("Incorrect number of the edge parameters!");
		int i = 4;
		ArrayList<String> vertexNameList = new ArrayList<>();
		ArrayList<String> edgeList = new ArrayList<>();
		while (i < lines.length && !lines[i].isEmpty())
		{
			grfValidationEdge(lines[i], edgeWeightDegree, directed, vertexNameList, edgeList);
			++i;
		}
		int edgeCounter = edgeList.size();
		int vertexCounter = vertexNameList.size();
		boolean existsGeometry = (edgeCounter + vertexCounter + 5) <= lines.length;
		if (existsGeometry)
		{
			++i;
			int validVertexCounter = 0;
			while (i < lines.length && !lines[i].isEmpty())
			{
				validVertexCounter = grfValidationVertexGeometry(lines[i], edgeWeightDegree, vertexNameList, validVertexCounter);
				++i;
			}
			if (validVertexCounter != vertexNameList.size())
				throw new IllegalArgumentException("Invalid number of vertices positions!");
		}
	}

	/**
	 * Metoda sprawdza poprawność fragmentu pliku grf zawierającego definicje krawędzi.
	 * @param line
	 * @param edgeWeightDegree
	 * @param directed
	 * @param vertexNameList
	 * @param edgeList
	 * @throws IllegalArgumentException
	 */
	private void grfValidationEdge(String line, int edgeWeightDegree, boolean directed,
			ArrayList<String> vertexNameList, ArrayList<String> edgeList) throws IllegalArgumentException
	{
		StringTokenizer st = new StringTokenizer(line);
		if (st.countTokens() != edgeWeightDegree + 2)
			throw new IllegalArgumentException("The edges have a different number of parameters!");
		String source = st.nextToken();
		if (!vertexNameList.contains(source))
			vertexNameList.add(source);
		String target = st.nextToken();
		if (!vertexNameList.contains(target))
			vertexNameList.add(target);
		if (directed)
		{
			if (!edgeList.contains(source+" "+target))
				edgeList.add(source+" "+target);
			else
				throw new IllegalArgumentException("Borders can not be repeated!");
		}
		else
		{
			if (!edgeList.contains(source+" "+target) && !edgeList.contains(target+" "+source))
				edgeList.add(source+" "+target);
			else
				throw new IllegalArgumentException("Borders can not be repeated!");
		}
		if (edgeWeightDegree == 1)
			Double.parseDouble(st.nextToken());
		if (edgeWeightDegree == 2)
		{
			Double.parseDouble(st.nextToken());
			Double.parseDouble(st.nextToken());
		}
	}

	/**
	 * Metoda sprawdza poprawność fragmentu pliku grf zawierającego położenia wierzchołków.
	 * @param line
	 * @param edgeWeightDegree
	 * @param vertexNameList
	 * @param validVertexCounter
	 * @return ++validVertexCounter
	 * @throws IllegalArgumentException
	 */
	private int grfValidationVertexGeometry(String line, int edgeWeightDegree,
			ArrayList<String> vertexNameList, Integer validVertexCounter) throws IllegalArgumentException
	{
		StringTokenizer st = new StringTokenizer(line);
		if (st.countTokens() != 3)
			throw new IllegalArgumentException("Invalid number of vertex positions!");
		if (!vertexNameList.contains(st.nextToken()))
			throw new IllegalArgumentException("Invalid vertex name!");
		Double.parseDouble(st.nextToken());
		Double.parseDouble(st.nextToken());
		return ++validVertexCounter;
	}

	/**
	 * Metoda tworzy graf na podstawie pliku grf.
	 * @param grfFile - zawartość pliku grf
	 */
	public void createGraphFromGrfFile(String grfFile)
	{
		String[] lines = grfFile.split("\n");
		boolean directed = lines[2].startsWith("s") || lines[2].startsWith("S");
		lines[4].split("\t");
		StringTokenizer st = new StringTokenizer(lines[4]);
		int edgeWeightDegree = st.countTokens() - 2;
		int i = 4;
		editor.setGraphT(editor.createNewGraphT(directed, edgeWeightDegree));
		editor.getGraphComponent().setGraph(editor.createNewMxGraph());
		while (i < lines.length && !lines[i].isEmpty())
		{
			createGrfEdge(lines[i], edgeWeightDegree);
			++i;
		}
		editor.setTextAboutNewGraph(directed, edgeWeightDegree, true);
	}

	private void createGrfEdge(String line, int edgeWeightDegree)
	{
		StringTokenizer st = new StringTokenizer(line);
		String sourceName = st.nextToken();
		mxGraph graph = editor.getGraphComponent().getGraph();
		Object parent = graph.getDefaultParent();
		mxGraphModel model = (mxGraphModel) graph.getModel();
		mxCell mxSource = null;
		mxCell mxTarget = null;
		if (!editor.getVertexValuesSet().contains(sourceName))
			mxSource = (mxCell) graph.insertVertex(parent, null, sourceName, 0, 0, 50, 50, "vertexStyle");
		else
			mxSource = (mxCell) model.getCell(editor.getVertexId(sourceName));
		String target = st.nextToken();
		if (!editor.getVertexValuesSet().contains(target))
			mxTarget = (mxCell) graph.insertVertex(parent, null, target, 0, 0, 50, 50, "vertexStyle");
		else
			mxTarget = (mxCell) model.getCell(editor.getVertexId(target));
		if (edgeWeightDegree == 0)
			graph.insertEdge(parent, null, null, mxSource, mxTarget);
		if (edgeWeightDegree == 1)
		{
			double weight = Double.parseDouble(st.nextToken());
			graph.insertEdge(parent, null, Double.toString(weight), mxSource, mxTarget);
		}
		if (edgeWeightDegree == 2)
		{
			double capacity = Double.parseDouble(st.nextToken());
			double cost = Double.parseDouble(st.nextToken());
			String value = capacity+"/"+cost;
			graph.insertEdge(parent, null, value, mxSource, mxTarget);
		}
	}

	private boolean existInformationAboutLocation(String grfFile)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		String[] lines = grfFile.split("\n");
		int edgeCounter = graphT.edgeSet().size();
		int vertexCounter = graphT.vertexSet().size();
		return (edgeCounter + vertexCounter + 5) <= lines.length;
	}

	private void setMxGeometryFromGrfFile(String grfFile)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		mxGraph graph = editor.getGraphComponent().getGraph();
		mxGraphModel model = (mxGraphModel) graph.getModel();
		String[] lines = grfFile.split("\n");
		int edgeCounter = graphT.edgeSet().size();
		int vertexCounter = graphT.vertexSet().size();
		for (int i = edgeCounter + 5; i < lines.length && !lines[i].isEmpty(); ++i)
		{
			StringTokenizer st = new StringTokenizer(lines[i]);
			mxCell cell = (mxCell) model.getCell(editor.getVertexId(st.nextToken()));
			model.beginUpdate();
			cell.getGeometry().setY(Double.parseDouble(st.nextToken()));
			cell.getGeometry().setX(Double.parseDouble(st.nextToken()));
			model.endUpdate();
		}
		editor.setVertexCounter(vertexCounter);
		editor.getGraphComponent().refresh();
	}

	public void setMxGeometryOnCircle()
	{
		Point center = new Point(275, 125);
		int r = 100;
		Set<Vertex> vertexSet = editor.getGraphT().vertexSet();
		int numberOfVerticles = vertexSet.size();
		if (numberOfVerticles > 10)
		{
			r = 10 * numberOfVerticles;
			int num = r + 50;
			center = new Point(num, num);
		}
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		int vertexNumber = 0;
		model.beginUpdate();
		for (Vertex vertexT : vertexSet)
		{
			mxCell vertex = (mxCell)model.getCell(vertexT.getId());
			++vertexNumber;
			double argument = Math.PI * 2 * vertexNumber / numberOfVerticles;
			double x = center.getX() + r * Math.sin(argument);
			vertex.getGeometry().setX(Math.round(x));
			double y = center.getY() + r * Math.cos(argument);
			vertex.getGeometry().setY(Math.round(y));
		}
		model.endUpdate();
		editor.getGraphComponent().refresh();
	}
}
