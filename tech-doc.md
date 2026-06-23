# 主数据平台 — 技术栈开发文档

## 一、技术架构总览

采用**模块化单体架构（Modular Monolith）**，代码按业务域拆分为独立 Maven Module，部署时打成一个 JAR 包，Nginx 负载均衡 2 实例运行。

```
                          ┌─────────────────────────┐
                          │    Nginx（反向代理+负载均衡）│
                          └────────────┬────────────┘
                                       │
                 ┌─────────────────────┼─────────────────────┐
                 │                     │                     │
          ┌──────┴──────┐    ┌────────┴────────┐    ┌──────┴──────┐
          │  前端静态资源  │    │ MDM 应用实例 ×2  │    │  Knife4j    │
          │  Vue 3 SPA   │    │ （Spring Boot）  │    │  API 文档    │
          └─────────────┘    └────────┬────────┘    └─────────────┘
                                      │
                                      │  单体内部模块（进程内调用）
                 ┌────────────────────┼────────────────────┐
                 │                    │                    │
          ┌──────┴──────┐   ┌───────┴───────┐   ┌───────┴───────┐
          │ organization │   │    supply     │   │   clinical    │
          │ 机构/科室/员工 │   │ 药品/耗材/物资  │   │ 诊疗项目/医嘱/规范│
          └─────────────┘   └───────────────┘   └───────────────┘
                 │                    │                    │
          ┌──────┴──────┐   ┌───────┴───────┐   ┌───────┴───────┐
          │  facility    │   │    sync       │   │   quality     │
          │  设备主数据    │   │ 同步/校验/对照  │   │  质量监控/报表  │
          └─────────────┘   └───────────────┘   └───────────────┘
                                      │
                               ┌──────┴──────┐
                               │    audit     │
                               │ 变更历史/日志  │
                               └─────────────┘
                                      │
                        ┌─────────────┼─────────────┐
                        │             │             │
                 ┌──────┴──────┐ ┌───┴────┐        │
                 │ MySQL 9.3   │ │ Redis 7│        │
                 │ (单机+备份)  │ │ (单机)  │        │
                 └─────────────┘ └────────┘        │
                                            ┌──────┴──────┐
                                            │ Prometheus  │
                                            │ + Grafana   │
                                            └─────────────┘
```

---

## 二、技术栈版本清单

### 2.1 前端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Node.js | ≥ 20 LTS | 前端构建运行环境 |
| Vue | 3.5+ | 前端框架 |
| Vite | 6.x | 构建工具 |
| TypeScript | 5.x | 类型安全 |
| Element Plus | 2.x | UI 组件库 |
| Vue Router | 4.x | 路由管理 |
| Pinia | 2.x | 状态管理 |
| Axios | 1.x | HTTP 请求 |
| ECharts | 5.x | 数据质量报表、驾驶舱图表 |
| xlsx / ExcelJS | 最新稳定版 | Excel 导入导出（对照表、批量审批） |

### 2.2 后端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| JDK | 21 LTS | 运行环境 |
| Spring Boot | 3.4+ | 应用框架 |
| MyBatis-Plus | 3.5+ | ORM 框架 |
| Sa-Token | 1.39+ | 认证授权（OAuth2.0 / API Key） |
| MapStruct | 1.6+ | 对象映射（DTO ↔ Entity） |
| Hibernate Validator | 8.x | 参数校验（Bean Validation 3.0） |
| Redisson | 3.x | Redis 客户端 + 分布式锁 |
| Knife4j | 4.x | API 文档（基于 OpenAPI 3） |
| Lombok | 最新稳定版 | 代码简化 |
| Spring Boot Actuator | 内置 | 健康检查 + Prometheus 指标暴露 |

### 2.3 中间件

| 技术 | 版本 | 用途 |
|------|------|------|
| MySQL | 9.3 | 主数据存储（单机 + 定时备份） |
| Redis | 7.x | 缓存 + 幂等键（单机） |

### 2.4 运维与监控

| 技术 | 版本 | 用途 |
|------|------|------|
| Nginx | 1.26+ | 反向代理、负载均衡、前端静态资源、限流 |
| Prometheus | 2.x | 指标采集（通过 Actuator 暴露） |
| Grafana | 10.x | 监控面板 |

> 生产环境可按需引入 Docker 容器化部署，开发阶段直接本地运行即可。

---

## 三、工程结构

### 3.1 后端工程结构

