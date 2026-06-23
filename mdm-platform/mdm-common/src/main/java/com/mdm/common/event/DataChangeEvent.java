package com.mdm.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
public class DataChangeEvent extends ApplicationEvent {

    private final String domainCode;
    private final String dataCode;
    private final String changeType;
    private final Map<String, String[]> changedFields;
    private final String approvalNo;
    private final String source;
    private final Integer version;

    public DataChangeEvent(Object source, String domainCode, String dataCode,
                           String changeType, Map<String, String[]> changedFields,
                           String approvalNo, String changeSource, Integer version) {
        super(source);
        this.domainCode = domainCode;
        this.dataCode = dataCode;
        this.changeType = changeType;
        this.changedFields = changedFields;
        this.approvalNo = approvalNo;
        this.source = changeSource;
        this.version = version;
    }
}
