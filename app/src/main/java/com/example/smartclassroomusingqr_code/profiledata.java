package com.example.smartclassroomusingqr_code;

public class profiledata {
    String ID;
    private String NAME;
    private String MOBILE;
    private String EMAIL;
    private String ADDRESS;
    private String DOB;
    private String SEMESTER;

    public profiledata() {
    }

    public profiledata(String ID, String NAME, String MOBILE, String EMAIL, String ADDRESS, String DOB, String SEMESTER) {
        this.ID = ID;
        this.NAME = NAME;
        this.MOBILE = MOBILE;
        this.EMAIL = EMAIL;
        this.ADDRESS = ADDRESS;
        this.DOB = DOB;
        this.SEMESTER = SEMESTER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getSEMESTER() {
        return SEMESTER;
    }

    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER = SEMESTER;
    }
}