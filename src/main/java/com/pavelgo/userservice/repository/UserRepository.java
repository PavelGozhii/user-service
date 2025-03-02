package com.pavelgo.userservice.repository;

import com.pavelgo.userservice.config.properties.JdbcTemplateProperties;
import com.pavelgo.userservice.config.properties.UserDataSourceMapping;
import com.pavelgo.userservice.model.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserRepository {

    private static final String selectQuery = "SELECT %s FROM %s";
    private final JdbcTemplateProperties jdbcTemplateProperties;

    public List<User> findAll() {
        UserDataSourceMapping mapping = jdbcTemplateProperties.getMapping();
        return jdbcTemplateProperties.getJdbcTemplate().query(buildSelectQuery(mapping),
                (rs, rowNum) -> new User(
                        rs.getString(mapping.getId()),
                        rs.getString(mapping.getUsername()),
                        rs.getString(mapping.getName()),
                        rs.getString(mapping.getSurname())));
    }

    private String buildSelectQuery(UserDataSourceMapping mapping) {
        String columnsString = String.join(", ",
                List.of(mapping.getId(), mapping.getUsername(), mapping.getName(), mapping.getSurname()));
        return String.format(selectQuery, columnsString, mapping.getTableName());
    }

}
