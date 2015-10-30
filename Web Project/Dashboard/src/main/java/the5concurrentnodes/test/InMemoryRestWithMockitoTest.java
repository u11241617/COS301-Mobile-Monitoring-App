package the5concurrentnodes.test;


/*
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
public class InMemoryRestWithMockitoTest {

/*
    @Path("resources")
    public static class Resources {

        private Calls backendServiceCalls;
        private Account backendServiceAccount;

        @GET @Path("/{deviceId}/calls")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getCall(@PathParam("deviceId") int deviceId) {

            return backendServiceCalls.getCall(deviceId);
        }

        @POST @Path("/login")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response doLogin(String cBody, String cType) {

            return backendServiceAccount.doLogin(cBody, MediaType.APPLICATION_JSON);
        }

    }

    @InjectMocks
    public static Resources sut = new Resources();
    public static InMemoryRestServer server;

    @Mock
    private Calls backendServiceCallMock;

    @Mock
    private Account backendServiceAccMock;

    @BeforeClass
    public static void beforeClass() throws Exception {
        server = InMemoryRestServer.create(sut);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        server.close();
    }

    @Test
    public void getWithMocking() throws Exception {

        when(backendServiceCallMock.getCalls(62)).thenReturn(new LinkedList<Call>());

        Response response = server.newRequest("/resources/52/calls").request().buildGet().invoke();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

    }

    @Test
    public void loginPostWithMocking() throws Exception {

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("email", "mock@gmail.com");
            jsonParams.put("password", "1234567");
            JSONObject deviceInfoParams = new JSONObject();
            deviceInfoParams.put("model", "SMT-300");
            deviceInfoParams.put("make", "Samsung");
            deviceInfoParams.put("os", "Android");
            deviceInfoParams.put("network", "Cell-C");
            deviceInfoParams.put("imeNumber", "1233sad222323");
            jsonParams .put("deviceInfo", deviceInfoParams.toString());

        }catch (JSONException e){}
        when(backendServiceAccMock.doLogin(jsonParams.toString(), "application/json; charset=utf-8")).thenReturn(Response.status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON).build());

        Response response = server.newRequest("/resources/login").request().
                buildPost(Entity.entity(jsonParams.toString(), MediaType.APPLICATION_JSON)).invoke();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

    }
}*/