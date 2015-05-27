package the5concurrentnodes.mobilemonitoringapp;

import junit.framework.TestCase;
import java.lang.String;

public class EmailValidateTest extends TestCase
{
    public void testEmail()
    {
        String result = ValidateUserInput.userEmail("developer@mma.com");
        assertEquals("valid",result);
    }
}