
package com.zaigo.mytest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetBoxAttributeModel {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("response")
    @Expose
    private List<GetBoxAttributeResponse> response = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<GetBoxAttributeResponse> getResponse() {
        return response;
    }

    public void setResponse(List<GetBoxAttributeResponse> response) {
        this.response = response;
    }

}
