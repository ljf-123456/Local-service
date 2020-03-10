package com.peatera.demo.dao;

import java.util.List;

import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;



public interface SerciceDao {
//检查服务是否存在
	public boolean checkSercice(Service vo);
	//新增服务
	public boolean inSertSercice(Service vo);
	//服务检索
	public List<Service> findService(String name);
	//删除服务
	public boolean deleteService(String name);
	//查看可绑定服务器
	public List<ServerVo> findServers(String name,int current,int pageSize);
	//查看可绑定服务器数量
	public int findServersNum(String name);
	//绑定服务器
	public boolean bangding(List<ServerVo>list,String name);
	//服务器解绑
	public boolean jiebang(String name,List<ServerVo>list);
	//
	public boolean jiebang(String name);
}
