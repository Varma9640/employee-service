package org.example.employeeservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTestClass {
    private AutoCloseable autoCloseable;

    @BeforeAll
    void setupAll(TestInfo testInfo) {
        log.debug("Start Test Class :: {}", testInfo.getDisplayName());
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setupTestCase(TestInfo testInfo) {
        log.debug("Start Test Case :: {}", testInfo.getDisplayName());
    }

    @AfterAll
    void tearDownAll() throws Exception {
        autoCloseable.close();
    }
}
