package modgraf.action;

import java.util.Map;

import com.mxgraph.model.mxCell;
import modgraf.view.Editor;

import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

/**
 * Klasa odpowiada za zmianę stylu wierzchołka.
 * 
 * @author Daniel Pogrebniak
 */
public abstract class ActionSetStyle
{
	protected Editor editor;
	protected String styleName;
    protected final boolean isVertex;

    protected ActionSetStyle(Editor e, String styleName, boolean isVertex)
	{
		editor = e;
		this.styleName = styleName;
        this.isVertex = isVertex;
    }
	
	protected void setStyle(String styleValue)
	{
		boolean selected = !editor.getGraphComponent().getGraph().isSelectionEmpty();
		if (selected)
			setStyleForSelected(styleValue);
		else {
            if (isVertex)
                setDefaultVertexStyle(styleValue);
            else
                setDefaultEdgeStyle(styleValue);
        }
		editor.getGraphComponent().refresh();
	}

    protected void setStyleForSelected(String styleValue)
	{
		mxGraph graph = editor.getGraphComponent().getGraph();

        //sprawdzenie czy zaznaczone są właściwe obiekty
        for (Object object : graph.getSelectionCells()) {
            if (object instanceof mxCell) {
                mxCell cell = (mxCell)object;
                if ((cell.isEdge() && isVertex) || (cell.isVertex() && !isVertex))
                    return;
            }
        }
		graph.setCellStyles(styleName, styleValue);
	}

	protected void setDefaultVertexStyle(String styleValue)
	{
		mxStylesheet stylesheet = editor.getGraphComponent().getGraph().getStylesheet();
		Map<String, Object> vertexStyle = stylesheet.getStyles().get("vertexStyle");
		vertexStyle.put(styleName, styleValue);
	}

    private void setDefaultEdgeStyle(String styleValue) {
        editor.getGraphComponent().getGraph().getStylesheet().getDefaultEdgeStyle()
                .put(styleName, styleValue);
    }
}
