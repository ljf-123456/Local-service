package com.peatera.demo.Controler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.peatera.demo.dao.ServiceDao;
import com.peatera.demo.dao.UserDao;
import com.peatera.demo.model.ServerVo;
import com.peatera.demo.model.Service;
import com.peatera.demo.service.dao.Dao1;
import com.peatera.demo.service.dao.JSchDao;
import com.peatera.demo.service.dao.ServiceDaoService;
import com.peatera.demo.utils.FtpUtils;
import com.peatera.demo.utils.JSchUtil;

@RestController
public class FromControler {

	@Autowired
	Dao1 dao;
	@Autowired
	ServiceDaoService s_dao;
	@Autowired
	ServiceDao ser_dao;
	@Autowired
	UserDao user_dao;
	@Autowired 
	JSchDao jsDao;

	// 初始服务信息（总记录数及某一页内容）
	// @ResponseBody
	@RequestMapping("/showMsg")
	@ResponseBody
	public JSONObject getMsg(@RequestBody JSONObject param) {
		int currPage = param.getIntValue("currPage");
		int pageSize = param.getIntValue("pageSize");

		// 获取该页记录
		List<Service> list = dao.getServMsg(currPage, pageSize);
		// 获取总记录数
		int count = ser_dao.getCountMsg();
		JSONObject json = new JSONObject();
		json.put("list", list);
		json.put("count", count);
		return json;
	}

	// 初始服务器信息
	@RequestMapping("/showServerMsg")
	public JSONObject getServerMsg(@RequestBody JSONObject param) {
		int currPage = param.getIntValue("currPage");
		int pageSize = param.getIntValue("pageSize");
		// 获取该页记录
		List<ServerVo> list = dao.getServerMsg(currPage, pageSize);
		// 获取总记录数
		int count = ser_dao.getCountServer();
		JSONObject json = new JSONObject();
		json.put("list", list);
		json.put("count", count);
		return json;
	}

	// 删除服务器
	@ResponseBody
	@RequestMapping("/delete")
	public boolean deleteServer(@RequestBody JSONObject param) {

		ServerVo vo = JSONObject.toJavaObject(param, ServerVo.class);
		boolean result = dao.deleteServer(vo);
		return result;
	}

	// 新增服务器
	@ResponseBody
	@RequestMapping("/insert")
	public boolean insertServer(@RequestBody JSONObject param) {

		ServerVo vo = JSONObject.toJavaObject(param, ServerVo.class);
		boolean result = dao.insertServer(vo);
		return result;
	}

	// 检索服务器
	@ResponseBody
	@RequestMapping("/seracho")
	public List<ServerVo> seracho(@RequestBody JSONObject param) {
		ServerVo vo = JSONObject.toJavaObject(param, ServerVo.class);
		String s_ip = vo.getS_ip();
		List<ServerVo> list = dao.seracho(s_ip);
		return list;
	}

	// 新增服务
	@ResponseBody
	@RequestMapping("/insertService")
	public boolean insertService(@RequestBody JSONObject param) {
		Service vo = JSONObject.toJavaObject(param, Service.class);
		boolean result = s_dao.insertService(vo);
		return result;
	}

	// 检索服务
	@ResponseBody
	@RequestMapping("/findService")
	public List<Service> findService(@RequestBody JSONObject param) {
		Service vo = JSONObject.toJavaObject(param, Service.class);
		String name = vo.getS_name();
		List<Service> list = s_dao.findServiceOne(name);
		return list;
	}

	// 删除服务
	@ResponseBody
	@RequestMapping("/deleteService")
	public boolean deleteService(@RequestBody JSONObject param) {
		Service vo = JSONObject.toJavaObject(param, Service.class);
		String name = vo.getS_name();
		boolean flag = s_dao.deleteService(name);
        System.out.println("进来了"+vo);
		return flag;
	}

	// 查看可绑定服务器
	@ResponseBody
	@RequestMapping("/bdServer")
	public JSONObject bdServer(@RequestBody JSONObject param) {

		String name = param.getString("name");
		int current = param.getIntValue("current");
		int pageSize = param.getIntValue("pageSize");
		// System.out.println("name=:"+name+"current=:"+current+"pageSize=:"+pageSize);
		JSONObject json = s_dao.seeServerVo(name, current, pageSize);

		return json;
	}

