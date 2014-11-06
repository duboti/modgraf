package modgraf.view;

import static com.mxgraph.util.mxConstants.STYLE_FILLCOLOR;
import static com.mxgraph.util.mxConstants.STYLE_STROKEWIDTH;
import static modgraf.view.properties.VertexTab.BORDER_MAXIMUM_WIDTH;
import static modgraf.view.properties.VertexTab.BORDER_MINIMUM_WIDTH;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import modgraf.action.ActionAddVertexWithPosition;
import modgraf.action.ActionChangeName;
import modgraf.action.ActionRemoveSelected;
import modgraf.action.ActionSetColor;
import modgraf.action.ActionSetCustomSize;
import modgraf.action.ActionSetIntegerValueStyle;
import modgraf.action.ActionSetShape;
import modgraf.action.ActionSetSize;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;

/**
 * Klasa zawiera menu kontekstowe.
 * 
 * @author Daniel Pogrebniak
 *
 */
public class PopupMenu extends JPopupMenu
{
	private static final long serialVersionUID = -4353932150674206563L;

	private Editor editor;
	private MouseEvent event;
	private boolean useClassLoader;
	
	public PopupMenu(Editor e, MouseEvent ev)
	{
		editor = e;
		event = ev;
		String useClassLoaderString = e.getProperties().getProperty("use-class-loader");
		useClassLoader = Boolean.parseBoolean(useClassLoaderString);
		
		JMenu size = new JMenu("Rozmiar");
		size.add(createMenuItem("Mały", 		new ActionSetSize(editor, 25.0)));
		size.add(createMenuItem("Średni", 		new ActionSetSize(editor, 50.0)));
		size.add(createMenuItem("Duży", 		new ActionSetSize(editor, 100.0)));
		size.add(createMenuItem("Niestandardowy",new ActionSetCustomSize(editor)));
		
		JMenu shape = new JMenu("Kształt");
		shape.add(createMenuItem("Koło", 		new ActionSetShape(editor, mxConstants.SHAPE_ELLIPSE)));
		shape.add(createMenuItem("Kwadrat", 	new ActionSetShape(editor, mxConstants.SHAPE_RECTANGLE)));
		shape.add(createMenuItem("Romb", 		new ActionSetShape(editor, mxConstants.SHAPE_RHOMBUS)));
		shape.add(createMenuItem("Chmura", 		new ActionSetShape(editor, mxConstants.SHAPE_CLOUD)));
		shape.add(createMenuItem("Sześciokąt", 	new ActionSetShape(editor, mxConstants.SHAPE_HEXAGON)));
		
		int selectionCount = editor.getGraphComponent().getGraph().getSelectionCount();
		
		if (selectionCount == 0)
			add(createMenuItem("Dodaj wierzchołek", 	new ActionAddVertexWithPosition(editor, event.getPoint()), 		"icons/add.png", 	"INSERT"));
		
		if (selectionCount == 1)
		{
			mxCell cell = (mxCell)editor.getGraphComponent().getGraph().getSelectionCell();
			if (cell.isVertex())
			{
				add(createMenuItem("Usuń zaznaczony", 	new ActionRemoveSelected(editor), 	"icons/minus.png", 	"DELETE"));
				addSeparator();
				add(createMenuItem("Zmień nazwę", 		new ActionChangeName(editor)));
				add(createMenuItem("Kolor wypełnienia", new ActionSetColor(editor, STYLE_FILLCOLOR, "frame-select-fill-color")));
				add(createMenuItem("Grubość obramowania",new ActionSetIntegerValueStyle(editor, STYLE_STROKEWIDTH, "frame-change-line-width", BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH)));
				add(size);
				add(shape);
			}
			else
			{
				add(createMenuItem("Usuń krawędź", 		new ActionRemoveSelected(editor), 	"icons/minus.png", 	"DELETE"));
				addSeparator();
				add(createMenuItem("Zmień wagę krawędzi", new ActionChangeName(editor)));
				add(createMenuItem("Grubość krawędzi",	new ActionSetIntegerValueStyle(editor, STYLE_STROKEWIDTH, "frame-change-line-width", BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH)));
			}
		}
		
		if (selectionCount > 1)
		{
			add(createMenuItem("Usuń zaznaczone", 	new ActionRemoveSelected(editor), 	"icons/minus.png", 	"DELETE"));
			addSeparator();
			add(createMenuItem("Kolor wypełnienia", new ActionSetColor(editor, STYLE_FILLCOLOR, "frame-select-fill-color")));
			add(createMenuItem("Grubość krawędzi",	new ActionSetIntegerValueStyle(editor, STYLE_STROKEWIDTH, "frame-change-line-width", BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH)));
			add(size);
			add(shape);
		}
	}
	
	private JMenuItem createMenuItem(String name, ActionListener action)
	{
		return createMenuItem(name, action, null, null);
	}
	
	private JMenuItem createMenuItem(String name, ActionListener action, String icon, String keyStroke)
	{
		JMenuItem element = new JMenuItem(name);
		if (action != null)
			element.addActionListener(action);
		if (icon != null)
		{
			if (useClassLoader)
				element.setIcon(new ImageIcon(getClass().getClassLoader().getResource(icon)));
			else
				element.setIcon(new ImageIcon(icon));
		}
		if (keyStroke != null)
			element.setAccelerator(KeyStroke.getKeyStroke(keyStroke));
		//element.setMnemonic('N');
		return element;
	}
}
