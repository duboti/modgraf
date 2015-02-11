package modgraf.jgrapht.edge;

import modgraf.jgrapht.Vertex;

import org.jgrapht.WeightedGraph;

/**
 * Reprezentuje krawędzie ważone skierowane.
 *
 * @author Daniel Pogrebniak
 */
public class DirectedWeightedEdge extends DirectedEdge implements WeightedEdge
{
	public DirectedWeightedEdge(Vertex source, Vertex target) {
		super(source, target);
	}

	private static final long serialVersionUID = 8724416985819280188L;
	private double weight = WeightedGraph.DEFAULT_EDGE_WEIGHT;

	@Override
	public double getWeight()
	{
		return weight;
	}

	@Override
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	@Override
	public String toString()
    {
        return "(" + getSource().getName() + ":" + getTarget().getName() + ").w:" + getWeight();
    }
}
