# Panduan Lengkap Android Studio Java dengan Firebase Authentication dan CRUD

---

## Tools yang Digunakan:

* Android Studio
* Firebase Console
* Firebase Authentication
* Firebase Realtime Database

---

# Langkah-Langkah

## 1. Setup Firebase

* Buat Project di [Firebase Console](https://console.firebase.google.com/).
* Tambahkan aplikasi Android (gunakan `package name`).
* Download file `google-services.json` dan letakkan di folder `app`.

## 2. Konfigurasi Gradle

### build.gradle (Project)

```gradle
buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:4.3.15'
    }
}
```

### build.gradle (App)

```gradle
apply plugin: 'com.google.gms.google-services'

dependencies {
    implementation 'com.google.firebase:firebase-auth:22.3.0'
    implementation 'com.google.firebase:firebase-database:20.3.0'
}
```

* Sync Project

---

# Layout XML

## Register Layout (`activity_register.xml`)

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Email" />

    <EditText
        android:id="@+id/etPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword" />

    <Button
        android:id="@+id/btnRegister"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Register" />
</LinearLayout>
```

---

## Login Layout (`activity_login.xml`)

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Email" />

    <EditText
        android:id="@+id/etPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Password"
        android:inputType="textPassword" />

    <Button
        android:id="@+id/btnLogin"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Login" />
</LinearLayout>
```

---

## Dashboard Layout (`activity_dashboard.xml`)

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/etName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Name" />

    <Button
        android:id="@+id/btnSave"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Save Data" />

    <Button
        android:id="@+id/btnRead"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Read Data" />

    <Button
        android:id="@+id/btnUpdate"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Update Data" />

    <Button
        android:id="@+id/btnDelete"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Delete Data" />

    <TextView
        android:id="@+id/tvData"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Data will appear here" />
</LinearLayout>
```

---

# Model User (`User.java`)

```java
public class User {
    public String name;
    public String email;

    public User() { }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
```

---

# Register Activity (`RegisterActivity.java`)

```java
public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, LoginActivity.class));
                    } else {
                        Toast.makeText(this, "Register Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }
}
```

---

# Login Activity (`LoginActivity.java`)

```java
public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DashboardActivity.class));
                    } else {
                        Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }
}
```

---

# Dashboard Activity (CRUD) (`DashboardActivity.java`)

```java
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
```