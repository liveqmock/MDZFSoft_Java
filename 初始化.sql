alter table DEV_INFO
   drop constraint FK_DEV_INFO_REFERENCE_DEV_TYPE;

alter table DEV_INFO
   drop constraint FK_DEV_INFO_REFERENCE_DEV_FACT;

alter table FILE_UPLOAD_INFO
   drop constraint FK_FILE_UPL_REFERENCE_FILE_TYP;

alter table SYS_LOGIN
   drop constraint FK_SYS_LOGI_REFERENCE_SYS_CORP;

alter table SYS_LOGIN_ROLE
   drop constraint FK_SYS_LOGI_REFERENCE_SYS_ROLE;

alter table SYS_LOGIN_ROLE
   drop constraint FK_SYS_LOGI_REFERENCE_SYS_LOGI;

alter table SYS_ROLE_PERMISSION
   drop constraint FK_SYS_ROLE_REFERENCE_SYS_ROLE;

alter table SYS_ROLE_PERMISSION
   drop constraint FK_SYS_ROLE_REFERENCE_SYS_PERM;

drop table DEV_FACTURER_INFO cascade constraints;

drop table DEV_INFO cascade constraints;

drop table DEV_TYPE_INFO cascade constraints;

drop table FILE_TYPE_INFO cascade constraints;

drop table FILE_UPLOAD_INFO cascade constraints;

drop table NOTICE_INFO cascade constraints;

drop table NOTICE_READ_LOG cascade constraints;

drop table SYS_CORP cascade constraints;

drop table SYS_LOG cascade constraints;

drop table SYS_LOGIN cascade constraints;

drop table SYS_LOGIN_ROLE cascade constraints;

drop table SYS_PERMISSION cascade constraints;

drop table SYS_ROLE cascade constraints;

drop table SYS_ROLE_PERMISSION cascade constraints;

drop table SYS_SERVER_INFO cascade constraints;

drop sequence HIBERNATE_SEQUENCE;

drop sequence "devfacturerinfo_sequence";

drop sequence "devinfo_sequence";

drop sequence "devtypeinfo_sequence";

drop sequence "filetypeinfo_sequence";

drop sequence "fileuploadinfo_sequence";

drop sequence "noticeinfo_sequence";

drop sequence "noticereadinfo_sequence";

drop sequence "syscorp_sequence";

drop sequence "syslog_sequence";

drop sequence "syslogin_sequence";

drop sequence "sysloginrole_sequence";

drop sequence "syspermission_sequence";

drop sequence "sysrole_sequence";

drop sequence "sysrolepermission_sequence";

drop sequence "sysserverinfo_sequence";

create sequence HIBERNATE_SEQUENCE
increment by 1
start with 10000
 maxvalue 9999999999
 nominvalue
nocycle
noorder;

create sequence "devfacturerinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "devinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "devtypeinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "filetypeinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "fileuploadinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "noticeinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "noticereadinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "syscorp_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "syslog_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "syslogin_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "sysloginrole_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "syspermission_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "sysrole_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "sysrolepermission_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create sequence "sysserverinfo_sequence"
start with 1
 maxvalue 9999999999
 nominvalue;

create table DEV_FACTURER_INFO  (
   "dev_facturer_id"    NUMBER(10)                      not null,
   "dev_facturer_name"  VARCHAR2(100),
   "contact"            VARCHAR2(20),
   "phone"              VARCHAR2(21),
   "address"            VARCHAR2(200),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_DEV_FACTURER_INFO primary key ("dev_facturer_id")
);

comment on table DEV_FACTURER_INFO is
'设备厂商信息表';

comment on column DEV_FACTURER_INFO."dev_facturer_id" is
'设备ID';

comment on column DEV_FACTURER_INFO."dev_facturer_name" is
'设备编号';

comment on column DEV_FACTURER_INFO.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column DEV_FACTURER_INFO.CREATE_TIME is
'创建时间';

