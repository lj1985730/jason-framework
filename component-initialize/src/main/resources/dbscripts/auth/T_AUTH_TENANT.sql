-- 初始化脚本 - 用友企业
DELETE FROM AUTH_TENANT WHERE ID <> '00000000-0000-0000-0000-000000000000';
INSERT INTO AUTH_TENANT VALUES
  ('00000000-0000-0000-0000-000000000000','昱橄', null, 0, 'Y', 'yoogun', '昱橄', null, null, null, '大连市沙河口区星河二街5号绿城深蓝国际602',
    null, null, null, null, null,
    null, null, null, null, null,
   0, '超管', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);

INSERT INTO AUTH_TENANT VALUES
  (UPPER(UUID()), '大连用友软件有限公司', null, 1, 'Y', 'DLYONYOU', '大连用友', '912102027772878726', null, null, '大连市沙河口区星河二街5号绿城深蓝国际602',
    '战威', null, '2005-10-12', null, null,
    '软件、信息服务', '王佳', null, null, null,
   1, 'CRM使用企业', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);

-- 更新租户ID
UPDATE AUTH_TENANT SET TENANT_ID = ID WHERE TENANT_ID <> ID;