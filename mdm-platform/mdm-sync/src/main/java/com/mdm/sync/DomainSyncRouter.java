package com.mdm.sync;

import com.mdm.common.core.DomainSyncHandler;
import com.mdm.common.exception.BizException;
import com.mdm.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            throw new BizException(ErrorCode.DOMAIN_NOT_SUPPORTED, "不支持的数据域: " + domainCode);
        }
        return handler;
    }
}
