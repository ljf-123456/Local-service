package com.peatera.demo.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;





import com.peatera.demo.utils.JSchUtil;

public class JSchTest {

	public static void main(String[] args) {
		String pid = "";
		//基础
		String baseCommand = "source /etc/profile;";
		//开启服务
		String runCommand = baseCommand+"find /usr/ftpdata/run";
		//String findServiceCommand = baseCommand +"find "+dir+"/"+s_name
		//查看服务Pid
		//String fundCommand = baseCommand+"jps -l";
		
	    //JSchUtil.runDistanceShell(runCommand, "root", "@sx04551", "106.12.214.205");
	    //List<String>list=JSchUtil.runDistanceShell(runCommand, "root", "@sx04551", "192.168.186.128");
	   // pid = JSchUtil.changeMSg(list, "run");
	  //关闭服务
	    //String closeCommand = baseCommand+"kill "+pid;
	    //JSchUtil.runDistanceShell(closeCommand, "root", "@sx04551", "106.12.214.205");
	    //System.out.println(list.size());
	    Date d = new Date();
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String time = sdf.format(d).replace(" ", "-");
	    System.out.println(sdf.format(d).replace(" ", "-"));
	   // System.out.println("pid=:"+pid);
	}

}