```
mdm-platform/
├── pom.xml                          # 父 POM（统一依赖版本、插件管理）
│
├── mdm-common/                      # 公共模块
│   ├── pom.xml
│   └── src/main/java/com/mdm/common
│       ├── core/                    # 统一响应、分页、常量、枚举
│       │   ├── Result.java
│       │   ├── PageResult.java
│       │   ├── BaseEntity.java      # 实体基类（id, createBy, createTime, updateBy, updateTime, deleted）
│       │   └── MdmConstants.java
│       ├── exception/               # 统一异常定义
│       │   ├── BizException.java
│       │   ├── ValidationException.java
│       │   ├── ConflictException.java
│       │   └── GlobalExceptionHandler.java
│       ├── config/
│       │   ├── MyBatisPlusConfig.java      # 分页插件、乐观锁插件、审计字段填充
│       │   ├── RedisConfig.java
│       │   └── JacksonConfig.java
│       ├── util/
│       │   ├── SnowflakeIdGenerator.java
│       │   ├── EncryptUtil.java            # 敏感字段加解密
│       │   └── ExcelUtil.java
│       └── log/
│           └── OperationLogAspect.java
│
├── mdm-auth/                        # 认证授权模块
│   ├── pom.xml
│   └── src/main/java/com/mdm/auth
│       ├── controller/
│       │   └── AuthController.java
│       ├── service/
│       │   ├── AuthService.java
│       │   └── ApiKeyService.java
│       ├── model/
│       │   ├── entity/
│       │   ├── dto/
│       │   └── vo/
│       ├── mapper/
│       └── config/
│           └── SaTokenConfig.java
│
├── mdm-organization/                # 组织人事模块（机构 + 科室 + 员工）
│   ├── pom.xml
│   └── src/main/java/com/mdm/organization
│       ├── controller/
│       │   ├── OrgController.java
│       │   ├── DeptController.java
│       │   └── EmployeeController.java
│       ├── service/
│       │   ├── OrgService.java
│       │   ├── DeptService.java
│       │   └── EmployeeService.java
│       ├── mapper/
│       ├── model/
│       │   ├── entity/
│       │   ├── dto/
│       │   ├── vo/
│       │   └── query/
│       ├── convert/                 # MapStruct 转换器
│       └── enums/
│
├── mdm-supply/                      # 供应链模块（药品 + 耗材 + 物资）
│   ├── pom.xml
│   └── src/main/java/com/mdm/supply
│       ├── controller/
│       │   ├── DrugController.java
│       │   ├── MaterialController.java
│       │   └── SupplyItemController.java
│       ├── service/
│       ├── mapper/
│       ├── model/
│       ├── convert/
│       └── enums/
│
├── mdm-clinical/                    # 临床业务模块（诊疗项目 + 医嘱 + 规范）
│   ├── pom.xml
│   └── src/main/java/com/mdm/clinical
│       ├── controller/
│       │   ├── TreatmentItemController.java
│       │   ├── OrderController.java
│       │   └── StandardController.java
│       ├── service/
│       ├── mapper/
│       ├── model/
│       ├── convert/
│       └── enums/
│
├── mdm-facility/                    # 设备模块（设备主数据）
│   ├── pom.xml
│   └── src/main/java/com/mdm/facility
│       ├── controller/
│       │   └── DeviceController.java
│       ├── service/
│       ├── mapper/
│       ├── model/
│       ├── convert/
│       └── enums/
│
├── mdm-sync/                        # 数据同步模块
│   ├── pom.xml
│   └── src/main/java/com/mdm/sync
│       ├── inbound/                 # 入站同步（上游 → MDM）
│       │   ├── callback/           # HTTP 回调接收（A通道）
│       │   │   └── InboundController.java
│       │   ├── polling/            # 定时增量轮询（B通道）
│       │   │   └── IncrementalPollingTask.java
│       │   └── batch/              # 批量导入
│       │       └── BatchImportService.java
│       ├── outbound/                # 出站（MDM → 下游）
│       │   └── DownstreamController.java
│       ├── validation/              # 入库校验层
│       │   ├── IdempotentValidator.java
│       │   ├── VersionValidator.java
│       │   ├── ApprovalValidator.java
│       │   ├── StandardValidator.java
│       │   ├── RelationValidator.java
│       │   └── ValidationPipeline.java
│       ├── conflict/
│       │   └── ConflictResolver.java
│       └── mapping/                 # 对照映射表管理
│           ├── MappingController.java
│           └── MappingService.java
│
├── mdm-quality/                     # 数据质量模块
│   ├── pom.xml
│   └── src/main/java/com/mdm/quality
│       ├── controller/
│       │   └── QualityController.java
│       ├── checker/
│       │   ├── CompletenessChecker.java
│       │   ├── AccuracyChecker.java
│       │   ├── ConsistencyChecker.java
│       │   └── UniquenessChecker.java
│       ├── report/
│       ├── alert/
│       └── scheduler/               # @Scheduled 定时质量检查
│
├── mdm-audit/                       # 审计模块
│   ├── pom.xml
│   └── src/main/java/com/mdm/audit
│       ├── controller/
│       │   └── AuditController.java
│       ├── listener/                # Spring Event 监听
│       │   └── ChangeHistoryListener.java
│       ├── service/
│       │   └── ChangeHistoryService.java
│       └── model/
│
├── mdm-app/                         # 启动模块（唯一的 Spring Boot Application）
│   ├── pom.xml
│   └── src/main
│       ├── java/com/mdm
│       │   └── MdmApplication.java
│       └── resources/
│           ├── application.yml
│           ├── application-dev.yml
│           ├── application-test.yml
│           ├── application-prod.yml
│           └── logback-spring.xml
│
└── docs/
    ├── sql/
    │   ├── init-schema.sql
    │   ├── init-data.sql
    │   └── migration/
    └── api/
```

