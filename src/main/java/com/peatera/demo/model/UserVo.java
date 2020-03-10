package com.peatera.demo.model;

import lombok.Data;

@Data
public class UserVo {
	private String name;
	private String psw;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	

	

}
