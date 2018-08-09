-- 初始化脚本 - 权限按钮 -- crm
# Customer
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '新增客户', 'createCustomer', NULL, 'T', '新增客户按钮', 'crm:customer:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '修改客户', 'updateCustomer', NULL, 'T', '修改客户按钮', 'crm:customer:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '删除客户', 'deleteCustomer', NULL, 'T', '删除客户按钮', 'crm:customer:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Chance
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '新增商机', 'createChance', NULL, 'T', '新增商机按钮', 'crm:chance:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '修改商机', 'updateChance', NULL, 'T', '修改商机按钮', 'crm:chance:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '删除商机', 'deleteChance', NULL, 'T', '删除商机按钮', 'crm:chance:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Doc
# INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '新增文档', 'createDoc', NULL, 'T', '新增文档按钮', 'crm:doc:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '修改文档', 'updateDoc', NULL, 'T', '修改文档按钮', 'crm:doc:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '删除文档', 'deleteDoc', NULL, 'T', '删除文档按钮', 'crm:doc:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Schedule
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '新增日程', 'createSchedule', NULL, 'T', '新增文档按钮', 'crm:schedule:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '修改日程', 'updateSchedule', NULL, 'T', '修改文档按钮', 'crm:schedule:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-AUTH_MENU-12}', '删除日程', 'deleteSchedule', NULL, 'T', '删除文档按钮', 'crm:schedule:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
