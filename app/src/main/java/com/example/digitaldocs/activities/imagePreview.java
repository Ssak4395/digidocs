package com.example.digitaldocs.activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.digitaldocs.R;
import com.example.digitaldocs.services.AbnSearchWSHttpGet;
import com.example.digitaldocs.services.algorithm.algorithms;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.http.entity.InputStreamEntity;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class imagePreview extends AppCompatActivity {

    ImageView imageView;
    private Context context = this;
    Button button;
    Bitmap bmpOriginal = null;
    private LoadingActivity loadingDialog;
    final static String guid =  "411e2117-1fe8-4876-a8a8-5e3150e22eda";
    private String ABN;
    private String totalDouble;
    private String companyName;
    private ArrayList<ArrayList<String>> lineAndPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        loadingDialog = new LoadingActivity(this);
        imageView  = findViewById(R.id.imagePreview);
       Uri uri = (Uri) getIntent().getExtras().get("uri");

        try {
             bmpOriginal = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bmpOriginal);
        button = findViewById(R.id.process);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                 explodeCropperActivity();

           }
       });

        final Bitmap finalBitmap1 = bmpOriginal;
//        button2.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               ByteArrayOutputStream stream = new ByteArrayOutputStream();
//               finalBitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
//               byte[] byteArray = stream.toByteArray();
//               new tryCloudVisionAPI().execute(byteArray);
//               new tryCloudVisionAPI().execute();
//           }
//       });


    }

    public void explodeCropperActivity()
    {
        Uri uri = (Uri) getIntent().getExtras().get("uri");
        CropImage.activity(uri)
                .start(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bmpfinal = null;
        System.out.println(CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bmpfinal = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                    imageView.setImageBitmap(bmpfinal);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bmpOriginal.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    bmpfinal.compress(Bitmap.CompressFormat.PNG,100,stream2);
                    byte[] byteArray = stream.toByteArray();
                    byte[] byteArray2 = stream2.toByteArray();
                    new tryCloudVisionAPI().execute(byteArray,byteArray2);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }






    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public class tryCloudVisionAPI extends AsyncTask<byte[],String,String> {
        @Override
        protected String doInBackground(byte[]... bytes) {
            Vision.Builder visionBuilder = new Vision.Builder(
                    new NetHttpTransport(),
                    new AndroidJsonFactory(),
                    null);

            visionBuilder.setVisionRequestInitializer(
                    new VisionRequestInitializer("AIzaSyD2p2Yc95RZ01oFNI9ox9yE2BhXR5X3rTw"));

            Vision vision = visionBuilder.build();

            Image image = new Image();
            image.encodeContent(bytes[0]);
            Image imageCropped = new Image();
            imageCropped.encodeContent(bytes[1]);
            Feature desiredFeature = new Feature();
            desiredFeature.setType("TEXT_DETECTION");

            List<EntityAnnotation> totalAbn = new ArrayList<>();
            List<EntityAnnotation> lineItems = new ArrayList<>();

            AnnotateImageRequest request = new AnnotateImageRequest();
            request.setImage(image);
            AnnotateImageRequest request2 = new AnnotateImageRequest();
            request2.setImage(imageCropped);
            request2.setFeatures(Arrays.asList(desiredFeature));
            request.setFeatures(Arrays.asList(desiredFeature));
            BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
            batchRequest.setRequests(Arrays.asList(request,request2));
            BatchAnnotateImagesResponse batchResponse = null;
            try {
                batchResponse = vision.images().annotate(batchRequest).execute();
                totalAbn = batchResponse.getResponses().get(0).getTextAnnotations();
                lineItems = batchResponse.getResponses().get(1).getTextAnnotations();


            } catch (IOException e) {
                e.printStackTrace();
            }

            algorithms algorithms = new algorithms();

            try {
                totalDouble = Double.toString(algorithms.startTotalStrategy(totalAbn));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                ABN = algorithms.startABNStrategy(totalAbn);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                lineAndPrice = algorithms.generateLineItems(lineItems);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!ABN.equals("Algorithm could not detect a valid ABN")) {
                try {
                    companyName = AbnSearchWSHttpGet.searchByABN(guid,ABN,false).getOrganisationName();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }




            return totalDouble;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // final Intent intent = new Intent(context, ImageTakenActivity.class);
            System.out.println("The total price " + "$"+ totalDouble);
            System.out.println("The ABN Number of this business is: " + ABN);
            System.out.println("ALL DONE!!!!!");
            Intent intent = new Intent(context,ImageTakenActivity.class);
            intent.putExtra("total",totalDouble);
            intent.putExtra("company",companyName);
            intent.putExtra("list", lineAndPrice);
            intent.putExtra("abn",ABN);
            startActivity(intent);



        }
    }


}