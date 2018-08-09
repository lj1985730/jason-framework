-- 初始化脚本 - 权限按钮
DELETE FROM AUTH_BUTTON WHERE TENANT_ID = '${tenantId}';
# Tenant
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-13}', '修改租户', 'updateSelfTenant', NULL, 'T', '修改租户', 'system:tenant:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Department
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-14}', '新增部门', 'createDepartment', NULL, 'T', '新增部门', 'auth:department:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-14}', '删除部门', 'deleteDepartment', NULL, 'T', '删除部门', 'auth:department:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Account
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-21}', '新增账户', 'createAccount', NULL, 'T', '新增账户', 'auth:account:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-21}', '修改账户', 'updateAccount', NULL, 'T', '修改账户', 'auth:account:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-21}', '删除账户', 'deleteAccount', NULL, 'T', '删除账户', 'auth:account:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-21}', '启用/禁用账户', 'enableAccount', NULL, 'T', '启用/禁用账户', 'auth:account:enable', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-21}', '重置密码', 'resetPassword', NULL, 'T', '重置密码', 'auth:account:resetPwd', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-21}', '解锁账户', 'unlockAccount', NULL, 'T', '解锁账户', 'auth:account:unlock', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Role
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-22}', '新增角色', 'createAccount', NULL, 'T', '新增角色', 'auth:role:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-22}', '修改角色', 'updateAccount', NULL, 'T', '修改角色', 'auth:role:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-22}', '删除角色', 'deleteAccount', NULL, 'T', '删除角色', 'auth:role:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-22}', '启用/禁用角色', 'enableRole', NULL, 'T', '启用/禁用角色', 'auth:role:enable', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-22}', '角色绑定菜单-保存', 'bindMenu', NULL, 'T', '角色绑定菜单的保存按钮', 'auth:role:bindMenu', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Post
-- INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-15}', 'createPost', NULL, 'T', '新增岗位', 'auth:post:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
-- INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-15}', 'updatePost', NULL, 'T', '修改岗位', 'auth:post:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
-- INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-15}', 'deletePost', NULL, 'T', '删除岗位', 'auth:post:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Person
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-16}', '新增人员', 'createPerson', NULL, 'T', '新增人员', 'auth:person:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-16}', '新增人员', 'updatePerson', NULL, 'T', '修改人员', 'auth:person:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${UUID-T_AUTH_MENU-16}', '新增人员', 'deletePerson', NULL, 'T', '删除人员', 'auth:person:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);

