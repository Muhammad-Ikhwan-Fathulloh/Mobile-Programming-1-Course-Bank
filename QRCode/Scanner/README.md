# QR Code Scanner di Android Studio Java

## Dependency

(Sudah sesuai dengan yang kamu gunakan)

```gradle
implementation 'com.google.zxing:core:3.4.1'
implementation 'com.journeyapps:zxing-android-embedded:4.2.0'
```

---

## Layout: `activity_qrscanner.xml`

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="16dp">

    <Button
        android:id="@+id/btnScan"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Scan QR Code" />

    <TextView
        android:id="@+id/tvResult"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Scan Result Will Appear Here"
        android:textSize="16sp"
        android:gravity="center"
        android:layout_marginTop="20dp" />

</LinearLayout>
```

---

## Kode Java: `QRScannerActivity.java`

```java
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QRScannerActivity extends AppCompatActivity {
    Button btnScan;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        btnScan = findViewById(R.id.btnScan);
        tvResult = findViewById(R.id.tvResult);

        btnScan.setOnClickListener(v -> startQRScanner());
    }

    private void startQRScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        qrCodeLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    tvResult.setText("Result: " + result.getContents());
                }
            }
    );
}
```

---

## Penjelasan:

* **ScanContract dan ScanOptions** adalah cara modern ZXing dengan `ActivityResultLauncher` (tanpa deprecated `startActivityForResult`).
* Hasil QR Code akan otomatis ditampilkan di `TextView`.