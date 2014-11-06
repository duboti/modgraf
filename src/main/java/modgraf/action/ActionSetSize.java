package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;

/**
 * Klasa odpowiada za zmianę rozmiaru wierzchołka.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionSetSize implements ActionListener 
{
	private Editor editor;
	private double newWidth;
	private double newHeight;
	
	public ActionSetSize(Editor e, double size)
	{
		editor = e;
		newWidth = size;
		newHeight = size;
	}
	
	public ActionSetSize(Editor e, double height, double width)
	{
		editor = e;
		newWidth = width;
		newHeight = height;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		boolean selected = !editor.getGraphComponent().getGraph().isSelectionEmpty();
		if (selected)
			setSizeForSelectedVertex();
		else
			setSizeForAllVertex();
		editor.getGraphComponent().refresh();
	}

	private void setSizeForSelectedVertex()
	{
		Object[] selectedCells = editor.getGraphComponent().getGraph().getSelectionCells();
		for (Object object : selectedCells)
		{
			if (object instanceof mxCell)
			{
				mxCell cell = (mxCell) object;
				if (cell.isVertex())
				{
					mxGeometry geometry = cell.getGeometry();
					geometry.setWidth(newWidth);
					geometry.setHeight(newHeight);
				}
			}
		}
	}

	private void setSizeForAllVertex()
	{
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		Set<Vertex> vertexSet = graphT.vertexSet();
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		Map<String, Object> cellsMap = model.getCells();
		model.beginUpdate();
		for (Vertex vertex : vertexSet)
		{
			mxCell cell = (mxCell)cellsMap.get(vertex.getId());
			mxGeometry geometry = cell.getGeometry();
			geometry.setWidth(newWidth);
			geometry.setHeight(newHeight);
		}
		model.endUpdate();
	}

}

