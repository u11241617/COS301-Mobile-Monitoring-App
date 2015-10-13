import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class CallTests extends TestCase 
{
	@Test
	public void addMessageShouldReturnJSONType() throws Exception
	{
		Calls calls = new Calls();
		
		Response response = Response.status(status)
						.type(MediaType.APPLICATION_JSON)
						.entity(response.toString()).build();
		
		JSONObject jsonObject = new JSONObject();

		try
		{

		    jsonObject.put("type", "RECIEVED");
		    jsonObject.put("source", "0829351745");
		    jsonObject.put("destination", "0123456789");
		    jsonObject.put("duration", "00:00:60");
		    jsonObject.put("date", "12-10-2015");

		}
		catch (JSONException e){}
			
		assertEquals("Return type is not of JSON type", response, calls.addMessage(jsonObject.toString(),"application/json","XESWAGS.REGS12432"))
	}
}