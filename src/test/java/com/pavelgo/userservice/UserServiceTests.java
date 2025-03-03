package com.pavelgo.userservice;

import com.pavelgo.userservice.config.properties.DataSourcePropertiesToJdbcTemplatePropertiesMapping;
import com.pavelgo.userservice.config.properties.JdbcTemplateProperties;
import com.pavelgo.userservice.config.properties.UserDataSourceMapping;
import com.pavelgo.userservice.model.User;
import com.pavelgo.userservice.repository.UserRepository;
import com.pavelgo.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
class UserServiceTests {

    static PostgreSQLContainer<?> firstContainer;
    static PostgreSQLContainer<?> secondContainer;
    static UserService userService;
    static DataSourcePropertiesToJdbcTemplatePropertiesMapping dataSourcePropertiesToJdbcTemplatePropertiesMapping;

    private static final String createTableSQL = """
            CREATE TABLE IF NOT EXISTS public.users (
            id VARCHAR(100) PRIMARY KEY,
            username VARCHAR(100),
            name VARCHAR(100),
            surname VARCHAR(100));
            """;

    private static final String insertTableSQL = """
            INSERT INTO public.users VALUES ('%s', '%s', '%s', '%s');
            """;

    @BeforeAll
    public static void setUp() {
        firstContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
                .withDatabaseName("testdb")
                .withUsername("testUser")
                .withPassword("testPass");

        secondContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
                .withDatabaseName("testdb")
                .withUsername("testUser")
                .withPassword("testPass");

        firstContainer.start();
        secondContainer.start();
        DriverManagerDataSource firstDataSource = new DriverManagerDataSource();
        firstDataSource.setDriverClassName(firstContainer.getDriverClassName());
        firstDataSource.setUrl(firstContainer.getJdbcUrl());
        firstDataSource.setUsername(firstContainer.getUsername());
        firstDataSource.setPassword(firstContainer.getPassword());
        JdbcTemplate firstJdbcTemplate = new JdbcTemplate(firstDataSource);

        DriverManagerDataSource secondDataSource = new DriverManagerDataSource();
        secondDataSource.setDriverClassName(secondContainer.getDriverClassName());
        secondDataSource.setUrl(secondContainer.getJdbcUrl());
        secondDataSource.setUsername(secondContainer.getUsername());
        secondDataSource.setPassword(secondContainer.getPassword());
        JdbcTemplate secondJdbcTemplate = new JdbcTemplate(secondDataSource);

        createAndInsertTables(firstJdbcTemplate);
        createAndInsertTables(secondJdbcTemplate);

        UserRepository firstUserRepository = new UserRepositoryImpl(JdbcTemplateProperties.builder()
                .jdbcTemplate(firstJdbcTemplate)
                .mapping(UserDataSourceMapping.builder()
                        .tableName("public.users")
                        .id("id")
                        .username("username")
                        .name("name")
                        .surname("surname")
                        .build())
                .build());

        UserRepository secondUserRepository = new UserRepositoryImpl(JdbcTemplateProperties.builder()
                .jdbcTemplate(secondJdbcTemplate)
                .mapping(UserDataSourceMapping.builder()
                        .tableName("public.users")
                        .id("id")
                        .username("username")
                        .name("name")
                        .surname("surname")
                        .build())
                .build());

        userService = new UserService(List.of(firstUserRepository, secondUserRepository));
    }

    @Test
    void testDatabaseConnection() {
        String jdbcUrl = firstContainer.getJdbcUrl();
        String username = firstContainer.getUsername();
        String password = firstContainer.getPassword();

        assertTrue(jdbcUrl != null && !jdbcUrl.isEmpty());
        assertTrue(username != null && !username.isEmpty());
        assertTrue(password != null && !password.isEmpty());
    }

    @Test
    void testGetUsers() {
        List<User> users = userService.getUsers();

        log.info("Get users {}", users);
        assertEquals(10, users.size());
    }

    @AfterAll
    public static void tearDown() {
        if (firstContainer != null) {
            firstContainer.stop();
        }
        if (secondContainer != null) {
            secondContainer.stop();
        }
    }

    private static void createAndInsertTables(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute(createTableSQL);

        for (int i = 0; i < 5; i++) {
            String formatingString = String.format(insertTableSQL,
                    "id-" + i, "username-" + i, "name-" + i, "surname-" + i);
            jdbcTemplate.execute(formatingString);
        }
    }

}