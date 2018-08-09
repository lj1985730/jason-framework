-- 初始化脚本 - 超管账户
DELETE FROM AUTH_ACCOUNT WHERE TENANT_ID = '${tenantId}';
INSERT INTO AUTH_ACCOUNT VALUES ('${superAdminId}', 'admin', '$shiro1$SHA-256$50$gBQS871/gqFVQbND47RWAA==$5tFmpXlCNV2rIrB2VE7Q1/Zp4EO3yrwxgTaMEYJQ1dA=', 'yoogun', null, 'T', 1, CURRENT_TIMESTAMP, 'T', 'F', '${yonyouId}', 'F', '${superAdminId}', CURRENT_TIMESTAMP);
