package org.szy.utils;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RedisData {
    private LocalDateTime expireTime;  // logical expire time
    private Object data;  // entity data
}
