
SET FOREIGN_KEY_CHECKS=0;

-- cms tables -----------------------------------------------------------------------------------------------

-- ----------------------------
-- Table structure for CMS_SUBJECT
-- ----------------------------
DROP TABLE IF EXISTS CMS_SUBJECT;
CREATE TABLE CMS_SUBJECT (
  ID                                    CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  NAME                            VARCHAR(100) NOT NULL COMMENT '名称',
  PARENT_ID                   VARCHAR(36) COMMENT '父节点，根栏目为#',
  SORT_NUMBER           INTEGER(9) COMMENT '排序号',
  ENABLED                       CHAR(1) COMMENT '是否使用',
  CODE                              VARCHAR(100) COMMENT '编号',
  CHANNEL                      INTEGER(9) NOT NULL COMMENT '频道（位置）',
  TENANT_ID                   VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                        CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS栏目表';

-- ----------------------------
-- Table structure for CMS_ARTICLE
-- ----------------------------
DROP TABLE IF EXISTS CMS_ARTICLE;
CREATE TABLE CMS_ARTICLE (
  ID                           CHAR(36) NOT NULL PRIMARY KEY COMMENT '主键',
  ROOT_SUBJECT_ID         CHAR(36) NOT NULL COMMENT '根栏目ID',
  SUBJECT_ID         CHAR(36) NOT NULL COMMENT '栏目ID',
  TITLE                      VARCHAR(200) NOT NULL COMMENT '标题',
  SUMMARY           VARCHAR(1000) NOT NULL COMMENT '概要',
  CONTENT             LONGTEXT COMMENT '内容',
  COVER_IMG_URI VARCHAR(200) NOT NULL COMMENT '标题图片URI',
  CREATE_TIME     DATETIME NOT NULL COMMENT '创建时间',
  AUTHOR              VARCHAR(200) COMMENT '作者',
  SOURCE              VARCHAR(200) COMMENT '来源',
  SOURCE_URL     VARCHAR(200) COMMENT '来源URL',
  PUBLISHER         VARCHAR(200) COMMENT '发布人',
  IS_TOP                 CHAR(1) DEFAULT 'F' COMMENT '是否置顶',
  VIEW_COUNT  INTEGER(9) NOT NULL DEFAULT 0 COMMENT '浏览次数',
  TEMPLATE_ID VARCHAR(36) COMMENT '模板ID',
  TENANT_ID               VARCHAR(36) NOT NULL COMMENT '租户ID',
  DELETED                 CHAR(1) NOT NULL COMMENT '是否删除',
  LAST_MODIFY_ACCOUNT_ID  VARCHAR(36) NOT NULL COMMENT '操作人',
  LAST_MODIFY_TIME        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS文章表';