comment on column DEV_FACTURER_INFO.LAST_MODIFY_BY is
'最新一次修改者';

comment on column DEV_FACTURER_INFO.LAST_MODIFY_TIME is
'最新一次修改时间';

create table DEV_INFO  (
   "dev_id"             NUMBER(10)                      not null,
   "dev_type_id"        NUMBER(10),
   "dev_facturer_id"    NUMBER(10),
   "dev_no"             VARCHAR2(100),
   "dev_user_id"        NUMBER(10),
   "corp_id"            NUMBER(10),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_DEV_INFO primary key ("dev_id")
);

comment on table DEV_INFO is
'设备信息表';

comment on column DEV_INFO."dev_id" is
'设备ID';

comment on column DEV_INFO."dev_type_id" is
'设备类型ID';

comment on column DEV_INFO."dev_facturer_id" is
'设备ID';

comment on column DEV_INFO."dev_no" is
'设备编号';

comment on column DEV_INFO."dev_user_id" is
'设备使用人';

comment on column DEV_INFO."corp_id" is
'设备所属机构';

comment on column DEV_INFO.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column DEV_INFO.CREATE_TIME is
'创建时间';

comment on column DEV_INFO.LAST_MODIFY_BY is
'最新一次修改者';

comment on column DEV_INFO.LAST_MODIFY_TIME is
'最新一次修改时间';

create table DEV_TYPE_INFO  (
   "dev_type_id"        NUMBER(10)                      not null,
   "dev_type_name"      VARCHAR2(100),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_DEV_TYPE_INFO primary key ("dev_type_id")
);

comment on table DEV_TYPE_INFO is
'设备类型表';

comment on column DEV_TYPE_INFO."dev_type_id" is
'设备类型ID';

comment on column DEV_TYPE_INFO."dev_type_name" is
'设备类型名称';

comment on column DEV_TYPE_INFO.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column DEV_TYPE_INFO.CREATE_TIME is
'创建时间';

comment on column DEV_TYPE_INFO.LAST_MODIFY_BY is
'最新一次修改者';

comment on column DEV_TYPE_INFO.LAST_MODIFY_TIME is
'最新一次修改时间';

create table FILE_TYPE_INFO  (
   "type_id"            NUMBER(10)                      not null,
   "type_name"          VARCHAR2(50),
   "valid_time"         NUMBER(10),
   constraint PK_FILE_TYPE_INFO primary key ("type_id")
);

comment on table FILE_TYPE_INFO is
'文件上传类型信息表';

comment on column FILE_TYPE_INFO."type_id" is
'类型ID';

comment on column FILE_TYPE_INFO."type_name" is
'类型名称';

comment on column FILE_TYPE_INFO."valid_time" is
'有效时间（单位：天）';

create table FILE_UPLOAD_INFO  (
   FILE_ID              NUMBER(10)                      not null,
   "type_id"            NUMBER(10),
   FILE_UPLOAD_NAME     VARCHAR2(100),
   FILE_TYPE            NUMBER(2),
   FILE_CONTEXT_PATH    VARCHAR2(50),
   FILE_SAVE_PATH       VARCHAR2(200),
   FILE_PLAY_PATH       VARCHAR2(200),
   FILE_SHOW_PATH       VARCHAR2(200),
   FILE_IMPORTANCE      NUMBER(2),
   FILE_REMARK          VARCHAR2(2000),
   UPLOAD_USER_ID       NUMBER(10),
   UPLOAD_USER_IP       VARCHAR2(15),
   UPLOAD_CORP_ID       NUMBER(10),
   FILE_EDIT_ID         NUMBER(10),
   FILE_UPLOAD_TIME     VARCHAR2(14),
   FILE_CREATE_TIME     VARCHAR2(14),
   FILE_RECORD_TIME     VARCHAR2(14),
   POLICE_CODE          VARCHAR2(30),
   POLICE_DESC          VARCHAR2(4000),
   POLICE_TIME          VARCHAR2(14),
   POLICE_COST_TIME     NUMBER(10),
   FILE_STATUS          CHAR,
   FILE_STORAGE_ROOT    VARCHAR2(255),
   FILE_SIZE            NUMBER(19),
   FILE_TIME            VARCHAR2(255),
   DELETE_BY            NUMBER(10),
   DELETE_TIME          VARCHAR2(14),
   constraint PK_FILE_UPLOAD_INFO primary key (FILE_ID)
)
pctfree 10
initrans 1
storage
(
    initial 3072K
    minextents 1
    maxextents unlimited
)
tablespace USERS
logging
nocompress
 monitoring
 noparallel;