**关键设计说明**：

- `mdm-app` 是唯一的启动模块，依赖所有业务模块，打成一个 FAT JAR
- 各业务模块之间通过 **Spring Event**（进程内事件）解耦
- 定时任务使用 Spring 自带的 **@Scheduled**，无需额外调度框架
- 上游同步的 A 通道是 HTTP 回调（InboundController），B 通道是定时轮询（IncrementalPollingTask）

**数据域扩展说明**：

新增数据域时，根据规模选择两种方式：

```
方式一：小域 → 放入已有模块
  例：床位（BED）→ 放入 mdm-organization 模块
  新增 BedController / BedService / BedMapper 即可

方式二：大域 → 新建 Maven 模块
  例：收费项目 + 医保目录 → 新建 mdm-finance 模块
  （当前 mdm-facility 就是这种方式的实例）
  步骤：
    1. 新建 mdm-finance/ 目录，结构与 mdm-supply 一致
    2. mdm-app/pom.xml 中添加对 mdm-finance 的依赖
    3. 在 mdm_domain_registry 中注册新域
    4. 实现 DomainSyncHandler 接口（见下方）
```

**DomainSyncHandler 接口**（定义在 `mdm-common` 中）：

```java
/**
 * 各数据域实现此接口，mdm-sync 模块根据 domainCode 自动路由到对应实现。
 * Spring 容器启动时自动扫描所有实现类并注册。
 */
public interface DomainSyncHandler {
    /** 返回此处理器负责的域编码 */
    String getDomainCode();
    /** 处理入站数据（校验通过后的写入逻辑） */
    void handleInbound(InboundDataDTO data);
    /** 返回增量数据（供下游拉取） */
    PageResult<?> queryIncremental(String since, int pageNum, int pageSize);
    /** 返回全量快照（供下游拉取） */
    PageResult<?> querySnapshot(int pageNum, int pageSize);
}
```

`mdm-sync` 模块中的路由逻辑：

```java
@Component
public class DomainSyncRouter {
    private final Map<String, DomainSyncHandler> handlerMap;

    public DomainSyncRouter(List<DomainSyncHandler> handlers) {
        this.handlerMap = handlers.stream()
            .collect(Collectors.toMap(DomainSyncHandler::getDomainCode, h -> h));
    }

    public DomainSyncHandler getHandler(String domainCode) {
        DomainSyncHandler handler = handlerMap.get(domainCode);
        if (handler == null) {
            throw new BizException("不支持的数据域: " + domainCode);
        }
        return handler;
    }
}
```

### 3.2 Maven 模块依赖关系

```
mdm-app（启动模块，依赖以下所有模块）
  ├── mdm-auth
  ├── mdm-organization
  ├── mdm-supply
  ├── mdm-clinical
  ├── mdm-facility
  ├── mdm-sync
  ├── mdm-quality
  └── mdm-audit

各业务模块均依赖：
  └── mdm-common

模块间不直接依赖，通过 Spring Event 或 mdm-common 中定义的接口通信
```

### 3.3 前端工程结构

