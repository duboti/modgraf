package modgraf.action;

import com.mxgraph.util.mxUtils;
import modgraf.view.Editor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Klasa odpowiada za zapisanie rozwiązania tekstowego do pliku.
 *
 * @author Daniel Pogrebniak
 *
 * @see ActionListener
 */
public class ActionSaveTextResult implements ActionListener {
    private Editor editor;
    private Properties lang;

    public ActionSaveTextResult(Editor editor) {
        this.editor = editor;
        lang = editor.getLanguage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = buildChooser();
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = chooser.getSelectedFile();
            if (!selectedFile.exists() || JOptionPane.showConfirmDialog(editor.getGraphComponent(),
                    lang.getProperty("question-file-exists")) == JOptionPane.YES_OPTION)
            {
                String fileName = getFileName(chooser);
                writeTextFile(fileName, editor.getText().replace("\n", System.lineSeparator()));
            }
        }
    }

    private JFileChooser buildChooser()
    {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setAcceptAllFileFilterUsed(true);
        FileNameExtensionFilter filterTxt = new FileNameExtensionFilter(lang.getProperty("files-txt"), "txt");
        chooser.addChoosableFileFilter(filterTxt);
        chooser.setFileFilter(filterTxt);
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
}
