
import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class AccountTests extends TestCase {
	@Test
	public void doTryShouldReturnJSONType() throws Exception
	{
		Account account = new Account();
		
		Response response = Response.status(status)
							.type(MediaType.APPLICATION_JSON)
							.entity(response.toString()).build();
							
		JSONObject jsonParams = new JSONObject();

        try 
        {
            jsonParams.put("email", "developer@icrawler.com");
            jsonParams.put("password", "123456");
            JSONObject deviceInfoParams = new JSONObject();
            DeviceInfo deviceInfo = new DeviceInfo(getApplicationContext());
            deviceInfoParams.put("model", "GT-N700");
            deviceInfoParams.put("make", "Samsung");
            deviceInfoParams.put("os", "Android");
            deviceInfoParams.put("network", "Vodacom");
            deviceInfoParams.put("imeNumber", "ABC123456789");

            jsonParams .put("deviceInfo", deviceInfoParams.toString());

        }
        catch (JSONException e){}
		
		assertEquals("Return type is not of JSON type", response, account.doTry(obj.toString(),"application/json"))
	}
}
