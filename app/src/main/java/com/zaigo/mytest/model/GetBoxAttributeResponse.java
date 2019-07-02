
package com.zaigo.mytest.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetBoxAttributeResponse {

    @SerializedName("jobid")
    @Expose
    private String jobid;
    @SerializedName("boxid")
    @Expose
    private String boxid;
    @SerializedName("attributetypeid")
    @Expose
    private String attributetypeid;
    @SerializedName("attributetype")
    @Expose
    private String attributetype;
    @SerializedName("attributename")
    @Expose
    private String attributename;
    @SerializedName("isrequired")
    @Expose
    private String isrequired;
    @SerializedName("optionvalue")
    @Expose
    private Object optionvalues = null;

    /*@SerializedName("optionvalue")
    @Expose
    private String stroptionvalue;*/

    @SerializedName("fieldid")
    @Expose
    private String fieldid;

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getBoxid() {
        return boxid;
    }

    public void setBoxid(String boxid) {
        this.boxid = boxid;
    }

    public String getAttributetypeid() {
        return attributetypeid;
    }

    public void setAttributetypeid(String attributetypeid) {
        this.attributetypeid = attributetypeid;
    }

    public String getAttributetype() {
        return attributetype;
    }

    public void setAttributetype(String attributetype) {
        this.attributetype = attributetype;
    }

    public String getAttributename() {
        return attributename;
    }

    public void setAttributename(String attributename) {
        this.attributename = attributename;
    }

    public String getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(String isrequired) {
        this.isrequired = isrequired;
    }

    /*public List<String> getOptionvalue() {
        return optionvalue;
    }

    public void setOptionvalue(List<String> optionvalue) {
        this.optionvalue = optionvalue;
    }*/


    public String getFieldid() {
        return fieldid;
    }

    public void setFieldid(String fieldid) {
        this.fieldid = fieldid;
    }

    public Object getOptionvalue() {
        return optionvalues;
    }

    public void setOptionvalue(Object optionvalue) {
        this.optionvalues = optionvalue;
    }

   /* public String getStroptionvalue() {
        return stroptionvalue;
    }

    public void setStroptionvalue(String stroptionvalue) {
        this.stroptionvalue = stroptionvalue;
    }*/



    public static class AccountStateDeserializer implements JsonDeserializer<GetBoxAttributeResponse> {

        @Override
        public GetBoxAttributeResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            GetBoxAttributeResponse accountState = new Gson().fromJson(json, GetBoxAttributeResponse.class);
            JsonObject jsonObject = json.getAsJsonObject();
            Log.i("nnnnx","nnnnx"+new Gson().toJson(json));
            if (jsonObject.has("optionvalue")) {
               // JsonElement  elem = jsonObject.get("optionvalue");
                JsonArray elem = jsonObject.getAsJsonArray("optionvalue");
                if (elem != null && !elem.isJsonNull()) {
                    String valuesString = elem.getAsString();
                    if (!TextUtils.isEmpty(valuesString)){
                        if(elem.isJsonArray()) {
                            List<String> values = new Gson().fromJson(valuesString, new TypeToken<ArrayList<String>>() {}.getType());
                            accountState.setOptionvalue(values);
                        }
                    }
                }
            }
            return accountState ;
        }
    }





}
