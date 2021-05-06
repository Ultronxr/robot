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