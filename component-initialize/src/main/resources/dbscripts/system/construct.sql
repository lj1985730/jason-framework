
SET FOREIGN_KEY_CHECKS=0;

-- system tables ---------------------------------------------------------------------------------------------------------

-- ----------------------------
-- Table structure for SYS_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS SYS_CONFIG;
CREATE TABLE SYS_CONFIG (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  CFG_KEY                 VARCHAR(200) NOT NULL COMMENT '配置KEY',
  CFG_VALUE               VARCHAR(500) COMMENT '配置VALUE',
  ENABLED                  CHAR(1) COMMENT '是否生效',
  EDITABLE                CHAR(1) COMMENT '是否可在系统界面修改',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Table structure for SYS_COMBO_DATA
-- ----------------------------
DROP TABLE IF EXISTS SYS_COMBO_DATA;
CREATE TABLE SYS_COMBO_DATA (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  BUSINESS_KEY            VARCHAR(200) NOT NULL COMMENT '业务主键',
  CONTENT                 TEXT NOT NULL COMMENT '内容',
  CONDITIONS              TEXT COMMENT '过滤条件',
  ORDERBY                 TEXT COMMENT '排序',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下拉菜单字典表';

-- ----------------------------
-- Table structure for SYS_DICT
-- ----------------------------
DROP TABLE IF EXISTS SYS_DICT;
CREATE TABLE SYS_DICT (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(200) NOT NULL COMMENT '名称',
  CODE                    INTEGER(9) NOT NULL COMMENT '编号',
  PARENT_CODE                    INTEGER(9) COMMENT '父级编号',
  CATEGORY_KEY            VARCHAR(200) COMMENT '字典分类',
  SORT_NUMBER             INTEGER(9) COMMENT '排序',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统字典表';

-- ----------------------------
-- Table structure for SYS_FOLDER
-- ----------------------------
DROP TABLE IF EXISTS SYS_FOLDER;
CREATE TABLE SYS_FOLDER (
  ID                        CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(200) NOT NULL COMMENT '部门名称',
  PARENT_ID               VARCHAR(36) COMMENT '父节点',
  SORT_NUMBER             INTEGER(9) COMMENT '排序号',
  ENABLED                  CHAR(1) COMMENT '是否使用',
  CODE            VARCHAR(500) COMMENT '编码',
  IS_SYSTEM             CHAR(1) NOT NULL COMMENT '是否系统文件夹,系统级别文件夹无法修改',
  PERMISSION              VARCHAR(500) COMMENT '权限名称',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID    VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统文件夹(网盘)';

-- ----------------------------
-- Table structure for SYS_FILE
-- ----------------------------
DROP TABLE IF EXISTS SYS_FILE;
CREATE TABLE SYS_FILE (
  ID                          CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(500) COMMENT '存储名称',
  EXTENSION            VARCHAR(50) COMMENT '扩展名',
  MD5                       VARCHAR(50) COMMENT 'MD5',
  SIZE                        INTEGER(20) COMMENT '文件大小（B）',
  ROOT_ADDRESS      VARCHAR(2000) COMMENT '保存根地址',
  RELATIVE_ADDRESS VARCHAR(2000) COMMENT '保存相对地址',
  UPLOAD_MODE         CHAR(1) COMMENT '上传方式，1 系统导入；2 外部程序导入；3 数据库初始化',
  PERMISSION              VARCHAR(500) COMMENT '权限名称',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID    VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统文件表';

-- ----------------------------
-- Table structure for SYS_FOLDER_FILE
-- ----------------------------
DROP TABLE IF EXISTS SYS_FOLDER_FILE;
CREATE TABLE SYS_FOLDER_FILE (
  ID                          CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  FILE_ID             CHAR(36) COMMENT '文件ID',
  FILE_NAME           VARCHAR(500) COMMENT '文件名称',
  BUSINESS_DATA_ID         CHAR(36) COMMENT '业务数据ID',
  BUSINESS_KEY       VARCHAR(500) COMMENT '业务键',
  FOLDER_ID           CHAR(36) COMMENT '文件夹ID',
  PERMISSION              VARCHAR(500) COMMENT '权限名称',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID    VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件夹与文件关系表';