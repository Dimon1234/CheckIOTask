import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Course;
import model.Example;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestClient {
    private static String baseUrl = "https://stepik.org/";
    private static List<Course> courseList = new ArrayList<>();
    private static int N;

    public static void main(String[] args) throws IOException {
        input();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonService service = retrofit.create(JsonService.class);

        for (int i = 1; ; i++) {
            Example example = service.getListCourses(i).execute().body();

            courseList.addAll(example.getCourses());
            courseList.sort(Comparator.comparing(course -> -course.getLearnersCount()));
            courseList = courseList.stream().limit(N).collect(Collectors.toList());

            if (!example.getMeta().getHasNext()) break;
        }

        courseList.stream().forEach(course ->
                System.out.println("\nназвание курса: " + course.getTitle() +
                        "\nколичество: " + course.getLearnersCount() +
                        "\nссылка: "+baseUrl+"/course/" +
                        course.getId())
        );
    }

    private static void input() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Введите N");
            try {
                N = Integer.parseInt(reader.readLine());
                reader.close();
                break;
            } catch (NumberFormatException | IOException e) {
                System.out.println("Неверный формат\n");
            }
        }

    }

}