```
mdm-web/
├── public/
├── src/
│   ├── api/
│   │   ├── auth.ts
│   │   ├── organization.ts
│   │   ├── supply.ts
│   │   ├── clinical.ts
│   │   ├── sync.ts
│   │   ├── quality.ts
│   │   └── audit.ts
│   ├── views/
│   │   ├── login/
│   │   ├── dashboard/
│   │   ├── organization/
│   │   │   ├── org/
│   │   │   ├── dept/
│   │   │   └── employee/
│   │   ├── supply/
│   │   │   ├── drug/
│   │   │   ├── material/
│   │   │   └── supply-item/
│   │   ├── clinical/
│   │   │   ├── treatment-item/
│   │   │   ├── order/
│   │   │   └── standard/
│   │   ├── facility/
│   │   │   └── device/
│   │   ├── sync/
│   │   │   ├── mapping/
│   │   │   ├── monitor/
│   │   │   └── exception/
│   │   ├── quality/
│   │   │   ├── report/
│   │   │   └── alert/
│   │   ├── audit/
│   │   │   ├── change-history/
│   │   │   └── operation-log/
│   │   └── system/
│   │       ├── user/
│   │       ├── role/
│   │       └── api-key/
│   ├── components/
│   │   ├── MdmCodeInput/
│   │   ├── TreeSelector/
│   │   ├── ChangeHistoryDrawer/
│   │   ├── ImportExcel/
│   │   └── ExportExcel/
│   ├── composables/
│   │   ├── useTable.ts
│   │   ├── useExport.ts
│   │   └── useDict.ts
│   ├── router/
│   ├── stores/
│   ├── utils/
│   ├── styles/
│   ├── App.vue
│   └── main.ts
├── .env.development
├── .env.production
├── vite.config.ts
├── tsconfig.json
└── package.json
```

---

## 四、数据库设计

### 4.1 数据库规范

| 规范项 | 约定 |
|-------|------|
| 命名风格 | 表名、字段名统一 snake_case |
| 主键 | 雪花算法生成 BIGINT 类型 ID |
| 逻辑删除 | 使用 `deleted` 字段（0=未删除，1=已删除），不做物理删除 |
| 审计字段 | 所有表必须包含 `create_by`、`create_time`、`update_by`、`update_time` |
| 版本号 | 所有主数据表必须包含 `version` 字段（乐观锁） |
| 字符集 | utf8mb4，排序规则 utf8mb4_general_ci |
| 索引 | 编码字段建唯一索引，外键字段建普通索引，状态字段按需建索引 |

### 4.2 核心表结构

共 16 张业务表，详细 DDL 见 `docs/sql/init-schema.sql`。

| 数据域 | 表名 | 说明 |
|-------|------|------|
| 机构 | mdm_org | 机构主数据 |
| 科室 | mdm_dept | 科室主数据 |
| 员工 | mdm_employee | 员工主数据 |
| 员工 | mdm_employee_position | 员工多机构任职记录 |
| 药品 | mdm_drug | 药品主数据（整包+拆零双记录模式，详见下方说明） |
| 耗材 | mdm_material | 耗材主数据 |
| 物资 | mdm_supply_item | 物资主数据（非医疗类） |
| 诊疗项目 | mdm_treatment_item | 诊疗项目主数据 |
| 设备 | mdm_device | 设备主数据 |
| 医嘱 | mdm_order | 医嘱主数据 |
| 医嘱 | mdm_order_item | 医嘱关联药品/耗材/诊疗项目 |
| 医嘱 | mdm_order_dept | 医嘱适用科室 |
| 规范 | mdm_standard | 规范主数据 |
| 通用 | mdm_code_mapping | 对照映射表 |
| 通用 | mdm_change_history | 变更历史表 |
| 通用 | mdm_validation_exception | 校验异常表 |
| 通用 | mdm_domain_registry | 数据域注册表（见 4.3） |

### 4.2.1 药品拆零双记录模式

`mdm_drug` 表采用**整包 + 拆零双记录模式**：同一药品存储为两条独立记录，通过 `parent_drug_code` 父子关联。

**核心字段说明**：

| 字段 | 类型 | 说明 |
|------|------|------|
| drug_code | VARCHAR(64) | 药品编码，整包为 `DRG00000001`，拆零为 `DRG00000001-S` |
| parent_drug_code | VARCHAR(64) | 父药品编码，整包记录为 NULL，拆零记录指向整包编码 |
| is_split | TINYINT | 0=整包记录，1=拆零记录 |
| unit | VARCHAR(32) | 计量单位（整包：盒/瓶/袋；拆零：片/支/粒/ml） |
| unit_price | DECIMAL(10,4) | 单价（元），整包和拆零各自维护 |
| pack_unit | VARCHAR(32) | 包装单位（盒/瓶/袋） |
| pack_qty | INT | 包装数量（1包装单位 = N拆零单位） |
| split_unit | VARCHAR(32) | 拆零单位（片/支/粒/ml） |
| split_price | DECIMAL(10,4) | 拆零单价（可手动填写或自动计算） |

**业务规则**：

