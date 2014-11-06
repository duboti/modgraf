package modgraf.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import modgraf.algorithm.ModgrafAbstractAlgorithm;
import modgraf.view.Editor;

/**
 * Klasa odpowiada za import nowego algorytmu z pliku jar.
 * 
 * @author Daniel Pogrebniak
 * 
 * @see ActionListener
 */
public class ActionAddNewAlghoritm  implements ActionListener 
{
	private Editor editor;
	private Properties lang;
	
	public ActionAddNewAlghoritm(Editor e)
	{
		editor = e;
		lang = editor.getLanguage();
	}
	
	/**
	 * Metoda odpowiada za import nowego algorytmu z pliku jar.<br>
	 * Metoda jest wywoływana z menu <i>Algorytmy --> Dodaj nowy algorytm</i>.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		JFileChooser chooser = buildChooser();
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
	    	ArrayList<String> foundClasses = findClassesInJarFile(chooser.getSelectedFile());
	    	if (foundClasses != null)
	    	{
	    		String selectedClassName = (String)JOptionPane.showInputDialog(editor.getGraphComponent(), 
	    				lang.getProperty("message-found-classes"), lang.getProperty("frame-found-classes"), 
	    				JOptionPane.PLAIN_MESSAGE, null, foundClasses.toArray(), null);
	    		if (selectedClassName != null)
	    			addAlgorithm(selectedClassName, chooser.getSelectedFile());
	    	}
	    }
	}
	
	/**
	 * @return okno wyboru pliku jar
	 */
	private JFileChooser buildChooser() 
	{
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		FileNameExtensionFilter filterJar = 
				new FileNameExtensionFilter(lang.getProperty("files-jar"), "jar");
	    chooser.addChoosableFileFilter(filterJar);
	    chooser.setFileFilter(filterJar);
		return chooser;
	}
	
	/**
	 * Metoda tworzy listę klas znajdujących się w pliku jar.
	 * @param file - plik jar
	 * @return lista klas
	 */
	private ArrayList<String> findClassesInJarFile(File file)
	{
		JarFile jarFile = null;
		ArrayList<String> foundClasses = null;
		try
		{
			jarFile = new JarFile(file);
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("error-not-open-jar-file"),
				    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
		}
		if (jarFile != null)
		{
			Enumeration<JarEntry> allEntries = jarFile.entries();
			foundClasses = new ArrayList<>();
			while (allEntries.hasMoreElements()) {
			    JarEntry entry = (JarEntry) allEntries.nextElement();
			    String name = entry.getName();
			    name = name.replace("/", ".");
			    if (name.endsWith(".class") && !name.contains("$"))
			    	foundClasses.add(name.substring(0, name.indexOf(".class")));
			}
			try
			{
				jarFile.close();
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(editor.getGraphComponent(),
						lang.getProperty("error-not-close-file"),
					    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return foundClasses;
	}
	
	/**
	 * Metoda dodaje algorytm do paska menu.
	 * @param selectedClassName - nazwa wybranej klasy
	 * @param selectedFile - plik jar
	 */
	private void addAlgorithm(String selectedClassName, File selectedFile)
	{
		Class<?> algorithm = loadClass(selectedClassName, selectedFile);
		if (algorithm != null)
		{
			ModgrafAbstractAlgorithm modgrafAlgorithm = checkIsCorrectModgrafAlgorithm(algorithm);
			if (modgrafAlgorithm != null)
			{
				editor.addAlgorithmToMemuBar(modgrafAlgorithm.getName(), modgrafAlgorithm);
				editor.setText(lang.getProperty("message-successfully-add-algorithm")+modgrafAlgorithm.getName());
			}
		}
	}

	/**
	 * Metoda wczytuje wybraną klasę z pliku do pamięci programu.
	 * @param selectedClassName - nazwa wybranej klasy
	 * @param selectedFile - plik jar
	 * @return nowy algorytm
	 */
	private Class<?> loadClass(String selectedClassName, File selectedFile)
	{
		Class<?> algorithm = null;
		try
		{
			URL fileUrl = selectedFile.toURI().toURL();
			URLClassLoader algorithmLoader = new URLClassLoader(new URL[] { fileUrl });
			algorithm = algorithmLoader.loadClass(selectedClassName);
			algorithmLoader.close();
		} catch (ClassNotFoundException | IOException e)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("error-not-read-class")+selectedClassName,
				    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
		}
		return algorithm;
	}
	
	/**
	 * Metoda sprawdza czy podana klasa dziedziczy po klasie ModgrafAbstractAlgorithm oraz 
	 * czy posiada publiczny konstruktor, który przyjmuje obiekt Editor jako parametr
	 * @param algorithm - testowana klasa
	 * @return W przypadku powodzenia obiekt klasy ModgrafAbstractAlgorithm, w przypadku błędu null.
	 */
	private ModgrafAbstractAlgorithm checkIsCorrectModgrafAlgorithm(Class<?> algorithm) 
	{
		ModgrafAbstractAlgorithm modgrafAlgorithm = null;
		try
		{
			modgrafAlgorithm = (ModgrafAbstractAlgorithm) algorithm.getConstructor(Editor.class).newInstance(editor);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(editor.getGraphComponent(),
					lang.getProperty("error-not-Modgraf-algorithm"),
				    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
		}
		return modgrafAlgorithm;
	}
}

