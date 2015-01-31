package modgraf.action;

import com.mxgraph.view.mxGraph;
import modgraf.view.Editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.mxgraph.util.mxConstants.*;

/**
 * Klasa odpowiada za wyczyszczenie styli.
 *
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionClearStyles implements ActionListener {
    private Editor editor;

    public ActionClearStyles(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mxGraph graph = editor.getGraphComponent().getGraph();
        graph.selectAll();
        graph.setCellStyles(STYLE_STROKEWIDTH, null);
        graph.setCellStyles(STYLE_SHAPE, null);
        graph.setCellStyles(STYLE_FILLCOLOR, null);
        graph.setCellStyles(STYLE_STROKECOLOR, null);
        graph.setCellStyles(STYLE_FONTCOLOR, null);
        graph.setCellStyles(STYLE_FONTSIZE, null);
        graph.setCellStyles(STYLE_FONTFAMILY, null);
        graph.clearSelection();
    }
}
