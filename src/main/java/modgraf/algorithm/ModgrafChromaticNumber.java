package modgraf.algorithm;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ChromaticNumber;

import org.jgrapht.graph.AsUndirectedGraph;

/**
 * Klasa rozwiązuje problem kolorowania wierzchołków.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ModgrafAbstractAlgorithm
 * @see ChromaticNumber
 * 
 */
public class ModgrafChromaticNumber extends ModgrafAbstractAlgorithm
{

	public ModgrafChromaticNumber(Editor e)
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
        startAlgorithmWithoutParams();
	}

	@Override
	protected void findAndShowResult()
	{
        UndirectedGraph<Vertex, ModgrafEdge> undirectedGraph;
        if (editor.getGraphT() instanceof UndirectedGraph)
            undirectedGraph = (UndirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT();
        else
            undirectedGraph = new AsUndirectedGraph<>((DirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT());
        Map<Integer, Set<Vertex>> result = ChromaticNumber.findGreedyColoredGroups(undirectedGraph);
		if (result != null)
		{
			createTextResult(result);
			createGraphicalResult(result);
		}
		else
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("message-no-solution"),
					lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void createGraphicalResult(Map<Integer, Set<Vertex>> result)
	{
		ArrayList<String> colorList = createColorList();
		for (Integer colorInt : result.keySet())
		{
			Set<Vertex> vertexSet = result.get(colorInt);
			String color = getColor(colorList, colorInt);
			for (Vertex vetrex : vertexSet)
				changeVertexFillColor(vetrex.getId(), color);
		}
		editor.getGraphComponent().refresh();
	}

	private void createTextResult(Map<Integer, Set<Vertex>> result)
	{
		StringBuilder sb = new StringBuilder();
		String newLine = "\n";
		sb.append(lang.getProperty("alg-cn-message-1"));
		sb.append(result.keySet().size());
		sb.append(lang.getProperty("alg-cn-message-2"));
		for (Integer colorInt : result.keySet())
		{
			Set<Vertex> vertexSet = result.get(colorInt);
			sb.append(colorInt);
			sb.append(" : ");
			for (Vertex vertex : vertexSet)
			{
				sb.append(vertex.getName());
				sb.append(", ");
			}
			sb.deleteCharAt(sb.length()-2);
			sb.append(newLine);
		}
		editor.setText(sb.toString());
	}

	@Override
	public String getName()
	{
		return lang.getProperty("menu-algorithm-chromatic-number");
	}

	private String getColor(ArrayList<String> colorList, Integer colorInt)
	{
		String color;
		if (colorInt < 16)
			color = colorList.get(colorInt.intValue());
		else
		{
			Random rand = new Random();
			int intColor = rand.nextInt(16777215);
			color = Integer.toString(intColor, 16);
			while (color.length() < 6)
				color = color+"F";
		}
		return color;
	}
	
	private ArrayList<String> createColorList()
	{
		ArrayList<String> colorList = new ArrayList<>();
		colorList.add("FF0000");
		colorList.add("00FF00");
		colorList.add("0000FF");
		colorList.add("FFFF00");
		colorList.add("00FFFF");
		colorList.add("FF00FF");
		colorList.add("FFFFFF");
		colorList.add("000000");
		colorList.add("C0C0C0");
		colorList.add("808000");
		colorList.add("008080");
		colorList.add("800080");
		colorList.add("800000");
		colorList.add("008000");
		colorList.add("000080");
		colorList.add("808080");
		return colorList;
	}
}
