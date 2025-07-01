# Materi Android Studio Java dengan MockAPI.io (Login, Register, CRUD)

## Tujuan:

Menggunakan layanan **mockapi.io** untuk simulasi API **Login, Register, dan CRUD** tanpa backend nyata.

---

## Tools:

* Android Studio
* mockapi.io (API generator gratis)
* Library: Retrofit, Gson, RecyclerView

---

# Langkah-Langkah

## 1. Buat API di mockapi.io

1. Kunjungi [https://mockapi.io/](https://mockapi.io/)
2. Buat project baru.
3. Buat resource: `users`

   * Field: `id` (auto), `email` (string), `password` (string), `name` (string)

> Contoh Base URL:

```text
https://<yourproject>.mockapi.io/api/v1/
```

---

## 2. Tambahkan Retrofit di Gradle

### build.gradle (App)

```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.9.3'
```

> Sync Project.

---

## 3. Buat API Interface

```java
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
```

---

## 4. Setup Retrofit Client

```java
public class ApiClient {
    private static final String BASE_URL = "https://<yourproject>.mockapi.io/api/v1/";
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
```

---

## 5. Model User

```java
public class User {
    public String id;
    public String name;
    public String email;
    public String password;
}
```

---

# LOGIN - REGISTER

## 6. Register Activity

```java
btnRegister.setOnClickListener(v -> {
    String email = etEmail.getText().toString();
    String password = etPassword.getText().toString();
    String name = etName.getText().toString();

    ApiService apiService = ApiClient.getClient().create(ApiService.class);
    User user = new User();
    user.email = email;
    user.password = password;
    user.name = name;

    apiService.registerUser(user).enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
        }
    });
});
```

---

## 7. Login Activity (Manual Matching)

```java
btnLogin.setOnClickListener(v -> {
    String email = etEmail.getText().toString();
    String password = etPassword.getText().toString();

    ApiService apiService = ApiClient.getClient().create(ApiService.class);
    apiService.getUsers().enqueue(new Callback<List<User>>() {
        @Override
        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
            if (response.isSuccessful() && response.body() != null) {
                for (User user : response.body()) {
                    if (user.email.equals(email) && user.password.equals(password)) {
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        return;
                    }
                }
                Toast.makeText(LoginActivity.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<User>> call, Throwable t) {
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    });
});
```

---

# CRUD DASHBOARD

## 8. Read All Users

```java
apiService.getUsers().enqueue(new Callback<List<User>>() {
    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.isSuccessful() && response.body() != null) {
            // Tampilkan data di RecyclerView
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) { }
});
```

---

## 9. Update User

```java
apiService.updateUser(userId, updatedUser).enqueue(new Callback<User>() {
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
            Toast.makeText(DashboardActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) { }
});
```

---

## 10. Delete User

```java
apiService.deleteUser(userId).enqueue(new Callback<Void>() {
    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            Toast.makeText(DashboardActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) { }
});
```

---

# 🚀 Penutup

Dengan mockapi.io, kamu **bisa belajar API client tanpa backend** langsung dengan:

* Login Manual (Matching API Response)
* Register User (POST)
* CRUD (GET, PUT, DELETE)