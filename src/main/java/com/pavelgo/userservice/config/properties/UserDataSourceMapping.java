package com.pavelgo.userservice.config.properties;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDataSourceMapping {

    private String id;
    private String username;
    private String name;
    private String surname;
    private String tableName;
}
