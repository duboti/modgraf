package modgraf.action;

import modgraf.view.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Klasa odpowiada za konwersję grafu do innego typu.
 *
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionGraphTypeConverter implements ActionListener {

    public enum NewType {
        undirected,
        unweighted,
        weightedCost,
        weightedCapacity
    }

    private Editor editor;
    private NewType newType;
    private Properties lang;

    public ActionGraphTypeConverter(Editor e, NewType newType)
    {
        editor = e;
        this.newType = newType;
        lang = e.getLanguage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fileName = "graph.xml";
        new ActionSaveAs(editor).saveGrf(fileName, newType);
        try {
            File file = new File(fileName);
            new ActionOpenGrf(editor).openGrf(file);
            editor.enableAllMenuElements();
            file.delete();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(editor.getGraphComponent(),
                    lang.getProperty("error-not-open-file"),
                    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(editor.getGraphComponent(),
                    lang.getProperty("error-names-with-spaces"),
                    lang.getProperty("error"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
