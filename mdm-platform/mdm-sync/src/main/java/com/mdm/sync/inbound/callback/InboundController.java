package com.mdm.sync.inbound.callback;

import com.mdm.common.core.DomainSyncHandler;
import com.mdm.common.core.InboundDataDTO;
import com.mdm.common.core.Result;
import com.mdm.sync.DomainSyncRouter;
import com.mdm.sync.validation.ValidationPipeline;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/sync/inbound")
@RequiredArgsConstructor
public class InboundController {

    private final DomainSyncRouter router;
    private final ValidationPipeline validationPipeline;

    @PostMapping
    public Result<Void> inbound(@Valid @RequestBody InboundDataDTO data) {
        log.info("收到入站数据: domain={}, code={}, type={}",
                data.getDomainCode(), data.getDataCode(), data.getOperationType());
        validationPipeline.validate(data, null);
        DomainSyncHandler handler = router.getHandler(data.getDomainCode());
        handler.handleInbound(data);
        return Result.ok();
    }

    @PostMapping("/batch")
    public Result<Void> batchInbound(@Valid @RequestBody List<InboundDataDTO> dataList) {
        for (InboundDataDTO data : dataList) {
            try {
                validationPipeline.validate(data, null);
                DomainSyncHandler handler = router.getHandler(data.getDomainCode());
                handler.handleInbound(data);
            } catch (Exception e) {
                log.error("批量导入失败: requestId={}, error={}", data.getRequestId(), e.getMessage());
            }
        }
        return Result.ok();
    }
}
