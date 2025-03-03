package com.pavelgo.userservice.config.properties;

import lombok.Builder;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Data
@Builder
public class JdbcTemplateProperties {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UserDataSourceMapping mapping;
}