```
新增药品（POST /api/v1/supply/drugs）：
  ├── 创建整包记录（is_split=0, drug_code=DRG00000001）
  ├── 如果 enableSplit=true 且填写了拆零单位和包装数量
  │     └── 自动创建拆零子记录（is_split=1, drug_code=DRG00000001-S, parent_drug_code=DRG00000001）
  └── 拆零单价未填写时，按 整包单价 ÷ 包装数量 自动计算（保留4位小数）

更新药品（PUT /api/v1/supply/drugs/{drugCode}）：
  ├── 更新整包记录
  ├── 联动更新拆零子记录（通用名、厂家、剂型、规格等字段自动同步）
  ├── 如果新开启拆零 → 自动创建拆零子记录
  └── 如果关闭拆零 → 已有拆零子记录状态置为"停用"

列表查询（GET /api/v1/supply/drugs）：
  ├── 只查 is_split=0 的整包记录进行分页
  └── 每条整包记录挂载其拆零子记录（children 字段）
```

**数据示例**：

| drug_code | parent_drug_code | is_split | generic_name | unit | unit_price |
|-----------|-----------------|----------|-------------|------|-----------|
| DRG00000001 | NULL | 0 | 阿莫西林胶囊 | 盒 | 12.0000 |
| DRG00000001-S | DRG00000001 | 1 | 阿莫西林胶囊（拆零） | 粒 | 1.0000 |

### 4.3 数据域注册表（扩展性核心）

新增数据域时，只需建表并在此注册表中插入一条记录，同步、校验、质量检查、缓存等框架层代码根据注册表配置自动适配。

```sql
CREATE TABLE mdm_domain_registry (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    domain_code     VARCHAR(32)     NOT NULL COMMENT '域编码（如 ORG, DEPT, DRUG, DEVICE）',
    domain_name     VARCHAR(100)    NOT NULL COMMENT '域名称',
    module_name     VARCHAR(64)     NOT NULL COMMENT '所属 Maven 模块（如 mdm-organization）',
    table_name      VARCHAR(64)     NOT NULL COMMENT '主表名（如 mdm_org）',
    code_field      VARCHAR(64)     NOT NULL COMMENT '编码字段名（如 org_code）',
    code_prefix     VARCHAR(16)     NOT NULL COMMENT '编码前缀（如 ORG）',
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
```

**初始化数据**（`docs/sql/init-data.sql` 中包含）：

```sql
INSERT INTO mdm_domain_registry (domain_code, domain_name, module_name, table_name, code_field, code_prefix, sort_order) VALUES
('ORG',       '机构',     'mdm-organization', 'mdm_org',              'org_code',           'ORG', 1),
('DEPT',      '科室',     'mdm-organization', 'mdm_dept',             'dept_code',          'DPT', 2),
('EMPLOYEE',  '员工',     'mdm-organization', 'mdm_employee',         'emp_code',           'EMP', 3),
('DRUG',      '药品',     'mdm-supply',       'mdm_drug',             'drug_code',          'DRG', 4),
('MATERIAL',  '耗材',     'mdm-supply',       'mdm_material',         'material_code',      'MAT', 5),
('SUPPLY_ITEM','物资',    'mdm-supply',       'mdm_supply_item',      'supply_item_code',   'SPL', 6),
('TREATMENT', '诊疗项目', 'mdm-clinical',     'mdm_treatment_item',   'treatment_code',     'TRT', 7),
('ORDER',     '医嘱',     'mdm-clinical',     'mdm_order',            'order_code',         'ODR', 8),
('STANDARD',  '规范',     'mdm-clinical',     'mdm_standard',         'std_code',           'STD', 9),
('DEVICE',    '设备',     'mdm-facility',     'mdm_device',           'device_code',        'DEV', 10);
```

---

## 五、API 设计规范

### 5.1 RESTful 规范

| 规范项 | 约定 |
|-------|------|
| 基础路径 | `/api/v1/{domain}/{resource}` |
| 请求方式 | GET（查询）、POST（新增）、PUT（全量更新）、PATCH（部分更新）、DELETE（删除） |
| 响应格式 | 统一 JSON 结构 |
| 分页参数 | `pageNum`（页码，从1开始）、`pageSize`（每页条数，默认20，最大200） |
| 排序参数 | `orderBy=field:asc/desc` |
| 时间格式 | ISO 8601（`yyyy-MM-dd'T'HH:mm:ss`） |

### 5.2 统一响应结构

```json
{
    "code": 200,
    "message": "success",
    "data": {},
    "timestamp": "2026-02-26T10:30:00"
}
```

