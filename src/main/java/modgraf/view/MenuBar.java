package modgraf.view;

import static com.mxgraph.util.mxConstants.SHAPE_CLOUD;
import static com.mxgraph.util.mxConstants.SHAPE_ELLIPSE;
import static com.mxgraph.util.mxConstants.SHAPE_HEXAGON;
import static com.mxgraph.util.mxConstants.SHAPE_RECTANGLE;
import static com.mxgraph.util.mxConstants.SHAPE_RHOMBUS;
import static com.mxgraph.util.mxConstants.STYLE_FILLCOLOR;
import static com.mxgraph.util.mxConstants.STYLE_FONTCOLOR;
import static com.mxgraph.util.mxConstants.STYLE_FONTSIZE;
import static com.mxgraph.util.mxConstants.STYLE_STROKECOLOR;
import static com.mxgraph.util.mxConstants.STYLE_STROKEWIDTH;
import static modgraf.view.properties.PreferencesTab.FONT_MAXIMUM_SIZE;
import static modgraf.view.properties.PreferencesTab.FONT_MINIMUM_SIZE;
import static modgraf.view.properties.VertexTab.BORDER_MAXIMUM_WIDTH;
import static modgraf.view.properties.VertexTab.BORDER_MINIMUM_WIDTH;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import modgraf.action.*;
import modgraf.algorithm.ModgrafBusackerGowenCheapestFlow;
import modgraf.algorithm.ModgrafChromaticNumber;
import modgraf.algorithm.ModgrafEdgeColoring;
import modgraf.algorithm.ModgrafEdmondsKarpMaximumFlow;
import modgraf.algorithm.ModgrafHamiltonianCycle;
import modgraf.algorithm.ModgrafShortestPath;
import modgraf.algorithm.steps.DijkstraAlgorithm;
import modgraf.view.properties.Preferences;

/**
 * Klasa zawiera pasek menu.
 * 
 * @author Daniel Pogrebniak
 *
 */
public class MenuBar extends JMenuBar 
{
	private Editor editor;
	private JMenu algorithm;
	private Properties lang;
	private AlgorithmMenuItems ami;
	boolean useClassLoader;
	
	private static final long serialVersionUID = -31093236943864394L;
	private JMenu steps;

