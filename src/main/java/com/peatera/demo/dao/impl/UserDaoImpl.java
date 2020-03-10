package com.peatera.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.peatera.demo.dao.UserDao;
import com.peatera.demo.model.UserVo;


@Component
public class UserDaoImpl implements UserDao {

@Autowired
JdbcTemplate jdbcTemplate;
	@Override
	public boolean checkLogin(String name, String psw) {
		String sql = "select * from users where name=? and psw=?";
		List<UserVo> list = jdbcTemplate.query(sql, new Object[]{name, psw},new BeanPropertyRowMapper<UserVo>(UserVo.class));
		if(list.size()!=0){
			return true;
		}
		return false;
	}

}
