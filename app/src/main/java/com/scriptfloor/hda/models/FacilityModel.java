package com.scriptfloor.hda.models;

/**
 * Created by Lincoln on 8/15/2018.
 */

public class FacilityModel {
    public String FacilityID;
    public String FacilityName;
    public String FacilityAddress;
    public String FacilitySector;
    public String FacilityCategory;

    public String getFacilityID() {
        return FacilityID;
    }

    public void setFacilityID(String facilityID) {
        FacilityID = facilityID;
    }

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String facilityName) {
        FacilityName = facilityName;
    }

    public String getFacilityAddress() {
        return FacilityAddress;
    }

    public void setFacilityAddress(String facilityAddress) {
        FacilityAddress = facilityAddress;
    }

    public String getFacilitySector() {
        return FacilitySector;
    }

    public void setFacilitySector(String facilitySector) {
        FacilitySector = facilitySector;
    }

    public String getFacilityCategory() {
        return FacilityCategory;
    }

    public void setFacilityCategory(String facilityCategory) {
        FacilityCategory = facilityCategory;
    }
}
