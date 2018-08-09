-- 初始化脚本 - 超管账户
DELETE FROM ACT_ID_USER;
INSERT INTO ACT_ID_USER VALUES (
  '11111111-1111-1111-1111-111111111111',
  '11111111-1111-1111-1111-111111111111',
  0, 'admin', null, null, null,
  '$shiro1$SHA-256$50$gBQS871/gqFVQbND47RWAA==$5tFmpXlCNV2rIrB2VE7Q1/Zp4EO3yrwxgTaMEYJQ1dA=',
  null, '00000000-0000-0000-0000-000000000000',
  'yoogun', null, 'T', 1,
  CURRENT_TIMESTAMP, 'T', 'F',
  '00000000-0000-0000-0000-000000000000', 'F',
  '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);

-- 企业管理员
INSERT INTO ACT_ID_USER VALUES (
  UPPER(UUID()),
  '1',
  0, 'dlyonyou', null, null, null,
  '$shiro1$SHA-256$50$gBQS871/gqFVQbND47RWAA==$5tFmpXlCNV2rIrB2VE7Q1/Zp4EO3yrwxgTaMEYJQ1dA=',
  null, '${tenantId}',
  null, null, 'T', 1,
        null, 'T', 'F',
        '${tenantId}', 'F',
        '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP);

UPDATE ACT_ID_USER SET ID_ = ID WHERE ID_ <> ID;
