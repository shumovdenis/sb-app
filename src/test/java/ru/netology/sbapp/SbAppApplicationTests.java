package ru.netology.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SbAppApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    private static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public  static  void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    public void devAppTest() {
        Integer devappPort = devapp.getMappedPort(8080);
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity(
                        "http://localhost:" + devappPort + "/profile", String.class);
        Assertions.assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    public void prodAppTest() {
        Integer prodappPort = prodapp.getMappedPort(8081);
        ResponseEntity<String> forEntity = restTemplate
                .getForEntity(
                        "http://localhost:" + prodappPort + "/profile", String.class);
        Assertions.assertEquals("Current profile is production", forEntity.getBody());
    }

}
