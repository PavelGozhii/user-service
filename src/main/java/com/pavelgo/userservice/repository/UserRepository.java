package com.pavelgo.userservice.repository;

import com.pavelgo.userservice.config.properties.JdbcTemplateProperties;
import com.pavelgo.userservice.config.properties.UserDataSourceMapping;
import com.pavelgo.userservice.model.User;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class UserRepository {

    private static final String SELECT_QUERY = "SELECT %s FROM %s";
    private static final String SELECT_QUERY_WITH_FILTERS = "SELECT %s FROM %s WHERE 1 = 1";
    private final JdbcTemplateProperties jdbcTemplateProperties;

    public List<User> findAll() {
        UserDataSourceMapping mapping = jdbcTemplateProperties.getMapping();
        return jdbcTemplateProperties.getJdbcTemplate().query(buildSelectQuery(mapping, SELECT_QUERY),
                (rs, rowNum) -> new User(
                        rs.getString(mapping.getId()),
                        rs.getString(mapping.getUsername()),
                        rs.getString(mapping.getName()),
                        rs.getString(mapping.getSurname())));
    }

    public List<User> findAll(Map<String, String> filters) {
        StringBuilder sql = new StringBuilder(SELECT_QUERY_WITH_FILTERS);
        UserDataSourceMapping mapping = jdbcTemplateProperties.getMapping();

        filters.forEach((key, value) -> {
            switch (key) {
                case "id" -> sql.append(" AND ").append(mapping.getId()).append(" = :id");
                case "username" -> sql.append(" AND ").append(mapping.getUsername()).append(" = :username");
                case "name" -> sql.append(" AND ").append(mapping.getName()).append(" = :name");
                case "surname" -> sql.append(" AND ").append(mapping.getSurname()).append(" = :surname");
            }
        });

        String query = buildSelectQuery(mapping, sql.toString());

        return jdbcTemplateProperties.getNamedParameterJdbcTemplate().query(query,
                filters,
                (rs, rowNum) -> new User(
                        rs.getString(mapping.getId()),
                        rs.getString(mapping.getUsername()),
                        rs.getString(mapping.getName()),
                        rs.getString(mapping.getSurname())));
    }

    private String buildSelectQuery(UserDataSourceMapping mapping, String query) {
        String columnsString = String.join(", ",
                List.of(mapping.getId(), mapping.getUsername(), mapping.getName(), mapping.getSurname()));
        return String.format(query, columnsString, mapping.getTableName());
    }
}
