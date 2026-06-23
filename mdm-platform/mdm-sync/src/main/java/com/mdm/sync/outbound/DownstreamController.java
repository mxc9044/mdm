package com.mdm.sync.outbound;

import com.mdm.common.core.DomainSyncHandler;
import com.mdm.common.core.PageResult;
import com.mdm.common.core.Result;
import com.mdm.sync.DomainSyncRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/downstream")
@RequiredArgsConstructor
public class DownstreamController {

    private final DomainSyncRouter router;

    @GetMapping("/incremental")
    public Result<PageResult<?>> incremental(
            @RequestParam String domain,
            @RequestParam(required = false) String since,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        DomainSyncHandler handler = router.getHandler(domain);
        return Result.ok(handler.queryIncremental(since, pageNum, pageSize));
    }

    @GetMapping("/snapshot")
    public Result<PageResult<?>> snapshot(
            @RequestParam String domain,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        DomainSyncHandler handler = router.getHandler(domain);
        return Result.ok(handler.querySnapshot(pageNum, pageSize));
    }
}
