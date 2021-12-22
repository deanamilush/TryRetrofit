package com.dean.tryretrofit.api;

import com.dean.tryretrofit.api.nama.NameItem;
import com.google.gson.annotations.SerializedName;

public class DataItem {

	@SerializedName("deskripsi_bagian")
	private String deskripsiBagian;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("lantai")
	private String lantai;

	@SerializedName("kode")
	private String kode;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public void setDeskripsiBagian(String deskripsiBagian){
		this.deskripsiBagian = deskripsiBagian;
	}

	public String getDeskripsiBagian(){
		return deskripsiBagian;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setLantai(String lantai){
		this.lantai = lantai;
	}

	public String getLantai(){
		return lantai;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}