package modgraf.view;

import static com.mxgraph.util.mxConstants.*;
import static modgraf.view.properties.PreferencesTab.FONT_MAXIMUM_SIZE;
import static modgraf.view.properties.PreferencesTab.FONT_MINIMUM_SIZE;
import static modgraf.view.properties.VertexTab.BORDER_MAXIMUM_WIDTH;
import static modgraf.view.properties.VertexTab.BORDER_MINIMUM_WIDTH;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import modgraf.action.*;

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
    private Properties lang;
	private boolean useClassLoader;
	
	public PopupMenu(Editor e, MouseEvent ev)
	{
		editor = e;
        lang = editor.getLanguage();
		event = ev;
		String useClassLoaderString = e.getProperties().getProperty("use-class-loader");
		useClassLoader = Boolean.parseBoolean(useClassLoaderString);
		
		int selectionCount = editor.getGraphComponent().getGraph().getSelectionCount();

        if (selectionCount > 1)
            return;

		if (selectionCount == 0)
			add(createMenuItem("menu-vertex-add", 	new ActionAddVertexWithPosition(editor, event.getPoint()), 		"icons/add.png", 	"INSERT"));
		
		if (selectionCount == 1)
		{
			mxCell cell = (mxCell)editor.getGraphComponent().getGraph().getSelectionCell();
			if (cell.isVertex())
			{
                add(createMenuItem("menu-vertex-delete", 	new ActionRemoveSelected(editor), 	"icons/minus.png", 	"DELETE"));
				addSeparator();
				add(createMenuItem("frame-change-name", 		new ActionChangeName(editor)));
				add(createMenuItem("menu-vertex-fill-color", new ActionSetColor(editor, STYLE_FILLCOLOR, "frame-select-fill-color", true)));
				add(createMenuSize());
				add(createMenuShape());
				add(createMenuBorder());
				add(createMenuFont(true));
			}
			else
			{
				add(createMenuItem("menu-edge-delete", 		new ActionRemoveSelected(editor), 	"icons/minus.png", 	"DELETE"));
				addSeparator();
				add(createMenuItem("frame-change-weight", new ActionChangeName(editor)));
				add(createMenuItem("menu-edge-width",	new ActionSetIntegerValueStyle(editor, STYLE_STROKEWIDTH, "frame-change-line-width", BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH, false)));
                add(createMenuItem("menu-edge-color",	new ActionSetColor(editor, STYLE_STROKECOLOR, "frame-select-border-color", false)));
                add(createMenuFont(false));
			}
		}
	}
	
	private JMenuItem createMenuItem(String propertyName, ActionListener action)
	{
		return createMenuItem(propertyName, action, null, null);
	}
	
	private JMenuItem createMenuItem(String propertyName, ActionListener action, String icon, String keyStroke)
	{
		JMenuItem element = new JMenuItem(lang.getProperty(propertyName));
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

    private JMenu createMenuFont(boolean isVertex) {
        JMenu font = new JMenu(lang.getProperty("menu-vertex-font"));
        font.add(createMenuItem("menu-vertex-font-size",	new ActionSetIntegerValueStyle(editor, STYLE_FONTSIZE, "frame-change-font-size", FONT_MINIMUM_SIZE, FONT_MAXIMUM_SIZE, isVertex)));
        font.add(createMenuItem("menu-vertex-font-color",	new ActionSetColor(editor, STYLE_FONTCOLOR, "frame-select-font-color", isVertex)));
        font.add(createMenuItem("menu-vertex-font-family",	new ActionSetFontFamily(editor, isVertex)));
        return font;
    }

    private JMenu createMenuBorder() {
        JMenu border = new JMenu(lang.getProperty("menu-vertex-border"));
        border.add(createMenuItem("menu-vertex-border-width",	new ActionSetIntegerValueStyle(editor, STYLE_STROKEWIDTH, "frame-change-line-width", BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH, true)));
        border.add(createMenuItem("menu-vertex-border-color",	new ActionSetColor(editor, STYLE_STROKECOLOR, "frame-select-border-color", true)));
        return border;
    }

    private JMenu createMenuShape() {
        JMenu shape = new JMenu(lang.getProperty("menu-vertex-shape"));
        shape.add(createMenuItem("menu-vertex-shape-circle", 	new ActionSetShape(editor, SHAPE_ELLIPSE)));
        shape.add(createMenuItem("menu-vertex-shape-square", 	new ActionSetShape(editor, SHAPE_RECTANGLE)));
        shape.add(createMenuItem("menu-vertex-shape-rhombus", 	new ActionSetShape(editor, SHAPE_RHOMBUS)));
        shape.add(createMenuItem("menu-vertex-shape-cloud", 	new ActionSetShape(editor, SHAPE_CLOUD)));
        shape.add(createMenuItem("menu-vertex-shape-hexagon", 	new ActionSetShape(editor, SHAPE_HEXAGON)));
        return shape;
    }

    private JMenu createMenuSize() {
        JMenu size = new JMenu(lang.getProperty("menu-vertex-size"));
        size.add(createMenuItem("menu-vertex-size-small", 	new ActionSetSize(editor, 25.0)));
        size.add(createMenuItem("menu-vertex-size-medium", 	new ActionSetSize(editor, 50.0)));
        size.add(createMenuItem("menu-vertex-size-big", 	new ActionSetSize(editor, 100.0)));
        size.add(createMenuItem("menu-vertex-size-custom",	new ActionSetCustomSize(editor)));
        return size;
    }
}
