package cn.tofucat.protocol.cmpp;


/**
 * zzzxb
 * 2023/10/31
 */
public class AccountInfo {
    private String host;
    private int port;
    private String spId;
    private String sk;

    public AccountInfo address(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    public AccountInfo account(String spId, String sk) {
        this.spId = spId;
        this.sk = sk;
        return this;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }
}
