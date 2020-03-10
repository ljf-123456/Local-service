package com.peatera.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.peatera.demo.dao.SerciceDao;
import com.peatera.demo.dao.ServiceDao;
import com.peatera.demo.dao.impl.SerciceDaoImpl;
import com.peatera.demo.model.RelationVo;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;
import com.peatera.demo.service.dao.ServiceDaoService;

@Component
public class ServiceDaoServiceImpl implements ServiceDaoService {
	@Autowired
	SerciceDao dao;
	@Autowired
	ServiceDao dao1;

	// 新增服务
	@Override
	public boolean insertService(Service vo) {
		boolean flag = false;
		// 检查是否存在
		// 不存在再新增
		if (!dao.checkSercice(vo)) {
			flag = dao.inSertSercice(vo);
		}
		return flag;
	}

	// 服务检索
	@Override
	public List<Service> findServiceOne(String name) {
		List<Service> list = dao.findService(name);
		for (int i = 0; i < list.size(); i++) {
			Service vo = list.get(i);
			String s_name = vo.getS_name();
			int n = dao1.getServerNum(s_name);

			vo.setS_num(n);
			// list2.add(vo);
		}
		// System.out.println("n=:"+list.get(0).getS_num());
		return list;
	}

	// 删除服务
	@Override
	public boolean deleteService(String name) {
		boolean flag = false;
		// 解绑
		dao.jiebang(name);
		flag = dao.deleteService(name);
		return flag;
	}

	// 查看可绑定服务器
	@Override
	public JSONObject seeServerVo(String name, int current, int pageSize) {
		List<ServerVo> list = dao.findServers(name, current, pageSize);
		int n = dao.findServersNum(name);
		JSONObject json = new JSONObject();
		System.out.println(list.size());
		json.put("list", list);
		json.put("count", n);
		return json;
	}

	// 绑定服务器
	@Override
	public boolean bangdingServer(List<ServerVo> list, String name) {
		boolean falg = dao.bangding(list, name);
		return falg;
	}

	// 查看已绑定服务器
	@Override
	public JSONObject seeBdServerVo(String sname) {
		List<RelationVo> list = dao1.seeBdServer(sname);
		List<ServerVo> newList = new ArrayList<ServerVo>();
		for (int i = 0; i < list.size(); i++) {
			RelationVo vo = list.get(i);
			List<ServerVo> list0 = dao1.seracho(vo.getServer_ip());
			newList.add(list0.get(0));
		}

		JSONObject json = new JSONObject();
		json.put("list", newList);
		return json;
	}

	// 服务器解绑
	@Override
	public boolean jiebang(String sname, List<ServerVo> list) {
		// TODO Auto-generated method stub
		return dao.jiebang(sname, list);
	}

}
