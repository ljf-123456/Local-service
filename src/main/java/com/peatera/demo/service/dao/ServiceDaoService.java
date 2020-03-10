package com.peatera.demo.service.dao;


import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;



public interface ServiceDaoService {
    //新增服务
	public boolean insertService(Service vo);
    //查询服务
	public List<Service> findServiceOne(String name);
	//删除服务
	public boolean deleteService(String name);
	//查看可绑定服务器
	public JSONObject seeServerVo(String name,int current,int pageSize);
	//绑定服务器
	public boolean bangdingServer(List<ServerVo>list,String name);
	//查看已绑定服务器
	public JSONObject seeBdServerVo(String name);
	//服务器解绑
	public boolean jiebang(String sname , List<ServerVo>list);
}
