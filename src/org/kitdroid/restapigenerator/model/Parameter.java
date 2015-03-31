package org.kitdroid.restapigenerator.model;

/**
 * Created by 远航 on 2015/3/11.
 */
public class Parameter {
    private String name;
    private String typeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue(){
        StringBuilder builder = new StringBuilder(typeName);
        builder.append(" ");
        builder.append(name);
        return builder.toString();
    }
}
