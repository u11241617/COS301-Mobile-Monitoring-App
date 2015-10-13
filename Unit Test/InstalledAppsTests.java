import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public class InstalledAppsTests extends TestCase 
{
	@Test
	public void addAppsShouldReturnJSONType() throws Exception
	{
		InstalledApps installedApps= new InstalledApps();
		
		Response response = Response.status(status)
						.type(MediaType.APPLICATION_JSON)
						.entity(response.toString()).build();
		
		JSONObject appInfo = new JSONObject();

		try {

		    appInfo.put("appName", "Facebook");
		    appInfo.put("version", "3.2.1.0");
		    appInfo.put("package", "/org/appname/location");
		    installedApps.add(appInfo);

		}catch(JSONException e){e.getStackTrace();}
			
		assertEquals("Return type is not of JSON type", response, installedApps.addApps(appInfo.toString(),"application/json","XESWAGS.REGS12432"))
	}
}