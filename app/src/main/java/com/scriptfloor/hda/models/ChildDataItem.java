package com.scriptfloor.hda.models;

import java.io.Serializable;

/**
 * Created by LINCOLN on 10/23/2018.
 */

public class ChildDataItem implements Serializable {
    private String childName;
    private String childDetail;

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildDetail() {
        return childDetail;
    }

    public void setChildDetail(String childDetail) {
        this.childDetail = childDetail;
    }
}
