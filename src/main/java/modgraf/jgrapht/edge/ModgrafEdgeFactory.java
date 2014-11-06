package modgraf.jgrapht.edge;

import java.io.Serializable;
import java.util.Properties;

import org.jgrapht.EdgeFactory;

import modgraf.jgrapht.Vertex;
import modgraf.view.Editor;

public class ModgrafEdgeFactory<M extends ModgrafEdge> implements EdgeFactory<Vertex, ModgrafEdge>, Serializable
{

	private static final long serialVersionUID = 9032722547801096765L;
	private final Class<M> edgeClass;
	private Properties prop;

    public ModgrafEdgeFactory(Class<M> edgeClass, Editor e)
    {
        this.edgeClass = edgeClass;
        prop = e.getProperties();
    }
	
	@Override
	public M createEdge(Vertex source, Vertex target) 
	{
		try {
            
			M edge = edgeClass.getConstructor(Vertex.class, Vertex.class)
            		.newInstance(source, target);
			if (edge instanceof WeightedEdge)
			{
				double weight = new Double(prop.getProperty("default-edge-weight"));
				((WeightedEdge) edge).setWeight(weight);
			}
			if (edge instanceof DoubleWeightedEdge)
			{
				double capacity = new Double(prop.getProperty("default-edge-capacity"));
				double cost = new Double(prop.getProperty("default-edge-cost"));
				DoubleWeightedEdge dwe = (DoubleWeightedEdge)edge;
				dwe.setCapacity(capacity);
				dwe.setCost(cost);
			}
			return edge;
        } catch (Exception ex) {
            throw new RuntimeException("Edge factory failed", ex);
        }
	}

}