comment on table FILE_UPLOAD_INFO is
'文件上传信息表';

comment on column FILE_UPLOAD_INFO.FILE_ID is
'ID,主键';

comment on column FILE_UPLOAD_INFO."type_id" is
'类型ID';

comment on column FILE_UPLOAD_INFO.FILE_UPLOAD_NAME is
'上传文件名';

comment on column FILE_UPLOAD_INFO.FILE_TYPE is
'文件类型 1-图片 2-视频 3-音频';

comment on column FILE_UPLOAD_INFO.FILE_CONTEXT_PATH is
'文件访问上下文地址';

comment on column FILE_UPLOAD_INFO.FILE_SAVE_PATH is
'文件存放地址';

comment on column FILE_UPLOAD_INFO.FILE_PLAY_PATH is
'文件播放地址';

comment on column FILE_UPLOAD_INFO.FILE_SHOW_PATH is
'文件预览地址';

comment on column FILE_UPLOAD_INFO.FILE_IMPORTANCE is
'文件重要性 0-普通；1-重要';

comment on column FILE_UPLOAD_INFO.FILE_REMARK is
'文件备注说明';

comment on column FILE_UPLOAD_INFO.UPLOAD_USER_ID is
'上传人ID';

comment on column FILE_UPLOAD_INFO.UPLOAD_USER_IP is
'上传人IP地址';

comment on column FILE_UPLOAD_INFO.UPLOAD_CORP_ID is
'上传人部门id';

comment on column FILE_UPLOAD_INFO.FILE_EDIT_ID is
'采集人ID';

comment on column FILE_UPLOAD_INFO.FILE_UPLOAD_TIME is
'文件上传时间';

comment on column FILE_UPLOAD_INFO.FILE_CREATE_TIME is
'文件创建时间';

comment on column FILE_UPLOAD_INFO.FILE_RECORD_TIME is
'录制时间';

comment on column FILE_UPLOAD_INFO.POLICE_CODE is
'接警编号';

comment on column FILE_UPLOAD_INFO.POLICE_DESC is
'接警描述';

comment on column FILE_UPLOAD_INFO.POLICE_TIME is
'接警时间';

comment on column FILE_UPLOAD_INFO.POLICE_COST_TIME is
'路程耗费时间（单位：分钟）';

comment on column FILE_UPLOAD_INFO.FILE_STATUS is
'A-有效；U-无效；F-过期；C-未剪辑；I-剪辑中；W-待删除';

comment on column FILE_UPLOAD_INFO.DELETE_BY is
'删除人';

comment on column FILE_UPLOAD_INFO.DELETE_TIME is
'删除时间';

create table NOTICE_INFO  (
   NOTICE_ID            NUMBER(10)                      not null,
   NOTICE_TITLE         VARCHAR2(100),
   NOTICE_CONTENT       CLOB,
   NOTICE_TYPE          NUMBER(2),
   TARGET_IDS           VARCHAR2(500),
   CORP_ID              NUMBER(10),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_NOTICE_INFO primary key (NOTICE_ID)
);

comment on table NOTICE_INFO is
'存放平台公告信息';

comment on column NOTICE_INFO.NOTICE_ID is
'公告id,主键';

