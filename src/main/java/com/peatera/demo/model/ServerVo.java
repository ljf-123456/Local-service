package com.peatera.demo.model;

public class ServerVo {
	private String s_ip;
	private int s_port;
	private String s_user;
	private String s_psw;
	private String s_dir;
	private String s_beizhu;
	private String s_date;

	
	public String getS_date() {
		return s_date;
	}

	public void setS_date(String s_date) {
		this.s_date = s_date;
	}

	public String getS_ip() {
		return s_ip;
	}

	public void setS_ip(String s_ip) {
		this.s_ip = s_ip;
	}

	public int getS_port() {
		return s_port;
	}

	public void setS_port(int s_port) {
		this.s_port = s_port;
	}

	public String getS_user() {
		return s_user;
	}

	public void setS_user(String s_user) {
		this.s_user = s_user;
	}

	public String getS_psw() {
		return s_psw;
	}

	public void setS_psw(String s_psw) {
		this.s_psw = s_psw;
	}

	public String getS_dir() {
		return s_dir;
	}

	public void setS_dir(String s_dir) {
		this.s_dir = s_dir;
	}

	public String getS_beizhu() {
		return s_beizhu;
	}

	public void setS_beizhu(String s_beizhu) {
		this.s_beizhu = s_beizhu;
	}

	@Override
	public String toString() {
		return "ServerVo [s_ip=" + s_ip + ", s_port=" + s_port + ", s_user="
				+ s_user + ", s_psw=" + s_psw + ", s_dir=" + s_dir
				+ ", s_beizhu=" + s_beizhu + "]";
	}


}
