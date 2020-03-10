package com.peatera.demo.test;

 
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.hyperic.sigar.SigarException;
 
 
 
 
/**
 * @Author: huangjie
 * @Description :
 * @Date: Created in 11:22 2018/9/8
 * @Modified By:*/
 
 
public class Test {

    public static void main(String[] args) throws SigarException {
        InetAddress ia=null;
        try {
            ia=ia.getLocalHost();
            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
            System.out.println("本机名称是："+ localname);
            System.out.println("本机的ip是 ："+localip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   


}
