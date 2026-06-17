package vn.leoo.audit.log.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.leoo.audit.log.context.AuditLogContext;

import java.net.InetAddress;

public class Helper {
    public static String getEndpoint() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes()).getRequest();
            return request.getMethod() + " " + request.getRequestURI();
        } catch (IllegalStateException e) {
            return null;   // ngoài HTTP scope (batch, scheduled...)
        }
    }
    public static String getUserAgent() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) return null;
        return userAgent.length() > 500 ? userAgent.substring(0, 500) : userAgent;
    }
    public  static String getMachineId() {
        try{
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostName();
        } catch (Exception e) {
            return null;
        }
    }

}
