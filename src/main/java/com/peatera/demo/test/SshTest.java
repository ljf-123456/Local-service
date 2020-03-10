package com.peatera.demo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
 

 
public class SshTest {
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String hostname ="127.0.0.1";
	        String username="admiN";
	        String password="sx04551";
	        try{
	            //建立连接
	            Connection conn= new Connection(hostname);
	       System.out.println("set up connections");
	            conn.connect();
	            //利用用户名和密码进行授权
	            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
	            if(isAuthenticated ==false)
	            {
	           	System.out.println("--------");
	                throw new IOException("Authorication failed");
	            }
	            //打开会话
	            Session sess = conn.openSession();
	           System.out.println("cmd----");
	            //执行命令
	            sess.execCommand("ls /home -l");
	          System.out.println("The execute command output is:");
	            InputStream stdout = new StreamGobbler(sess.getStdout());
	            System.out.println("The execute command output is2:");
	            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
	            System.out.println("The execute command output is3:");
	            while(true)
	            {
	            	System.out.println("The execute command output is4:");
	                String line = br.readLine();
	                if(line==null) break;
	                System.out.println(line);
	            }
	            
	            System.out.println("Exit code "+sess.getExitStatus());
	            sess.close();
	            conn.close();
	            System.out.println("Connection closed");
	            
	        }catch(IOException e)
	        {
	            System.out.println("can not access the remote machine");
	        }
	    }
 
 
}
