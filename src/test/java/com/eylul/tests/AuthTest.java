package com.eylul.tests;

import com.eylul.base.BaseTest;
import com.eylul.utils.AuthUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTest extends BaseTest {

    @Test
    void authTest(){
        assertNotNull(AuthUtils.getToken());
    }
}
