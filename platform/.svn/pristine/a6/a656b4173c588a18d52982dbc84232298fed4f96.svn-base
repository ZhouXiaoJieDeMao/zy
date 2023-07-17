package com.bsoft.common.utils.drgs;

import cn.hutool.core.util.NetUtil;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.LinkedHashSet;

public class PcInfoUtils {

    private static String IP = "";
    private static String MACADD = "";

    @PostConstruct
    private void init(){
        IP = NetUtil.getLocalhostStr();
        MACADD = getMACADD();
    }

    public static String getIP(){
        //ip  = NetUtil.getLocalhostStr();
        if(IP.isEmpty()){
            LinkedHashSet<String> linkedHashSet = NetUtil.localIpv4s();
            linkedHashSet.forEach((ip)->{
                if(!ip.equals(NetUtil.LOCAL_IP) && ip.length() <= 15 && ip.length() >= 7){
                    IP = ip;
                    return;
                }
            });
        }
        return IP;
    }

    public static String getMACADD(){
        if(MACADD.isEmpty()){
            MACADD = NetUtil.getMacAddress(getLocalhost());
        }
        return MACADD;
    }

    private static InetAddress getLocalhost() {
        InetAddress candidateAddress = null;
        NetworkInterface iface;
        InetAddress inetAddr;
        try {
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                iface = ifaces.nextElement();
                for (Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    inetAddr = inetAddrs.nextElement();
                    if (false == inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            String ls_mac = NetUtil.getMacAddress(inetAddr);
                            //常规mac地址不会超过17位
                            if(ls_mac.length()>18){
                                continue;
                            }
                            return inetAddr;
                        } else if (null == candidateAddress) {
                            // 非site-local地址做为候选地址返回
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
        } catch (SocketException e) {
        }
        if (null == candidateAddress) {
            try {
                candidateAddress = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
            }
        }

        return candidateAddress;
    }
}
