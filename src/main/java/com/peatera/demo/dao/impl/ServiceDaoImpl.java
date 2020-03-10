package com.peatera.demo.dao.impl;

import java.util.List;

import javax.management.relation.Relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.peatera.demo.dao.ServiceDao;
import com.peatera.demo.model.RelationVo;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;


@Component
public class ServiceDaoImpl implements ServiceDao {
	@Autowired
   private JdbcTemplate jdbcTemplate;
	// 查看服务总记录数
	@Override
	public int getCountMsg() {
		String sql = "select * from service";
		List<Service>list=jdbcTemplate.query(sql, new BeanPropertyRowMapper<Service>(Service.class));
		return list.size();
	}

	// 分页查看服务信息
	@Override
	public List<Service> getService(int currPage, int pageSize) {
		int star = (currPage - 1) * pageSize;
		String sql = "select * from service ORDER BY s_date DESC limit ?,?";
		return jdbcTemplate.query(sql, new Object[]{star,pageSize},new BeanPropertyRowMapper<Service>(Service.class));
	}

	// 查看服务绑定的服务器数量
	@Override
	public int getServerNum(String s_name) {
		String sql = "select * from relation where service_name=?";

		List<RelationVo>list=jdbcTemplate.query(sql,new Object[]{s_name},new BeanPropertyRowMapper<RelationVo>(RelationVo.class));
		return list.size();
	}

	// 分页查看服务器信息
	@Override
	public List<ServerVo> getServerMsg(int currPage, int pageSize) {
		// TODO Auto-generated method stub
		int start = (currPage - 1) * pageSize;
		String sql = "select * from my_server ORDER BY s_date DESC limit ?,?";
		

		return jdbcTemplate.query(sql,new Object[]{start,pageSize},new BeanPropertyRowMapper<ServerVo>(ServerVo.class));
	}

	// 查看服务器总记录
	@Override
	public int getCountServer() {
		String sql = "select * from my_server";
		List<ServerVo>list=jdbcTemplate.query(sql,new BeanPropertyRowMapper<ServerVo>(ServerVo.class));
		return list.size();
	}

	// 删除服务器
	@Override
	public boolean deleteServer(ServerVo vo) {
		boolean flag = false;
		String sql = "delete from my_server where s_ip=? and s_port=?";
		System.out.println(vo.getS_ip()+"-"+vo.getS_port());
		int n=jdbcTemplate.update(sql,new Object[]{vo.getS_ip(),vo.getS_port()});
		if(n!=0){
			flag=true;
		}
		return flag;
	}

	// 新增服务器
	@Override
	public boolean insertServer(ServerVo vo) {
		boolean flag = false;
		String sql = "insert into my_server values(?,?,?,?,?,?,now())";
		int n=jdbcTemplate.update(sql, new Object[]{
				vo.getS_ip(),
				vo.getS_port(),
				vo.getS_user(),
				vo.getS_psw(),
				vo.getS_dir(),
				vo.getS_beizhu()
		});
		if(n!=0){
			flag=true;
		}
		return flag;
	}

	// 检查服务器是否存在
	@Override
	public boolean checkServerExist(ServerVo vo) {
		boolean flag = false;
		String sql = "select * from my_server where s_ip=?";
		List<ServerVo>list=jdbcTemplate.query(sql,new Object[]{vo.getS_ip()},new BeanPropertyRowMapper<ServerVo>(ServerVo.class) );
		if(0!=list.size()){
			flag=true;
		}
		return flag;
	}

	// 服务器检索
	@Override
	public List<ServerVo> seracho(String s_ip) {
		// TODO Auto-generated method stub
		String sql = "select * from my_server where s_ip=?";
		
		return jdbcTemplate.query(sql,new Object[]{s_ip}, new BeanPropertyRowMapper<ServerVo>(ServerVo.class));
	}
    
	
	//查看已绑定服务器
	@Override
	public List<RelationVo> seeBdServer(String sname) {
		String sql = "select * from relation where service_name=?";
		return jdbcTemplate.query(sql,new Object[]{sname}, new BeanPropertyRowMapper<RelationVo>(RelationVo.class));
	}
   
	
	//查看是否绑定服务
	@Override
	public boolean checkBdService(ServerVo vo) {
		boolean flag =false;
		String sql = "select * from relation where server_ip=?";
		List<RelationVo>list = jdbcTemplate.query(sql,new Object[]{vo.getS_ip()}, new BeanPropertyRowMapper<RelationVo>(RelationVo.class));
		if(0!=list.size()){
			flag=true;
		}
		System.out.println(flag);
		return flag;
	}

}
