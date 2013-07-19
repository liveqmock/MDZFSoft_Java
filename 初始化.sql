alter table DEV_INFO
   drop constraint FK_DEV_INFO_REFERENCE_DEV_TYPE;

alter table DEV_INFO
   drop constraint FK_DEV_INFO_REFERENCE_DEV_FACT;

alter table FILE_UPLOAD_INFO
   drop constraint FK_FILE_UPL_REFERENCE_FILE_TYP;

alter table SYS_CORP
   drop constraint FK_SYS_CORP_REFERENCE_SYS_FTP;

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

drop table SYS_FTP cascade constraints;

drop table SYS_LOG cascade constraints;

drop table SYS_LOGIN cascade constraints;

drop table SYS_LOGIN_ROLE cascade constraints;

drop table SYS_PERMISSION cascade constraints;

drop table SYS_ROLE cascade constraints;

drop table SYS_ROLE_PERMISSION cascade constraints;

drop table SYS_SERVER_INFO cascade constraints;

drop table SYS_CONFIG cascade constraints;

drop sequence DEVFACTURERINFO_SEQUENCE;

drop sequence DEVINFO_SEQUENCE;

drop sequence DEVTYPEINFO_SEQUENCE;

drop sequence FILETYPEINFO_SEQUENCE;

drop sequence FILEUPLOADINFO_SEQUENCE;

drop sequence HIBERNATE_SEQUENCE;

drop sequence NOTICEINFO_SEQUENCE;

drop sequence NOTICEREADINFO_SEQUENCE;

drop sequence SYSCORP_SEQUENCE;

drop sequence SYSFTP_SEQUENCE;

drop sequence SYSLOGINROLE_SEQUENCE;

drop sequence SYSLOGIN_SEQUENCE;

drop sequence SYSLOG_SEQUENCE;

drop sequence SYSPERMISSION_SEQUENCE;

drop sequence SYSROLEPERMISSION_SEQUENCE;

drop sequence SYSROLE_SEQUENCE;

drop sequence SYSSERVERINFO_SEQUENCE;


create table DEV_FACTURER_INFO  (
   DEV_FACTURER_ID    NUMBER(10)                      not null,
   DEV_FACTURER_NAME  VARCHAR2(100),
   CONTACT            VARCHAR2(20),
   PHONE              VARCHAR2(21),
   ADDRESS            VARCHAR2(200),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_DEV_FACTURER_INFO primary key (DEV_FACTURER_ID)
);

comment on table DEV_FACTURER_INFO is
'设备厂商信息表';

comment on column DEV_FACTURER_INFO.DEV_FACTURER_ID is
'设备ID';

comment on column DEV_FACTURER_INFO.DEV_FACTURER_NAME is
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

----------------------------------------------------------------------------
create table DEV_TYPE_INFO  (
   DEV_TYPE_ID        NUMBER(10)                      NOT NULL,
   DEV_TYPE_NAME      VARCHAR2(100),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_DEV_TYPE_INFO primary key (DEV_TYPE_ID)
);

comment on table DEV_TYPE_INFO is
'设备类型表';

comment on column DEV_TYPE_INFO.DEV_TYPE_ID is
'设备类型ID';

comment on column DEV_TYPE_INFO.DEV_TYPE_NAME is
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

-----------------------------------------------------------------------
create table DEV_INFO  (
   DEV_ID             NUMBER(10)                      NOT NULL,
   DEV_TYPE_ID        NUMBER(10),
   DEV_FACTURER_ID    NUMBER(10),
   DEV_NO             VARCHAR2(100),
   DEV_USER_ID        NUMBER(10),
   CORP_ID            NUMBER(10),
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_DEV_INFO primary key (dev_id)
);

comment on table DEV_INFO is
'设备信息表';

comment on column DEV_INFO.DEV_ID is
'设备ID';

comment on column DEV_INFO.DEV_TYPE_ID is
'设备类型ID';

comment on column DEV_INFO.DEV_FACTURER_ID is
'设备ID';

comment on column DEV_INFO.DEV_NO is
'设备编号';

comment on column DEV_INFO.DEV_USER_ID is
'设备使用人';

comment on column DEV_INFO.CORP_ID is
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

----------------------------------------------------
create table FILE_TYPE_INFO  (
   TYPE_ID            NUMBER(10)                      NOT NULL,
   TYPE_NAME          VARCHAR2(50),
   VALID_TIME         NUMBER(10),
   constraint PK_FILE_TYPE_INFO primary key (TYPE_ID)
);

