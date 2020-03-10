package com.peatera.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.peatera.demo.dao.ServiceDao;
import com.peatera.demo.dao.impl.ServiceDaoImpl;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;
import com.peatera.demo.service.dao.Dao1;


@Component
public class Dao1Impl implements Dao1 {
	@Autowired
    ServiceDao dao;
    

	
    
    //查看服务信息
	@Override
	public List<Service> getServMsg(int currPage,int pageSize ) {
		List<Service>list=dao.getService(currPage,pageSize);
	
		for(int i=0;i<list.size();i++){
			Service vo=list.get(i);
			String s_name=vo.getS_name();
			int n = dao.getServerNum(s_name);
			vo.setS_num(n);
			//list2.add(vo);
		}
		return list;
	}
	
	//查看服务器配置信息
	@Override
	public List<ServerVo> getServerMsg(int currPage, int pageSize) {
		List<ServerVo>list=dao.getServerMsg(currPage, pageSize);
		return list;
	}

	
	//删除服务器
	@Override
	public boolean deleteServer(ServerVo vo) {
		boolean flag=false;
		if(!dao.checkBdService(vo)){
		   flag=dao.deleteServer(vo);
		}
		return flag;
	}

	//新增服务器
	@Override
	public boolean insertServer(ServerVo vo) {
		boolean flag=false;
		if(!dao.checkServerExist(vo)){
			flag=dao.insertServer(vo);
		}
		return flag;
	}

	//服务器检索
	@Override
	public List<ServerVo> seracho(String s_ip) {
		List<ServerVo>list=dao.seracho(s_ip);
		return list;
	}


}
