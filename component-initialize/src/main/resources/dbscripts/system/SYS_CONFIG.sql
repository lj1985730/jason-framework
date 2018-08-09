-- 初始化脚本 - 下拉框字典
DELETE FROM SYS_CONFIG;

INSERT INTO SYS_CONFIG VALUES (UPPER(UUID()), 'superTenantId', '00000000-0000-0000-0000-000000000000', 'T', 'T', '用友Id', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);
INSERT INTO SYS_CONFIG VALUES (UPPER(UUID()), 'superAdminId', '11111111-1111-1111-1111-111111111111', 'T', 'T', '超管ID', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);
INSERT INTO SYS_CONFIG VALUES (UPPER(UUID()), 'defaultPassword', '123123', 'T', 'T', '默认密码，在本值未配置/被删除/为空的情况下，系统读取配置文件中的值作为默认密码', '00000000-0000-0000-0000-000000000000', 'F', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);