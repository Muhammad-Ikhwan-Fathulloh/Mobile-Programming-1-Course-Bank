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