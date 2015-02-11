package modgraf.jgrapht.edge;

import java.io.Serializable;
import java.util.Map;

import modgraf.jgrapht.Vertex;

import com.mxgraph.model.mxCell;

/**
 * Klasa bazowa dla wszystkich klas reprezentujących krawędzie.
 *
 * @author Daniel Pogrebniak
 */
public abstract class ModgrafEdge implements Serializable
{
	private static final long serialVersionUID = 4970392287185325708L;
	private Vertex source;
	private Vertex target;
	private String name;
	private String id;

	protected ModgrafEdge(Vertex source, Vertex target)
	{
		this.source = source;
		this.target = target;
	}
	
	public ModgrafEdge(mxCell edge, Map<String, Vertex> vertices) 
	{
		this.source = vertices.get(edge.getSource().getId());
		this.target = vertices.get(edge.getTarget().getId());
		this.id = edge.getId();
	}
	
	public Vertex getSource() 
	{
		return source;
	}

	public void setSource(Vertex source) 
	{
		this.source = source;
	}

	public Vertex getTarget() 
	{
		return target;
	}

	public void setTarget(Vertex target) 
	{
		this.target = target;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public Vertex getOtherVertex(Vertex vertex)
	{
		if (vertex.equals(getSource()))
			return getTarget();
		if (vertex.equals(getTarget()))
			return getSource();
		return null;
	}
	
	@Override
	public String toString()
    {
        return "(" + source.getName() + " : " + target.getName() + ")";
    }
}
