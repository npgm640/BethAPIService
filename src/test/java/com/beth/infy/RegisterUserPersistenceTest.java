package com.beth.infy;

import com.beth.infy.domain.UserDto;
import com.beth.infy.model.UserOrm;
import com.beth.infy.service.AuthorizationService;
import com.beth.infy.util.CommonConstants;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegisterUserPersistenceTest {

    @Autowired
    private AuthorizationService userService;

    @Test
    public void registerUserSuccess() {
        UserDto user = new UserDto();
        UserOrm userOrm = new UserOrm();

        user.setUserName("testUserName");
        user.setPassword("nopassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("noemail@email.com");
        userOrm = userService.save(user);
        Assert.assertNotNull(userOrm);
        //verify user saved to DB

    }

}
