package com.dean.tryretrofit.api.login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("password")
	private String password;

	@SerializedName("status_code")
	private int statusCode;

	@SerializedName("id_jenis_user")
	private String idJenisUser;

	@SerializedName("name")
	private String name;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("nrp")
	private String nrp;

	@SerializedName("email")
	private String email;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public void setIdJenisUser(String idJenisUser){
		this.idJenisUser = idJenisUser;
	}

	public String getIdJenisUser(){
		return idJenisUser;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIdUser(int idUser){
		this.idUser = idUser;
	}

	public int getIdUser(){
		return idUser;
	}

	public void setNrp(String nrp){
		this.nrp = nrp;
	}

	public String getNrp(){
		return nrp;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}