```json
{
    "code": 200,
    "message": "success",
    "data": {
        "list": [],
        "total": 100,
        "pageNum": 1,
        "pageSize": 20
    },
    "timestamp": "2026-02-26T10:30:00"
}
```

### 5.3 错误码定义

| 错误码 | 含义 | 说明 |
|-------|------|------|
| 200 | 成功 | |
| 400 | 请求参数错误 | 参数校验失败 |
| 401 | 未认证 | Token 无效或过期 |
| 403 | 无权限 | 角色权限不足 |
| 404 | 资源不存在 | |
| 409 | 数据冲突 | 编码重复、版本冲突等 |
| 422 | 业务校验失败 | 入库校验不通过 |
| 429 | 请求过于频繁 | 触发 Nginx 限流 |
| 500 | 服务器内部错误 | |

### 5.4 核心 API 列表

#### 上游入站接口（上游系统调用）

```
POST    /api/v1/sync/inbound                # 上游系统 HTTP 回调写入（A通道）
POST    /api/v1/sync/inbound/batch          # 批量导入
```

#### 组织人事域

```
GET     /api/v1/org/orgs                    # 机构列表（分页/树形）
GET     /api/v1/org/orgs/{orgCode}          # 机构详情
GET     /api/v1/org/orgs/{orgCode}/history  # 机构变更历史
POST    /api/v1/org/orgs                    # 新增机构
PUT     /api/v1/org/orgs/{orgCode}          # 更新机构
PATCH   /api/v1/org/orgs/{orgCode}/status   # 变更机构状态

GET     /api/v1/org/depts                   # 科室列表
GET     /api/v1/org/depts/tree/{orgCode}    # 科室树（按机构）
GET     /api/v1/org/depts/{deptCode}        # 科室详情
POST    /api/v1/org/depts                   # 新增科室
PUT     /api/v1/org/depts/{deptCode}        # 更新科室

GET     /api/v1/org/employees               # 员工列表
GET     /api/v1/org/employees/{empCode}     # 员工详情
GET     /api/v1/org/employees/{empCode}/positions  # 员工任职记录
POST    /api/v1/org/employees               # 新增员工
PUT     /api/v1/org/employees/{empCode}     # 更新员工
POST    /api/v1/org/employees/{empCode}/positions  # 新增任职记录
```

#### 供应链域

```
GET     /api/v1/supply/drugs                        # 药品列表（整包+拆零子记录，分页只计整包）
GET     /api/v1/supply/drugs/{drugCode}             # 药品详情（含拆零子记录 children）
GET     /api/v1/supply/drugs/search?keyword=xxx     # 药品搜索
POST    /api/v1/supply/drugs                        # 新增药品（enableSplit=true 时自动创建拆零子记录）
PUT     /api/v1/supply/drugs/{drugCode}             # 更新药品（联动更新/创建/停用拆零子记录）

GET     /api/v1/supply/materials                    # 耗材列表
GET     /api/v1/supply/materials/{materialCode}     # 耗材详情
POST    /api/v1/supply/materials                    # 新增耗材
PUT     /api/v1/supply/materials/{materialCode}     # 更新耗材

GET     /api/v1/supply/supply-items                 # 物资列表
GET     /api/v1/supply/supply-items/{supplyItemCode}  # 物资详情
POST    /api/v1/supply/supply-items                 # 新增物资
PUT     /api/v1/supply/supply-items/{supplyItemCode}  # 更新物资
```

#### 临床业务域

```
GET     /api/v1/clinical/treatment-items             # 诊疗项目列表
GET     /api/v1/clinical/treatment-items/{treatmentCode}  # 诊疗项目详情
POST    /api/v1/clinical/treatment-items             # 新增诊疗项目
PUT     /api/v1/clinical/treatment-items/{treatmentCode}  # 更新诊疗项目

GET     /api/v1/clinical/orders                     # 医嘱列表
GET     /api/v1/clinical/orders/{orderCode}         # 医嘱详情
POST    /api/v1/clinical/orders                     # 新增医嘱
PUT     /api/v1/clinical/orders/{orderCode}         # 更新医嘱

GET     /api/v1/clinical/standards                  # 规范列表
GET     /api/v1/clinical/standards/{stdCode}        # 规范详情
POST    /api/v1/clinical/standards                  # 新增规范
PUT     /api/v1/clinical/standards/{stdCode}        # 更新规范
```

#### 设备域

```
GET     /api/v1/facility/devices                    # 设备列表
GET     /api/v1/facility/devices/{deviceCode}       # 设备详情
GET     /api/v1/facility/devices/{deviceCode}/history  # 设备变更历史
POST    /api/v1/facility/devices                    # 新增设备
PUT     /api/v1/facility/devices/{deviceCode}       # 更新设备
PATCH   /api/v1/facility/devices/{deviceCode}/status   # 变更设备状态
```

