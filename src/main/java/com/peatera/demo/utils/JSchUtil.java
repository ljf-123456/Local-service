package com.peatera.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.peatera.demo.WebSocket.ProductWebSocket;

public class JSchUtil {
	public static String BASECOMMAND = "source /etc/profile;";
	public static Session session;
	// 获取Logger对象的实例
	static Logger logger = Logger.getLogger(JSchUtil.class);

	// 使用默认的配置信息，不需要写log4j.properties

	// 建立连接
	public static boolean connect(String user, String passwd, String host,
			int port) {
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(user, host, port);
			session.setPassword(passwd);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
			// System.out.println("connect error !");
			return false;
		}
		return true;
	}

	// 执行命令
	public static List<String> runDistanceShell(String commands, String user,
			String passwd, String host, int port, String msg) {
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		try {
			ProductWebSocket.sendInfo(json);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BasicConfigurator.configure();
		// 设置日志输出级别为WARN，这将覆盖配置文件中设置的级别，只有日志级别高于WARN的日志才输出
		logger.setLevel(Level.INFO);
		if (!connect(user, passwd, host, 22)) {
			return null;
		}
		//System.out.println("连接成功");
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		Channel channel = null;
		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(commands);

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			channel.connect();
			InputStream in = channel.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			String buf;
			while ((buf = reader.readLine()) != null) {
                  
				/*
				 * // 舍弃PID 进程信息 if (buf.contains("PID")) { break; }
				 */
				logger.info(buf.trim());
				json.put("msg", buf);
				ProductWebSocket.sendInfo(json);
				list.add(buf.trim());
			}
			json.put("msg", "完成................");
			ProductWebSocket.sendInfo(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}