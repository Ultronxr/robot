package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.util.PhantomjsUtils;

import java.io.IOException;


public class PhantomjsTest {

    public static void main(String[] args) {

        String url = "https://apph5.qichacha.com/h5/app/morning-paper/paper-list?_bridge=1&groupId=816";
        String outputPathAndFilename = "cache\\qichacha_news\\qcc.png";
        try {
            PhantomjsUtils.screenCapture(url, outputPathAndFilename, 650, 5000, 1.3f, 5000);
        } catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }

    }

}