	// 绑定服务器
	@ResponseBody
	@RequestMapping("/bangding")
	public boolean bangdingServer(@RequestBody JSONObject param) {
		boolean flag = false;
		List<ServerVo> list = param.getJSONArray("list").toJavaList(
				ServerVo.class);
		String name = param.getString("name");
		flag = s_dao.bangdingServer(list, name);
		// System.out.println(list.size()+"------"+name);
		return flag;
	}

	// 查看已绑定服务器
	@ResponseBody
	@RequestMapping("/seebangding")
	public JSONObject seebdServer(@RequestBody JSONObject param) {
        System.out.println("进来了");
		String name = param.getString("sname");

		JSONObject json = s_dao.seeBdServerVo(name);/*
													 * =s_dao.seeServerVo(name,
													 * current,pageSize);
													 */
     
		return json;
	}

	// 文件上传
	@ResponseBody
	@RequestMapping("/uploadFile")
	public int uploaddile(@RequestParam("file") MultipartFile file,
			@RequestParam("ip") String ip, @RequestParam("port") int port,
			@RequestParam("usr") String user, @RequestParam("psw") String psw,
			@RequestParam("dir") String dir,@RequestParam("s_name") String s_name) {
		int n = 1;
		//System.out.println("进来了");
		try {
			jsDao.checkFile(dir, s_name, user, psw, ip,port);
			//System.out.println(dir+s_name+user+psw+ip+port);
			if (FtpUtils.uploadFile(ip, port, user, psw, dir, s_name, file.getInputStream())){
				n = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("n="+n);
		return n;
	}
	//开启服务
	@ResponseBody
	@RequestMapping("/startService")
	public String startService(@RequestBody JSONObject param) {
		String s_name = param.getString("s_name");
		String ip = param.getString("ip");
		String port = param.getString("port");
		String user = param.getString("user");
		String psw = param.getString("psw");
		String dir = param.getString("dir");
		//开启服务命令
		String command = JSchUtil.BASECOMMAND+"cd "+dir+";"+"java -jar "+s_name;
		
		List<String>list=JSchUtil.runDistanceShell(command, user, psw, ip,Integer.parseInt(port),"正在启动,请稍等...........");
		System.out.println("出阿里了------------------");
		
		if(list==null){
			return "服务不存在或文件损坏,请重新上传";
		}else if(list.size()==0){
			return "服务不存在或文件损坏,请重新上传";
		}
		return "远程开启服务成功";
	}
	// 停止服务
		@ResponseBody
		@RequestMapping("/closeService")
		public String closeService(@RequestBody JSONObject param) {
            
			String s_name = param.getString("s_name");
			String ip = param.getString("ip");
			String port = param.getString("port");
			String user = param.getString("user");
			String psw = param.getString("psw");
			String dir = param.getString("dir");
            
			//查看服务Pid
			 String fundCommand = JSchUtil.BASECOMMAND+"ps -aux|grep "+s_name+"|grep -v grep|awk '{print $2}'";
			 List<String>list=JSchUtil.runDistanceShell(fundCommand,user, psw, ip,Integer.parseInt(port),"开始查找服务进程.............");
			// List<String> pid = JSchUtil.changeMSg(list);
			 if(null==list || 0==list.size()){
				 return "服务未开启";
			 }else{
			//关闭服务
				for(int i=0 ;i<list.size();i++){
			    String closeCommand = JSchUtil.BASECOMMAND+"kill "+list.get(i);
			    JSchUtil.runDistanceShell(closeCommand,user, psw, ip,Integer.parseInt(port),"开始杀死服务进程"+list.get(i)+".............");
			    }
			 }
			return "服务已关闭";
		}
		
     //服务器解绑
		@ResponseBody
		@RequestMapping("/jiebang")
		public boolean jiebang(@RequestBody JSONObject param) {

			JSONArray select = param.getJSONArray("select");
			String sname = param.getString("s_name");
			System.out.println(sname);
			List<ServerVo>list = select.toJavaList(ServerVo.class);
           
			return s_dao.jiebang(sname, list);
		}
		 //登录
		@ResponseBody
		@RequestMapping("/login")
		public boolean login(@RequestBody JSONObject param) {

			String name = param.getString("name");
			String psw = param.getString("psw");
			//System.out.println(name+"-"+psw);
			return user_dao.checkLogin(name, psw);
		}
}
