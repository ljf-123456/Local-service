package com.peatera.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.peatera.demo.dao.SerciceDao;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;

@Component
public class SerciceDaoImpl implements SerciceDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 检查服务是否存在
	@Override
	public boolean checkSercice(Service vo) {
		boolean flag = false;
		String sql = "select * from service where s_name=?";

		List<Service> list = jdbcTemplate.query(sql,
				new Object[] { vo.getS_name() },
				new BeanPropertyRowMapper<Service>(Service.class));
		if (0 != list.size()) {
			flag = true;
		}
		return flag;
	}

	// 新增服务
	@Override
	public boolean inSertSercice(Service vo) {
		boolean flag = false;
		String sql = "insert into service values(?,?,?,now())";
		int n = jdbcTemplate.update(
				sql,
				new Object[] { vo.getS_name(), vo.getS_type(),
						vo.getS_shuoming() });
		if (n != 0) {
			flag = true;
		}
		return flag;
	}

	// 服务检索
	@Override
	public List<Service> findService(String name) {
		String sql = "select * from service where s_name=?";

		List<Service> list = jdbcTemplate.query(sql, new Object[] { name },
				new BeanPropertyRowMapper<Service>(Service.class));

		return list;
	}

	// 删除服务
	@Override
	public boolean deleteService(String name) {
		boolean flag = false;
		String sql = "delete from service where s_name=?";
        int n=jdbcTemplate.update(sql,new Object[]{
        		name
        });
        if(n!=0){
        	flag=true;
        }
		return flag;
	}

	//查看可绑定服务器
	@Override
	public List<ServerVo> findServers(String name,int current,int pageSize) {
		int start=(current-1)*pageSize;
		String sql="SELECT * FROM my_server WHERE s_ip NOT IN(SELECT server_ip FROM relation WHERE service_name=? ) limit ?,?";
		List<ServerVo>list=jdbcTemplate.query(sql,new Object[]{name,start,pageSize},new BeanPropertyRowMapper<ServerVo>(ServerVo.class));
		
		return list;
	}
	//查看可绑定服务器数量
	@Override
	public int findServersNum(String name) {
		String sql="SELECT * FROM my_server WHERE s_ip NOT IN(SELECT server_ip FROM relation WHERE service_name=?)";
		List<ServerVo>list=jdbcTemplate.query(sql,new Object[]{name},new BeanPropertyRowMapper<ServerVo>(ServerVo.class));
		int n=list.size();
		return n;
	}

	//绑定服务器
	@Override
	public boolean bangding(List<ServerVo> list, String name) {
		boolean flag=false;
		String sql="";
		int n=0;
		for(int i=0;i<list.size();i++){
			ServerVo vo=list.get(i);
		    sql="insert into relation values(?,?,?)";
		    n=jdbcTemplate.update(sql, new Object[]{name,vo.getS_ip(),vo.getS_port()});
		}
		if(n!=0){
			flag=true;
		}
		return flag;
	}
    //解除指定服务的服务器
	public boolean jiebang(String name) {
		boolean flag=false;
		
		int n=0;
		 String sql="delete from relation where service_name=?";
		    n=jdbcTemplate.update(sql, new Object[]{name});
		if(n!=0){
			flag=true;
		}
		return flag;
	}
	//服务器解绑
	@Override
	public boolean jiebang(String name, List<ServerVo> list) {
		boolean flag=false;
		String sql="";
		int n=0;
		for(int i=0;i<list.size();i++){
			ServerVo vo=list.get(i);
		    sql="delete from relation where service_name=? and server_ip=?";
		    n=jdbcTemplate.update(sql, new Object[]{name,vo.getS_ip()});
		}
		if(n!=0){
			flag=true;
		}
		return flag;
	}

}
