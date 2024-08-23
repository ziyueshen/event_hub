package org.szy.utils;

public class RedisConstants {
    public static final String CACHE_EVENT_KEY = "cache:event:";
    public static final String CACHE_PRE_KEY = "cache:precachejob:";
    public static final String LOCK_EVENT_KEY = "lock:event:";
    public static final String LOCK_TEAM_KEY = "lock:team:";
    public static final Long LOCK_EVENT_TTL= 10L;
    public static final Long CACHE_EVENT_TTL= 30L;
    public static final Long CACHE_NULL_TTL = 2L;
}
