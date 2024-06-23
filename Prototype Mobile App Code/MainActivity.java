package com.example.diabeticretinopathyidentification;

import static com.example.diabeticretinopathyidentification.R.id;
import static com.example.diabeticretinopathyidentification.R.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.diabeticretinopathyidentification.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MainActivity extends AppCompatActivity
{

    private ImageView imageView;
    private Bitmap bitmap;
    private boolean clicker = false;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        imageView = findViewById(id.image_view);
        imageView.setImageResource(R.drawable.icon);
        Button imageButton = findViewById(id.classify);
        Button predictButton = findViewById(id.predict);
        Button infoButton = findViewById(id.info);
        TextView textView = findViewById(id.textDisplay);

        imageButton.setOnClickListener(view ->
        {
            // Create an intent to open the file picker
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,100);
        });

        infoButton.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, Pop.class)));


        predictButton.setOnClickListener(view ->
        {
            if (clicker)
            {
                bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, false);

                try
                {
                    Model model = Model.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
                    byteBuffer.order(ByteOrder.nativeOrder());

                    int[] intValues = new int[224 * 224];
                    bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                    int pixel = 0;
                    //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                    for(int i = 0; i < 224; i ++){
                        for(int j = 0; j < 224; j++){
                            int val = intValues[pixel++]; // RGB
                            byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1/255));
                            byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1/255));
                            byteBuffer.putFloat((val & 0xFF) * (1.f / 1/255));
                        }
                    }

                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    Model.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();

                    float output = outputFeature0.getFloatArray()[0];
                    boolean hasDR = output > 0.5;

                    if (hasDR)
                    {
                        textView.setText("Diabetic Retinopathy Detected");
                    }
                    else
                    {
                        textView.setText("Diabetic Retinopathy NOT Detected");
                    }
                }
                catch (IOException e)
                {
                    // TODO Handle the exception
                }
            }
            else
            {
                textView.setText("Please Upload An Image First");
            }
        });
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100)
        {
            // Get the image URI from the intent
            Uri imageUri = data.getData();

            // Set the image to the ImageView
            imageView.setImageURI(imageUri);
            clicker = true;

            // Convert the image URI to a Bitmap object
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }
            catch (IOException e) {
                // TODO Handle the exception
            }
        }
    }
}
