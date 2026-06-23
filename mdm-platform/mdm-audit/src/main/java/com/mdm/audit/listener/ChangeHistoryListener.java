package com.mdm.audit.listener;

import com.mdm.audit.service.ChangeHistoryService;
import com.mdm.common.event.DataChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeHistoryListener {

    private final ChangeHistoryService changeHistoryService;

    @Async
    @EventListener
    public void onDataChange(DataChangeEvent event) {
        try {
            String operator = "system";
            try {
                Class<?> stpClass = Class.forName("cn.dev33.satoken.stp.StpUtil");
                Object loginId = stpClass.getMethod("getLoginIdDefaultNull").invoke(null);
                if (loginId != null) {
                    operator = loginId.toString();
                }
            } catch (Exception ignored) {}

            changeHistoryService.recordChange(
                    event.getDomainCode(),
                    event.getDataCode(),
                    event.getChangeType(),
                    event.getChangedFields(),
                    event.getApprovalNo(),
                    event.getSource(),
                    event.getVersion(),
                    operator
            );
            log.info("变更历史已记录: domain={}, code={}, type={}",
                    event.getDomainCode(), event.getDataCode(), event.getChangeType());
        } catch (Exception e) {
            log.error("记录变更历史失败: {}", e.getMessage(), e);
        }
    }
}
