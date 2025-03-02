package com.pavelgo.userservice.config.properties;

import lombok.Builder;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

@Data
@Builder
public class JdbcTemplateProperties {

    private JdbcTemplate jdbcTemplate;
    private UserDataSourceMapping mapping;
}

