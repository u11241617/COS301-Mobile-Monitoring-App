import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class DataUsageTests extends TestCase 
{
	@Test
	public void addDataUsageShouldReturnJSONType() throws Exception
	{
		DataUsage dataUsage= new DataUsage();
		
		Response response = Response.status(status)
						.type(MediaType.APPLICATION_JSON)
						.entity(response.toString()).build();
		
		JSONObject jsonObject = new JSONObject();

		try
		{
		    jsonObject.put("name", "Twitter");
		    jsonObject.put("usage", "10000");
		    jsonObject.put("total", "75000");
			
		}catch(JSONException e){}
			
		assertEquals("Return type is not of JSON type", response, dataUsage.addDataUsage(jsonObject.toString(),"application/json"))
	}
}