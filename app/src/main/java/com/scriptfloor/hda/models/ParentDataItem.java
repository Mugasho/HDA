package com.scriptfloor.hda.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LINCOLN on 10/23/2018.
 */

public class ParentDataItem implements Serializable {
    private String parentName;
    private ArrayList<ChildDataItem> childDataItems;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<ChildDataItem> getChildDataItems() {
        return childDataItems;
    }

    public void setChildDataItems(ArrayList<ChildDataItem> childDataItems) {
        this.childDataItems = childDataItems;
    }
}
