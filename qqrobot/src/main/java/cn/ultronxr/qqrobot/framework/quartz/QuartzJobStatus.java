package cn.ultronxr.qqrobot.framework.quartz;

import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2022/03/06 17:27
 *
 * QuartzJob任务状态枚举封装
 */
public class QuartzJobStatus {

    // 状态枚举一：任务进行状态
    public enum ProcessStatus {
        /** 任务进行状态：停止的；对应数据库 quartz_job 字段 status=0 */
        STOPPED(0, "停止的"),
        /** 任务进行状态：激活的；对应数据库 quartz_job 字段 status=1 */
        ACTIVATED(1, "激活的"),
        /** 任务进行状态：暂停的；对应数据库 quartz_job 字段 status=2 */
        PAUSED(2, "暂停的");

        private final Integer status;
        private final String info;

        ProcessStatus(Integer status, String info) {
            this.status = status;
            this.info = info;
        }

        public Integer getStatus() {
            return this.status;
        }

        public String getInfo() {
            return this.info;
        }

    }

    // 状态枚举二：删除标记
    public enum DeleteStatus {
        /** 任务删除标记：未被删除；对应数据库 quartz_job 字段 is_del=0 */
        NOT_DELETED(0, "未被删除"),
        /** 任务删除标记：已被删除；对应数据库 quartz_job 字段 is_del=1 */
        DELETED(1, "已被删除");

        private final Integer status;
        private final String info;

        DeleteStatus(Integer status, String info) {
            this.status = status;
            this.info = info;
        }

        public Integer getStatus() {
            return this.status;
        }

        public String getInfo() {
            return this.info;
        }
    }

    public static @NotNull Boolean isStopped(Integer targetStatus) {
        return ProcessStatus.STOPPED.getStatus().equals(targetStatus);
    }

    public static @NotNull Boolean isActivated(Integer targetStatus) {
        return ProcessStatus.ACTIVATED.getStatus().equals(targetStatus);
    }

    public static @NotNull Boolean isPaused(Integer targetStatus) {
        return ProcessStatus.PAUSED.getStatus().equals(targetStatus);
    }

    public static @NotNull Boolean isNotDeleted(Integer targetStatus) {
        return DeleteStatus.NOT_DELETED.getStatus().equals(targetStatus);
    }

    public static @NotNull Boolean isDeleted(Integer targetStatus) {
        return DeleteStatus.DELETED.getStatus().equals(targetStatus);
    }

}
