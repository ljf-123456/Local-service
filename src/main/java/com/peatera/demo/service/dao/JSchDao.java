package com.peatera.demo.service.dao;

public interface JSchDao {
  //查看服务文件是否存在  ----存在--改名备份
	public boolean checkFile(String dir, String s_name,String user,String passwd,String host,int port);
}
