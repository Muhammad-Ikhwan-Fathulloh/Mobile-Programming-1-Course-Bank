# QR Code Generator di Android Studio Java

## Dependency

Tambahkan di `build.gradle (App)`:

```gradle
implementation 'com.google.zxing:core:3.4.1'
implementation 'com.journeyapps:zxing-android-embedded:4.2.0'
```

---

## Layout: `activity_qrcode.xml`

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <EditText
        android:id="@+id/etInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter text for QR Code" />

    <Button
        android:id="@+id/btnGenerate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Generate QR Code" />

    <ImageView
        android:id="@+id/qrCodeImageView"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_marginTop="20dp" />

</LinearLayout>
```

---

## Kode Java: `QRCodeActivity.java`

```java
public class QRCodeActivity extends AppCompatActivity {
    EditText etInput;
    Button btnGenerate;
    ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        etInput = findViewById(R.id.etInput);
        btnGenerate = findViewById(R.id.btnGenerate);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        btnGenerate.setOnClickListener(v -> {
            String qrContent = etInput.getText().toString();
            try {
                MultiFormatWriter writer = new MultiFormatWriter();
                BitMatrix matrix = writer.encode(qrContent, BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                qrCodeImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
```