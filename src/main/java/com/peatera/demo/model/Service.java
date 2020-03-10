package com.peatera.demo.model;

public class Service {
	private String s_name;
	private int s_num;
	private String s_type;
	private String s_shuoming;
   private String s_date;
   
	public String getS_date() {
	return s_date;
}
public void setS_date(String s_date) {
	this.s_date = s_date;
}
	public Service(){
		
	}
	public Service(String s_name, String s_type, String s_shuoming) {
		super();
		this.s_name = s_name;
		this.s_type = s_type;
		this.s_shuoming = s_shuoming;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public int getS_num() {
		return s_num;
	}

	public void setS_num(int s_num) {
		this.s_num = s_num;
	}

	public String getS_type() {
		return s_type;
	}

	public void setS_type(String s_type) {
		this.s_type = s_type;
	}

	public String getS_shuoming() {
		return s_shuoming;
	}

	public void setS_shuoming(String s_shuoming) {
		this.s_shuoming = s_shuoming;
	}
	@Override
	public String toString() {
		return "Service [s_name=" + s_name + ", s_num=" + s_num + ", s_type="
				+ s_type + ", s_shuoming=" + s_shuoming + ", s_date=" + s_date
				+ "]";
	}

}
