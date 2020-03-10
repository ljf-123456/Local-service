package com.peatera.demo.dao;

import java.util.List;


import com.peatera.demo.model.RelationVo;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;



public interface ServiceDao {
//分页查询服务信息
public List<Service> getService(int currPage,int pageSize );
//查看服务总记录数
public int getCountMsg();
//查找服务器数量
public int getServerNum(String s_name);

//查看服务器信息
public List<ServerVo> getServerMsg(int currPage, int pageSize);
//查看服务器总记录
public int getCountServer();

//删除服务器
public boolean deleteServer(ServerVo vo);

//检查服务器石佛已绑定服务
public boolean checkBdService(ServerVo vo);

//新增服务器
public boolean insertServer(ServerVo vo);
//检查服务器是否存在
public boolean checkServerExist(ServerVo vo);

//服务器检索
public List<ServerVo> seracho(String s_ip);

//查看已绑定服务器
public List<RelationVo>seeBdServer(String sname);

}
