package com.mdm.sync.inbound.batch;

import com.mdm.common.core.DomainSyncHandler;
import com.mdm.common.core.InboundDataDTO;
import com.mdm.sync.DomainSyncRouter;
import com.mdm.sync.validation.ValidationPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchImportService {

    private final DomainSyncRouter router;
    private final ValidationPipeline validationPipeline;

    public int batchImport(List<InboundDataDTO> dataList) {
        int success = 0;
        for (InboundDataDTO data : dataList) {
            try {
                validationPipeline.validate(data, null);
                DomainSyncHandler handler = router.getHandler(data.getDomainCode());
                handler.handleInbound(data);
                success++;
            } catch (Exception e) {
                log.error("批量导入单条失败: requestId={}, error={}", data.getRequestId(), e.getMessage());
            }
        }
        return success;
    }
}
