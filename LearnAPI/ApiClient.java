// Lokasi: app/src/main/java/com/example/project/ApiClient.java
public class ApiClient {
    private static final String BASE_URL = "https://yourproject.mockapi.io/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}