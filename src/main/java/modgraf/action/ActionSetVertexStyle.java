package modgraf.action;

import java.util.Map;

import modgraf.view.Editor;

import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

/**
 * Klasa odpowiada za zmianę stylu wierzchołka.
 * 
 * @author Daniel Pogrebniak
 */
public class ActionSetVertexStyle
{
	protected Editor editor;
	protected String styleName;
	
	public ActionSetVertexStyle(Editor e,  String styleName)
	{
		editor = e;
		this.styleName = styleName;
	}
	
	protected void setStyle(String styleValue)
	{
		boolean selected = !editor.getGraphComponent().getGraph().isSelectionEmpty();
		if (selected)
			setStyleForSelectedVertices(styleValue);
		else
			setDefaultVertexStyle(styleValue);
		editor.getGraphComponent().refresh();
	}
	
	protected void setStyleForSelectedVertices(String styleValue)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.setCellStyles(styleName, styleValue, graph.getSelectionCells());
	}
	
	protected void setStyleForAllVertices(String styleValue) 
	{
		mxGraph graph = editor.getGraphComponent().getGraph();
		graph.selectVertices();
		graph.setCellStyles(styleName, styleValue, graph.getSelectionCells());
		graph.clearSelection();
	}
	
	protected void setDefaultVertexStyle(String styleValue)
	{
		mxStylesheet stylesheet = editor.getGraphComponent().getGraph().getStylesheet();
		Map<String, Object> vertexStyle = stylesheet.getStyles().get("vertexStyle");
		vertexStyle.put(styleName, styleValue);
	}
}