comment on column NOTICE_INFO.NOTICE_TITLE is
'公告标题';

comment on column NOTICE_INFO.NOTICE_CONTENT is
'公告内容';

comment on column NOTICE_INFO.NOTICE_TYPE is
'1,普通公告';

comment on column NOTICE_INFO.TARGET_IDS is
'公告发布对象ID列表，多个之间以逗号分开';

comment on column NOTICE_INFO.CORP_ID is
'公告发布单位ID';

comment on column NOTICE_INFO.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column NOTICE_INFO.CREATE_TIME is
'创建时间';

comment on column NOTICE_INFO.LAST_MODIFY_BY is
'最新一次修改者';

comment on column NOTICE_INFO.LAST_MODIFY_TIME is
'最新一次修改时间';

create table NOTICE_READ_LOG  (
   ID                   NUMBER(10)                      not null,
   NOTICE_ID            NUMBER(10),
   LOGIN_ID             NUMBER(10)                      not null,
   READ_TIME            VARCHAR2(14),
   constraint PK_NOTICE_READ_LOG primary key (ID)
);

comment on table NOTICE_READ_LOG is
'存放公告查看记录信息';

comment on column NOTICE_READ_LOG.ID is
'主键，序列';

comment on column NOTICE_READ_LOG.NOTICE_ID is
'公告ID';

comment on column NOTICE_READ_LOG.LOGIN_ID is
'公告查看人ID';

comment on column NOTICE_READ_LOG.READ_TIME is
'公告查看时间';

create table SYS_CORP  (
   CORP_ID              NUMBER(10)                      not null,
   PARENT_CORP_ID       NUMBER(10)                      not null,
   CORP_NAME            VARCHAR2(50)                    not null,
   CORP_TYPE            NUMBER(2)                       not null,
   FTP_IP               VARCHAR2(100),
   FTP_PORT             VARCHAR2(20),
   FTP_USER             VARCHAR2(20),
   FTP_PWD              VARCHAR2(20),
   FILE_ROOT_URL        VARCHAR2(200),
   CORP_DESC            VARCHAR2(200),
   TREE_CODE            VARCHAR2(200),
   STATUS               NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_CORP primary key (CORP_ID)
);

comment on table SYS_CORP is
'存放单位信息';

comment on column SYS_CORP.CORP_ID is
'ID，主键';

comment on column SYS_CORP.PARENT_CORP_ID is
'无上级单位则为空';

comment on column SYS_CORP.CORP_NAME is
'单位名称';

comment on column SYS_CORP.CORP_TYPE is
'1 - 单位
2 - 部门
';

comment on column SYS_CORP.FTP_IP is
'FTP地址';

comment on column SYS_CORP.FTP_PORT is
'FTP端口';

comment on column SYS_CORP.FTP_USER is
'FTP用户名';

comment on column SYS_CORP.FTP_PWD is
'FTP密码';

comment on column SYS_CORP.FILE_ROOT_URL is
'文件存储URL';

comment on column SYS_CORP.CORP_DESC is
'单位描述';

comment on column SYS_CORP.TREE_CODE is
'组织机构数编码
编码规则：上级机构树编码.本单位编号';

comment on column SYS_CORP.STATUS is
'状态
0－无效
1－有效';

comment on column SYS_CORP.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column SYS_CORP.CREATE_TIME is
'创建时间';

comment on column SYS_CORP.LAST_MODIFY_BY is
'最新一次修改者';

comment on column SYS_CORP.LAST_MODIFY_TIME is
'最新一次修改时间';

