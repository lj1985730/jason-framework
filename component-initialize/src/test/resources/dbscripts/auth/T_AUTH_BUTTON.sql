-- 初始化脚本 - 权限按钮
DELETE FROM AUTH_BUTTON WHERE TENANT_ID = '${tenantId}';
# Tenant
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0012}', 'createTenant', NULL, 'T', '新增租户', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0012}', 'updateTenant', NULL, 'T', '修改租户', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0012}', 'deleteTenant', NULL, 'T', '删除租户', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0013}', 'updateSelfTenant', NULL, 'T', '修改租户', 'system:tenant:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Department
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0014}', 'createDept', NULL, 'T', '新增部门', 'system:department:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0014}', 'updateDept', NULL, 'T', '修改部门', 'system:department:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0014}', 'deleteDept', NULL, 'T', '删除部门', 'system:department:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Post
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0015}', 'createPost', NULL, 'T', '新增岗位', 'system:post:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0015}', 'updatePost', NULL, 'T', '修改岗位', 'system:post:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0015}', 'deletePost', NULL, 'T', '删除岗位', 'system:post:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Person
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0016}', 'createPerson', NULL, 'T', '新增人员', 'system:person:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0016}', 'updatePerson', NULL, 'T', '修改人员', 'system:person:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0016}', 'deletePerson', NULL, 'T', '删除人员', 'system:person:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# SysConfig
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0017}', 'createSysConfig', NULL, 'T', '新增系统参数', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0017}', 'updateSysConfig', NULL, 'T', '修改系统参数', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0017}', 'deleteSysConfig', NULL, 'T', '删除系统参数', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Account
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0021}', 'createAccount', NULL, 'T', '新增账号', 'auth:account:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0021}', 'updateAccount', NULL, 'T', '修改账号', 'auth:account:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0021}', 'deleteAccount', NULL, 'T', '删除账号', 'auth:account:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Role
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0022}', 'createAccount', NULL, 'T', '新增角色', 'auth:role:create', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0022}', 'updateAccount', NULL, 'T', '修改角色', 'auth:role:update', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0022}', 'deleteAccount', NULL, 'T', '删除角色', 'auth:role:delete', '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Menu
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0023}', 'createAccount', NULL, 'T', '新增菜单', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0023}', 'updateAccount', NULL, 'T', '修改菜单', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0023}', 'deleteAccount', NULL, 'T', '删除菜单', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
# Button
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0024}', 'createAccount', NULL, 'T', '新增按钮', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0024}', 'updateAccount', NULL, 'T', '修改按钮', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
INSERT INTO AUTH_BUTTON VALUES (UPPER(UUID()), '${menuId0024}', 'deleteAccount', NULL, 'T', '删除按钮', NULL, '${tenantId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
