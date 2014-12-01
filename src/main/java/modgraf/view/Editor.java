package modgraf.view;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import modgraf.Main;
import modgraf.action.ActionSave;
import modgraf.event.*;
import modgraf.jgrapht.*;
import modgraf.jgrapht.edge.*;
import modgraf.view.properties.Language;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static modgraf.view.properties.DefaultProperties.createDefaultLanguage;
import static modgraf.view.properties.DefaultProperties.createDefaultProperties;

/**
 * Najważniejsza klasa w progrmie. Prawie wszystkie pozostałe klasy 
 * wymagają podania instancji tej klasy w konstruktorze.
 * 
 * @author Daniel Pogrebniak
 *
 */
public class Editor
{
	private mxGraphComponent graphComponent;
	private Graph<Vertex, ModgrafEdge> graphT;
	private JTextComponent textPane;
	private MenuBar menuBar;
	private int vertexCounter;
	private Hashtable<String, String> vertexValuesIdMap;
	private Hashtable<String, String> edgeSourceTargetIdMap;
	private Map<String, Vertex> vertices;
	private Map<String, ModgrafEdge> edges;
	private File currentFile;
	private boolean modified;
	private JFrame frame;
	private Toolbar toolbar;
	private Properties properties;
	private Properties language;
	private AlgorithmMenuItems ami;
	
	/**
	 * Konstruktor. Jest wywoływany w metodzie <code>main(String[] args)
	 * </code> klasy {@link Main}.
	 * @see Main
	 */
	public Editor()
	{
		properties = createDefaultProperties();
		loadPropertiesFile();
		if (language == null)
			language = createDefaultLanguage();
		ami = new AlgorithmMenuItems();
		menuBar = new MenuBar(this, ami);
		toolbar = new Toolbar(this);
		graphT = createNewGraphT(false, 0);
		createTextPane();
		createGraphComponent();
	}

	private void createGraphComponent()
	{
		mxGraph graph = createNewMxGraph();
		graphComponent = new mxGraphComponent(graph);
		graphComponent.setPreferredSize(createDimensionFromProperty("graphComponent-width", "graphComponent-height"));
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(mxUtils.parseColor(properties.getProperty("background-color")));
		addGraphComponentListeners();
	}