comment on table FILE_TYPE_INFO is
'文件上传类型信息表';

comment on column FILE_TYPE_INFO.TYPE_ID is
'类型ID';

comment on column FILE_TYPE_INFO.TYPE_NAME is
'类型名称';

comment on column FILE_TYPE_INFO.VALID_TIME is
'有效时间（单位：天）';

-----------------------------------------------------
create table FILE_UPLOAD_INFO  (
   FILE_ID              NUMBER(10)                      not null,
   TYPE_ID            NUMBER(10),
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
);

comment on table FILE_UPLOAD_INFO is
'文件上传信息表';

comment on column FILE_UPLOAD_INFO.FILE_ID is
'ID,主键';

comment on column FILE_UPLOAD_INFO.TYPE_ID is
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

----------------------------------------------------------------
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

--------------------------------------------------------------
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

create table SYS_FTP  (
   FTP_ID               NUMBER(10)                      not null,
   SERVER_NAME		    VARCHAR2(20),
   FTP_IP               VARCHAR2(100),
   FTP_PORT             VARCHAR2(20),
   FTP_USER             VARCHAR2(20),
   FTP_PWD              VARCHAR2(20),
   FILE_ROOT_URL        VARCHAR2(200),
   FTP_DESC             VARCHAR2(200),
   STATUS               NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_FTP primary key (FTP_ID)
);

comment on table SYS_FTP is
'FTP信息';

comment on column SYS_FTP.FTP_ID is
'ID，主键';

comment on column SYS_FTP.FTP_IP is
'FTP地址';

comment on column SYS_FTP.FTP_PORT is
'FTP端口';

comment on column SYS_FTP.FTP_USER is
'FTP用户名';

comment on column SYS_FTP.FTP_PWD is
'FTP密码';

comment on column SYS_FTP.FILE_ROOT_URL is
'文件存储URL';

comment on column SYS_FTP.FTP_DESC is
'单位描述';

comment on column SYS_FTP.STATUS is
'状态
0－无效
1－有效';

comment on column SYS_FTP.CREATE_BY is
'创建人
系统自动创建则此处为system';

comment on column SYS_FTP.CREATE_TIME is
'创建时间';

comment on column SYS_FTP.LAST_MODIFY_BY is
'最新一次修改者';

comment on column SYS_FTP.LAST_MODIFY_TIME is
'最后一次修改时间';

