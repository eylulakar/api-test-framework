package com.eylul.tests;

import com.eylul.base.BaseTest;
import com.eylul.utils.AuthUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTest extends BaseTest {

    @Test
    @DisplayName("POST auth credentials returns auth token")
    void authTest(){
        assertNotNull(AuthUtils.getToken());
    }
}