create table SYS_LOG  (
   LOG_ID               NUMBER(10)                      not null,
   OPER_USER_ID         NUMBER(10)                      not null,
   OPER_USER_NAME       VARCHAR2(50)                    not null,
   OPER_USER_CODE       VARCHAR2(50),
   OPER_CORP_ID         NUMBER(10),
   OPER_CORP_NAME       VARCHAR2(50),
   OPER_TYPE            NUMBER(2)                       not null,
   OPER_TIME            VARCHAR2(14)                    not null,
   OPER_IP              VARCHAR2(20)                    not null,
   OPER_DESC            VARCHAR2(200),
   SYSTEM_ID            NUMBER(2)                       not null,
   constraint PK_SYS_LOG primary key (LOG_ID)
);

comment on table SYS_LOG is
'系统日志表-记录系统中的增删改查登录等操作';

comment on column SYS_LOG.LOG_ID is
'日志序号';

comment on column SYS_LOG.OPER_USER_ID is
'操作者ID';

comment on column SYS_LOG.OPER_USER_NAME is
'操作者名称';

comment on column SYS_LOG.OPER_USER_CODE is
'操作者编号（警员编号）';

comment on column SYS_LOG.OPER_CORP_ID is
'操作单位ID';

comment on column SYS_LOG.OPER_CORP_NAME is
'操作者名称';

comment on column SYS_LOG.OPER_TYPE is
'操作类型0：登录 ，1：增加，2：删除，3：修改';

comment on column SYS_LOG.OPER_TIME is
'操作时间：（YYYYMMDDHHMISS）';

comment on column SYS_LOG.OPER_IP is
'操作者ip：192.168.1.1';

comment on column SYS_LOG.OPER_DESC is
'对当前操作做详细的描述';

comment on column SYS_LOG.SYSTEM_ID is
'日志所属系统';

create table SYS_LOGIN  (
   "login_id"           NUMBER(10)                      not null,
   "login_name"         VARCHAR2(50)                    not null,
   "login_pwd"          VARCHAR2(32)                    not null,
   "user_name"          VARCHAR2(20),
   "id_card"            varchar2(20),
   "user_type"          NUMBER(2)                       not null,
   "corp_id"            NUMBER(10),
   "system_id"          NUMBER(2)                       not null,
   "user_code"          VARCHAR2(50),
   "sex"                VARCHAR2(20),
   "tel"                VARCHAR2(21),
   "valid_tag"          CHAR(1),
   "valid_date"         VARCHAR2(14),
   "user_remark"        VARCHAR2(200),
   "record_corp_id"     NUMBER(10),
   "login_last_time"    VARCHAR2(14),
   "status"             NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_LOGIN primary key ("login_id")
);

comment on table SYS_LOGIN is
'定义登录帐号信息';

comment on column SYS_LOGIN."login_id" is
'ID，主键';

comment on column SYS_LOGIN."login_name" is
'登录用户名';

comment on column SYS_LOGIN."login_pwd" is
'登录密码';

comment on column SYS_LOGIN."user_name" is
' 用户真实姓名';

comment on column SYS_LOGIN."user_type" is
'0－超级管理员
1－普通用户';

comment on column SYS_LOGIN."corp_id" is
'用户所属单位编号';

comment on column SYS_LOGIN."system_id" is
'用户所属系统';

comment on column SYS_LOGIN."user_code" is
'用户编号（警员编号）';

comment on column SYS_LOGIN."sex" is
'性别';

comment on column SYS_LOGIN."tel" is
'联系电话';

comment on column SYS_LOGIN."valid_tag" is
'是否永久有效
Y：永久有效
N：有时间限制';

comment on column SYS_LOGIN."valid_date" is
'有效日期，当是否永久有效为N时此字段才有值';

comment on column SYS_LOGIN."user_remark" is
'用户描述';

comment on column SYS_LOGIN."record_corp_id" is
'用户的创建单位，默认谁创建谁管理';

comment on column SYS_LOGIN."login_last_time" is
'最后一次登录时间';

comment on column SYS_LOGIN."status" is
'状态
0－无效
1－有效';

comment on column SYS_LOGIN.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column SYS_LOGIN.CREATE_TIME is
'创建时间';

comment on column SYS_LOGIN.LAST_MODIFY_BY is
'最新一次修改者';

