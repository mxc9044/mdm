package com.mdm.common.core;

public interface DomainSyncHandler {

    String getDomainCode();

    void handleInbound(InboundDataDTO data);

    PageResult<?> queryIncremental(String since, int pageNum, int pageSize);

    PageResult<?> querySnapshot(int pageNum, int pageSize);
}
