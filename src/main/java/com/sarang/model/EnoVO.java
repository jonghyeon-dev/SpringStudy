package com.sarang.model;



import java.util.HashMap;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnoVO {
    private Integer seq;
    private String eno;
    private String enoPw;
    private String name;
    private String celph;
    private String email;
    private String cretDt;
    private String cretTm;
    private String chgDt;
    private String chgTm;

    public HashMap<String, Object> toHashMap(){
        HashMap<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("seq",this.getSeq());
        retMap.put("eno",this.getEno());
        retMap.put("enoPw",this.getEnoPw());
        retMap.put("name",this.getName());
        retMap.put("celph",this.getCelph());
        retMap.put("email",this.getEmail());
        retMap.put("cretDt",this.getCretDt());
        retMap.put("cretTm",this.getCretTm());
        retMap.put("chgDt",this.getChgDt());
        retMap.put("chgTm",this.getChgTm());
        return retMap;
    }

    public void fromHashMap(HashMap<String,Object> reqMap){
        if(reqMap.containsKey("seq")){
            this.setSeq(Integer.parseInt(String.valueOf(reqMap.get("seq"))));
        }
        this.setEno(String.valueOf(reqMap.get("eno")));
        this.setEnoPw(String.valueOf(reqMap.get("enoPw")));
        this.setName(String.valueOf(reqMap.get("name")));
        this.setCelph(String.valueOf(reqMap.get("celph")));
        this.setEmail(String.valueOf(reqMap.get("email")));
        this.setCretDt(String.valueOf(reqMap.get("cretDt")));
        this.setCretTm(String.valueOf(reqMap.get("cretTm")));
        this.setChgDt(String.valueOf(reqMap.get("chgDt")));
        this.setChgTm(String.valueOf(reqMap.get("chgTm")));
    }

    public JSONObject toJsonObj() throws Exception{
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("seq", this.getSeq());
        jsonObj.put("eno",this.getEno());
        jsonObj.put("enoPw",this.getEnoPw());
        jsonObj.put("name",this.getName());
        jsonObj.put("celph",this.getCelph());
        jsonObj.put("email",this.getEmail());
        jsonObj.put("cretDt",this.getCretDt());
        jsonObj.put("cretTm",this.getCretTm());
        jsonObj.put("chgDt",this.getChgDt());
        jsonObj.put("chgTm",this.getChgTm());
        return jsonObj;
    }

    public void fromJsonObj(JSONObject jsonObj) throws Exception{
        if(jsonObj.has("seq")){
            this.setSeq(Integer.parseInt(String.valueOf(jsonObj.get("seq"))));
        }
        this.setEno(String.valueOf(jsonObj.get("eno")));
        this.setEnoPw(String.valueOf(jsonObj.get("enoPw")));
        this.setName(String.valueOf(jsonObj.get("name")));
        this.setCelph(String.valueOf(jsonObj.get("celph")));
        this.setEmail(String.valueOf(jsonObj.get("email")));
        this.setCretDt(String.valueOf(jsonObj.get("cretDt")));
        this.setCretTm(String.valueOf(jsonObj.get("cretTm")));
        this.setChgDt(String.valueOf(jsonObj.get("chgDt")));
        this.setChgTm(String.valueOf(jsonObj.get("chgTm")));
    }
}
