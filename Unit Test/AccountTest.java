package the5concurrentnodes.mobilemonitoringapp;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.IOError;
import java.lang.Exception;
import java.lang.Override;

import the5concurrentnodes.account.Account;
import the5concurrentnodes.account.Login;
import the5concurrentnodes.account.Register;

public class AccountTest extends TestCase {

    @Override
    protected void setUp() throws Exception {

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }

    @SmallTest
    public void accountTest() {

        Account register = new Register("developer@mma.co.za", "1234", "1234");
        Account login = new Login("client@mma.co.za", "1234");

        /**Register user testing*/
        assertTrue(register.getEmail().equals("developer@mma.co.za"));
        assertTrue(register.getPassword().equals("1234"));

        /**Login user testing*/
        assertTrue(login.getEmail().equals("client@mma.co.za"));
        assertTrue(login.getPassword().equals("1234"));
    }


}
