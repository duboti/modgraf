package modgraf.algorithm;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.util.mxConstants;

public class ModgrafEdgeColoring extends ModgrafAbstractAlgorithm 
{
	public ModgrafEdgeColoring(Editor e) 
	{
		super(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (editor.getGraphT() instanceof UndirectedGraph)
		{
			startAlgorithmWithoutParams();
		}
		else
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-wrong-graph-type")+
				    lang.getProperty("alg-cn-graph-type"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public String getName() {
		return "Kolorowanie krawędzi";
	}

	@Override
	protected void findAndShowResult() 
	{
		Date start = new Date();
		EdgeColoring edgeColoring = new EdgeColoring();
		Map<Integer, Set<ModgrafEdge>> result = edgeColoring.findColoredEgdeGroups(
				(UndirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT());
		Date end = new Date();
		if (result != null)
		{
			createTextResult(result, end.getTime() - start.getTime());
			createGraphicalResult(result);
		}
		else
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("message-no-solution"),
					lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void createTextResult(Map<Integer, Set<ModgrafEdge>> result, long time)
	{
		StringBuilder sb = new StringBuilder();
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
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
				Vertex sourceId = edge.getSource();
				mxCell source = (mxCell) model.getCell(sourceId.getId());
				sb.append("(");
				sb.append(source.getValue().toString());
				sb.append(",");
				Vertex targetId = edge.getTarget();
				mxCell target = (mxCell) model.getCell(targetId.getId());
				sb.append(target.getValue().toString());
				sb.append("), ");
			}
			sb.deleteCharAt(sb.length()-2);
			sb.append(newLine);
		}
		editor.setText(sb.toString());
	}

	private void createGraphicalResult(Map<Integer, Set<ModgrafEdge>> result)
	{
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		ArrayList<String> colorList = createColorList();
		model.beginUpdate();
		for (Integer colorInt : result.keySet())
		{
			Set<ModgrafEdge> edgeSet = result.get(colorInt);
			String color = getColor(colorList, colorInt);
			for (ModgrafEdge edge : edgeSet)
			{
				Vertex sourceId = graphT.getEdgeSource(edge);
				Vertex targetId = graphT.getEdgeTarget(edge);
				String edgeId = editor.getEdgeId(sourceId.getId(), targetId.getId());
				setCellStyle(edgeId, mxConstants.STYLE_STROKECOLOR, color);
			}
		}
		model.endUpdate();
		editor.getGraphComponent().refresh();
	}

	private String getColor(ArrayList<String> colorList, Integer colorInt)
	{
		String color = null;
		if (colorInt.intValue() < colorList.size())
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
