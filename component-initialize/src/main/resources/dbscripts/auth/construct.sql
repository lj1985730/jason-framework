
SET FOREIGN_KEY_CHECKS=0;

-- authentication tables -----------------------------------------------------------------------------------------------
-- ----------------------------
-- Table structure for AUTH_TENANT
-- ----------------------------
DROP TABLE IF EXISTS AUTH_TENANT;
CREATE TABLE AUTH_TENANT (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(200) NOT NULL COMMENT '租户名称',
  PARENT_ID               VARCHAR(36) COMMENT '父节点',
  SORT_NUMBER             INTEGER(9) COMMENT '排序号',
  ENABLED                  CHAR(1) COMMENT '是否使用',
  EN_NAME                 VARCHAR(100) COMMENT '英文名称',
  SHORT_NAME              VARCHAR(100) COMMENT '简称',
  USCC                    CHAR(18) COMMENT '统一社会信用代码',
  CODE                    VARCHAR(100) COMMENT '系统内代码',
  CATEGORY                VARCHAR(200) COMMENT '类型，可关联字典表',
  LOCATION                VARCHAR(200) COMMENT '住所',
  LEGAL_REPRESENTATIVE    VARCHAR(200) COMMENT '法定代表人',
  REGISTERED_CAPITAL      DECIMAL(15,1) COMMENT '注册资本',
  ESTABLISHMENT_DATE      DATE COMMENT '成立日期',
  BUSINESS_TERM_START     DATE COMMENT '营业期限开始日期',
  BUSINESS_TERM_END       DATE COMMENT '营业期限截止日期',
  BUSINESS_SCOPE          VARCHAR(1000) COMMENT '经营范围',
  LINKMAN                 VARCHAR(100) COMMENT '联系人',
  FAX                     VARCHAR(100) COMMENT '传真',
  PHONE                   VARCHAR(100) COMMENT '电话',
  EMAIL                   VARCHAR(100) COMMENT '电子邮件',
  LEVEL                   INTEGER(2) COMMENT '级别',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='租户信息表';

-- ----------------------------
-- Table structure for AUTH_DEPARTMENT
-- ----------------------------
DROP TABLE IF EXISTS AUTH_DEPARTMENT;
CREATE TABLE AUTH_DEPARTMENT (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(200) NOT NULL COMMENT '部门名称',
  PARENT_ID               VARCHAR(36) COMMENT '父节点',
  SORT_NUMBER             INTEGER(9) COMMENT '排序号',
  ENABLED                  CHAR(1) COMMENT '是否使用',
  EN_NAME                 VARCHAR(100) COMMENT '英文名称',
  CODE                    VARCHAR(100) COMMENT '部门编码',
  SHORT_NAME              VARCHAR(100) COMMENT '部门简称',
  CATEGORY                VARCHAR(200) COMMENT '部门类型，关联字典表',
  LINKMAN                 VARCHAR(100) COMMENT '联系人',
  FAX                     VARCHAR(100) COMMENT '传真',
  PHONE                   VARCHAR(100) COMMENT '电话',
  EMAIL                   VARCHAR(100) COMMENT '电子邮件',
  LEVEL                   INTEGER(2) COMMENT '部门级别',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门信息表';

-- ----------------------------
-- Table structure for AUTH_PERSON
-- ----------------------------
DROP TABLE IF EXISTS AUTH_PERSON;
CREATE TABLE AUTH_PERSON (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(100) NOT NULL COMMENT '姓名',
  EN_NAME                 VARCHAR(100) COMMENT '英文名称',
  GENDER                  CHAR(1) COMMENT '性别，1 男；2 女；3 保密',
  ID_NUMBER               VARCHAR(100) COMMENT '身份证号',
  COUNTRY                 VARCHAR(100) COMMENT '国籍',
  NATIONALITY             VARCHAR(100) COMMENT '民族',
  BIRTH_PLACE             VARCHAR(100) COMMENT '出生地',
  BIRTH_DATE              DATE COMMENT '出生日期',
  PHONE                   VARCHAR(100) COMMENT '手机号码',
  OFFICE_NUMBER           VARCHAR(100) COMMENT '办公号码',
  EMAIL                   VARCHAR(100) COMMENT '邮箱',
  ADDRESS                 VARCHAR(100) COMMENT '家庭住址',
  STATE                   CHAR(1) COMMENT '人员状态，1 在职；2 离职；3 公休',
  CATEGORY                VARCHAR(100) COMMENT '人员分类',
  NATURE                  CHAR(1) COMMENT '人员性质，1 自由；2 借调；3 临时；4 外派',
  DEPARTMENT_ID           VARCHAR(36) COMMENT '所属部门ID',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员信息表';

-- ----------------------------
-- Table structure for AUTH_DEPARTMENT_PERSON
-- ----------------------------
DROP TABLE IF EXISTS AUTH_DEPARTMENT_PERSON;
CREATE TABLE AUTH_DEPARTMENT_PERSON (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  DEPARTMENT_ID   CHAR(36) NOT NULL COMMENT '部门ID',
  PERSON_ID     CHAR(36) NOT NULL COMMENT '人员ID',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门人员关系表';

-- 暂定以Role做数据权限控制，POST相关表不使用
-- ----------------------------
-- Table structure for AUTH_POST
-- ----------------------------
# DROP TABLE IF EXISTS AUTH_POST;
# CREATE TABLE AUTH_POST (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   NAME                    VARCHAR(100) NOT NULL COMMENT '岗位名称',
#   CODE                    VARCHAR(100) COMMENT '岗位编码',
#   IS_LEADER               CHAR(1) COMMENT '是否部门长',
#   DEPARTMENT_ID           VARCHAR(36) COMMENT '所属部门ID',
#   REMARK                  VARCHAR(1000) COMMENT '备注',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位信息';

-- ----------------------------
-- Table structure for AUTH_POST_RLAT
-- ----------------------------
# DROP TABLE IF EXISTS AUTH_POST_RLAT;
# CREATE TABLE AUTH_POST_RLAT (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   SUPERIOR_ID       CHAR(36) NOT NULL COMMENT '上级ID',
#   SUBORDINATE_ID       CHAR(36) NOT NULL COMMENT '下级ID',
#   DATA_PERMISSION       CHAR(1) COMMENT '上级对下级是否有数据权限',
#   AUDIT_PERMISSION       CHAR(1) COMMENT '上级对下级是否有审批权限',
#   REMARK                  VARCHAR(1000) COMMENT '备注',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位上下级关系表';

-- ----------------------------
-- Table structure for AUTH_PERSON_POST
-- ----------------------------
# DROP TABLE IF EXISTS AUTH_PERSON_POST;
# CREATE TABLE AUTH_PERSON_POST (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   PERSON_ID               CHAR(36) NOT NULL COMMENT '人员ID',
#   POST_ID                 CHAR(36) NOT NULL COMMENT '岗位ID',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员岗位关系表';

-- ----------------------------
-- Table structure for AUTH_SYSTEM
-- ----------------------------
# DROP TABLE IF EXISTS AUTH_SYSTEM;
# CREATE TABLE AUTH_SYSTEM (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   NAME                    VARCHAR(200) NOT NULL COMMENT '名称',
#   PARENT_ID               VARCHAR(36) COMMENT '父节点',
#   SORT_NUMBER             INTEGER(9) COMMENT '排序号',
#   ENABLED                  CHAR(1) COMMENT '是否使用',
#   URI                     VARCHAR(500) COMMENT '系统资源标识符',
#   DESCRIPTION             VARCHAR(1000) COMMENT '描述',
#   REMARK                  VARCHAR(1000) COMMENT '备注',
#   PERMISSION              VARCHAR(500) COMMENT '权限名称',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统表';

-- ----------------------------
-- Table structure for AUTH_MENU
-- ----------------------------
DROP TABLE IF EXISTS AUTH_MENU;
CREATE TABLE AUTH_MENU (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(200) NOT NULL COMMENT '名称',
  PARENT_ID               VARCHAR(36) COMMENT '父节点',
  SORT_NUMBER             INTEGER(9) COMMENT '排序号',
  ENABLED                  CHAR(1) COMMENT '是否使用',
  URI                     VARCHAR(500) COMMENT '菜单资源标识符',
  ICON                    VARCHAR(100) COMMENT '图标名称',
  BIG_ICON                VARCHAR(100) COMMENT '大图标名称',
  DESCRIPTION             VARCHAR(1000) COMMENT '描述',
  LEVEL                   INTEGER(2) COMMENT '菜单级别',
  SHORTCUT                VARCHAR(100) COMMENT '快捷键',
  IS_PUBLIC               INTEGER(1) COMMENT '是否前台，0 全部；1 前台；2 后台',
  ASSIGNABLE              CHAR(1) NOT NULL COMMENT '是否可分配，0 否；1 是',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  PERMISSION              VARCHAR(500) COMMENT '权限名称',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for AUTH_SYSTEM_MENU
-- ----------------------------
# DROP TABLE IF EXISTS AUTH_SYSTEM_MENU;
# CREATE TABLE AUTH_SYSTEM_MENU (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   SYSTEM_ID               CHAR(36) NOT NULL COMMENT '系统ID',
#   MENU_ID                 CHAR(36) NOT NULL COMMENT '菜单ID',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单关系表';

-- ----------------------------
-- Table structure for AUTH_BUTTON
-- ----------------------------
DROP TABLE IF EXISTS AUTH_BUTTON;
CREATE TABLE AUTH_BUTTON (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  MENU_ID                 CHAR(36) NOT NULL COMMENT '菜单ID',
  NAME                    VARCHAR(100) NOT NULL COMMENT '按钮名称',
  ELEMENT_ID                VARCHAR(100) NOT NULL COMMENT 'HTML元素ID',
  ICON                    VARCHAR(100) COMMENT '按钮样式',
  ENABLED                 CHAR(1) COMMENT '是否使用',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  PERMISSION              VARCHAR(500) COMMENT '权限名称',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能按钮表';

-- ----------------------------
-- Table structure for AUTH_ROLE
-- ----------------------------
DROP TABLE IF EXISTS AUTH_ROLE;
CREATE TABLE AUTH_ROLE (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                    VARCHAR(100) NOT NULL COMMENT '角色名称',
  CODE                    VARCHAR(100) COMMENT '角色编码',
  ENABLED                 CHAR(1) COMMENT '是否使用',
  IS_SYSTEM               CHAR(1) COMMENT '是否系统角色',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

DROP TABLE IF EXISTS AUTH_ROLE_RLAT;
CREATE TABLE AUTH_ROLE_RLAT (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  SUPERIOR_ID       CHAR(36) NOT NULL COMMENT '上级ID',
  SUBORDINATE_ID       CHAR(36) NOT NULL COMMENT '下级ID',
  DATA_PERMISSION       CHAR(1) COMMENT '上级对下级是否有数据权限',
  AUDIT_PERMISSION       CHAR(1) COMMENT '上级对下级是否有审批权限',
  REMARK                  VARCHAR(1000) COMMENT '备注',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色上下级关系表';

-- ----------------------------
-- Table structure for AUTH_ROLE_MENU
-- ----------------------------
DROP TABLE IF EXISTS AUTH_ROLE_MENU;
CREATE TABLE AUTH_ROLE_MENU (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  ROLE_ID                 CHAR(36) NOT NULL COMMENT '角色ID',
  MENU_ID                 CHAR(36) NOT NULL COMMENT '菜单ID',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

-- ----------------------------
-- Table structure for AUTH_ROLE_BUTTON
-- ----------------------------
DROP TABLE IF EXISTS AUTH_ROLE_BUTTON;
CREATE TABLE AUTH_ROLE_BUTTON (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  ROLE_ID                 CHAR(36) NOT NULL COMMENT '角色ID',
  BUTTON_ID               CHAR(36) NOT NULL COMMENT '按钮ID',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色按钮关系表';

-- ----------------------------
-- Table structure for ACT_ID_USER
-- ----------------------------
DROP TABLE IF EXISTS ACT_ID_USER;
CREATE TABLE ACT_ID_USER (
  ID                     VARCHAR(64) NOT NULL COMMENT '框架主键',
  ID_                     VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '主键',
  REV_                    INTEGER COMMENT '数据库表更新次数',
  FIRST_                  VARCHAR(255) COMMENT '账户名',
  LAST_                   VARCHAR(255) COMMENT '暂时不使用',
  PHONE                 VARCHAR(20) COMMENT '手机号',
  EMAIL_                  VARCHAR(255) COMMENT '邮箱',
  PWD_                    VARCHAR(255) COMMENT '密码',
  PICTURE_ID_             VARCHAR(64) COMMENT '照片ID',
  TENANT_ID_             VARCHAR(255) COMMENT '租户ID',
  SALT                    VARCHAR(100) COMMENT '公盐',
  PERSON_ID               VARCHAR(36) COMMENT '人员ID',
  IS_ADMIN           CHAR(1) COMMENT '是否管理员，F 否；T 是',
  LEVEL                   INTEGER(9) COMMENT '用户等级',
  LAST_LOGIN              DATETIME COMMENT '上次登录时间',
  ENABLED                 CHAR(1) NOT NULL COMMENT '是否启用',
  LOCKED                  CHAR(1) NOT NULL COMMENT '是否锁定',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=INNODB DEFAULT CHARSET=UTF8 COLLATE UTF8_BIN COMMENT='账号表';

# -- ----------------------------
# -- Table structure for AUTH_ACCOUNT
# -- ----------------------------
# DROP TABLE IF EXISTS AUTH_ACCOUNT;
# CREATE TABLE AUTH_ACCOUNT (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   NAME                    VARCHAR(100) NOT NULL COMMENT '用户名',
#   PASSWORD                VARCHAR(100) NOT NULL COMMENT '密码',
#   SALT                    VARCHAR(100) COMMENT '公盐',
#   PERSON_ID               VARCHAR(36) COMMENT '人员ID',
#   IS_ADMIN           CHAR(1) COMMENT '是否管理员，F 否；T 是',
#   LEVEL                   INTEGER(9) COMMENT '用户等级',
#   LAST_LOGIN              DATETIME COMMENT '上次登录时间',
#   ENABLED                 CHAR(1) NOT NULL COMMENT '是否启用',
#   LOCKED                  CHAR(1) NOT NULL COMMENT '是否锁定',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号表';

-- ----------------------------
-- Table structure for AUTH_ACCOUNT_ROLE
-- ----------------------------
DROP TABLE IF EXISTS AUTH_ACCOUNT_ROLE;
CREATE TABLE AUTH_ACCOUNT_ROLE (
  ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  ACCOUNT_ID              CHAR(36) NOT NULL COMMENT '账号ID',
  ROLE_ID                 CHAR(36) NOT NULL COMMENT '角色ID',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号角色关系表';

-- ----------------------------
-- Table structure for AUTH_QUICK_MENU
-- ----------------------------
# DROP TABLE IF EXISTS AUTH_QUICK_MENU;
# CREATE TABLE AUTH_QUICK_MENU (
#   ID                      CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
#   ACCOUNT_ID              CHAR(36) NOT NULL COMMENT '账号ID',
#   MENU_ID                 CHAR(36) NOT NULL COMMENT '快速菜单ID',
#   TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
#   DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
#   LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
#   LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号快速菜单表';
