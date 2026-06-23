-- ============================================================
-- 主数据平台 - 初始化数据
-- ============================================================

-- 数据域注册表
INSERT INTO mdm_domain_registry (domain_code, domain_name, module_name, table_name, code_field, code_prefix, sort_order) VALUES
('ORG',        '机构',     'mdm-organization', 'mdm_org',            'org_code',          'ORG', 1),
('DEPT',       '科室',     'mdm-organization', 'mdm_dept',           'dept_code',         'DPT', 2),
('EMPLOYEE',   '员工',     'mdm-organization', 'mdm_employee',       'emp_code',          'EMP', 3),
('DRUG',       '药品',     'mdm-supply',       'mdm_drug',           'drug_code',         'DRG', 4),
('MATERIAL',   '耗材',     'mdm-supply',       'mdm_material',       'material_code',     'MAT', 5),
('SUPPLY_ITEM','物资',     'mdm-supply',       'mdm_supply_item',    'supply_item_code',  'SPL', 6),
('TREATMENT',  '诊疗项目', 'mdm-clinical',     'mdm_treatment_item', 'treatment_code',    'TRT', 7),
('ORDER',      '医嘱',     'mdm-clinical',     'mdm_order',          'order_code',        'ODR', 8),
('STANDARD',   '规范',     'mdm-clinical',     'mdm_standard',       'std_code',          'STD', 9),
('DEVICE',     '设备',     'mdm-facility',     'mdm_device',         'device_code',       'DEV', 10);

-- 系统角色
INSERT INTO sys_role (id, role_code, role_name, remark) VALUES
(1, 'ADMIN',           '主数据管理员',   '全域数据查看、审核、修改、异常处理'),
(2, 'DATA_QUALITY',    '数据质量管理员', '查看质量报表、处理质量异常'),
(3, 'AUDITOR',         '审计人员',       '只读全量数据、变更历史及操作日志'),
(4, 'BUSINESS_USER',   '业务申请人',     '发起申请、查看本人申请记录'),
(5, 'DEPT_REVIEWER',   '部门审核人',     '审核本部门申请、查看本域数据'),
(6, 'API_CONSUMER',    '下游系统账号',   '只读 API 调用');

-- 管理员账号（密码: admin123，BCrypt 加密）
INSERT INTO sys_user (id, username, password, real_name, status) VALUES
(1, 'admin', '$2a$10$XjVazXkSkUI7Sal6JLGJse1zyuIgdOEXXu3IH1PPmw0moalh1IPjy', '系统管理员', 1);

-- 管理员角色关联
INSERT INTO sys_user_role (id, user_id, role_id) VALUES
(1, 1, 1);
