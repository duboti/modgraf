package modgraf.jgrapht;

import org.jgrapht.VertexFactory;

/**
 * "Fabryka" wierzchołków wykorzystywana podczas generowania grafów.
 *
 * @author Daniel Pogrebniak
 */
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
