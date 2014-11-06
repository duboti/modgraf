package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.DoubleWeightedEdge;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.jgrapht.edge.WeightedEdge;
import modgraf.view.Editor;

import org.jgrapht.Graph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mxgraph.io.mxCodec;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngTextDecoder;
import com.mxgraph.view.mxGraph;

/**
 * Klasa odpowiada za wczytanie grafu z pliku.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionOpen implements ActionListener 
{
	private Editor editor;
	private Properties lang;
	private Properties prop;
	
	public ActionOpen(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
		prop = e.getProperties();
	}
	
	/**
	 * Metoda (w razie potrzeby) wyświetla okno z informacją o istnieniu niezapisanych 
	 * zmian, a następnie okno z wyborem pliku do wczytania.<br>
	 * Metoda jest wywoływana z menu <i>Plik --> Otwórz<i>.
	 */
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if (!editor.isModified()
				|| JOptionPane.showConfirmDialog(editor.getGraphComponent(),
						lang.getProperty("question-changes-exist")) 
						== JOptionPane.YES_OPTION)
		{
			openFile();
		}
	}

	/**
	 * Metoda otwiera wybrany plik.
	 */
	private void openFile()
	{
		JFileChooser chooser = buildChooser();
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			try 
			{
				chooseFileType(chooser);
				editor.setCurrentFile(chooser.getSelectedFile());
				editor.setModified(false);
				editor.enableAllMenuElements();
			} 
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("error-not-open-file"),
					    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
			}
			catch (RuntimeException e)
			{
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("error-not-graph"),
					    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * @return Okno wyboru pliku do wczytania.
	 */
	private JFileChooser buildChooser() 
	{
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		FileNameExtensionFilter filterAllSupported = new FileNameExtensionFilter(
				lang.getProperty("files-all"), "xml", "grf", "png");
		FileNameExtensionFilter filterGrf = new FileNameExtensionFilter(lang.getProperty("files-grf"), "grf");
		FileNameExtensionFilter filterXml = new FileNameExtensionFilter(lang.getProperty("files-xml"), "xml");
		FileNameExtensionFilter filterPng = new FileNameExtensionFilter(lang.getProperty("files-png"), "png");
		chooser.addChoosableFileFilter(filterAllSupported);
		chooser.addChoosableFileFilter(filterXml);
		chooser.addChoosableFileFilter(filterGrf);
	    chooser.addChoosableFileFilter(filterPng);
	    chooser.setFileFilter(filterAllSupported);
		return chooser;
	}

	/**
	 * Metoda wybiera sposób wczytywania pliku po jego rozszerzeniu.
	 * @param chooser 
	 * @throws IOException
	 */
	private void chooseFileType(JFileChooser chooser) throws IOException
	{
		String fileName = chooser.getSelectedFile().getName();
		if (fileName.endsWith(".xml") || fileName.endsWith(".XML"))
			openXml(chooser);
		else if (fileName.endsWith(".grf") || fileName.endsWith(".GRF"))
			new ActionOpenGrf(editor).openGrf(chooser);	
		else if (fileName.endsWith(".png") || fileName.endsWith(".PNG"))
			openPng(chooser);
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("error-not-supported-extension"),
				    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
	}

	

	private void openXml(JFileChooser chooser) throws IOException
	{
		Document xmlDocument = buildXmlDocument(chooser);
		createGraphTFromXmlDocument(xmlDocument);
		setmxGraph(xmlDocument);
	}

	private Document buildXmlDocument(JFileChooser chooser) throws IOException 
	{
		String file = readFile(chooser);
		Document document = mxXmlUtils.parseXml(file);
		document.getDocumentElement().normalize();
		return document;
	}
	
	public String readFile(JFileChooser chooser) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(chooser.getSelectedFile()), prop.getProperty("file-encoding")));
		StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
		return sb.toString();
	}
	
	private void createGraphTFromXmlDocument(Document document) 
	{
		NodeList mxGraphModelList = document.getElementsByTagName("mxGraphModel");
		if (mxGraphModelList != null)
		{
			Element graphModel = (Element)mxGraphModelList.item(0);
			boolean directed = graphModel.getAttribute("type").equals("directed");
			int edgeWeightDegree = Integer.parseInt(graphModel.getAttribute("weighted"));
			int vertexCounter = Integer.parseInt(graphModel.getAttribute("vertexCounter"));
			Graph<Vertex, ModgrafEdge> graphT = editor.createNewGraphT(directed, edgeWeightDegree);
			NodeList mxCellList = document.getElementsByTagName("mxCell");
			for (int i = 0; i < mxCellList.getLength(); ++i)
			{
				Node nodeCell = mxCellList.item(i);
				if (nodeCell.getNodeType() == Node.ELEMENT_NODE)
				{
					Element elementCell = (Element)nodeCell;
					addVertex(graphT, elementCell, edgeWeightDegree);
				}
			}
			for (int i = 0; i < mxCellList.getLength(); ++i)
			{
				Node nodeCell = mxCellList.item(i);
				if (nodeCell.getNodeType() == Node.ELEMENT_NODE)
				{
					Element elementCell = (Element)nodeCell;
					addEdge(graphT, elementCell, edgeWeightDegree);
				}
			}
			editor.setVertexCounter(vertexCounter);
			editor.setGraphT(graphT);
			editor.setTextAboutNewGraph(directed, edgeWeightDegree, true);
		}
	}

	private void addVertex(Graph<Vertex, ModgrafEdge> graphT, Element elementCell, int edgeWeightDegree) 
	{
		String vertex = elementCell.getAttribute("vertex");
		String id = elementCell.getAttribute("id");
		String name = elementCell.getAttribute("value");
		if (vertex != null && vertex.equals("1"))
		{
			Vertex v = new Vertex(id, name);
			editor.setVertexId(name, id);
			graphT.addVertex(v);
			editor.getVertices().put(id, v);
		}
	}
	
	private void addEdge(Graph<Vertex, ModgrafEdge> graphT, Element elementCell, int edgeWeightDegree) 
	{
		String edge = elementCell.getAttribute("edge");
		String id = elementCell.getAttribute("id");
		if (edge != null && edge.equals("1"))
		{
			String source = elementCell.getAttribute("source");
			String target = elementCell.getAttribute("target");
			Map<String, Vertex> vertices = editor.getVertices();
			editor.setEdgeId(source, target, id);
			ModgrafEdge e = graphT.addEdge(vertices.get(source), vertices.get(target));
			e.setId(id);
			editor.getEdges().put(id, e);
			if (edgeWeightDegree == 1)
				setWeightedEdgeValue(elementCell, (WeightedEdge) e);
			if (edgeWeightDegree == 2)
				setDoubleWeightedEdgeValue(elementCell, (DoubleWeightedEdge) e);
		}
	}

	private void setWeightedEdgeValue(Element elementCell, WeightedEdge e)
	{
		double weight = Double.parseDouble(elementCell.getAttribute("value"));
		e.setWeight(weight);
	}

	private void setDoubleWeightedEdgeValue(Element elementCell, DoubleWeightedEdge e)
	{
		String value = elementCell.getAttribute("value");
		String[] values = value.split("/");
		double capacity = Double.parseDouble(values[0]);
		double cost = Double.parseDouble(values[1]);
		e.setCapacity(capacity);
		e.setCost(cost);
	}

	private void setmxGraph(Document document) 
	{
		mxCodec codec = new mxCodec(document);
		mxGraph graf = editor.getGraphComponent().getGraph();
		NodeList mxGraphModelList = document.getElementsByTagName("mxGraphModel");
		NodeList mxStylesheetList = document.getElementsByTagName("mxStylesheet");
		if (mxStylesheetList != null)
			codec.decode(mxStylesheetList.item(0), graf.getStylesheet());
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
				    lang.getProperty("warning-missing-style-definition"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
		if (mxGraphModelList != null)
			codec.decode(mxGraphModelList.item(0), graf.getModel());
		else
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("warning-missing-graph-definition"),
				    lang.getProperty("warning"), JOptionPane.WARNING_MESSAGE);
	}

	private void openPng(JFileChooser chooser) throws IOException
	{
		FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
		Map<String, String> text = mxPngTextDecoder.decodeCompressedText(fis);
		fis.close();
		if (text != null)
		{
			String value = text.get("mxGraphModel");
			if (value != null)
			{
				String xml = URLDecoder.decode(value, "UTF-8");
				Document document = mxXmlUtils.parseXml(xml);
				createGraphTFromXmlDocument(document);
				setmxGraph(document);
			}
			else
				JOptionPane.showMessageDialog(editor.getGraphComponent(), 
						lang.getProperty("error-missing-graph-xml"), 
						lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
		}
	}
}

