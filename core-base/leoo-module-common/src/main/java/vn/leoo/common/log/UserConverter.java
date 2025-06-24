package vn.leoo.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class UserConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        //--- get username here
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if(auth != null){
//            return auth.getName();
//        }
        return "NO_USER";
    }
}
