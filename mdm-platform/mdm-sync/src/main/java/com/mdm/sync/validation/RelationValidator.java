package com.mdm.sync.validation;

import org.springframework.stereotype.Component;

@Component
public class RelationValidator {
    public boolean validate(String domainCode, java.util.Map<String, Object> payload) {
        return true;
    }
}
