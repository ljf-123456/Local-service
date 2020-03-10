package com.peatera.demo.service.dao;

import java.util.List;

import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;


public interface Dao1 {
//分页查看服务信息
public List<Service> getServMsg(int currPage,int pageSize ); 

//查看服务器信息
public List<ServerVo> getServerMsg(int currPage, int pageSize);

//删除服务器
public boolean deleteServer(ServerVo vo);

//新增服务器
public boolean insertServer(ServerVo vo);
//服务器检索
public List<ServerVo> seracho(String s_ip);
}
