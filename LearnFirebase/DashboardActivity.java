public class DashboardActivity extends AppCompatActivity {
    EditText etName;
    TextView tvData;
    Button btnSave, btnRead, btnUpdate, btnDelete;
    FirebaseAuth mAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        etName = findViewById(R.id.etName);
        tvData = findViewById(R.id.tvData);
        btnSave = findViewById(R.id.btnSave);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");

        String userId = mAuth.getCurrentUser().getUid();

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = mAuth.getCurrentUser().getEmail();
            User user = new User(name, email);

            database.child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                });
        });

        btnRead.setOnClickListener(v -> {
            database.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        tvData.setText("Name: " + user.name + "\nEmail: " + user.email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        });

        btnUpdate.setOnClickListener(v -> {
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", etName.getText().toString());

            database.child(userId).updateChildren(updates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
                    }
                });
        });

        btnDelete.setOnClickListener(v -> {
            database.child(userId).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show();
                        tvData.setText("Data has been deleted");
                    }
                });
        });
    }
}