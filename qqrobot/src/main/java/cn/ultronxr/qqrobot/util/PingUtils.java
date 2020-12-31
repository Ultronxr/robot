package cn.ultronxr.qqrobot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
public class PingUtils {

    /**
     * 调用系统层面的cmd进行ping，并导出cmd的输出内容
     *
     * @param ipOrDomain IP地址或域名（不能携带 协议标识 和 端口号）
     *                   如：可以是baidu.com  不能是https://baidu.com
     */
    public static String pingByCmd(String ipOrDomain) throws IOException {
        String cmdLine = "ping " + ipOrDomain;
        Process p = Runtime.getRuntime().exec("cmd /c " + cmdLine);
        InputStreamReader inputStreamReader = new InputStreamReader(p.getInputStream(), Charset.forName("GBK"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder output = new StringBuilder();
        String line = null;
        while(Objects.nonNull(line = bufferedReader.readLine())){
            if(line.contains("字节的数据")){
                continue;
            }
            if(line.contains("统计信息")){
                break;
            }
            output.append(line).append("\n");
        }

        bufferedReader.close();
        inputStreamReader.close();

        return output.toString();
    }

}
