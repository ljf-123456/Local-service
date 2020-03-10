package com.peatera.demo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 远程调用Linux shell 命令
 *
 * @author wei.Li by 14-9-2.
 */
public class Test1 {


	//启动服务
    public static final String CPU_MEM_SHELL = "source /etc/profile;cd /usr/ftpdata;java -jar run";
    //查看进程
    public static final String see_pid = "source /etc/profile;jps -l";
    
    
    public static final String[] COMMANDS = {CPU_MEM_SHELL};
    
    public static final String[] COMMANDS1 = {see_pid};
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static Session session;

    /**
     * 连接到指定的HOST
     *
     * @return isConnect
     * @throws JSchException JSchException
     */
    private static boolean connect(String user, String passwd, String host) {
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(passwd);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("connect error !");
            return false;
        }
        return true;
    }

    /**
     * 远程连接Linux 服务器 执行相关的命令
     *
     * @param commands 执行的脚本
     * @param user     远程连接的用户名
     * @param passwd   远程连接的密码
     * @param host     远程连接的主机IP
     * @return 最终命令返回信息
     */
    public static Map<String, String> runDistanceShell(String[] commands, String user, String passwd, String host) {
        if (!connect(user, passwd, host)) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
       

        BufferedReader reader = null;
        Channel channel = null;
        try {
            for (String command : commands) {
              
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);

                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err);

                channel.connect();
                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf;
                while ((buf = reader.readLine()) != null) {

                    //舍弃PID 进程信息
                    if (buf.contains("PID")) {
                        break;
                    }
                    System.out.println(buf.trim().substring(0, buf.trim().indexOf(' ')));
                    /*stringBuffer.append(buf.trim()).append(LINE_SEPARATOR);*/
                }
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
        return map;
    }
    public static void main(String[] args) {
        Map<String, String> result = runDistanceShell(COMMANDS1, "root", "@sx04551", "106.12.214.205");
       // System.out.println(disposeResultMessage(result));
    }

}