comment on column SYS_LOGIN.LAST_MODIFY_TIME is
'最新一次修改时间';

create table SYS_LOGIN_ROLE  (
   "id"                 NUMBER(10)                      not null,
   "role_id"            NUMBER(10),
   "login_id"           NUMBER(10),
   constraint PK_SYS_LOGIN_ROLE primary key ("id")
);

comment on table SYS_LOGIN_ROLE is
'定义登录者与角色的关联关系，一个登录者可以有一个或多个角色';

comment on column SYS_LOGIN_ROLE."id" is
'ID，主键';

comment on column SYS_LOGIN_ROLE."role_id" is
'ID，主键';

comment on column SYS_LOGIN_ROLE."login_id" is
'ID，主键';

create table SYS_PERMISSION  (
   "permission_id"      NUMBER(10)                      not null,
   "permission_name"    VARCHAR2(50)                    not null,
   "permission_type"    NUMBER(2)                       not null,
   "permission_url"     VARCHAR2(250),
   "permission_sort"    NUMBER(10)                      not null,
   "parent_permission_id" NUMBER(10),
   "system_id"          NUMBER(2)                       not null,
   "permission_ico"     VARCHAR2(100),
   "tree_code"          varchar(200),
   "status"             NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_PERMISSION primary key ("permission_id")
);

comment on column SYS_PERMISSION."permission_id" is
'ID，主键';

comment on column SYS_PERMISSION."permission_name" is
'权限名称';

comment on column SYS_PERMISSION."permission_type" is
'权限类型
1－菜单
2－操作';

comment on column SYS_PERMISSION."permission_url" is
'当权限类型为菜单时，该处表示菜单的URL
当权限类型为操作时，该处表示操作对应的按钮名称';

comment on column SYS_PERMISSION."permission_sort" is
'树形展现的排列顺序，类似
1 
   11
   12
2
   21
   22';

comment on column SYS_PERMISSION."parent_permission_id" is
'上级权限ID';

comment on column SYS_PERMISSION."system_id" is
'该菜单的所属系统';

comment on column SYS_PERMISSION."permission_ico" is
'权限图标';

comment on column SYS_PERMISSION."tree_code" is
'树编码
编码规则：上级权限树编码.本权限编号';

comment on column SYS_PERMISSION."status" is
'状态
0－无效
1－有效';

comment on column SYS_PERMISSION.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column SYS_PERMISSION.CREATE_TIME is
'创建时间';

comment on column SYS_PERMISSION.LAST_MODIFY_BY is
'最新一次修改者';

comment on column SYS_PERMISSION.LAST_MODIFY_TIME is
'最新一次修改时间';

create table SYS_ROLE  (
   "role_id"            NUMBER(10)                      not null,
   "role_name"          VARCHAR2(50)                    not null,
   "role_desc"          VARCHAR2(200),
   "corp_id"            NUMBER(10)                      not null,
   "system_id"          NUMBER(2)                       not null,
   "status"             NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_ROLE primary key ("role_id")
);

comment on table SYS_ROLE is
'定义系统角色信息，角色是一组权限的集合';

comment on column SYS_ROLE."role_id" is
'ID，主键';

comment on column SYS_ROLE."role_name" is
'角色名称';

comment on column SYS_ROLE."role_desc" is
'角色描述';

comment on column SYS_ROLE."corp_id" is
'角色所属单位编号';

comment on column SYS_ROLE."system_id" is
'角色所属系统
';

comment on column SYS_ROLE."status" is
'状态
0－无效
1－有效';

comment on column SYS_ROLE.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column SYS_ROLE.CREATE_TIME is
'创建时间';

comment on column SYS_ROLE.LAST_MODIFY_BY is
'最新一次修改者';

comment on column SYS_ROLE.LAST_MODIFY_TIME is
'最新一次修改时间';

