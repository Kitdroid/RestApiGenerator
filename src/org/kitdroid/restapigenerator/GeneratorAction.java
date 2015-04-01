package org.kitdroid.restapigenerator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;

/**
 * Created by Ô¶º½ on 2015/4/1.
 */
public class GeneratorAction extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        showDialog();
    }

    private void showDialog() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        org.kitdroid.restapigenerator.gui.RestApiGeneratorPanel pane = new org.kitdroid.restapigenerator.gui.RestApiGeneratorPanel();
        frame.setContentPane(pane);
        frame.pack();
        frame.setVisible(true);
        frame.setMinimumSize(frame.getSize());
    }
}
