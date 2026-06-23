package com.mdm.common.core;

public final class MdmConstants {

    private MdmConstants() {}

    public static final int STATUS_ENABLED = 1;
    public static final int STATUS_DISABLED = 2;
    public static final int STATUS_CANCELLED = 3;

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 200;

    public static final String CACHE_PREFIX = "mdm:";
    public static final String IDEMPOTENT_PREFIX = "mdm:idempotent:";
    public static final String SEQ_PREFIX = "mdm:seq:";
    public static final String DOMAIN_REGISTRY_KEY = "mdm:domain:registry";

    public static final String CHANGE_CREATE = "CREATE";
    public static final String CHANGE_UPDATE = "UPDATE";
    public static final String CHANGE_DISABLE = "DISABLE";
    public static final String CHANGE_CANCEL = "CANCEL";
    public static final String CHANGE_MERGE = "MERGE";

    public static final String SOURCE_OA_CALLBACK = "OA_CALLBACK";
    public static final String SOURCE_SUPPLY_SYNC = "SUPPLY_SYNC";
    public static final String SOURCE_POLLING = "POLLING";
    public static final String SOURCE_MANUAL = "MANUAL";
    public static final String SOURCE_BATCH_IMPORT = "BATCH_IMPORT";
}