#### 同步与对照

```
GET     /api/v1/sync/mappings                       # 对照映射列表
POST    /api/v1/sync/mappings                       # 新增对照映射
POST    /api/v1/sync/mappings/batch                 # 批量导入对照映射
PUT     /api/v1/sync/mappings/{id}                  # 更新对照映射
GET     /api/v1/sync/mappings/translate             # 编码翻译

GET     /api/v1/sync/status                         # 同步状态总览
GET     /api/v1/sync/exceptions                     # 校验异常列表
PATCH   /api/v1/sync/exceptions/{id}/handle         # 处理校验异常
```

#### 数据质量

```
GET     /api/v1/quality/dashboard                   # 质量驾驶舱数据
GET     /api/v1/quality/reports/daily               # 日报
GET     /api/v1/quality/reports/weekly              # 周报
GET     /api/v1/quality/metrics                     # 质量指标明细
```

#### 审计

```
GET     /api/v1/audit/changes                       # 变更历史查询
GET     /api/v1/audit/changes/{dataCode}            # 指定数据的变更链
GET     /api/v1/audit/operations                    # 操作日志查询
```

#### 下游系统专用接口

```
GET     /api/v1/downstream/incremental?domain=DRUG&since=2026-02-25T00:00:00
GET     /api/v1/downstream/snapshot?domain=DRUG&asOf=2026-02-25T00:00:00
```

> 下游接口通过 `domain` 参数路由，新增数据域后无需新增接口，`DomainSyncRouter` 自动根据 `domain_code` 分发到对应处理器。

#### 扩展数据域的 API 路径规范

新增数据域时，API 路径遵循统一模板，无需额外约定：

```
路径模板：/api/v1/{moduleName}/{resourceName}

示例（假设新增设备主数据，归入 mdm-facility 模块）：
GET     /api/v1/facility/devices                    # 设备列表
GET     /api/v1/facility/devices/{deviceCode}       # 设备详情
GET     /api/v1/facility/devices/{deviceCode}/history  # 变更历史
POST    /api/v1/facility/devices                    # 新增设备
PUT     /api/v1/facility/devices/{deviceCode}       # 更新设备
PATCH   /api/v1/facility/devices/{deviceCode}/status   # 变更状态

示例（假设新增收费项目，归入 mdm-finance 模块）：
GET     /api/v1/finance/charge-items                # 收费项目列表
POST    /api/v1/finance/charge-items                # 新增收费项目
...
```

下游系统无需关心具体域的 CRUD 接口，统一通过 `/api/v1/downstream/incremental?domain=DEVICE` 拉取增量数据即可。

---

## 六、缓存设计

### 6.1 缓存策略

| 缓存数据 | Key 格式 | 过期时间 | 更新策略 |
|---------|---------|---------|---------|
| 机构详情 | `mdm:org:{orgCode}` | 24h | 变更时主动删除 |
| 科室树 | `mdm:dept:tree:{orgCode}` | 12h | 科室变更时删除对应机构的树 |
| 员工详情 | `mdm:emp:{empCode}` | 24h | 变更时主动删除 |
| 药品详情（含拆零子记录） | `mdm:drug:{drugCode}` | 24h | 整包或拆零变更时主动删除整包缓存 |
| 诊疗项目详情 | `mdm:treatment:{treatmentCode}` | 24h | 变更时主动删除 |
| 设备详情 | `mdm:device:{deviceCode}` | 24h | 变更时主动删除 |
| 物资详情 | `mdm:supply_item:{supplyItemCode}` | 24h | 变更时主动删除 |
| 对照映射 | `mdm:mapping:{mdmCode}:{localSystem}` | 12h | 映射变更时删除 |
| 编码自增序列 | `mdm:seq:{domain}` | 永不过期 | Redis INCR 原子自增 |
| 请求幂等键 | `mdm:idempotent:{requestId}` | 48h | 写入成功后设置 |
| 域注册表缓存 | `mdm:domain:registry` | 1h | 注册表变更时删除 |

> **扩展域缓存 Key 模板**：新增数据域的缓存 Key 统一遵循 `mdm:{domainCode}:{bizCode}` 格式，过期时间从 `mdm_domain_registry.cache_ttl_hours` 读取。例如新增设备域后，缓存 Key 为 `mdm:device:{deviceCode}`，TTL 由注册表配置决定，无需硬编码。

### 6.2 缓存更新模式

采用 **Cache-Aside** 模式：

