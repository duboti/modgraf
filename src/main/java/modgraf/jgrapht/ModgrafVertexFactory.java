package modgraf.jgrapht;

import modgraf.jgrapht.Vertex;

import org.jgrapht.VertexFactory;

public class ModgrafVertexFactory implements VertexFactory<Vertex>
{
	Integer counter = 0;
	
	@Override
	public Vertex createVertex()
	{
		++counter;
		String str = String.valueOf(counter);
		return new Vertex(str, str);
	}

}
