import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class MassagesTests extends TestCase 
{
	@Test
	public void addMessageShouldReturnJSONType() throws Exception
	{
		Massages messages= new Messages();
		
		Response response = Response.status(status)
						.type(MediaType.APPLICATION_JSON)
						.entity(response.toString()).build();
		
		JSONObject jsonObject = new JSONObject();

		try
		{

		    jsonObject.put("type", "SENT");
		    jsonObject.put("source", "0829351745");
		    jsonObject.put("destination", "0123456789");
		    jsonObject.put("date", "10-09-2015");

		}
		catch (JSONException e){}
			
		assertEquals("Return type is not of JSON type", response, calls.addMessage(jsonObject.toString(),"application/json","XESWAGS.REGS12432"))
	}
}