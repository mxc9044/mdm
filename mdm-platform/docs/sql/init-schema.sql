-- ============================================================
-- 主数据平台 - 数据库初始化脚本
-- 数据库: mdm
-- 字符集: utf8mb4
-- ============================================================

-- -----------------------------------------------------------
-- 1. 数据域注册表
-- -----------------------------------------------------------
CREATE TABLE mdm_domain_registry (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    domain_code     VARCHAR(32)     NOT NULL COMMENT '域编码（如 ORG, DEPT, DRUG, DEVICE）',
    domain_name     VARCHAR(100)    NOT NULL COMMENT '域名称',
    module_name     VARCHAR(64)     NOT NULL COMMENT '所属 Maven 模块',
    table_name      VARCHAR(64)     NOT NULL COMMENT '主表名',
    code_field      VARCHAR(64)     NOT NULL COMMENT '编码字段名',
    code_prefix     VARCHAR(16)     NOT NULL COMMENT '编码前缀',
    sync_enabled    TINYINT         NOT NULL DEFAULT 1 COMMENT '是否启用同步（1=是 0=否）',
    quality_enabled TINYINT         NOT NULL DEFAULT 1 COMMENT '是否启用质量检查',
    approval_required TINYINT       NOT NULL DEFAULT 1 COMMENT '是否需要 OA 审批',
    cache_ttl_hours INT             NOT NULL DEFAULT 24 COMMENT '缓存过期时间（小时）',
    sort_order      INT             NOT NULL DEFAULT 0 COMMENT '排序',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 0=停用）',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_domain_code (domain_code),
    UNIQUE KEY uk_table_name (table_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据域注册表';

-- -----------------------------------------------------------
-- 2. 机构主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_org (
    id              BIGINT          NOT NULL COMMENT '主键',
    org_code        VARCHAR(64)     NOT NULL COMMENT '机构编码（全局唯一）',
    org_name        VARCHAR(200)    NOT NULL COMMENT '机构名称',
    short_name      VARCHAR(100)    DEFAULT NULL COMMENT '机构简称',
    org_type        TINYINT         NOT NULL COMMENT '机构类型（1=总院 2=分院 3=门诊部 4=诊所）',
    parent_code     VARCHAR(64)     DEFAULT NULL COMMENT '上级机构编码',
    region_code     VARCHAR(12)     DEFAULT NULL COMMENT '行政区划编码',
    address         VARCHAR(500)    DEFAULT NULL COMMENT '详细地址',
    contact_phone   VARCHAR(32)     DEFAULT NULL COMMENT '联系电话',
    license_no      VARCHAR(64)     DEFAULT NULL COMMENT '执业许可证号',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号（乐观锁）',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除（0=未删除 1=已删除）',
    PRIMARY KEY (id),
    UNIQUE KEY uk_org_code (org_code),
    KEY idx_parent_code (parent_code),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='机构主数据';

-- -----------------------------------------------------------
-- 3. 科室主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_dept (
    id              BIGINT          NOT NULL COMMENT '主键',
    dept_code       VARCHAR(64)     NOT NULL COMMENT '科室编码（全局唯一）',
    dept_name       VARCHAR(200)    NOT NULL COMMENT '科室名称',
    alias_name      VARCHAR(200)    DEFAULT NULL COMMENT '科室别名',
    dept_type       TINYINT         NOT NULL COMMENT '科室类型（1=临床科室 2=医技科室 3=行政科室）',
    org_code        VARCHAR(64)     NOT NULL COMMENT '所属机构编码',
    parent_code     VARCHAR(64)     DEFAULT NULL COMMENT '上级科室编码',
    sort_order      INT             NOT NULL DEFAULT 0 COMMENT '排序',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_dept_code (dept_code),
    KEY idx_org_code (org_code),
    KEY idx_parent_code (parent_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='科室主数据';

-- -----------------------------------------------------------
-- 4. 员工主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_employee (
    id              BIGINT          NOT NULL COMMENT '主键',
    emp_code        VARCHAR(64)     NOT NULL COMMENT '员工编号（全局唯一）',
    emp_name        VARCHAR(100)    NOT NULL COMMENT '姓名',
    id_card         VARCHAR(256)    DEFAULT NULL COMMENT '身份证号（加密存储）',
    phone           VARCHAR(256)    DEFAULT NULL COMMENT '联系电话（加密存储）',
    gender          TINYINT         DEFAULT NULL COMMENT '性别（1=男 2=女）',
    practice_cert   VARCHAR(64)     DEFAULT NULL COMMENT '执业证号',
    title_cert      VARCHAR(64)     DEFAULT NULL COMMENT '职称证号',
    org_code        VARCHAR(64)     NOT NULL COMMENT '主岗机构编码',
    dept_code       VARCHAR(64)     NOT NULL COMMENT '主岗科室编码',
    position        VARCHAR(100)    DEFAULT NULL COMMENT '岗位/职务',
    title           VARCHAR(100)    DEFAULT NULL COMMENT '职称',
    practice_type   TINYINT         DEFAULT NULL COMMENT '执业类别（1=医师 2=护士 3=药师 4=技师）',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=在职 2=离职 3=停职）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_emp_code (emp_code),
    KEY idx_org_code (org_code),
    KEY idx_dept_code (dept_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工主数据';

-- -----------------------------------------------------------
-- 5. 员工多机构任职记录
-- -----------------------------------------------------------
CREATE TABLE mdm_employee_position (
    id              BIGINT          NOT NULL COMMENT '主键',
    emp_code        VARCHAR(64)     NOT NULL COMMENT '员工编号',
    org_code        VARCHAR(64)     NOT NULL COMMENT '任职机构编码',
    dept_code       VARCHAR(64)     NOT NULL COMMENT '任职科室编码',
    position_type   TINYINT         NOT NULL COMMENT '任职类型（1=主岗 2=兼职 3=多点执业 4=进修）',
    start_date      DATE            NOT NULL COMMENT '生效日期',
    end_date        DATE            DEFAULT NULL COMMENT '失效日期（空=长期有效）',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用）',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_emp_code (emp_code),
    KEY idx_org_code (org_code),
    KEY idx_dept_code (dept_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='员工多机构任职记录';

-- -----------------------------------------------------------
-- 6. 药品主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_drug (
    id              BIGINT          NOT NULL COMMENT '主键',
    drug_code       VARCHAR(64)     NOT NULL COMMENT '药品编码（全局唯一，拆零记录为父编码+"-S"）',
    parent_drug_code VARCHAR(64)    DEFAULT NULL COMMENT '父药品编码（拆零记录指向整包记录）',
    is_split        TINYINT         NOT NULL DEFAULT 0 COMMENT '是否拆零记录（0=整包 1=拆零）',
    generic_name    VARCHAR(200)    NOT NULL COMMENT '通用名',
    trade_name      VARCHAR(200)    DEFAULT NULL COMMENT '商品名',
    dosage_form     VARCHAR(64)     DEFAULT NULL COMMENT '剂型',
    specification   VARCHAR(200)    DEFAULT NULL COMMENT '规格',
    unit            VARCHAR(32)     DEFAULT NULL COMMENT '计量单位（整包:盒/瓶 拆零:片/支/粒）',
    unit_price      DECIMAL(10,4)   DEFAULT NULL COMMENT '单价（元）',
    manufacturer    VARCHAR(200)    DEFAULT NULL COMMENT '生产厂家',
    approval_no     VARCHAR(64)     DEFAULT NULL COMMENT '国药准字',
    insurance_code  VARCHAR(64)     DEFAULT NULL COMMENT '医保编码',
    drug_category   TINYINT         DEFAULT NULL COMMENT '药品分类（1=西药 2=中成药 3=中草药 4=生物制品）',
    storage_condition TINYINT       DEFAULT NULL COMMENT '存储条件（1=常温 2=冷藏 3=冷冻）',
    pack_unit       VARCHAR(32)     DEFAULT NULL COMMENT '包装单位（盒/瓶/袋等）',
    pack_qty        INT             DEFAULT NULL COMMENT '包装数量（1包装单位=N拆零单位）',
    split_unit      VARCHAR(32)     DEFAULT NULL COMMENT '拆零单位（片/支/粒等）',
    split_price     DECIMAL(10,4)   DEFAULT NULL COMMENT '拆零单价（元）',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_drug_code (drug_code),
    KEY idx_generic_name (generic_name),
    KEY idx_approval_no (approval_no),
    KEY idx_parent_drug_code (parent_drug_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='药品主数据（整包+拆零双记录模式）';

-- -----------------------------------------------------------
-- 7. 耗材主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_material (
    id              BIGINT          NOT NULL COMMENT '主键',
    material_code   VARCHAR(64)     NOT NULL COMMENT '耗材编码（全局唯一）',
    material_name   VARCHAR(200)    NOT NULL COMMENT '耗材名称',
    material_category TINYINT       DEFAULT NULL COMMENT '耗材类别（1=高值耗材 2=低值耗材 3=试剂）',
    specification   VARCHAR(200)    DEFAULT NULL COMMENT '规格型号',
    unit            VARCHAR(32)     DEFAULT NULL COMMENT '计量单位',
    manufacturer    VARCHAR(200)    DEFAULT NULL COMMENT '生产厂家',
    reg_cert_no     VARCHAR(64)     DEFAULT NULL COMMENT '注册证号',
    insurance_code  VARCHAR(64)     DEFAULT NULL COMMENT '医保编码',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_material_code (material_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='耗材主数据';

-- -----------------------------------------------------------
-- 8. 物资主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_supply_item (
    id              BIGINT          NOT NULL COMMENT '主键',
    supply_item_code VARCHAR(64)    NOT NULL COMMENT '物资编码（全局唯一）',
    supply_item_name VARCHAR(200)   NOT NULL COMMENT '物资名称',
    item_category   TINYINT         DEFAULT NULL COMMENT '物资类别（1=办公用品 2=后勤物资 3=维修配件 4=其他）',
    specification   VARCHAR(200)    DEFAULT NULL COMMENT '规格型号',
    unit            VARCHAR(32)     DEFAULT NULL COMMENT '计量单位',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    brand           VARCHAR(100)    DEFAULT NULL COMMENT '品牌',
    storage_condition TINYINT       DEFAULT NULL COMMENT '存储条件（1=常温 2=特殊存储）',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_supply_item_code (supply_item_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物资主数据';

-- -----------------------------------------------------------
-- 9. 诊疗项目主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_treatment_item (
    id              BIGINT          NOT NULL COMMENT '主键',
    treatment_code  VARCHAR(64)     NOT NULL COMMENT '诊疗项目编码（全局唯一）',
    treatment_name  VARCHAR(200)    NOT NULL COMMENT '项目名称',
    item_category   TINYINT         DEFAULT NULL COMMENT '项目类别（1=检查 2=检验 3=治疗 4=手术 5=护理 6=其他）',
    charge_category TINYINT         DEFAULT NULL COMMENT '收费类别（1=甲类 2=乙类 3=丙类）',
    insurance_code  VARCHAR(64)     DEFAULT NULL COMMENT '医保编码',
    exec_dept_code  VARCHAR(64)     DEFAULT NULL COMMENT '默认执行科室编码',
    unit_price      DECIMAL(12,2)   DEFAULT NULL COMMENT '标准收费单价',
    unit            VARCHAR(32)     DEFAULT NULL COMMENT '计量单位',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_treatment_code (treatment_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='诊疗项目主数据';

-- -----------------------------------------------------------
-- 10. 设备主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_device (
    id              BIGINT          NOT NULL COMMENT '主键',
    device_code     VARCHAR(64)     NOT NULL COMMENT '设备编码（全局唯一）',
    device_name     VARCHAR(200)    NOT NULL COMMENT '设备名称',
    device_category TINYINT         DEFAULT NULL COMMENT '设备类别（1=医疗设备 2=信息设备 3=后勤设备）',
    brand           VARCHAR(100)    DEFAULT NULL COMMENT '品牌',
    specification   VARCHAR(200)    DEFAULT NULL COMMENT '规格型号',
    org_code        VARCHAR(64)     NOT NULL COMMENT '所属机构编码',
    dept_code       VARCHAR(64)     DEFAULT NULL COMMENT '所属科室编码',
    asset_no        VARCHAR(64)     DEFAULT NULL COMMENT '资产编号',
    reg_cert_no     VARCHAR(64)     DEFAULT NULL COMMENT '注册证号',
    purchase_date   DATE            DEFAULT NULL COMMENT '购入日期',
    service_life    INT             DEFAULT NULL COMMENT '使用年限',
    warranty_expire DATE            DEFAULT NULL COMMENT '维保到期日',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=在用 2=停用 3=报废 4=维修中）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_device_code (device_code),
    KEY idx_org_code (org_code),
    KEY idx_dept_code (dept_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='设备主数据';

-- -----------------------------------------------------------
-- 11. 医嘱主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_order (
    id              BIGINT          NOT NULL COMMENT '主键',
    order_code      VARCHAR(64)     NOT NULL COMMENT '医嘱编码（全局唯一）',
    order_name      VARCHAR(200)    NOT NULL COMMENT '医嘱名称',
    order_type      TINYINT         DEFAULT NULL COMMENT '医嘱类型（1=长期医嘱 2=临时医嘱）',
    order_category  TINYINT         DEFAULT NULL COMMENT '医嘱分类（1=药品医嘱 2=检查医嘱 3=手术医嘱 4=护理医嘱）',
    frequency       VARCHAR(32)     DEFAULT NULL COMMENT '执行频次',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用 3=注销）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_code (order_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='医嘱主数据';

-- -----------------------------------------------------------
-- 12. 医嘱关联项目（药品/耗材/诊疗项目）
-- -----------------------------------------------------------
CREATE TABLE mdm_order_item (
    id              BIGINT          NOT NULL COMMENT '主键',
    order_code      VARCHAR(64)     NOT NULL COMMENT '医嘱编码',
    item_type       TINYINT         NOT NULL COMMENT '关联类型（1=药品 2=耗材 3=诊疗项目）',
    item_code       VARCHAR(64)     NOT NULL COMMENT '关联项目编码',
    sort_order      INT             NOT NULL DEFAULT 0 COMMENT '排序',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_order_code (order_code),
    KEY idx_item_code (item_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='医嘱关联项目';

-- -----------------------------------------------------------
-- 13. 医嘱适用科室
-- -----------------------------------------------------------
CREATE TABLE mdm_order_dept (
    id              BIGINT          NOT NULL COMMENT '主键',
    order_code      VARCHAR(64)     NOT NULL COMMENT '医嘱编码',
    dept_code       VARCHAR(64)     NOT NULL COMMENT '科室编码',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_order_code (order_code),
    KEY idx_dept_code (dept_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='医嘱适用科室';

-- -----------------------------------------------------------
-- 14. 规范主数据
-- -----------------------------------------------------------
CREATE TABLE mdm_standard (
    id              BIGINT          NOT NULL COMMENT '主键',
    std_code        VARCHAR(64)     NOT NULL COMMENT '规范编码（全局唯一）',
    std_name        VARCHAR(200)    NOT NULL COMMENT '规范名称',
    std_type        TINYINT         DEFAULT NULL COMMENT '规范类型（1=临床路径 2=诊疗指南 3=操作规程）',
    biz_version     VARCHAR(32)     DEFAULT NULL COMMENT '业务版本号',
    effective_date  DATE            DEFAULT NULL COMMENT '生效日期',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=草稿 2=生效 3=废止）',
    version         INT             NOT NULL DEFAULT 1 COMMENT '数据版本号',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_std_code (std_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='规范主数据';

-- -----------------------------------------------------------
-- 15. 对照映射表
-- -----------------------------------------------------------
CREATE TABLE mdm_code_mapping (
    id              BIGINT          NOT NULL COMMENT '主键',
    mdm_code        VARCHAR(64)     NOT NULL COMMENT '主数据平台标准编码',
    mdm_name        VARCHAR(200)    DEFAULT NULL COMMENT '主数据平台标准名称',
    local_system    VARCHAR(64)     NOT NULL COMMENT '本地系统标识',
    local_code      VARCHAR(64)     NOT NULL COMMENT '本地系统编码',
    local_name      VARCHAR(200)    DEFAULT NULL COMMENT '本地系统名称',
    data_domain     VARCHAR(32)     NOT NULL COMMENT '主数据域编码',
    valid_from      DATE            DEFAULT NULL COMMENT '生效日期',
    valid_to        DATE            DEFAULT NULL COMMENT '失效日期（空=长期有效）',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 2=停用）',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_mapping (mdm_code, local_system),
    KEY idx_local (local_system, local_code),
    KEY idx_domain (data_domain)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='对照映射表';

-- -----------------------------------------------------------
-- 16. 变更历史表
-- -----------------------------------------------------------
CREATE TABLE mdm_change_history (
    id              BIGINT          NOT NULL COMMENT '主键',
    data_domain     VARCHAR(32)     NOT NULL COMMENT '主数据域编码',
    data_code       VARCHAR(64)     NOT NULL COMMENT '主数据编码',
    change_type     VARCHAR(16)     NOT NULL COMMENT '变更类型（CREATE/UPDATE/DISABLE/CANCEL/MERGE）',
    field_name      VARCHAR(64)     DEFAULT NULL COMMENT '变更字段名',
    old_value       TEXT            DEFAULT NULL COMMENT '变更前值',
    new_value       TEXT            DEFAULT NULL COMMENT '变更后值',
    version         INT             DEFAULT NULL COMMENT '变更后版本号',
    approval_no     VARCHAR(64)     DEFAULT NULL COMMENT '关联 OA 审批单号',
    operator        VARCHAR(64)     DEFAULT NULL COMMENT '操作人',
    operate_time    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    source          VARCHAR(32)     DEFAULT NULL COMMENT '变更来源（OA_CALLBACK/SUPPLY_SYNC/POLLING/MANUAL）',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_data_code (data_domain, data_code),
    KEY idx_operate_time (operate_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='变更历史表';

-- -----------------------------------------------------------
-- 17. 校验异常表
-- -----------------------------------------------------------
CREATE TABLE mdm_validation_exception (
    id              BIGINT          NOT NULL COMMENT '主键',
    data_domain     VARCHAR(32)     NOT NULL COMMENT '主数据域编码',
    data_code       VARCHAR(64)     DEFAULT NULL COMMENT '主数据编码',
    request_id      VARCHAR(64)     DEFAULT NULL COMMENT '请求唯一ID',
    exception_type  VARCHAR(32)     NOT NULL COMMENT '异常类型（DUPLICATE/VERSION_CONFLICT/VALIDATION_FAIL/NO_APPROVAL）',
    exception_detail TEXT           NOT NULL COMMENT '异常详情',
    raw_data        TEXT            DEFAULT NULL COMMENT '原始数据（JSON）',
    handle_status   TINYINT         NOT NULL DEFAULT 0 COMMENT '处理状态（0=待处理 1=已处理 2=已忽略）',
    handle_by       VARCHAR(64)     DEFAULT NULL COMMENT '处理人',
    handle_time     DATETIME        DEFAULT NULL COMMENT '处理时间',
    handle_remark   VARCHAR(500)    DEFAULT NULL COMMENT '处理备注',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_domain_status (data_domain, handle_status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='校验异常表';

-- -----------------------------------------------------------
-- 18. 系统用户表
-- -----------------------------------------------------------
CREATE TABLE sys_user (
    id              BIGINT          NOT NULL COMMENT '主键',
    username        VARCHAR(64)     NOT NULL COMMENT '用户名',
    password        VARCHAR(256)    NOT NULL COMMENT '密码（BCrypt加密）',
    real_name       VARCHAR(64)     DEFAULT NULL COMMENT '真实姓名',
    phone           VARCHAR(32)     DEFAULT NULL COMMENT '手机号',
    email           VARCHAR(128)    DEFAULT NULL COMMENT '邮箱',
    org_code        VARCHAR(64)     DEFAULT NULL COMMENT '所属机构编码',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 0=停用）',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户表';

-- -----------------------------------------------------------
-- 19. 系统角色表
-- -----------------------------------------------------------
CREATE TABLE sys_role (
    id              BIGINT          NOT NULL COMMENT '主键',
    role_code       VARCHAR(64)     NOT NULL COMMENT '角色编码',
    role_name       VARCHAR(100)    NOT NULL COMMENT '角色名称',
    remark          VARCHAR(500)    DEFAULT NULL COMMENT '备注',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 0=停用）',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色表';

-- -----------------------------------------------------------
-- 20. 用户角色关联表
-- -----------------------------------------------------------
CREATE TABLE sys_user_role (
    id              BIGINT          NOT NULL COMMENT '主键',
    user_id         BIGINT          NOT NULL COMMENT '用户ID',
    role_id         BIGINT          NOT NULL COMMENT '角色ID',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色关联表';

-- -----------------------------------------------------------
-- 21. API Key 表
-- -----------------------------------------------------------
CREATE TABLE sys_api_key (
    id              BIGINT          NOT NULL COMMENT '主键',
    key_name        VARCHAR(100)    NOT NULL COMMENT 'Key 名称',
    api_key         VARCHAR(128)    NOT NULL COMMENT 'API Key 值',
    api_secret      VARCHAR(256)    NOT NULL COMMENT 'API Secret（加密存储）',
    system_name     VARCHAR(100)    NOT NULL COMMENT '所属系统名称',
    allowed_domains VARCHAR(500)    DEFAULT NULL COMMENT '允许访问的数据域（逗号分隔，空=全部）',
    allowed_orgs    VARCHAR(500)    DEFAULT NULL COMMENT '允许访问的机构编码（逗号分隔，空=全部）',
    expire_time     DATETIME        DEFAULT NULL COMMENT '过期时间（空=永不过期）',
    status          TINYINT         NOT NULL DEFAULT 1 COMMENT '状态（1=启用 0=停用）',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted         TINYINT         NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_api_key (api_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API Key 表';

-- -----------------------------------------------------------
-- 22. 操作日志表
-- -----------------------------------------------------------
CREATE TABLE sys_operation_log (
    id              BIGINT          NOT NULL COMMENT '主键',
    module          VARCHAR(64)     DEFAULT NULL COMMENT '操作模块',
    operation       VARCHAR(64)     DEFAULT NULL COMMENT '操作类型',
    method          VARCHAR(200)    DEFAULT NULL COMMENT '请求方法',
    request_url     VARCHAR(500)    DEFAULT NULL COMMENT '请求URL',
    request_params  TEXT            DEFAULT NULL COMMENT '请求参数',
    response_code   INT             DEFAULT NULL COMMENT '响应码',
    cost_time       BIGINT          DEFAULT NULL COMMENT '耗时(ms)',
    operator        VARCHAR(64)     DEFAULT NULL COMMENT '操作人',
    operator_ip     VARCHAR(64)     DEFAULT NULL COMMENT '操作人IP',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_operator (operator),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志表';
