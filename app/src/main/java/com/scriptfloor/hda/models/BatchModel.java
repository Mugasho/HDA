package com.scriptfloor.hda.models;

/**
 * Created by Lincoln on 8/28/2018.
 */

public class BatchModel {
private String BatchId,BatchNo,BatchMade,BatchExpiry;

    public String getBatchId() {
        return BatchId;
    }

    public void setBatchId(String batchId) {
        BatchId = batchId;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getBatchMade() {
        return BatchMade;
    }

    public void setBatchMade(String batchMade) {
        BatchMade = batchMade;
    }

    public String getBatchExpiry() {
        return BatchExpiry;
    }

    public void setBatchExpiry(String batchExpiry) {
        BatchExpiry = batchExpiry;
    }
}
