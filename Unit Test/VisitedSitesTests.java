import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class VisitedWebsitesTests extends TestCase 
{
	@Test
	public void addVisitedWebsitesShouldReturnJSONType() throws Exception
	{
		VisitedWebsites vWebsites = new VisitedWebsites();
		
		Response response = Response.status(status)
						.type(MediaType.APPLICATION_JSON)
						.entity(response.toString()).build();
		
		JSONObject jsonObject = new JSONObject();

		try
		{

		    jsonObject.put("browser", "Chrome");
		    jsonObject.put("url", "http://up.ac.za/undergrad");
		    jsonObject.put("visits", "16");
		}
		catch (JSONException e){}
			
		assertEquals("Return type is not of JSON type", response, vWebsites.addVisitedWebsites(jsonObject.toString(),"application/json","XESWAGS.REGS12432"))
	}
}