create table SYS_ROLE_PERMISSION  (
   "id"                 NUMBER(10)                      not null,
   "permission_id"      NUMBER(10),
   "role_id"            NUMBER(10),
   constraint PK_SYS_ROLE_PERMISSION primary key ("id")
);

comment on table SYS_ROLE_PERMISSION is
'定义角色与权限的关联关系，一个角色可以有一个或多个权限';

comment on column SYS_ROLE_PERMISSION."id" is
'ID，主键';

comment on column SYS_ROLE_PERMISSION."permission_id" is
'菜单ID，主键';

comment on column SYS_ROLE_PERMISSION."role_id" is
'ID，主键';

create table SYS_SERVER_INFO  (
   SERVERINFO_ID        NUMBER(10)                      not null,
   RATIO_CPU            NUMBER(3),
   RATIO_MEMORY         NUMBER(3),
   USE_MEMORY           VARCHAR2(20),
   RATIO_HARDDISK       NUMBER(3),
   USE_HARDDISK         VARCHAR2(20),
   LETTER               CHAR,
   CREATE_TIME          VARCHAR2(14),
   SAVE_IP              VARCHAR2(15),
   constraint PK_SYS_SERVER_INFO primary key (SERVERINFO_ID)
)
pctfree 10
initrans 1
storage
(
    initial 64K
    minextents 1
    maxextents unlimited
)
tablespace USERS
logging
nocompress
 monitoring
 noparallel;

comment on table SYS_SERVER_INFO is
'服务器状况';

comment on column SYS_SERVER_INFO.SERVERINFO_ID is
'ID,主键';

comment on column SYS_SERVER_INFO.RATIO_CPU is
'CPU占用率';

comment on column SYS_SERVER_INFO.RATIO_MEMORY is
'内存占用率';

comment on column SYS_SERVER_INFO.USE_MEMORY is
'内存使用（单位：kb）';

comment on column SYS_SERVER_INFO.RATIO_HARDDISK is
'硬盘占用率';

comment on column SYS_SERVER_INFO.USE_HARDDISK is
'硬盘使用（单位：gb）';

comment on column SYS_SERVER_INFO.LETTER is
'硬盘盘符';

comment on column SYS_SERVER_INFO.CREATE_TIME is
'记录时间';

comment on column SYS_SERVER_INFO.SAVE_IP is
'服务器IP';

alter table DEV_INFO
   add constraint FK_DEV_INFO_REFERENCE_DEV_TYPE foreign key ("dev_type_id")
      references DEV_TYPE_INFO ("dev_type_id");

alter table DEV_INFO
   add constraint FK_DEV_INFO_REFERENCE_DEV_FACT foreign key ("dev_facturer_id")
      references DEV_FACTURER_INFO ("dev_facturer_id");

alter table FILE_UPLOAD_INFO
   add constraint FK_FILE_UPL_REFERENCE_FILE_TYP foreign key ("type_id")
      references FILE_TYPE_INFO ("type_id");

alter table SYS_LOGIN
   add constraint FK_SYS_LOGI_REFERENCE_SYS_CORP foreign key ("corp_id")
      references SYS_CORP (CORP_ID);

alter table SYS_LOGIN_ROLE
   add constraint FK_SYS_LOGI_REFERENCE_SYS_ROLE foreign key ("role_id")
      references SYS_ROLE ("role_id");

alter table SYS_LOGIN_ROLE
   add constraint FK_SYS_LOGI_REFERENCE_SYS_LOGI foreign key ("login_id")
      references SYS_LOGIN ("login_id");

alter table SYS_ROLE_PERMISSION
   add constraint FK_SYS_ROLE_REFERENCE_SYS_ROLE foreign key ("role_id")
      references SYS_ROLE ("role_id");

alter table SYS_ROLE_PERMISSION
   add constraint FK_SYS_ROLE_REFERENCE_SYS_PERM foreign key ("permission_id")
      references SYS_PERMISSION ("permission_id");

