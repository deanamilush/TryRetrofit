package com.dean.tryretrofit.api.nama;

import com.google.gson.annotations.SerializedName;

public class NameItem {

	@SerializedName("deskripsi_bagian")
	private String deskripsiBagian;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("lantai")
	private String lantai;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("id_bagian")
	private int idBagian;

	@SerializedName("nrp")
	private String nrp;

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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setIdBagian(int idBagian){
		this.idBagian = idBagian;
	}

	public int getIdBagian(){
		return idBagian;
	}

	public void setNrp(String nrp){
		this.nrp = nrp;
	}

	public String getNrp(){
		return nrp;
	}
}