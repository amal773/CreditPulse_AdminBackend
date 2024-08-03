package com.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
 
/**
* This class performs a basic "smoke test" to ensure that the Spring application context
* loads without any issues.
*/
@SpringBootTest
public class AdminApplicationTests {
 
    /**
     * Test if the application context loads successfully.
     */
	@Test
    void applicationStarts() {
        AdminApplication.main(new String[] {});
        Assertions.assertDoesNotThrow(()->{});
        // If the application fails to start, this test will fail.
        // No assertion is needed here because we are only interested in whether the application context loads without throwing exceptions.
    }
}