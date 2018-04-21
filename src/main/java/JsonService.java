import model.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface JsonService {

    @Headers("Content-Type: application/json")
    @GET("api/courses")
    Call<Example> getListCourses(@Query("page") int page);
}
