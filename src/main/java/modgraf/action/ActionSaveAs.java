package modgraf.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import modgraf.jgrapht.DoubleWeightedGraph;
import modgraf.jgrapht.Vertex;
import modgraf.jgrapht.edge.DoubleWeightedEdge;
import modgraf.jgrapht.edge.ModgrafEdge;
import modgraf.jgrapht.edge.WeightedEdge;
import modgraf.view.Editor;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;

import static modgraf.action.ActionGraphTypeConverter.NewType.*;

/**
 * Klasa odpowiada za zapisanie grafu do dowolnego pliku.
 * 
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionSaveAs implements ActionListener 
{
	private Editor editor;
	private Properties lang;
	
	public ActionSaveAs(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		saveFileAs();
	}

	protected void saveFileAs()
	{
		JFileChooser chooser = buildChooser();
	    int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	File selectedFile = chooser.getSelectedFile();
	    	if (!selectedFile.exists() || JOptionPane.showConfirmDialog(editor.getGraphComponent(),
	    			lang.getProperty("question-file-exists")) == JOptionPane.YES_OPTION)
	    	{
	    		String fileName = getFileName(chooser);
		       	saveFile(fileName);
	    	}
	    }
	}

	protected void saveFile(String fileName)
	{
		mxGraphComponent graphComponent = editor.getGraphComponent();
		Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
		File file = null; 
		if (fileName.endsWith(".xml"))
			file = saveXml(fileName, graphComponent, graphT);
		if (fileName.endsWith(".png"))
			file = savePng(fileName, graphComponent, graphT);	
		if (fileName.endsWith(".grf"))
			file = saveGrf(fileName, null);
		editor.setCurrentFile(file);
		editor.setModified(false);
		editor.setText(lang.getProperty("message-save-file")+file);
	}
	
	private JFileChooser buildChooser()
	{
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filterGrf = new FileNameExtensionFilter(lang.getProperty("files-grf"), "grf");
		FileNameExtensionFilter filterXml = new FileNameExtensionFilter(lang.getProperty("files-xml"), "xml");
		FileNameExtensionFilter filterPng = new FileNameExtensionFilter(lang.getProperty("files-png"), "png");
	    chooser.addChoosableFileFilter(filterXml);
		chooser.addChoosableFileFilter(filterGrf);
	    chooser.addChoosableFileFilter(filterPng);
	    chooser.setFileFilter(filterXml);
		return chooser;
	}
	
	private String getFileName(JFileChooser chooser)
	{
		File file = chooser.getSelectedFile();
		String fileName = chooser.getSelectedFile().getAbsolutePath();
		FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter)chooser.getFileFilter();
		if (!selectedFilter.accept(file))
			fileName = fileName+"."+selectedFilter.getExtensions()[0];
		return fileName;
	}
	
	private File saveXml(String fileName, mxGraphComponent graphComponent,
			Graph<Vertex, ModgrafEdge> graphT)
	{
		String xml = buildXml(graphComponent.getGraph(), graphT);
		return writeTextFile(fileName, xml);
	}
	
	private String buildXml(mxGraph graph, Graph<Vertex, ModgrafEdge> graphT)
	{
		String type = null;
		String weighted = "0";
		String vertexCounter = Integer.toString(editor.getVertexCounter());
		if (graphT instanceof DirectedGraph)
			type = "directed";
		if (graphT instanceof UndirectedGraph)
			type = "undirected";
		if (graphT instanceof WeightedGraph)
			weighted = "1";
		if (graphT instanceof DoubleWeightedGraph)
			weighted = "2";
		mxCodec codec = new mxCodec();
		Element graphModel = (Element)codec.encode(graph.getModel());
		Element stylesheet = (Element)codec.encode(graph.getStylesheet());
		NodeList list = stylesheet.getElementsByTagName("add");
		for (int i = 0; i < list.getLength(); ++i)
		{
			Node addNode = list.item(i);
			if (addNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element addElement = (Element)addNode;
				if (addElement.hasAttribute("as"))
				{
					if (addElement.getAttribute("as").equals("defaultVertex"))
					{
						stylesheet.removeChild(addElement);
						i = list.getLength(); //exit from loop
					}
				}
			}
		}
		graphModel.setAttribute("type", type);
		graphModel.setAttribute("weighted", weighted);
		graphModel.setAttribute("vertexCounter", vertexCounter);
		String model = mxXmlUtils.getXml(graphModel);
		String style = mxXmlUtils.getXml(stylesheet);
		String xml = "<Modgraf>"+model+style+"</Modgraf>";
		xml = xml.replace("><", ">\r\n<");
		return xml;
	}
	
	private File writeTextFile(String fileName, String contents)
	{
		try 
		{
			mxUtils.writeFile(contents, fileName);
			return new File(fileName);
		} 
		catch (IOException e1) 
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("error-not-save-file"),
				    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public File saveGrf(String fileName, ActionGraphTypeConverter.NewType newType)
	{
        mxGraphComponent graphComponent = editor.getGraphComponent();
        Graph<Vertex, ModgrafEdge> graphT = editor.getGraphT();
        String newLine = "\r\n";
		StringBuilder grf = createGrfHeader(graphT, newLine, newType);
		createGrfEdges(graphT, grf, newLine, newType);
		createGrfGeometry(graphComponent, graphT, grf, newLine);
		return writeTextFile(fileName, grf.toString());
	}

	private StringBuilder createGrfHeader(Graph<Vertex, ModgrafEdge> graphT, String newLine,
                                          ActionGraphTypeConverter.NewType newType)
	{
		StringBuilder grf = new StringBuilder("Graf programu Modgraf 3.0");//TODO property
		grf.append(newLine);
		grf.append(newLine);
		String type = null;
		if (graphT instanceof DirectedGraph)
			type = "skierowany";
		if (graphT instanceof UndirectedGraph || undirected.equals(newType))
			type = "nieskierowany";
		grf.append(type);
		grf.append(newLine);
		grf.append(newLine);
		return grf;
	}

	private void createGrfEdges(Graph<Vertex, ModgrafEdge> graphT,
                                StringBuilder grf, String newLine, ActionGraphTypeConverter.NewType newType)
	{
		Set<ModgrafEdge> egdeSet = graphT.edgeSet();
		mxGraphModel model = (mxGraphModel)editor.getGraphComponent().getGraph().getModel();
		for (ModgrafEdge edge : egdeSet)
		{
			mxCell source = (mxCell) model.getCell(edge.getSource().getId());
			grf.append(source.getValue().toString());
			grf.append("\t");
			mxCell target = (mxCell) model.getCell(edge.getTarget().getId());
			grf.append(target.getValue().toString());
			grf.append("\t");
            if (newType == null || undirected.equals(newType)) {
                if (edge instanceof WeightedEdge)
                    grf.append(((WeightedEdge) edge).getWeight());
                if (edge instanceof DoubleWeightedEdge)
                {
                    grf.append(((DoubleWeightedEdge) edge).getCapacity());
                    grf.append("\t");
                    grf.append(((DoubleWeightedEdge) edge).getCost());
                }
            } else {
                if (weightedCapacity.equals(newType))
                    grf.append(((DoubleWeightedEdge) edge).getCapacity());
                if (weightedCost.equals(newType))
                    grf.append(((DoubleWeightedEdge) edge).getCost());
            }
			grf.append(newLine);
		}
		grf.append(newLine);
	}
	
	private void createGrfGeometry(mxGraphComponent graphComponent,
			Graph<Vertex, ModgrafEdge> graphT, StringBuilder grf, String newLine)
	{
		Set<Vertex> vertexSet = graphT.vertexSet();
		mxGraphModel model = (mxGraphModel)graphComponent.getGraph().getModel();
		Map<String, Object> cellsMap = model.getCells();
		for (Vertex vertex : vertexSet)
		{
			mxCell cell = (mxCell)cellsMap.get(vertex.getId());
			grf.append(cell.getValue().toString());
			grf.append("\t");
			mxGeometry geometry	= cell.getGeometry();
			if (geometry != null)
			{
				grf.append(geometry.getY());
				grf.append("\t");
				grf.append(geometry.getX());
			}
			grf.append(newLine);
		}
	}

	private File savePng(String fileName, mxGraphComponent graphComponent, Graph<Vertex, ModgrafEdge> graphT)
	{
		Color backgroundColor = null;
		int odp = JOptionPane.showConfirmDialog(graphComponent, lang.getProperty("question-transparent-background")); 
		if (odp == JOptionPane.NO_OPTION)
			backgroundColor = graphComponent.getBackground();
		BufferedImage image = mxCellRenderer.createBufferedImage(graphComponent.getGraph(),
				null, 1, backgroundColor, graphComponent.isAntiAlias(), null, graphComponent.getCanvas());
		if (image != null)
		{
			String xml = buildXml(graphComponent.getGraph(), graphT);
			try
			{
				xml = URLEncoder.encode(xml, "UTF-8");
				mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
				param.setCompressedText(new String[] { "mxGraphModel", xml });
				FileOutputStream outputStream = new FileOutputStream(new File(fileName));
				mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream, param);
				encoder.encode(image);
				outputStream.close();
				return new File(fileName);
			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("error-not-save-file"),
					    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		else 
		{
			JOptionPane.showMessageDialog(graphComponent,
					lang.getProperty("error-missing-data"), 
					lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
}

