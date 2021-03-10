package cn.ultronxr.qqrobot.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.GlobalData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Objects;


@Slf4j
public class PingUtils {

    /** 操作系统名称 */
    private static final String OS_NAME = GlobalData.OS_NAME;
    //private static final String OS_NAME = "CentOS8";

    /** 用于解析出 ping命令输出内容 中的 <b>统计信息<b/> 的正则表达式 */
    private static final String PING_RESULT_STATISTIC_REGEX =
            OS_NAME.contains("Windows") ? "\n.*统计信息.*\n" : "\n.*statistics.*\n";


    public static String pingByRuntime(String ipOrDomain) throws IOException {
        return modifyPingResult(
                ipOrDomain,
                pingByRuntime(ipOrDomain, 3, 1000)
        );
    }

    /**
     * 使用Java Runtime调用ping命令
     *
     * @param ipOrDomain      IP地址或域名（不能携带 协议标识 和 端口号）
     * @param pingTimes       ping次数，传入值>0有效
     * @param timeOutMs       超时时间（单位：毫秒ms），传入值>0有效
     *
     * @return {@code String} 返回ping命令执行结果内容
     * @throws IOException    输入输出流抛出异常、Runtime执行命令抛出异常
     */
    public static String pingByRuntime(String ipOrDomain, Integer pingTimes, Integer timeOutMs) throws IOException {
        String pingCmd = getPingCmd(ipOrDomain, pingTimes, timeOutMs);
        Process process = Runtime.getRuntime().exec(pingCmd);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
        String line = null;
        StringBuilder strBuilder = new StringBuilder();
        while (Objects.nonNull(line = bufferedReader.readLine())){
            strBuilder.append(line).append("\n");
        }
        bufferedReader.close();

        return strBuilder.toString();
    }

    /**
     * 区分操作系统环境，组装不同的ping命令行
     *
     * @param ipOrDomain      IP地址或域名（不能携带 协议标识 和 端口号）
     * @param pingTimes       ping次数，传入值>0有效
     * @param timeOutMs       超时时间（单位：毫秒ms），传入值>0有效
     *
     * @return {@code String} 返回不同操作系统下的完整ping命令行
     */
    public static String getPingCmd(String ipOrDomain, Integer pingTimes, Integer timeOutMs){
        StringBuilder strBuilder = new StringBuilder();
        if(OS_NAME.contains("Windows")){
            strBuilder.append("ping ").append(ipOrDomain)
                    .append((Objects.nonNull(pingTimes) && pingTimes > 0 ? (" -n " + pingTimes) : " "))
                    .append((Objects.nonNull(timeOutMs) && timeOutMs > 0 ? (" -w " + timeOutMs) : " "));
        } else if(OS_NAME.contains("Mac")){
            strBuilder.append("ping ")
                    .append((Objects.nonNull(pingTimes) && pingTimes > 0 ? (" -c " + pingTimes) : " "))
                    .append((Objects.nonNull(timeOutMs) && timeOutMs > 0 ? (" -t " + timeOutMs) : " "))
                    .append(" ").append(ipOrDomain);
        } else{
            //linux
            strBuilder.append("ping ")
                    .append((Objects.nonNull(pingTimes) && pingTimes > 0 ? (" -c " + pingTimes) : " "))
                    .append((Objects.nonNull(timeOutMs) && timeOutMs > 0 ? (" -w " + timeOutMs) : " "))
                    .append(" ").append(ipOrDomain);
        }
        return strBuilder.toString();
    }

    /**
     * 简化ping命令输出结果（只保留统计信息），以便后续处理
     * 注：ping命令输出结果参照下面main方法中的内容
     *
     * @param ipOrDomain      传入ping命令参数的原IP地址或URL链接
     *                        （用于直接输出，不用再在ping输出结果里再找一遍）
     * @param pingResult      ping命令输出结果
     *
     * @return {@code String} 简化后的ping命令输出结果
     */
    public static String modifyPingResult(String ipOrDomain, String pingResult){
        StringBuilder strBuilder = new StringBuilder();

        String ipStr = ReUtil.get(PatternPool.IPV4, pingResult, 0);
        if(null == ipStr){
            ipStr = ReUtil.get(PatternPool.IPV6, pingResult, 0);
        }

        if(StringUtils.isNotEmpty(ipOrDomain)){
            strBuilder.append("--- ping  ")
                    .append(ipOrDomain)
                    .append(" (")
                    .append(ipStr)
                    .append(") ")
                    .append(" 统计信息 ---\n");
        }
        strBuilder.append(pingResult.split(PING_RESULT_STATISTIC_REGEX)[1]);

        return strBuilder.toString();
    }


    public static void main(String[] args) {
        String ubuntuPingResult = "PING baidu.com (39.156.69.79) 56(84) bytes of data.\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=1 ttl=49 time=40.2 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=2 ttl=49 time=40.1 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=3 ttl=49 time=40.2 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=4 ttl=49 time=40.4 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=5 ttl=49 time=40.1 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=6 ttl=49 time=40.2 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=7 ttl=49 time=40.2 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=8 ttl=49 time=40.2 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=9 ttl=49 time=40.2 ms\n" +
                                "64 bytes from 39.156.69.79: icmp_seq=10 ttl=49 time=40.2 ms\n" +
                                "\n" +
                                "--- baidu.com ping statistics ---\n" +
                                "10 packets transmitted, 10 received, 0% packet loss, time 9012ms\n" +
                                "rtt min/avg/max/mdev = 40.184/40.256/40.489/0.251 ms";

        String centOSPingResult = "PING baidu.com (39.156.69.79) 56(84) bytes of data.\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=1 ttl=52 time=32.6 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=2 ttl=52 time=32.3 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=3 ttl=52 time=31.8 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=4 ttl=52 time=32.2 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=5 ttl=52 time=32.5 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=6 ttl=52 time=32.3 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=7 ttl=52 time=32.2 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=8 ttl=52 time=32.4 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=9 ttl=52 time=32.3 ms\n" +
                                "64 bytes from 39.156.69.79 (39.156.69.79): icmp_seq=10 ttl=52 time=32.2 ms\n" +
                                "\n" +
                                "--- baidu.com ping statistics ---\n" +
                                "10 packets transmitted, 10 received, 0% packet loss, time 21ms\n" +
                                "rtt min/avg/max/mdev = 31.751/32.279/32.560/0.287 ms";

        String windowsPingResult = "\n" +
                                "正在 Ping baidu.com [39.156.69.79] 具有 32 字节的数据:\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=33ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=34ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=33ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=33ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=36ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=33ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=33ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=38ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=34ms TTL=53\n" +
                                "来自 39.156.69.79 的回复: 字节=32 时间=33ms TTL=53\n" +
                                "\n" +
                                "39.156.69.79 的 Ping 统计信息:\n" +
                                "    数据包: 已发送 = 10，已接收 = 10，丢失 = 0 (0% 丢失)，\n" +
                                "往返行程的估计时间(以毫秒为单位):\n" +
                                "    最短 = 33ms，最长 = 38ms，平均 = 34ms";

        if(OS_NAME.contains("Windows")){
            System.out.println(modifyPingResult("baidu.com", windowsPingResult));
        } else {
            System.out.println(modifyPingResult("baidu.com", centOSPingResult));
        }

    }

}
