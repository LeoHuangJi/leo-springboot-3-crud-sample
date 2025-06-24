package vn.leoo.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * public class TimeEstimation {
 * 
 * @SuppressWarnings("rawtypes") private final static Map m = new
 * java.util.HashMap();
 * 
 * @SuppressWarnings("unchecked") public final static void beginEstimate(String
 * method_name){ long start_time=System.currentTimeMillis();
 * m.put(method_name,new Long(start_time)); }
 * 
 * public final static void endEstimate(String method_name){ long
 * end_time=System.currentTimeMillis(); Long s_time=(Long)m.get(method_name);
 * 
 * if(s_time!=null){ long start_time=s_time.longValue();
 * System.out.println("\n<<<<<<<   Time to execute method "+method_name+" is : "
 * +(end_time-start_time)+" milliseconds    >>>>>>>"); }else{ System.err.
 * println("\n********* Not yet invoke beginEstimate for method *******"
 * +method_name); } m.remove(method_name); } }
 */

public class TimeEstimation {

    private static final Map<String, Long> methodStartTimes = new ConcurrentHashMap<>();

    public static void beginEstimate(String methodName) {
        methodStartTimes.put(methodName, System.currentTimeMillis());
    }

    public static void endEstimate(String methodName) {
        Long startTime = methodStartTimes.get(methodName);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            System.out.printf("⏱️ Method '%s' executed in %d ms%n", methodName, duration);
        } else {
            System.err.printf("⚠️ beginEstimate was not called for method '%s'%n", methodName);
        }
        methodStartTimes.remove(methodName);
    }
}
