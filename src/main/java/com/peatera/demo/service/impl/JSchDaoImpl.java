package com.peatera.demo.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.peatera.demo.WebSocket.ProductWebSocket;
import com.peatera.demo.service.dao.JSchDao;
import com.peatera.demo.utils.JSchUtil;
@Component
public class JSchDaoImpl implements JSchDao {

	//查看服务文件是否存在  ----存在--改名备份
	@Override
	public boolean checkFile(String dir, String s_name,String user,String passwd,String host,int port) {
		
		//当前时间
		JSONObject json = new JSONObject();
		json.put("msg", "开始.............");
		try {
			ProductWebSocket.sendInfo(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Date d = new Date();
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 String time = sdf.format(d).replace(" ", "-");
		 
		 
		String findServiceCommand = JSchUtil.BASECOMMAND +"find "+dir+"/"+s_name;//检查服务文件是否存在
		String findFileCommand = JSchUtil.BASECOMMAND +"find "+dir+"/"+"beifen";//检查备份文件夹是否存在
		String createFileCommand = JSchUtil.BASECOMMAND +"mkdir "+dir+"/beifen";//创建备份文件夹
		String beifenFileCommand = JSchUtil.BASECOMMAND +"mv "+dir+"/"+s_name+" "+dir+"/beifen/"+s_name+"-"+time;//移动文件到备份文件价并改名
		
		List<String>list=JSchUtil.runDistanceShell(findServiceCommand, user, passwd, host,port,"开始检查旧版本.........");//检查服务文件是否存在
		List<String>filelist=JSchUtil.runDistanceShell(findFileCommand, user, passwd, host,port,"开始检查备份文件.........");//检查备份文件是否存在
		/*System.out.println("list=:"+list);
		System.out.println("filelist=:"+filelist);*/
		if(list!=null&&list.size()!=0){
			//服务文件存在  ----备份
			if(filelist==null&&list.size()==0){     //备份文件价不存在---新建
			   //创建备份文件夹
			   List<String>createlist=JSchUtil.runDistanceShell(createFileCommand, user, passwd, host,port,"备份文件夹不存在，开始新建备份文件夹.........");
			   /*System.out.println("createlist=:"+createlist);*/
			}
			 List<String>beifenFile=JSchUtil.runDistanceShell(beifenFileCommand, user, passwd, host,port,"开始备份上一版本..............");//备份文件
			/* System.out.println("beifenFile=:"+beifenFile);*/
		}
		/*System.out.println("findServiceCommand=:"+findServiceCommand);
		System.out.println("findFileCommand=:"+findFileCommand);
		System.out.println("createFileCommand=:"+createFileCommand);
		System.out.println("beifenFileCommand=:"+beifenFileCommand);*/
		return true;
	}

}
