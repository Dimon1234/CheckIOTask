import model.Example;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class RestClientTest {

    private JsonService service = RestClient.getJsonService();

    @Test
    public void RequestTest() throws IOException {
        Example example = service.getListCourses(1).execute().body();
        Assert.assertEquals(5,example.getCourses().size());
    }


}