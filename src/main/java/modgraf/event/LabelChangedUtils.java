package modgraf.event;

import java.util.Properties;

import javax.swing.JOptionPane;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.DoubleWeightedEdge;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.jgrapht.edge.WeightedEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;

import com.mxgraph.model.mxCell;

/**
 * Klasa zawiera metody odpowiedzialne za sprawdzenie poprawności wag 
 * krawędzi w grafach ważonych i podwójnie ważonych oraz ustawienie 
 * tychże wartości w warstwie wizualnej i matematycznej.
 * 
 * @author Daniel Pogrebniak
 *
 */
public class LabelChangedUtils
{
	protected Editor editor;
	protected Properties lang;
	protected Properties prop;
	
	public LabelChangedUtils(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
		prop = e.getProperties();
	}

	public void changeValueForDoubleWeightedEdge(mxCell cell)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		String value = (String)cell.getValue();
		String[] valueArray = value.split("/");
		double capacity = new Double(prop.getProperty("default-edge-capacity"));
		double cost = new Double(prop.getProperty("default-edge-cost"));
		if (valueArray.length == 2)
		{
			try
			{
				capacity = Double.parseDouble(valueArray[0]);
				cost = Double.parseDouble(valueArray[1]);
			} catch (NumberFormatException e)
			{
				showDoubleEdgeWarning(cell);
			}
		}
		else
			showDoubleEdgeWarning(cell);
		Vertex source = new Vertex(cell.getSource());
		Vertex target = new Vertex(cell.getTarget());
		ModgrafEdge edge = graphT.getEdge(source, target);
		DoubleWeightedEdge dwe = (DoubleWeightedEdge)edge;
		dwe.setCapacity(capacity);
		dwe.setCost(cost);
	}

	private void showDoubleEdgeWarning(mxCell cell)
	{
		String defaultValue = prop.getProperty("default-edge-capacity")+"/"+prop.getProperty("default-edge-cost");
		cell.setValue(defaultValue);
		JOptionPane.showMessageDialog(editor.getGraphComponent(),
				lang.getProperty("warning-not-number-double-weight-default")+defaultValue,
				lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
	}

	public void changeValueForWeightedEdge(mxCell cell)
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		double weight = new Double(prop.getProperty("default-edge-weight"));
		Object value = cell.getValue();
		if (value instanceof String)
		{
			String stringValue = (String)cell.getValue();
			try
			{
				weight = Double.parseDouble(stringValue);
			} catch (NumberFormatException e)
			{
				cell.setValue(prop.getProperty("default-edge-weight"));
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("warning-not-number-weight-default")+
						prop.getProperty("default-edge-weight"),
					    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
			}
		}
		if (value instanceof Double)
			weight = ((Double) value).doubleValue();
		Vertex source = new Vertex(cell.getSource());
		Vertex target = new Vertex(cell.getTarget());
		ModgrafEdge edge = graphT.getEdge(source, target);
		if (edge instanceof WeightedEdge)
			((WeightedEdge)edge).setWeight(weight);
	}

}