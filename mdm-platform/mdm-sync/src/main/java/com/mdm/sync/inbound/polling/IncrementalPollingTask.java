package com.mdm.sync.inbound.polling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IncrementalPollingTask {

    @Scheduled(fixedDelay = 300000)
    public void poll() {
        log.debug("B通道轮询开始");
        // TODO 接上游增量接口
        log.debug("B通道轮询结束");
    }
}
