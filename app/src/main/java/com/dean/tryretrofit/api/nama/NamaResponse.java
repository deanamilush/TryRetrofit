package com.dean.tryretrofit.api.nama;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NamaResponse{

	@SerializedName("data")
	private List<NameItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setData(List<NameItem> data){
		this.data = data;
	}

	public List<NameItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}