package xyz.zzzxb.tofucat.protocol.cmpp;

import java.util.Collections;
import java.util.List;

/**
 * zzzxb
 * 2023/10/31
 */
public class Message {
    private AccountInfo accountInfo;

    private List<String> phones;
    private String content;

    public Message setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
        return this;
    }

    public Message setPhone(String phone) {
        this.phones = Collections.singletonList(phone);
        return this;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public String getContent() {
        return content;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
