package modgraf.algorithm;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.EulerianCircuit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Klasa szuka w grafie cyklu Eulera.
 *
 * @author Daniel Pogrebniak
 *
 * @see ModgrafAbstractAlgorithm
 * @see EulerianCircuit
 */
public class ModgrafEulerianCycle extends ModgrafAbstractAlgorithm {

    public ModgrafEulerianCycle(Editor e) {
        super(e);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (editor.getGraphT() instanceof UndirectedGraph)
            startAlgorithmWithoutParams();
        else
            JOptionPane.showMessageDialog(editor.getGraphComponent(),
                    lang.getProperty("warning-wrong-graph-type")+
                            lang.getProperty("alg-ec-graph-type"),
                    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public String getName() {
        return lang.getProperty("menu-algorithm-eulerian-cycle");
    }

    @Override
    protected void findAndShowResult() {
        List<Vertex> result = EulerianCircuit.getEulerianCircuitVertices(
                (UndirectedGraph<Vertex, ModgrafEdge>) editor.getGraphT());
        if (result != null) {
            createTextResult(result);
            createGraphicalResult(result);
        } else {
            JOptionPane.showMessageDialog(editor.getGraphComponent(),
                    lang.getProperty("message-not-eulerian-graph"),
                    lang.getProperty("information"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void createGraphicalResult(List<Vertex> result) {
        int width = 4;
        for (int i = 0; i < result.size()-1; ++i){
            changeVertexStrokeWidth(result.get(i), width);
            changeEdgeStrokeWidth(result.get(i), result.get(i+1), width);
        }
        editor.getGraphComponent().refresh();
    }

    private void createTextResult(List<Vertex> result) {
        StringBuilder sb = new StringBuilder()
                .append(lang.getProperty("menu-algorithm-eulerian-cycle"))
                .append(":\n");
        for (Vertex vertex : result) {
            sb.append(vertex.getName());
            sb.append(", ");
        }
        sb.replace(sb.length()-2, sb.length(), ".");
        editor.setText(sb.toString());
    }
}
