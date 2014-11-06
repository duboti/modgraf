package modgraf.action;

import static com.mxgraph.util.mxConstants.STYLE_SHAPE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modgraf.view.Editor;

/**
 * Klasa odpowiada za zmianę kształtu wierzchołka.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionSetVertexStyle
 * @see ActionListener
 */
public class ActionSetShape extends ActionSetVertexStyle implements ActionListener 
{
	private String shape;
	
	public ActionSetShape(Editor e, String shape)
	{
		super(e, STYLE_SHAPE);
		this.shape = shape;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		setStyle(shape);
	}
}