--------------------------------------------------------------
create table SYS_CORP  (
   CORP_ID              NUMBER(10)                      not null,
   ID                   NUMBER(10),
   PARENT_CORP_ID       NUMBER(10)                      not null,
   CORP_NAME            VARCHAR2(50)                    not null,
   CORP_TYPE            NUMBER(2)                       not null,
   FTP_ID               NUMBER(10),
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

comment on column SYS_CORP.ID is
'ID，主键';

comment on column SYS_CORP.PARENT_CORP_ID is
'无上级单位则为空';

comment on column SYS_CORP.CORP_NAME is
'单位名称';

comment on column SYS_CORP.CORP_TYPE is
'1 - 单位
2 - 部门
';

comment on column SYS_CORP.FTP_ID is
'FTP地址';

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


-------------------------------------------------------
create table SYS_LOGIN  (
   LOGIN_ID           NUMBER(10)                      NOT NULL,
   LOGIN_NAME         VARCHAR2(50)                    NOT NULL,
   LOGIN_PWD          VARCHAR2(32)                    NOT NULL,
   USER_NAME          VARCHAR2(20),
   ID_CARD            VARCHAR2(20),
   USER_TYPE          NUMBER(2)                       NOT NULL,
   CORP_ID            NUMBER(10),
   SYSTEM_ID          NUMBER(2)                       NOT NULL,
   USER_CODE          VARCHAR2(50),
   SEX                VARCHAR2(20),
   TEL                VARCHAR2(21),
   VALID_TAG          CHAR(1),
   VALID_DATE         VARCHAR2(14),
   USER_REMARK        VARCHAR2(200),
   RECORD_CORP_ID     NUMBER(10),
   LOGIN_LAST_TIME    VARCHAR2(14),
   STATUS             NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_LOGIN primary key (LOGIN_ID)
);

comment on table SYS_LOGIN is
'定义登录帐号信息';

comment on column SYS_LOGIN.LOGIN_ID is
'ID，主键';

comment on column SYS_LOGIN.LOGIN_NAME is
'登录用户名';

comment on column SYS_LOGIN.LOGIN_PWD is
'登录密码';

comment on column SYS_LOGIN.USER_NAME is
' 用户真实姓名';

comment on column SYS_LOGIN.USER_TYPE is
'0－超级管理员
1－普通用户';

comment on column SYS_LOGIN.CORP_ID is
'用户所属单位编号';

comment on column SYS_LOGIN.SYSTEM_ID is
'用户所属系统';

comment on column SYS_LOGIN.USER_CODE is
'用户编号（警员编号）';

comment on column SYS_LOGIN.SEX is
'性别';

comment on column SYS_LOGIN.TEL is
'联系电话';

comment on column SYS_LOGIN.VALID_TAG is
'是否永久有效
Y：永久有效
N：有时间限制';

comment on column SYS_LOGIN.VALID_DATE is
'有效日期，当是否永久有效为N时此字段才有值';

comment on column SYS_LOGIN.USER_REMARK is
'用户描述';

comment on column SYS_LOGIN.RECORD_CORP_ID is
'用户的创建单位，默认谁创建谁管理';

comment on column SYS_LOGIN.LOGIN_LAST_TIME is
'最后一次登录时间';

comment on column SYS_LOGIN.STATUS is
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

-----------------------------------------------------
create table SYS_LOGIN_ROLE  (
   ID                 NUMBER(10)                      NOT NULL,
   ROLE_ID            NUMBER(10),
   LOGIN_ID           NUMBER(10),
   constraint PK_SYS_LOGIN_ROLE primary key (ID)
);

comment on table SYS_LOGIN_ROLE is
'定义登录者与角色的关联关系，一个登录者可以有一个或多个角色';

comment on column SYS_LOGIN_ROLE.ID is
'ID，主键';

comment on column SYS_LOGIN_ROLE.ROLE_ID is
'ID，主键';

comment on column SYS_LOGIN_ROLE.LOGIN_ID is
'ID，主键';

---------------------------------------------------------
create table SYS_PERMISSION  (
   PERMISSION_ID      NUMBER(10)                      NOT NULL,
   PERMISSION_NAME    VARCHAR2(50)                    NOT NULL,
   PERMISSION_TYPE    NUMBER(2)                       NOT NULL,
   PERMISSION_URL     VARCHAR2(250),
   PERMISSION_SORT    NUMBER(10)                      NOT NULL,
   PARENT_PERMISSION_ID NUMBER(10),
   SYSTEM_ID          NUMBER(2)                       NOT NULL,
   PERMISSION_ICO     VARCHAR2(100),
   TREE_CODE          VARCHAR(200),
   STATUS             NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_PERMISSION primary key (PERMISSION_ID)
);

comment on column SYS_PERMISSION.PERMISSION_ID is
'ID，主键';

comment on column SYS_PERMISSION.PERMISSION_NAME is
'权限名称';

comment on column SYS_PERMISSION.PERMISSION_TYPE is
'权限类型
1－菜单
2－操作';

comment on column SYS_PERMISSION.PERMISSION_URL is
'当权限类型为菜单时，该处表示菜单的URL
当权限类型为操作时，该处表示操作对应的按钮名称';

comment on column SYS_PERMISSION.PERMISSION_SORT is
'树形展现的排列顺序，类似
1 
   11
   12
2
   21
   22';

comment on column SYS_PERMISSION.PARENT_PERMISSION_ID is
'上级权限ID';

comment on column SYS_PERMISSION.SYSTEM_ID is
'该菜单的所属系统';

comment on column SYS_PERMISSION.PERMISSION_ICO is
'权限图标';

comment on column SYS_PERMISSION.TREE_CODE is
'树编码
编码规则：上级权限树编码.本权限编号';

comment on column SYS_PERMISSION.STATUS is
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


-------------------------------------------------------
create table SYS_ROLE  (
   ROLE_ID            NUMBER(10)                      NOT NULL,
   ROLE_NAME          VARCHAR2(50)                    NOT NULL,
   ROLE_DESC          VARCHAR2(200),
   CORP_ID            NUMBER(10)                      NOT NULL,
   SYSTEM_ID          NUMBER(2)                       NOT NULL,
   STATUS             NUMBER(2)                       not null,
   CREATE_BY            VARCHAR2(50),
   CREATE_TIME          VARCHAR2(14),
   LAST_MODIFY_BY       VARCHAR2(50),
   LAST_MODIFY_TIME     VARCHAR2(14),
   constraint PK_SYS_ROLE primary key (ROLE_ID)
);

comment on table SYS_ROLE is
'定义系统角色信息，角色是一组权限的集合';

comment on column SYS_ROLE.ROLE_ID is
'ID，主键';

comment on column SYS_ROLE.ROLE_NAME is
'角色名称';

comment on column SYS_ROLE.ROLE_DESC is
'角色描述';

comment on column SYS_ROLE.CORP_ID is
'角色所属单位编号';

comment on column SYS_ROLE.SYSTEM_ID is
'角色所属系统
';

comment on column SYS_ROLE.STATUS is
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


-----------------------------------------------------------
create table SYS_ROLE_PERMISSION  (
   ID                 NUMBER(10)                      NOT NULL,
   PERMISSION_ID      NUMBER(10),
   ROLE_ID            NUMBER(10),
   constraint PK_SYS_ROLE_PERMISSION primary key (ID)
);

comment on table SYS_ROLE_PERMISSION is
'定义角色与权限的关联关系，一个角色可以有一个或多个权限';

comment on column SYS_ROLE_PERMISSION.ID is
'ID，主键';

comment on column SYS_ROLE_PERMISSION.PERMISSION_ID is
'菜单ID，主键';

comment on column SYS_ROLE_PERMISSION.ROLE_ID is
'ID，主键';

-------------------------------------------------------------
create table SYS_CONFIG  (
   ID                   NUMBER(10)                      not null,
   PARENT_ID            NUMBER(10),
   CODE                 VARCHAR(20),
   VAL                  VARCHAR(500),
   constraint PK_SYS_CONFIG primary key (ID)
);

comment on column SYS_CONFIG.ID is
'ID，主键';

comment on column SYS_CONFIG.PARENT_ID is
'父类编号';

comment on column SYS_CONFIG.CODE is
'属性编码';

comment on column SYS_CONFIG.VAL is
'属性值';


-------------------------------------------------------------
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


----------------------------------------------------
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
);

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