```
读取流程：
  1. 查 Redis 缓存 → 命中则返回
  2. 未命中 → 查 MySQL → 写入 Redis → 返回
  3. Redis 故障 → 直接查 MySQL（自动降级）

写入流程：
  1. 写入 MySQL（本地事务，药品域含整包+拆零联动写入）
  2. 删除 Redis 缓存（药品域同时删除整包和拆零的缓存）
  3. 发布 Spring Event → audit 模块写入变更历史（整包和拆零各自记录变更）
```

---

## 七、开发规范

### 7.1 代码分层规范

```
Controller 层：参数校验、权限校验、调用 Service、返回 VO
    ↓
Service 层：业务逻辑、事务管理（@Transactional）、调用 Mapper、发布 Spring Event
    ↓
Mapper 层：数据库操作（MyBatis-Plus）
    ↓
Entity：数据库实体，与表结构一一对应
DTO：模块间传输对象、上游回调请求体
VO：前端展示对象
Query：查询参数对象
```

### 7.2 模块间通信规范

模块之间**禁止直接依赖**，通过以下方式通信：

```
方式一：Spring Event（推荐，异步解耦）
  Service 写入数据后发布事件 → 其他模块监听处理
  示例：sync 模块写入主数据 → 发布 DataChangeEvent → audit 模块写入变更历史

方式二：mdm-common 中定义公共接口
  模块 A 需要查询模块 B 的数据时，通过 common 中定义的接口调用
  示例：sync 模块校验关联完整性时，调用 OrgQueryService 查询机构是否存在
```

### 7.3 异常处理规范

```java
// 业务异常（可预期）→ 返回具体错误码和提示
throw new BizException(ErrorCode.ORG_CODE_DUPLICATE, "机构编码已存在: ORG000001");

// 校验异常 → 返回 422
throw new ValidationException(ErrorCode.VALIDATION_FAILED, "必填字段缺失: orgName");

// 版本冲突 → 返回 409
throw new ConflictException(ErrorCode.VERSION_CONFLICT, "数据已被其他操作更新，请刷新后重试");
```

### 7.4 日志规范

| 级别 | 使用场景 |
|------|---------|
| ERROR | 系统异常、数据库连接失败 |
| WARN | 入库校验拦截、版本冲突、请求重复 |
| INFO | 主数据变更操作、同步完成、回调接收 |
| DEBUG | SQL 执行详情、缓存命中情况（仅开发环境开启） |

### 7.5 Git 分支规范

```
main                    # 生产分支
├── develop             # 开发主分支
├── feature/xxx         # 功能分支（从 develop 拉出）
├── hotfix/xxx          # 紧急修复（从 main 拉出）
└── release/x.x.x      # 发布分支（从 develop 拉出）
```

---

## 八、部署架构

### 8.1 环境规划

| 环境 | 用途 | 配置 |
|------|------|------|
| dev | 开发环境 | 本地运行（MySQL + Redis 装本地或用远程共享实例） |
| test | 测试环境 | 单实例，独立中间件 |
| prod | 生产环境 | 2 实例 + Nginx 负载均衡 |

### 8.2 生产环境部署清单

| 组件 | 实例数 | 配置建议 |
|------|-------|---------|
| Nginx | 1 | 2C4G |
| **MDM 应用** | **2** | **4C8G（单个 JAR，2 实例负载均衡）** |
| MySQL | 1 | 4C16G，SSD 200G |
| Redis | 1 | 2C4G |
| Prometheus + Grafana | 1 | 2C4G |

**总计**：约 **20C48G**，5 台服务器（或等量云资源）即可承载。

### 8.3 开发环境搭建

开发阶段不使用 Docker，本地直接安装中间件：

**1. 创建数据库**（MySQL 9.3 已安装）

```sql
CREATE DATABASE mdm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

- 执行 `docs/sql/init-schema.sql` 建表
- 执行 `docs/sql/init-data.sql` 初始化数据

**2. 安装 Redis 7**
- 下载安装 Redis 7，默认配置启动即可（端口 6379）

**3. 启动应用**

```bash
# 进入项目根目录，编译打包
mvn clean package -DskipTests

# 启动应用（开发环境）
java -jar mdm-app/target/mdm-app.jar --spring.profiles.active=dev
```

**4. 开发环境配置（application-dev.yml）**

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mdm?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: xcxx9492
  data:
    redis:
      host: localhost
      port: 6379
```

**5. 启动前端**

```bash
cd mdm-web
npm install
npm run dev
```

---

*文档版本：v3.0*
*编制日期：2026年2月*
*配套文档：[主数据平台建设方案](./readme.md)*
