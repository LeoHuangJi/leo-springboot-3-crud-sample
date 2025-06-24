package vn.leoo.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class NetWorkUtils {
	public static String getMethodName(String methodName){
		String name = "";
		boolean flag = false;
		
		try {
			StackTraceElement[] elements = Thread.currentThread()
					.getStackTrace();
			for (StackTraceElement e : elements) {
				flag = e.getMethodName().equals(methodName);
				if (flag) {
					name = e.getMethodName();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return name;
	}
	
	/**
	 * @see mapNetworkDriver(): Tao o mang.
	 */
	public static void mapNetworkDriver(){
		try {
			/*String driver = Config.getNet_Driver();
			String ip = Config.getNet_IP();
			String path = Config.getNet_Path();
			String username = Config.getNet_Username();
			String password = Config.getNet_Password();
			String command = "cmd.exe /C START C:\\Windows\\system32\\net.exe use "+driver+": \\\\"+ip+"\\"+path+" /user:"+ip+"\\"+username+" "+password;
			Process p = Runtime.getRuntime().exec(command);*/
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * @see deleteMapNetworkDriver(): Xoa o mang.
	 */
	public static void deleteMapNetworkDriver(){
		try {
			String command = "cmd.exe /C START C:\\Windows\\system32\\net.exe use * /delete";
			Process p = Runtime.getRuntime().exec(command);
			if(p.isAlive())
				p.destroy();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static boolean pingIPAddress(String ipAddress, int timeout){
		boolean reachable, bl = false;

		try {
			System.out.println("Sending Ping Request to " + ipAddress);
			
			/*InetAddress inet = InetAddress.getByName(ipAddress);
			reachable = inet.isReachable(timeout * 1000);*/
			reachable = (Runtime.getRuntime().exec("ping -n 3 "+ipAddress).waitFor()==0);
			if (reachable){
				bl = true;
			} else {
				bl = false;
			}

	    } catch ( Exception e ) {
	    	System.out.println("Exception:" + e.getMessage());
	    	bl = false;
	    }
		return bl;
	}
	
	public static boolean telnetIPAddress(String ipAddress, int port){
		boolean reachable = false;

		Socket pingSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
        	System.out.println("Sending telnet Request to " + ipAddress);
            pingSocket = new Socket(ipAddress, port);
            out = new PrintWriter(pingSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            reachable = true;
        } catch (IOException e) {
        	reachable = false;
        }finally{
        	try{
        		out.println("ping");
                System.out.println(in.readLine());
                out.close();
                in.close();
                pingSocket.close();
        	}catch(Exception ex){
        		
        	}
        }

        return reachable;
	}
	
	public static boolean telnetIPAddress(String ipAddress, int port, int timeout){
		boolean reachable = false;
		Socket socket = null;

		try {            
			System.out.println("Sending telnet Request to " + ipAddress);
			InetAddress addr = InetAddress.getByName(ipAddress);
            SocketAddress sockaddr = new InetSocketAddress(addr, port);
 
            // Creates an unconnected socket
            socket = new Socket();
 
            // Connects this socket to the server with a specified timeout value
            // If timeout occurs, SocketTimeoutException is thrown
            socket.connect(sockaddr, timeout);
             
            System.out.println("Socket connected..." + socket);
            reachable = true;
        }catch (UnknownHostException e) {
            System.out.println("Host not found: " + e.getMessage());
        }catch (IOException ioe) {
            System.out.println("I/O Error " + ioe.getMessage());
        }finally{
        	try{
                socket.close();
        	}catch(Exception ex){
        		
        	}
        }

        return reachable;
	}
	
//	public static void main(String[] args) throws Exception {
//		boolean bl = false;
//	   	String ipAddress = "";
//	   	ipAddress = "108.177.97.10";
//	   	int port = 25;
//  		bl = NetWorkUtils.telnetIPAddress(ipAddress, port, 3 * 1000);
//		if (bl) {
//			System.out.println(ipAddress + " is reachable.");
//		} else {
//			System.out.println(ipAddress + " NOT reachable.");
//		}
//	}
}
