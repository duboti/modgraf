package modgraf.view.properties;

import java.util.Properties;

import com.mxgraph.util.mxConstants;


/**
 * Klasa zawiera domyślne ustawienia programu.
 * 
 * @author Daniel Pogrebniak
 *
 */
public class DefaultProperties
{
	public static Properties createDefaultProperties()
	{
		Properties prop = new Properties();
		prop.setProperty("language", "polski");
		prop.setProperty("properties-file", "config.properties");
		prop.setProperty("program-version", "3.2 beta");
		prop.setProperty("program-name", "Modgraf v"+prop.getProperty("program-version"));
		prop.setProperty("use-class-loader", "false");
		prop.setProperty("graphComponent-width", "600");
		prop.setProperty("graphComponent-height", "300");
		prop.setProperty("default-graph-type", "undirected");
		prop.setProperty("default-edge-type", "0");
		prop.setProperty("textPane-font", "Arial");
		prop.setProperty("textPane-font-size", "14");
		prop.setProperty("textPane-width", "600");
		prop.setProperty("textPane-height", "200");
		prop.setProperty("show-new-graph-window-on-startup", "false");
		prop.setProperty("default-file-format", "xml");
		prop.setProperty("file-encoding", "UTF-8");
		prop.setProperty("background-color", "#F0F0F0");
		prop.setProperty("default-vertex-shape", mxConstants.SHAPE_ELLIPSE);
		prop.setProperty("default-vertex-height", "50");
		prop.setProperty("default-vertex-width", "50");
		prop.setProperty("default-vertex-fill-color", "#C3D9FF");
		prop.setProperty("default-vertex-border-color", "#6482B9");
		prop.setProperty("default-vertex-border-width", "1");
		prop.setProperty("default-vertex-font-family", "Helvetica");
		prop.setProperty("default-vertex-font-size", "14");
		prop.setProperty("default-vertex-font-color", "#774400");
		prop.setProperty("step-sleep-time-ms", "500");
		prop.setProperty("frame-algorithm-steps-width", "450");
		prop.setProperty("frame-algorithm-steps-height", "110");
		prop.setProperty("default-edge-width", "1");
		prop.setProperty("default-edge-color", "#6482B9");
		prop.setProperty("default-edge-weight", "1.0");
		prop.setProperty("default-edge-capacity", "1.0");
		prop.setProperty("default-edge-cost", "1.0");
		prop.setProperty("default-edge-font-size", "14");
		prop.setProperty("default-edge-font-color", "#446299");
		return prop;
	}
	
	public static Properties createDefaultLanguage()
	{
		return Language.createPolishLanguage();
	}
	
}
