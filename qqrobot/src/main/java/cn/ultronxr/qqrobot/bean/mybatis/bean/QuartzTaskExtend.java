package cn.ultronxr.qqrobot.bean.mybatis.bean;

public class QuartzTaskExtend extends QuartzTaskExtendKey {
    private String msg;

    private String files;

    private String sms;

    private String email;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files == null ? null : files.trim();
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms == null ? null : sms.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}