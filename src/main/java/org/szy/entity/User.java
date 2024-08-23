package org.szy.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String preferCategory;
    private String preferFormat;
    private String preferCity;
}
