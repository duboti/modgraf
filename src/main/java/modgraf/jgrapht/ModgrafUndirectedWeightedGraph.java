package modgraf.jgrapht;

import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.jgrapht.edge.WeightedEdge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * @author Daniel Pogrebniak
 */
public class ModgrafUndirectedWeightedGraph extends SimpleWeightedGraph<Vertex, ModgrafEdge>
{
    public ModgrafUndirectedWeightedGraph(EdgeFactory<Vertex, ModgrafEdge> ef)
    {
        super(ef);
    }

    @Override
    public double getEdgeWeight(ModgrafEdge edge)
    {
        assert (edge instanceof WeightedEdge);
        return ((WeightedEdge) edge).getWeight();
    }

    @Override
    public void setEdgeWeight(ModgrafEdge edge,  double weight)
    {
        assert (edge instanceof WeightedEdge);
        ((WeightedEdge) edge).setWeight(weight);
    }
}
