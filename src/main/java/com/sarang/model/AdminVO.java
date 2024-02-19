package com.sarang.model;



import java.util.HashMap;

import org.json.JSONObject;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminVO {
    private Integer seq;
    @NonNull
    private String eno;
    @NonNull
    private String enoPw;
    @NonNull
    private String name;
    private String celph;
    private String email;
    private String cretDate;
    private String cretTime;
    private String chgDate;
    private String chgTime;

    public HashMap<String, Object> toHashMap(){
        HashMap<String,Object> retMap = new HashMap<String,Object>();
        retMap.put("seq",this.getSeq());
        retMap.put("eno",this.getEno());
        retMap.put("enoPw",this.getEnoPw());
        retMap.put("name",this.getName());
        retMap.put("celph",this.getCelph());
        retMap.put("email",this.getEmail());
        retMap.put("cretDate",this.getCretDate());
        retMap.put("cretTime",this.getCretTime());
        retMap.put("chgDate",this.getChgDate());
        retMap.put("chgTime",this.getChgTime());
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
        this.setCretDate(String.valueOf(reqMap.get("cretDate")));
        this.setCretTime(String.valueOf(reqMap.get("cretTime")));
        this.setChgDate(String.valueOf(reqMap.get("chgDate")));
        this.setChgTime(String.valueOf(reqMap.get("chgTime")));
    }

    public JSONObject toJsonObj() throws Exception{
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("seq", this.getSeq());
        jsonObj.put("eno",this.getEno());
        jsonObj.put("enoPw",this.getEnoPw());
        jsonObj.put("name",this.getName());
        jsonObj.put("celph",this.getCelph());
        jsonObj.put("email",this.getEmail());
        jsonObj.put("cretDate",this.getCretDate());
        jsonObj.put("cretTime",this.getCretTime());
        jsonObj.put("chgDate",this.getChgDate());
        jsonObj.put("chgTime",this.getChgTime());
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
        this.setCretDate(String.valueOf(jsonObj.get("cretDate")));
        this.setCretTime(String.valueOf(jsonObj.get("cretTime")));
        this.setChgDate(String.valueOf(jsonObj.get("chgDate")));
        this.setChgTime(String.valueOf(jsonObj.get("chgTime")));
    }
}
