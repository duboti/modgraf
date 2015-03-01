package modgraf.jgrapht;

import java.io.Serializable;

import com.mxgraph.model.mxICell;

/**
 * Reprezentuje wierzchołek.
 *
 * @author Daniel Pogrebniak
 */
public class Vertex implements Serializable
{
	private static final long serialVersionUID = -1378221843083731105L;
	private String id;
	private String name;
	
	public Vertex(mxICell vertex) 
	{
		name = vertex.getValue().toString();
		setId(vertex.getId());
	}
	
	public Vertex(String id, String name) 
	{
		this.id = id;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof Vertex)
		{
			Vertex v = (Vertex)obj;
			return id.equals(v.getId());
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() 
	{
		return id.hashCode();
	}
}
