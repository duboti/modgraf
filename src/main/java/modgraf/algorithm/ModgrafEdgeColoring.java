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

import com.mxgraph.util.mxConstants;
import org.jgrapht.graph.AsUndirectedGraph;

public class ModgrafEdgeColoring extends ModgrafAbstractAlgorithm 
{
	public ModgrafEdgeColoring(Editor e) 
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
        startAlgorithmWithoutParams();
	}

	@Override
	public String getName() {
		return "Kolorowanie krawędzi";
	}

	@Override
	protected void findAndShowResult() 
	{
		EdgeColoring edgeColoring = new EdgeColoring();
        UndirectedGraph<Vertex, ModgrafEdge> undirectedGraph;
        if (editor.getGraphT() instanceof UndirectedGraph)
            undirectedGraph = (UndirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT();
        else
            undirectedGraph = new AsUndirectedGraph<>((DirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT());
		Map<Integer, Set<ModgrafEdge>> result = edgeColoring.findColoredEgdeGroups(undirectedGraph);
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

	private void createTextResult(Map<Integer, Set<ModgrafEdge>> result)
	{
		StringBuilder sb = new StringBuilder();
		String newLine = "\n";
		sb.append(lang.getProperty("alg-ec-message-1"));
		sb.append(result.keySet().size());
		sb.append(lang.getProperty("alg-ec-message-2"));
		for (Integer colorInt : result.keySet())
		{
			Set<ModgrafEdge> edgeSet = result.get(colorInt);
			sb.append(colorInt);
			sb.append(" : ");
			for (ModgrafEdge edge : edgeSet)
			{
				sb.append("(");
                sb.append(edge.getSource().getName());
				sb.append(",");
                sb.append(edge.getTarget().getName());
				sb.append("), ");
			}
			sb.deleteCharAt(sb.length()-2);
			sb.append(newLine);
		}
		editor.setText(sb.toString());
	}

	private void createGraphicalResult(Map<Integer, Set<ModgrafEdge>> result)
	{
		ArrayList<String> colorList = createColorList();
		for (Integer colorInt : result.keySet())
		{
			Set<ModgrafEdge> edgeSet = result.get(colorInt);
			String color = getColor(colorList, colorInt);
			for (ModgrafEdge edge : edgeSet)
			{
				setCellStyle(edge.getId(), mxConstants.STYLE_STROKECOLOR, color);
			}
		}
		editor.getGraphComponent().refresh();
	}

	private String getColor(ArrayList<String> colorList, Integer colorInt)
	{
		String color;
		if (colorInt < colorList.size())
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
		colorList.add("808000");
		colorList.add("008080");
		colorList.add("800080");
		colorList.add("800000");
		colorList.add("008000");
		colorList.add("000080");
		colorList.add("808080");
		colorList.add("FF8080");
		colorList.add("80FF80");
		colorList.add("8080FF");
		colorList.add("80FFFF");
		colorList.add("FF80FF");
		colorList.add("FFFF80");
		return colorList;
	}
}