	public MenuBar(Editor e, AlgorithmMenuItems ami)
	{
		editor = e;
		lang = editor.getLanguage();
		this.ami = ami;
		String useClassLoaderString = e.getProperties().getProperty("use-class-loader");
		useClassLoader = Boolean.parseBoolean(useClassLoaderString);
		
		JMenu file = new JMenu(lang.getProperty("menu-file"));
		file.add(createMenuItem("menu-file-new", 	new ActionNewGraph(editor), true, "icons/new.gif", 	"ctrl N"));
		file.add(createMenuItem("menu-file-open", 	new ActionOpen(editor), 	true, "icons/open.png",	"ctrl O"));
		file.add(createMenuItem("menu-file-save", 	new ActionSave(editor), 	false,"icons/save.gif", "ctrl S"));
		file.add(createMenuItem("menu-file-saveas",	new ActionSaveAs(editor), 	false));
		file.add(createMenuItem("menu-file-save-text-result",	new ActionSaveTextResult(editor), 	false));
		add(file);
		
		JMenu vertex = new JMenu(lang.getProperty("menu-vertex"));
		vertex.add(createMenuItem("menu-vertex-add", 	new ActionAddVertex(editor), 		false, "icons/add.png", 	"INSERT"));
		vertex.add(createMenuItem("menu-vertex-delete", new ActionRemoveSelected(editor), 	false, "icons/minus.png", 	"DELETE"));
		vertex.add(createMenuItem("menu-vertex-fill-color", 	new ActionSetColor(editor, STYLE_FILLCOLOR, "frame-select-fill-color", true)));
		vertex.add(createMenuSize());
		vertex.add(createMenuShape());
		vertex.add(createMenuBorder());
		vertex.add(createMenuFont(true));
		add(vertex);

        JMenu edge = new JMenu(lang.getProperty("menu-edge"));
        edge.add(createMenuItem("menu-edge-delete", new ActionRemoveSelected(editor), 	false, "icons/minus.png", 	"DELETE"));
        edge.add(createMenuItem("menu-edge-width",	new ActionSetIntegerValueStyle(editor, STYLE_STROKEWIDTH, "frame-change-line-width", BORDER_MINIMUM_WIDTH, BORDER_MAXIMUM_WIDTH, false)));
        edge.add(createMenuItem("menu-edge-color",	new ActionSetColor(editor, STYLE_STROKECOLOR, "frame-select-border-color", false)));
        edge.add(createMenuFont(false));
        add(edge);

		algorithm = new JMenu(lang.getProperty("menu-algorithm"));
		algorithm.add(createMenuItem("menu-algorithm-add", new ActionAddNewAlghoritm(editor)));
		algorithm.addSeparator();
		JMenu shortestPath = new JMenu(lang.getProperty("menu-algorithm-shortest-path"));
		shortestPath.add(createDisabledAlgorithm("menu-algorithm-shortest-path-bf",	new ModgrafShortestPath(editor, ModgrafShortestPath.Algorithm.BellmanFord), 2, 1));
		shortestPath.add(createDisabledAlgorithm("menu-algorithm-shortest-path-d",	new ModgrafShortestPath(editor, ModgrafShortestPath.Algorithm.Dijkstra), 2, 1));
		shortestPath.add(createDisabledAlgorithm("menu-algorithm-shortest-path-fw",	new ModgrafShortestPath(editor, ModgrafShortestPath.Algorithm.FloydWarshall), 2, 1));
		algorithm.add(shortestPath);
		algorithm.add(createDisabledAlgorithm("menu-algorithm-maximum-flow", 	 new ModgrafEdmondsKarpMaximumFlow(editor), 0, 1));
		algorithm.add(createDisabledAlgorithm("menu-algorithm-cheapest-flow", 	new ModgrafBusackerGowenCheapestFlow(editor), 0, 2));
		algorithm.add(createDisabledAlgorithm("menu-algorithm-hamiltonian-cycle",new ModgrafHamiltonianCycle(editor), 2, 1));
		algorithm.add(createDisabledAlgorithm("menu-algorithm-chromatic-number", new ModgrafChromaticNumber(editor), 2, 3));
		algorithm.add(createDisabledAlgorithm("menu-algorithm-edge-coloring", new ModgrafEdgeColoring(editor), 2, 3));
		add(algorithm);
		
		steps = new JMenu(lang.getProperty("menu-algorithm-steps"));
		steps.add(createDisabledAlgorithm("menu-algorithm-steps-dijkstra",	new DijkstraAlgorithm(editor), 2, 1));
		add(steps);
		
		JMenu utils = new JMenu(lang.getProperty("menu-utils"));
		utils.add(createMenuItem("menu-utils-preferences", new Preferences(editor)));
		utils.add(createMenuItem("menu-utils-clear-styles", new ActionClearStyles(editor)));
		JMenu generators = new JMenu(lang.getProperty("menu-utils-generators"));
		generators.add(createMenuItem("menu-utils-gen-complete", new ActionGenerateGraph(editor, ActionGenerateGraph.Type.COMPLETE)));
		generators.add(createMenuItem("menu-utils-gen-random", new ActionGenerateGraph(editor, ActionGenerateGraph.Type.RANDOM)));
		generators.add(createMenuItem("menu-utils-gen-ring", new ActionGenerateGraph(editor, ActionGenerateGraph.Type.RING)));
		generators.add(createMenuItem("menu-utils-gen-scale-free", new ActionGenerateGraph(editor, ActionGenerateGraph.Type.SCALE_FREE)));
		generators.add(createMenuItem("menu-utils-gen-complete-bipartite", new ActionGenerateGraph(editor, ActionGenerateGraph.Type.COMPLETE_BIPARTITE)));
//		generators.add(createMenuItem("menu-utils-gen-random-flow", new ActionGenerateGraph(editor, ActionGenerateGraph.Type.RANDOM_FLOW)));
		utils.add(generators);
		add(utils);
		
		JMenu help = new JMenu(lang.getProperty("menu-help"));
		help.add(createMenuItem("menu-help-help", new Help(editor)));
		help.addSeparator();
		help.add(createMenuItem("menu-help-about", new AboutModgraf(editor)));
		add(help);
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

    private JMenuItem createMenuItem(String propertyName, ActionListener action)
	{
		return createMenuItem(propertyName, action, true);
	}
	
	private JMenuItem createMenuItem(String propertyName, ActionListener action, boolean enabled)
	{
		return createMenuItem(propertyName, action, enabled, null, null);
	}
	
	private JMenuItem createMenuItem(String propertyName, ActionListener action, boolean enabled, String icon, String keyStroke)
	{
		JMenuItem element = new JMenuItem(lang.getProperty(propertyName));
		element.setEnabled(enabled);
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
		return element;
	}
	
	public JMenuItem addAlgorithm(String propertyName, ActionListener action, int directedType, int egdeWeight)
	{
		JMenuItem alg = createDisabledAlgorithm(propertyName, action, directedType, egdeWeight);
		alg.setEnabled(true);
		algorithm.add(alg);
		return alg;
	}
	
	private JMenuItem createDisabledAlgorithm(String propertyName, ActionListener action, int directedType, int egdeWeight)
	{
		JMenuItem alg = createMenuItem(propertyName, action, false);
		ami.addAlgorithm(alg, directedType, egdeWeight);
		return alg;
	}
	
	public void enableAllElements()
	{
		Component[] components = this.getComponents();
		for(Component menu:components)
		{
			if (menu instanceof JMenu && !menu.equals(algorithm) && !menu.equals(steps))
			{
				Component[] menuItems = ((JMenu)menu).getMenuComponents();
				for (Component menuItem:menuItems)
					menuItem.setEnabled(true);
			}
		}
	}
}