	public void loadPropertiesFile()
	{
		File propFile = new File(properties.getProperty("properties-file"));
		try
		{
			if (propFile.exists())
			{
				properties.load(new FileReader(propFile));
				setLanguage(properties.getProperty("language"));
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(graphComponent,
					language.getProperty("warning-not-load-properties-file"),
					language.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
		}
	}

	private void addGraphComponentListeners()
	{
		graphComponent.addListener(mxEvent.LABEL_CHANGED, new EventLabelChangedListener(this));
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (e.isPopupTrigger())
					showGraphPopupMenu(e);
			}
		});
	}

	private void showGraphPopupMenu(MouseEvent e)
	{
		PopupMenu menu = new PopupMenu(this, e);
		menu.setLightWeightPopupEnabled(false);
		menu.show(e.getComponent(), e.getX(), e.getY());
		e.consume();
	}

	private void createTextPane()
	{
		textPane = new JTextPane();
		textPane.setFont(new Font("Arial", Font.PLAIN, 14));
		textPane.setEditable(false);
		textPane.setPreferredSize(createDimensionFromProperty("textPane-width", "textPane-height"));
	}
	
	/**
	 * @return Komponent odpowiedzialny za wyświetlanie grafu.
	 */
	public mxGraphComponent getGraphComponent()
	{
		return graphComponent;
	}
	
	/**
	 * @return Graf warstwy matematycznej.
	 */
	public Graph<Vertex, ModgrafEdge> getGraphT()
	{
		return graphT;
	}
	
	/**
	 * @param gr Graf warstwy matematycznej.
	 */
	public void setGraphT(Graph<Vertex, ModgrafEdge> gr)
	{
		graphT = gr;
	}
	
	/**
	 * @param str napis, który zostanie ustawiony w polu tekstowym
	 */
	public void setText(String str)
	{
		textPane.setText(str);
	}
	
	/**
	 * @param vc wartość licznika wierzchołków
	 */
	public void setVertexCounter(int vc)
	{
		vertexCounter = vc;
	}
	
	/**
	 * @return Licznik wierzchołków. Wykorzystywany przy nadawaniu nazw początkowych.
	 */
	public int getVertexCounter()
	{
		return vertexCounter;
	}
	
	/**
	 * @return najniższy numer wierzchołka, który jeszcze nie wystąpił
	 */
	public int incrementAndGetNewVertexCounter()
	{
		while (vertexValuesIdMap.containsKey(Integer.toString(++vertexCounter)));
		return vertexCounter;
	}
	
	/**
	 * @param vertexValue nazwa wierzchołka
	 * @param vertexId id wierzchołka
	 */
	public void setVertexId (String vertexValue, String vertexId)
	{
		vertexValuesIdMap.put(vertexValue, vertexId);
	}
	
	/**
	 * @param vertexValue nazwa wierzchołka
	 * @return id wierzchołka
	 */
	public String getVertexId (String vertexValue)
	{
		return vertexValuesIdMap.get(vertexValue);
	}
	
	/**
	 * Metoda usuwa z mapy <code>vertexValuesIdMap</code> wierzchołek o nazwie 
	 * <code>vertexValue</code> i zwraca jego id.
	 * 
	 * @param vertexValue nazwa wierzchołka
	 * @return id usuniętego wierzchołka
	 */
	public String removeVertexId (String vertexValue)
	{
		return vertexValuesIdMap.remove(vertexValue);
	}
	
	/**
	 * @return zbiór nazw wszystkich wierzchołków
	 */
	public Set<String> getVertexValuesSet ()
	{
		return vertexValuesIdMap.keySet();
	}
	
	public Map<String, Vertex> getVertices() {
		return vertices;
	}

	public Map<String, ModgrafEdge> getEdges() {
		return edges;
	}

	/**
	 * @param source id źródła
	 * @param target id celu
	 * @param id krawędzi między wierzchołkiem o id <code>source</code>
	 * 			 a wierzchołkiem o id <code>target</code>
	 */
	public void setEdgeId (String source, String target, String id)
	{
		edgeSourceTargetIdMap.put(source+","+target, id);
	}
	
	/**
	 * @param source id źródła
	 * @param target id celu
	 * @return id krawędzi między wierzchołkiem o id <code>source</code>
	 * 			 a wierzchołkiem o id <code>target</code>
	 */
	public String getEdgeId (String source, String target)
	{
		return edgeSourceTargetIdMap.get(source+","+target);
	}
	
	/**
	 * Metoda usuwa z mapy <code>edgeSourceTargetIdMap</code> krawędź 
	 * między wierzchołkiem o id <code>source</code> a wierzchołkiem o 
	 * id <code>target</code> i zwraca id tej krawędzi.
	 * 
	 * @param source id źródła
	 * @param target id celu
	 * @return usunięta krawędź w przypadku powodzenia 
	 * 			lub <code>null</code> w przypadku błędu
	 */
	public String removeEdgeId (String source, String target)
	{
		return edgeSourceTargetIdMap.remove(source+","+target);
	}
	
	/**
	 * Metoda dodaje algorytm o podanej nazwie do menu <i>Algorytmy</i>. 
	 * @param name nazwa widoczna w menu <i>Algorytmy</i>
	 * @param algorithm instancja klasy z algorytmem
	 */
	public void addAlgorithmToMemuBar(String name, ActionListener algorithm)
	{
		menuBar.addAlgorithm(name, algorithm, 2, 3);
	}
	
	/**
	 * Metoda tworzy i wyświetla główne okno programu. Jest wywoływana w metodzie <code>main 
	 * (String[] args)</code> klasy <code>Main</code>.
	 */
	public void createFrame()
	{
		frame = new JFrame(properties.getProperty("program-name"));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		frame.add(toolbar, BorderLayout.NORTH);
		Dimension minimumSize = new Dimension(100, 100);
		graphComponent.setMinimumSize(minimumSize);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphComponent, new JScrollPane(textPane));
		splitPane.setOneTouchExpandable(true);
		frame.add(splitPane, BorderLayout.CENTER);
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.addWindowListener(new ActionSave(this));
		updateTitle();
		frame.setVisible(true);
	}
	
	/**
	 * @return nowy graf warstwy wizualnej
	 */
	public mxGraph createNewMxGraph()
	{
		mxGraph graph = new mxGraph();
		graph.setAllowDanglingEdges(false);
		graph.setEdgeLabelsMovable(true);
		graph.addListener(mxEvent.CELLS_ADDED, new EventAddCellsListener(this));
		graph.addListener(mxEvent.REMOVE_CELLS, new EventRemoveCellsListener(this));
		graph.addListener(mxEvent.CONNECT_CELL, new EventConnectCellListener(this));
		graph.addListener(mxEvent.SPLIT_EDGE, new EventSplitEdgeListener(this));
		graph.getModel().addListener(mxEvent.CHANGE, new mxIEventListener()
		{
			public void invoke(Object source, mxEventObject evt)
			{
				setModified(true);
			}
		});
		vertexCounter = 0;
		vertexValuesIdMap = new Hashtable<>();
		edgeSourceTargetIdMap = new Hashtable<>();
		vertices = new Hashtable<>();
		edges = new Hashtable<>();
		currentFile = null;
		setModified(false);
		mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> vertexStyle = createVertexStyle();
		stylesheet.putCellStyle("vertexStyle", vertexStyle);
		createEdgeStyle(stylesheet);
		return graph;
	}

	private void createEdgeStyle(mxStylesheet stylesheet) 
	{
		Map<String, Object> edgeStyle = stylesheet.getDefaultEdgeStyle();
		edgeStyle.put(mxConstants.STYLE_STROKEWIDTH, properties.getProperty("default-edge-width"));
		edgeStyle.put(mxConstants.STYLE_STROKECOLOR, properties.getProperty("default-edge-color"));
		edgeStyle.put(mxConstants.STYLE_FONTSIZE, properties.getProperty("default-edge-font-size"));
		edgeStyle.put(mxConstants.STYLE_FONTCOLOR, properties.getProperty("default-edge-font-color"));
		if (graphT instanceof DirectedGraph)
			edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		else
			edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE);
	}

	private Hashtable<String, Object> createVertexStyle()
	{
		Hashtable<String, Object> vertexStyle = new Hashtable<>();
		vertexStyle.put(mxConstants.STYLE_SHAPE, properties.getProperty("default-vertex-shape"));
		vertexStyle.put(mxConstants.STYLE_FILLCOLOR, properties.getProperty("default-vertex-fill-color"));
		vertexStyle.put(mxConstants.STYLE_STROKECOLOR, properties.getProperty("default-vertex-border-color"));
		vertexStyle.put(mxConstants.STYLE_STROKEWIDTH, properties.getProperty("default-vertex-border-width"));
		vertexStyle.put(mxConstants.STYLE_FONTFAMILY, properties.getProperty("default-vertex-font-family"));
		vertexStyle.put(mxConstants.STYLE_FONTSIZE, properties.getProperty("default-vertex-font-size"));
		vertexStyle.put(mxConstants.STYLE_FONTCOLOR, properties.getProperty("default-vertex-font-color"));
		return vertexStyle;
	}
	
	/**
	 * Metoda tworzy nowy graf warstwy matematycznej o typie zależnym od podanych parametrów.
	 * 
	 * @param directed <code>true</code> jeśli skierowany, <code>false</code> jeśli nieskierowany
	 * @param egdeWeight liczba parametrów krawędzi
	 * @return graf warstwy matematycznej
	 */
	public Graph<Vertex, ModgrafEdge> createNewGraphT (boolean directed, int egdeWeight)
	{
		Graph<Vertex, ModgrafEdge> graph = null;
		int number = egdeWeight;
		if (directed)
			number = number + 10;
		switch (number)
		{
		case 0:
			ModgrafEdgeFactory<UndirectedEdge> edgeFactory0 = 
					new ModgrafEdgeFactory<>(UndirectedEdge.class, this);
			graph = new SimpleGraph<>(edgeFactory0);
			break;
		case 1:
			ModgrafEdgeFactory<WeightedEdgeImpl> edgeFactory1 = 
					new ModgrafEdgeFactory<>(WeightedEdgeImpl.class, this);
			graph = new ModgrafUndirectedWeightedGraph(edgeFactory1);
			break;
		case 2:
			ModgrafEdgeFactory<DoubleWeightedEdgeImpl> edgeFactory2 = 
					new ModgrafEdgeFactory<>(DoubleWeightedEdgeImpl.class, this);
			graph = new UndirectedDoubleWeightedGraph<>(edgeFactory2);
			break;
		case 10:
			ModgrafEdgeFactory<DirectedEdge> edgeFactory10 = 
					new ModgrafEdgeFactory<>(DirectedEdge.class, this);
			graph = new SimpleDirectedGraph<>(edgeFactory10);
			break;
		case 11:
			ModgrafEdgeFactory<DirectedWeightedEdge> edgeFactory11 = 
					new ModgrafEdgeFactory<>(DirectedWeightedEdge.class, this);
			graph = new ModgrafDirectedWeightedGraph(edgeFactory11);
			break;
		case 12:
			ModgrafEdgeFactory<DirectedDoubleWeightedEdge> edgeFactory12 = 
					new ModgrafEdgeFactory<>(DirectedDoubleWeightedEdge.class, this);
			graph = new DirectedDoubleWeightedGraph<>(edgeFactory12);
			break;
		}
		return graph;
	}
	
	/**
	 * Metoda ustawia w polu tekstowym napis informujący o poprawnym stworzeniu
	 * lub wczytaniu grafu określonego typu. 
	 * 
	 * @param directed <code>true</code> jeśli skierowany, <code>false</code> jeśli nieskierowany
	 * @param edgeWeightDegree liczba parametrów krawędzi
	 * @param read <code>true</code> jeśli wczytwanie grafu z pliku, 
	 * 			<code>false</code> jeśli tworzenie nowego grafu
	 */
	public void setTextAboutNewGraph(boolean directed, int edgeWeightDegree, boolean read)
	{
		StringBuilder sb = new StringBuilder();
		if (read)
			sb.append(language.getProperty("text-read-graph"));
		else
			sb.append(language.getProperty("text-create-graph"));
		String graphType;
		if (directed)
			graphType = language.getProperty("graph-type-directed");
		else
			graphType = language.getProperty("graph-type-undirected");
		String edgeType = null;
		if (edgeWeightDegree == 0)
			edgeType = language.getProperty("edge-type-0");
		if (edgeWeightDegree == 1)
			edgeType = language.getProperty("edge-type-1");
		if (edgeWeightDegree == 2)
			edgeType = language.getProperty("edge-type-2");
		toolbar.setEdgeTypeText(edgeType);
		toolbar.setGraphTypeText(graphType);
		sb.append(" ");
		sb.append(graphType);
		sb.append(" ");
		sb.append(edgeType);
		sb.append(".");
		setText(sb.toString());
		ami.enableSpecifiedAlgorithms(directed, edgeWeightDegree);
	}
	
	/**
	 * @param file aktualnie otwarty plik
	 */
	public void setCurrentFile(File file)
	{
		File oldValue = currentFile;
		currentFile = file;
		if (oldValue != file)
			updateTitle();
	}

	/**
	 * @return aktualnie otwarty plik
	 */
	public File getCurrentFile()
	{
		return currentFile;
	}

	/**
	 * @param modified true jeśli istnieją niezapisane zmiany
	 */
	public void setModified(boolean modified)
	{
		boolean oldValue = this.modified;
		this.modified = modified;
		if (oldValue != modified)
			updateTitle();
	}

	/**
	 * @return true jeśli istnieją niezapisane zmiany
	 */
	public boolean isModified()
	{
		return modified;
	}

	private void updateTitle()
	{
		if (frame != null)
		{
			String title = "";
			if (modified)
				title += "*";
			if (currentFile != null)
				title += currentFile.getAbsolutePath();
			else
				title += language.getProperty("frame-new-graph-name");
			frame.setTitle(title + " - " + properties.getProperty("program-name"));
		}
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * @return the language
	 */
	public Properties getLanguage()
	{
		return language;
	}
	
	private void setLanguage(String languageName)
	{
		if (languageName.equals("polski"))
			language = Language.createPolishLanguage();
		if (languageName.equals("english"))
			language = Language.createEnglishLanguage();
	}
	
	public void updateFrameLanguage()
	{
		if (frame != null)
		{
			frame.setVisible(false);
			frame.remove(menuBar);
			frame.remove(toolbar);
			menuBar = new MenuBar(this, ami);
			toolbar = new Toolbar(this);
			frame.setJMenuBar(menuBar);
			frame.add(toolbar, BorderLayout.NORTH);
			setText("");
			frame.setVisible(true);
		}
	}
	
	public void enableAllMenuElements()
	{
		menuBar.enableAllElements();
		toolbar.enableAllElements();
	}
	
	public Dimension createDimensionFromProperty(String widthName, String heightName)
	{
		int width = Integer.parseInt(properties.getProperty(widthName));
		int height = Integer.parseInt(properties.getProperty(heightName));
		return new Dimension(width, height);
	}
}
