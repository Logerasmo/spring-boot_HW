package com.example.SpringBoot_HW;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> myAppFirst = new GenericContainer<>("devapp").withExposedPorts(8080);
    private static final GenericContainer<?> myAppSecond = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myAppFirst.start();
        myAppSecond.start();
    }

    @Test
    void test1() {
        ResponseEntity<String> forEntity1 = restTemplate.getForEntity("http://localhost:" + myAppFirst.getMappedPort(8080) + "/profile", String.class);
        System.out.println(forEntity1.getBody());
        Assertions.assertEquals("Current profile is dev", forEntity1.getBody());

        
    }

    @Test
    void test2(){
        ResponseEntity<String> forEntity2 = restTemplate.getForEntity("http://localhost:" + myAppSecond.getMappedPort(8081) + "/profile", String.class);
        System.out.println(forEntity2.getBody());
        Assertions.assertEquals("Current profile is production", forEntity2.getBody());

}
