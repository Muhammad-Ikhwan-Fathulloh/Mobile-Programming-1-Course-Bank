public class DashboardActivity extends AppCompatActivity {
    EditText etName, etEmail, etPassword;
    Button btnSave, btnRead, btnUpdate, btnDelete;
    TextView tvData;
    ApiService apiService;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        tvData = findViewById(R.id.tvData);

        apiService = ApiClient.getClient().create(ApiService.class);

        btnSave.setOnClickListener(v -> {
            User user = new User();
            user.name = etName.getText().toString();
            user.email = etEmail.getText().toString();
            user.password = etPassword.getText().toString();

            apiService.registerUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        userId = response.body().id;
                        Toast.makeText(DashboardActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) { }
            });
        });

        btnRead.setOnClickListener(v -> {
            apiService.getUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        StringBuilder result = new StringBuilder();
                        for (User user : response.body()) {
                            result.append("Name: ").append(user.name)
                                    .append(", Email: ").append(user.email)
                                    .append("\n");
                        }
                        tvData.setText(result.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) { }
            });
        });

        btnUpdate.setOnClickListener(v -> {
            if (userId == null) {
                Toast.makeText(this, "No User to Update", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User();
            user.name = etName.getText().toString();
            user.email = etEmail.getText().toString();
            user.password = etPassword.getText().toString();

            apiService.updateUser(userId, user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(DashboardActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) { }
            });
        });

        btnDelete.setOnClickListener(v -> {
            if (userId == null) {
                Toast.makeText(this, "No User to Delete", Toast.LENGTH_SHORT).show();
                return;
            }
            apiService.deleteUser(userId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(DashboardActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                        tvData.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) { }
            });
        });
    }
}