package org.kitdroid.restapigenerator;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon.Position;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import org.kitdroid.plugin.util.NotifyUtils;
import org.kitdroid.util.Log;
import org.kitdroid.util.StringUtils;

import javax.swing.*;

/**
 * Created by Ô¶º½ on 2015/4/1.
 */
public class Settings implements Configurable {

    public static final String HOST_VALUE_KEY = "RestApiGengrator_HostValue";
    public static final String PATH_VALUE_KEY = "RestApiGengrator_PathValue";
    public static final String DATA_TYPE_VALUE_KEY = "RestApiGengrator_DataType";
    public static final String CODE_STYLE_VALUE_KEY = "RestApiGengrator_CodeStyle";
    public static final String REQUEST_TYPE_VALUE_KEY = "RestApiGengrator_RequestType";

    public static final String HOST_VALUE_DEFAULT = "getHost()";
    public static final String PATH_VALUE_DEFAULT = "";
    public static final String DATA_TYPE_VALUE_DEFAULT = "String";
    public static final int CODE_STYLE_VALUE_DEFAULT = 0;
    public static final int REQUEST_TYPE_VALUE_DEFAULT = 0;

    private boolean isInit = true;
    private JPanel mPanel;
    private JTextField mHostValue;
    private JTextField mPathValue;
    private JTextField mDataType;
    private JComboBox mCodeStyleBox;
    private JComboBox mRequestTypeBox;

    @Nls
    @Override
    public String getDisplayName() {
        return "RestApiGenerator";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "Email: huiyhs@gmail.com";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        for(String name : Style.names()){
            mCodeStyleBox.addItem(name);
        }
        for(String name : RequestType.names()){
            mRequestTypeBox.addItem(name);
        }
        return mPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent instance = PropertiesComponent.getInstance();

        String hostValue = mHostValue.getText().trim();
        if(StringUtils.isEmpty(hostValue)){
            NotifyUtils.showErrorNotify("Host can't be empty!", mHostValue);
            return;
        }

        String dataType = mDataType.getText().trim();
        if(StringUtils.isEmpty(dataType)){
            NotifyUtils.showErrorNotify("DataType can't be empty!", mDataType);
            return;
        }

        instance.setValue(HOST_VALUE_KEY, hostValue);
        instance.setValue(PATH_VALUE_KEY, mPathValue.getText());
        instance.setValue(DATA_TYPE_VALUE_KEY, dataType);
        instance.setValue(CODE_STYLE_VALUE_KEY, String.valueOf(mCodeStyleBox.getSelectedIndex()));
        instance.setValue(REQUEST_TYPE_VALUE_KEY, String.valueOf(mRequestTypeBox.getSelectedIndex()));
    }

    @Override
    public void reset() {
        if(isInit){
            isInit = false;
            setConfigValue();
        }else {
            setDefaultValue();
        }
    }

    private void setDefaultValue() {
        mHostValue.setText(HOST_VALUE_DEFAULT);
        mPathValue.setText(PATH_VALUE_DEFAULT);
        mDataType.setText(DATA_TYPE_VALUE_DEFAULT);
        mCodeStyleBox.setSelectedIndex(CODE_STYLE_VALUE_DEFAULT);
        mRequestTypeBox.setSelectedIndex(REQUEST_TYPE_VALUE_DEFAULT);
    }

    private void setConfigValue() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String hostValue = instance.getValue(HOST_VALUE_KEY, HOST_VALUE_DEFAULT);
        String pathValue = instance.getValue(PATH_VALUE_KEY, PATH_VALUE_DEFAULT);
        String dataTypeValue = instance.getValue(DATA_TYPE_VALUE_KEY, DATA_TYPE_VALUE_DEFAULT);
        int codeStyleIndex = instance.getOrInitInt(CODE_STYLE_VALUE_KEY, CODE_STYLE_VALUE_DEFAULT);
        int requestTypeIndex = instance.getOrInitInt(REQUEST_TYPE_VALUE_KEY, REQUEST_TYPE_VALUE_DEFAULT);

        mHostValue.setText(hostValue);
        mPathValue.setText(pathValue);
        mDataType.setText(dataTypeValue);
        mCodeStyleBox.setSelectedIndex(codeStyleIndex);
        mRequestTypeBox.setSelectedIndex(requestTypeIndex);
    }

    @Override
    public void disposeUIResources() {

    }
}
