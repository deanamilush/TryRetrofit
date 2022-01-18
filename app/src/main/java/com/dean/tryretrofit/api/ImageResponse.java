package com.dean.tryretrofit.api;

import com.google.gson.annotations.SerializedName;

public class ImageResponse{

	@SerializedName("data")
	private String data;

	@SerializedName("status")
	private String status;

	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}