# Google Maps Embed HTML di Android Studio Java

## Layout: `activity_maps.xml`

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <WebView
        android:id="@+id/webViewMap"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

---

## Kode Java: `MapsActivity.java`

```java
public class MapsActivity extends AppCompatActivity {
    WebView webViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        webViewMap = findViewById(R.id.webViewMap);
        webViewMap.getSettings().setJavaScriptEnabled(true);

        double latitude = -6.200000; // Ganti dengan koordinat Anda
        double longitude = 106.816666;

        String mapHtml = "<iframe width='100%' height='100%' frameborder='0' style='border:0' " +
                "src='https://www.google.com/maps?q=" + latitude + "," + longitude + "&hl=es;z=14&amp;output=embed'></iframe>";

        webViewMap.loadData(mapHtml, "text/html", "UTF-8");
    }
}
```