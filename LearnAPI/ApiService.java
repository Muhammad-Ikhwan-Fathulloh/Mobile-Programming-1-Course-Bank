// Lokasi: app/src/main/java/com/example/project/ApiService.java
public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> registerUser(@Body User user);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") String id, @Body User user);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id);
}