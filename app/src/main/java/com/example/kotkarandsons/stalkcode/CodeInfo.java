package com.example.kotkarandsons.stalkcode;

public class CodeInfo {
    public String codeName;
    public  String codeType;
    public  String codeLevel;
    public  String codeStatus;
    public String date;

    public CodeInfo()
    {}

    public CodeInfo(String codeName, String codeType, String codeLevel, String codeStatus , String date) {
        this.codeName = codeName;
        this.codeType = codeType;
        this.codeLevel = codeLevel;
        this.codeStatus = codeStatus;
        this.date=date;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeLevel() {
        return codeLevel;
    }

    public void setCodeLevel(String codeLevel) {
        this.codeLevel = codeLevel;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }
}