------------------------------------------------------
alter table DEV_INFO
   add constraint FK_DEV_INFO_REFERENCE_DEV_TYPE foreign key (DEV_TYPE_ID)
      references DEV_TYPE_INFO (DEV_TYPE_ID);

alter table DEV_INFO
   add constraint FK_DEV_INFO_REFERENCE_DEV_FACT foreign key (DEV_FACTURER_ID)
      references DEV_FACTURER_INFO (DEV_FACTURER_ID);

alter table FILE_UPLOAD_INFO
   add constraint FK_FILE_UPL_REFERENCE_FILE_TYP foreign key (TYPE_ID)
      references FILE_TYPE_INFO (TYPE_ID);

alter table SYS_LOGIN
   add constraint FK_SYS_LOGI_REFERENCE_SYS_CORP foreign key (CORP_ID)
      references SYS_CORP (CORP_ID);

alter table SYS_LOGIN_ROLE
   add constraint FK_SYS_LOGI_REFERENCE_SYS_ROLE foreign key (ROLE_ID)
      references SYS_ROLE (ROLE_ID);

alter table SYS_LOGIN_ROLE
   add constraint FK_SYS_LOGI_REFERENCE_SYS_LOGI foreign key (LOGIN_ID)
      references SYS_LOGIN (LOGIN_ID);

alter table SYS_ROLE_PERMISSION
   add constraint FK_SYS_ROLE_REFERENCE_SYS_ROLE foreign key (ROLE_ID)
      references SYS_ROLE (ROLE_ID);

alter table SYS_ROLE_PERMISSION
   add constraint FK_SYS_ROLE_REFERENCE_SYS_PERM foreign key (PERMISSION_ID)
      references SYS_PERMISSION (PERMISSION_ID);

-----------------------------------------------------
create sequence HIBERNATE_SEQUENCE
increment by 1
start with 10000
 maxvalue 9999999999
 nominvalue
nocycle
noorder;

