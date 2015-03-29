package org.kitdroid.restapigenerator.gui;

import org.kitdroid.restapigenerator.RequestType;
import org.kitdroid.restapigenerator.Style;
import org.kitdroid.restapigenerator.common.TextUtils;
import org.kitdroid.restapigenerator.common.UiUtils;
import org.kitdroid.restapigenerator.generator.Generator;
import org.kitdroid.restapigenerator.generator.GeneratorFactory;
import org.kitdroid.restapigenerator.generator.GeneratorUtil;
import org.kitdroid.restapigenerator.generator.Parameter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by 远航 on 2015/3/11.
 */
public class RestApiGeneratorPanel extends JPanel{

    public static final int FONT_SIZE = 16;
    private final Font mFont;
    private final JTextArea mDataTypeArea;
    private final JTextArea mParametersArea;
    private JTextField mHostField;
    private JTextField mPathField;
    private JComboBox mStyleBox;
    private JComboBox mRequestBox;
    private JTextArea mCommentArea;

    public RestApiGeneratorPanel() {

        mFont = new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        JPanel infoPanel = initInfoPane();
        add(infoPanel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        layout.addLayoutComponent(infoPanel, constraints);

        mDataTypeArea = new JTextArea();
        JScrollPane dataTypePane = getjScrollPane("Data type", mFont, mDataTypeArea);
        add(dataTypePane);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 2;
        constraints.weighty = 1;
        layout.addLayoutComponent(dataTypePane, constraints);

        mParametersArea = new JTextArea();
        JScrollPane parametersPane = getjScrollPane("Parameters", mFont, mParametersArea);
        add(parametersPane);
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.weightx = 2;
        constraints.weighty = 1;
        layout.addLayoutComponent(parametersPane, constraints);

    }

    private JScrollPane getjScrollPane(String title, Font font, JTextArea textArea) {
        textArea.setRows(16);
        textArea.setColumns(20);
        textArea.setFont(font);
        JScrollPane parametersPane = new JScrollPane(textArea);
        parametersPane.setBorder(BorderFactory.createTitledBorder(title));
        parametersPane.setRowHeaderView(new LineNumberView(FONT_SIZE));
        return parametersPane;
    }

    private JPanel initInfoPane() {
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Api information"));
        GridBagLayout layout = new GridBagLayout();
        infoPanel.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel styleLabel = addLabel(infoPanel, "* Code Style：");
        JLabel requestLabel = addLabel(infoPanel, "* Request Type：");
        JLabel commentLabel = addLabel(infoPanel, "  Comment：");
        JLabel hostLabel = addLabel(infoPanel, "* Host：");
        JLabel pathLabel = addLabel(infoPanel, "  Path：");

        mStyleBox = new JComboBox(Style.names());
        mStyleBox.setFont(mFont);
        infoPanel.add(mStyleBox);

        mRequestBox = new JComboBox(RequestType.names());
        mRequestBox.setFont(mFont);
        infoPanel.add(mRequestBox);

        mCommentArea = new JTextArea(3,20);
        mCommentArea.setFont(mFont);
        infoPanel.add(mCommentArea);


        mHostField = addField(infoPanel, 20);
        mPathField = addField(infoPanel, 20);

        JButton cleanButton = addButton(infoPanel, "Clean");
        JButton createButton = addButton(infoPanel, "Create");

        JPanel gapPanel = new JPanel();
        infoPanel.add(gapPanel);

        JPanel vPanel = new JPanel();
        infoPanel.add(vPanel);

        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;

        constraints.gridy = 0;
        layout.setConstraints(styleLabel,constraints);
        constraints.gridy = 1;
        layout.setConstraints(requestLabel,constraints);

        constraints.gridy = 2;
        layout.setConstraints(hostLabel,constraints);
        constraints.gridy = 3;
        layout.setConstraints(pathLabel,constraints);
        constraints.gridy = 4;
        layout.setConstraints(commentLabel,constraints);


        // 第二列 输入框等
        constraints.gridx = 1;
        constraints.gridwidth = 3;
        constraints.weighty = 1;

        constraints.gridy = 0;
        layout.setConstraints(mStyleBox,constraints);

        constraints.gridy = 1;
        layout.setConstraints(mRequestBox,constraints);

        constraints.gridy = 2;
        layout.setConstraints(mHostField,constraints);
        constraints.gridy = 3;
        layout.setConstraints(mPathField,constraints);
        constraints.gridy = 4;
        constraints.weightx = 3;
        layout.setConstraints(mCommentArea,constraints);

        constraints.gridy = 5;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        layout.setConstraints(cleanButton,constraints);
        constraints.gridx = 2;
        layout.setConstraints(gapPanel,constraints);
        constraints.gridx = 3;
        layout.setConstraints(createButton,constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 5;
        constraints.weightx = 5;
        constraints.weighty = 5;
        layout.setConstraints(vPanel,constraints);

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClean();
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doGenerate();
            }
        });

        mHostField.setText("getHost()");
        mPathField.setText("user/login");
        return infoPanel;
    }

    private void doGenerate() {
        Style style = Style.values()[mStyleBox.getSelectedIndex()];
        Generator generator = GeneratorFactory.create(style);

        RequestType requestType = RequestType.values()[mRequestBox.getSelectedIndex()];
        String commentText = mCommentArea.getText();
        String hostText = mHostField.getText();
        String pathText = mPathField.getText();
        String dataTypeLines = mDataTypeArea.getText();
        String parameterLines = mParametersArea.getText();

        if(TextUtils.isEmpty(hostText)){
            UiUtils.showAlert("Host can't be null !");
            return;
        }


        String codeString = generator.generate(requestType,commentText,hostText,pathText, dataTypeLines, parameterLines);
        // TODO 处理生成的代码
        System.out.println(codeString);
    }

    private void doClean() {
        mCommentArea.setText("");
        mHostField.setText("");
        mPathField.setText("");
        mDataTypeArea.setText("");
        mParametersArea.setText("");
    }

    private JButton addButton(JPanel infoPanel, String text) {
        JButton cleanButton = new JButton(text);
        infoPanel.add(cleanButton);
        return cleanButton;
    }

    private JTextField addField(JPanel parent, int columns) {
        JTextField hostField = new JTextField(columns);
        hostField.setFont(mFont);
        parent.add(hostField);
        return hostField;
    }

    private JLabel addLabel(JPanel parent, String text) {
        JLabel commentLabel = new JLabel(text);
        commentLabel.setFont(mFont);
        parent.add(commentLabel);
        return commentLabel;
    }
}
