# sql语句摘录

## qqrobot - 群/群成员（发言）活跃统计

### 建库

```mysql
CREATE DATABASE `robot` DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
```

### 建表

```mysql
CREATE TABLE qq_group(
    `group_id` varchar(25) NOT NULL COMMENT 'QQ群号',
    `is_statistic` int(1) NOT NULL DEFAULT 1 COMMENT '是否统计',
    PRIMARY KEY(`group_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'QQ群表';

CREATE TABLE qq_group_member(
    `group_id` varchar(25) NOT NULL COMMENT 'QQ群号',
    `member_id` varchar(25) NOT NULL COMMENT '群成员QQ号',
    `is_statistic` int(1) NOT NULL DEFAULT 1 COMMENT '是否统计',
    PRIMARY KEY(`group_id`, `member_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'QQ群成员表';

CREATE TABLE qq_group_member_chat(
    `group_id` varchar(25) NOT NULL COMMENT 'QQ群号',
    `member_id` varchar(25) NOT NULL COMMENT '群成员QQ号',
    `chat_time` varchar(25) NOT NULL DEFAULT '' COMMENT '发言时间',
    #`chat_time` varchar(25) NOT NULL DEFAULT (date_format(now(), '%y-%m-%d %H:00:00')) COMMENT '发言时间',
    `chat_num` int(5) NOT NULL DEFAULT 1 COMMENT '发言数量',
    `is_statistic` int(1) NOT NULL DEFAULT 1 COMMENT '是否统计'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'QQ群成员发言记录表';
```

### 查询

```mysql
use robot;

# 某段时间内某个群的活跃程度
select 
    sum(chat_num) chat_sum
from 
    qq_group_member_chat
where 
    group_id = ''
    and chat_time > '' 
    and chat_time <= ''
    and is_statistic = 1
;

# 某段时间内所有群的活跃程度/排名
select 
    group_id, sum(chat_num) chat_sum
from 
    qq_group_member_chat
where 
    chat_time > '2021-05-03 00' 
    and chat_time <= '2021-05-03 24'
    and is_statistic = 1
group by 
    group_id
order by 
    chat_sum desc
;

# 某段时间内某个群的某位群成员的活跃程度
select 
    sum(chat_num) chat_sum
from 
    qq_group_member_chat
where 
    group_id = '' 
    and member_id = '' 
    and chat_time > '' 
    and chat_time <= ''
    and is_statistic = 1
;

# 某段时间内某个群的所有群成员活跃程度/排名
select 
    member_id, sum(chat_num) chat_sum
from 
    qq_group_member_chat
where 
    group_id = '222' 
    and chat_time > '2021-05-04 00' 
    and chat_time <= '2021-05-04 24'
    and is_statistic = 1
group by 
    member_id
order by 
    chat_sum desc
;
```

## qqrobot - Quartz定时任务

### 建表

```mysql
CREATE TABLE quartz_job(
    `job_id` int NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `job_name` varchar(25) NOT NULL COMMENT '任务名称',
    `job_group` varchar(25) NOT NULL COMMENT '任务分组',
    `job_description` varchar(255) DEFAULT NULL COMMENT '任务描述',
    `job_class` varchar(255) DEFAULT NULL COMMENT '全限定类名，任务执行的类方法',
    `job_cron` varchar(25) NOT NULL COMMENT 'cron表达式，用于设置任务循环时间',
    `pause_time_limit` datetime DEFAULT NULL COMMENT '暂停时间期限，当任务暂停到此期限之后，任务自动被重新激活',
    `status` int(1) NOT NULL DEFAULT 1 COMMENT '任务状态，0停止、1激活、2暂停',
    `is_del` int(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0未删除、1已删除',
    `attach_files` varchar(255) DEFAULT NULL COMMENT '任务附件路径',
    `sms_contact` varchar(25) DEFAULT NULL COMMENT '手机短信联系方式，为NULL则不激活',
    `email_contact` varchar(50) DEFAULT NULL COMMENT '邮件联系方式，为NULL则不激活',
    `create_time` datetime NOT NULL DEFAULT now() COMMENT '创建时间',
    `create_user` varchar(25) NOT NULL COMMENT '创建者',
    `update_time` datetime NOT NULL DEFAULT now() COMMENT '最后更新时间',
    `update_user` varchar(25) NOT NULL COMMENT '最后更新者',
    PRIMARY KEY(`job_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Quartz任务表';

CREATE TABLE quartz_job_target(
    `job_id` int NOT NULL COMMENT '任务ID',
    `group_id` varchar(25) DEFAULT NULL COMMENT '目标群ID',
    `qq_id` varchar(25) DEFAULT NULL COMMENT 'QQ号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '“Quartz任务目标群/群成员”表';
```