create sequence DEVFACTURERINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence DEVINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence DEVTYPEINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence FILETYPEINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence FILEUPLOADINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence NOTICEINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence NOTICEREADINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence SYSCORP_SEQUENCE
increment by 1
start with 0
 maxvalue 9999999999
 minvalue 0
 nocycle
 cache 20;

create sequence SYSLOG_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 minvalue 0
 nocycle
 cache 20;

create sequence SYSLOGIN_SEQUENCE
increment by 1
start with 0
 maxvalue 9999999999
 minvalue 0
 nocycle
 cache 20;

create sequence SYSLOGINROLE_SEQUENCE
increment by 1
start with 0
 maxvalue 9999999999
 minvalue 0
 nocycle
 cache 20;

create sequence SYSPERMISSION_SEQUENCE
increment by 1
start with 0
 maxvalue 9999999999
 minvalue 0
 nocycle
 cache 20;

create sequence SYSROLE_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence SYSROLEPERMISSION_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence SYSSERVERINFO_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

create sequence SYSFTP_SEQUENCE
increment by 1
start with 1
 maxvalue 9999999999
 nominvalue
 nocycle
 cache 20;

------------------------------------------------------------------
insert into sys_corp(CORP_ID,CORP_NAME,CORP_TYPE,PARENT_CORP_ID,STATUS,TREE_CODE)
values( SYSCORP_SEQUENCE.NEXTVAL,'XXX公安局','0','0','1',',1,');
commit;

insert into sys_login(LOGIN_ID,LOGIN_NAME,LOGIN_PWD,USER_NAME,USER_TYPE,CORP_ID,SYSTEM_ID,
		USER_CODE,STATUS)
values( SYSLOGIN_SEQUENCE.NEXTVAL ,'admin','96e79218965eb72c92a549dd5a330112','超级管理员','0','1','1','000000','1');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values(SYSPERMISSION_SEQUENCE.NEXTVAL, '公告管理','1','','1','0','1','1',',1,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'文件管理','1','','2','0','1','1',',2,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'设备管理','1','','3','0','1','1',',3,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'统计查询','1','','4','0','1','1',',4,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'组织管理','1','','5','0','1','1',',5,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'系统管理','1','','6','0','1','1',',6,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'公告管理','1','/noticeMgr','7','1','1','1',',1,7,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'文件上传','1','/pages/file_mgr/file_upload/index.jsp','11','2','1','1',',2,11,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'文件查看','1','/fileMgr','12','2','1','1',',2,12,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'文件删除','1','/fileMgr/deleteList','12','2','1','1',',2,12,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'文件类型管理','1','/fileTypeMgr','14','2','1','1',',2,14,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'设备管理','1','/devMgr','8','3','1','1',',3,8,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'设备类型管理','1','/devTypeMgr','10','3','1','1',',3,10,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'设备厂商管理','1','/devFacturerMgr','9','3','1','1',',3,9,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'部门统计','1','/reportStatistics/corpStatistics','15','4','1','1',',4,15,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'警员统计','1','/reportStatistics/userStatistics','16','4','1','1',',4,16,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'部门管理','1','/corpMgr','19','5','1','1',',5,19,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'角色管理','1','/roleMgr','20','5','1','1',',5,20,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'用户管理','1','/userMgr','21','5','1','1',',5,21,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'操作日志','1','/logMgr','17','6','1','1',',6,17,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'系统监控','1','/sysMonitor','18','6','1','1',',6,18,');
commit;

insert into SYS_PERMISSION(PERMISSION_ID,PERMISSION_NAME,PERMISSION_TYPE,PERMISSION_URL,PERMISSION_SORT,PARENT_PERMISSION_ID,SYSTEM_ID,STATUS,TREE_CODE)
values( SYSPERMISSION_SEQUENCE.NEXTVAL ,'文件服务器管理','1','/ftpMgr','19','6','1','1',',6,19,');
commit;

insert into FILE_TYPE_INFO(TYPE_ID,TYPE_NAME,VALID_TIME)
values( '0' ,'未分类','30');
commit;

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
values('1','0','license','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
values('2','1','Product.name','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
values('3','1','Product.version','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
VALUES('4','1','License.type','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
VALUES('5','1','License.expiry','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
VALUES('6','1','License.ips','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
VALUES('7','1','Server.macaddress','');

insert into SYS_CONFIG(ID,PARENT_ID,CODE,VAL)
VALUES('8','1','signature','');

commit;

