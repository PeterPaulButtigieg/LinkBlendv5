package com.example.linkblendv5.ui.profile;

public class Account {
    private String accountType;
    private String iconFilePath;
    private String link;

    public Account(String accountType, String iconFilePath, String link) {
        this.accountType = accountType;
        this.iconFilePath = iconFilePath;
        this.link = link;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIconFilePath() {
        return iconFilePath;
    }

    public void setIconFilePath(String iconFilePath) {
        this.iconFilePath = iconFilePath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
