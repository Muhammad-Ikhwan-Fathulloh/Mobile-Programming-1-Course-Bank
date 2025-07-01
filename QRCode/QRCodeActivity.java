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