package org.szy.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// jackson use getter to serialize R
@Data
public class R<T> {
    private Integer code; //20000: success

    private String msg;

    private T data;

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 20000;
        return r;
    }
    public static <T> R<T> success() {
        R<T> r = new R<T>();
        r.code = 20000;
        return r;
    }
    public static <T> R<T> success(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 20000;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
