-- 初始化脚本 - 下拉框字典
DELETE FROM SYS_COMBO_DATA;

-- INSERT INTO SYS_COMBO_DATA VALUES (UPPER(UUID()), 'POST', 'SELECT ID, NAME AS VALUE FROM AUTH_POST WHERE DELETED = \'F\' AND TENANT_ID = \'<TENANT_ID>\' ORDER BY NAME ASC', NULL, NULL, '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);

-- 部门
INSERT INTO SYS_COMBO_DATA VALUES (UPPER(UUID()), 'DEPARTMENT', 'SELECT ID, NAME AS VALUE FROM AUTH_DEPARTMENT WHERE DELETED = \'F\' AND TENANT_ID = \'<TENANT_ID>\'', NULL, ' ORDER BY LEVEL ASC, SORT_NUMBER ASC', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);
-- 人员
INSERT INTO SYS_COMBO_DATA VALUES (UPPER(UUID()), 'PERSON', 'SELECT ID, NAME AS VALUE FROM AUTH_PERSON WHERE DELETED = \'F\' AND TENANT_ID = \'<TENANT_ID>\'', NULL, ' ORDER BY NAME ASC', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);
