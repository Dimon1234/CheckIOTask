import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Course;
import model.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private static volatile List<Course> courseList = new ArrayList<>();
    private static int N;

    public static void main(String[] args) {
        input();

        long s = System.currentTimeMillis();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonService service = retrofit.create(JsonService.class);
        final boolean[] flag = {true};
        for (int i = 1; flag[0]; i++) {
            final Example[] example = new Example[1];
            service.getListCourses(i).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    example[0] = response.body();
                    if (example[0] == null) flag[0] = false;
                    else addListCoursesToCourseList(sortingStream(example[0].getCourses()));
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    System.err.println(t.toString());
                    System.exit(1);
                }
            });
        }

        courseList.forEach(System.out::println);
        System.out.println("time " +(System.currentTimeMillis() - s));
        System.exit(0);
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

    private static synchronized void addListCoursesToCourseList(List<Course> list) {
        courseList.addAll(list);
        courseList = sortingStream(courseList);

    }

    private static List<Course> sortingStream(List<Course> list) {
        return list.stream()
                .sorted(Comparator.comparing(course -> -course.getLearnersCount()))
                .limit(N)
                .collect(Collectors.toList());
    }

}
