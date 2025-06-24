package vn.leoo.common.mail;

public class SmtpMail {
    private String host;
    private String socketFactoryclass = "javax.net.ssl.SSLSocketFactory";
    private String socketFactoryfallback = "true";
    private String port = "465";
    private String socketFactoryport;
    private String auth = "true";
    private String quitwait = "true";

    public static class AUTH {
        public static String ENABLE = "true";
        public static String DISABLE = "false";
    }

    public static class QUITWAIT {
        public static String ENABLE = "true";
        public static String DISABLE = "false";
    }

    public static class FALLBACK {
        public static String ENABLE = "true";
        public static String DISABLE = "false";
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSocketFactoryclass() {
        return socketFactoryclass;
    }

    public void setSocketFactoryclass(String socketFactoryclass) {
        this.socketFactoryclass = socketFactoryclass;
    }

    public String getSocketFactoryfallback() {
        return socketFactoryfallback;
    }

    public void setSocketFactoryfallback(String socketFactoryfallback) {
        this.socketFactoryfallback = socketFactoryfallback;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSocketFactoryport() {
        return socketFactoryport;
    }

    public void setSocketFactoryport(String socketFactoryport) {
        this.socketFactoryport = socketFactoryport;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getQuitwait() {
        return quitwait;
    }

    public void setQuitwait(String quitwait) {
        this.quitwait = quitwait;
    }
}
