package com.mdm.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    CONFLICT(409, "数据冲突"),
    VALIDATION_FAILED(422, "业务校验失败"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    CODE_DUPLICATE(1001, "编码已存在"),
    VERSION_CONFLICT(1002, "数据已被其他操作更新，请刷新后重试"),
    RELATION_NOT_FOUND(1003, "关联数据不存在"),
    STATUS_INVALID(1004, "状态不允许此操作"),
    DOMAIN_NOT_SUPPORTED(1005, "不支持的数据域"),
    IDEMPOTENT_REJECT(1006, "重复请求"),
    NO_APPROVAL(1007, "未经审批的变更"),
    IMPORT_ERROR(1008, "导入数据错误"),
    ;

    private final int code;
    private final String message;
}
