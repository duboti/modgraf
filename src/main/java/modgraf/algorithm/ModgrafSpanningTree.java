package modgraf.algorithm;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.view.Editor;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.alg.interfaces.MinimumSpanningTree;

import java.awt.event.ActionEvent;
import java.util.Set;

/**
 * Klasa rozwiązuje problem minimalne drzewo rozpinające.
 *
 * @author Daniel Pogrebniak
 *
 * @see ModgrafAbstractAlgorithm
 * @see MinimumSpanningTree
 * @see KruskalMinimumSpanningTree
 * @see PrimMinimumSpanningTree
 */
public class ModgrafSpanningTree extends ModgrafAbstractAlgorithm{
    public enum Algorithm{
        Kruskal,
        Prim
    }

    private Algorithm algorithm;

    public ModgrafSpanningTree(Editor e, Algorithm alg) {
        super(e);
        algorithm = alg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        startAlgorithmWithoutParams();
    }

    @Override
    public String getName() {
        return lang.getProperty("menu-algorithm-spanning-tree");
    }

    @Override
    protected void findAndShowResult() {
        MinimumSpanningTree<Vertex, ModgrafEdge> minimumSpanningTree;
        if (algorithm == Algorithm.Kruskal) {
            minimumSpanningTree = new KruskalMinimumSpanningTree<>(editor.getGraphT());
        } else {//if (algorithm == Algorithm.Prim) {
            minimumSpanningTree = new PrimMinimumSpanningTree<>(editor.getGraphT());
        }
        Set<ModgrafEdge> edges = minimumSpanningTree.getMinimumSpanningTreeEdgeSet();
        double totalWeight = minimumSpanningTree.getMinimumSpanningTreeTotalWeight();
        createTextResult(edges, totalWeight);
        createGraphicalResult(edges);
    }

    private void createTextResult(Set<ModgrafEdge> edges, double totalWeight) {
        StringBuilder sb = new StringBuilder();
        if (editor.getGraphT() instanceof WeightedGraph) {
            sb.append(lang.getProperty("alg-st-message-1"))
                    .append(totalWeight);
        } else {
            sb.append(lang.getProperty("alg-st-message-2"))
                    .append(Math.round(totalWeight))
                    .append(lang.getProperty("alg-st-message-3"));
        }
        sb.append(lang.getProperty("alg-st-message-4"));
        for (ModgrafEdge edge : edges) {
            sb.append(edge.getSource().getName())
                    .append("-")
                    .append(edge.getTarget().getName())
                    .append("\n");
        }
        editor.setText(sb.toString());
    }

    private void createGraphicalResult(Set<ModgrafEdge> result) {
        int width = 4;
        for (ModgrafEdge edge : result)
            changeEdgeStrokeWidth(edge, width);
        editor.getGraphComponent().refresh();
    }
}
