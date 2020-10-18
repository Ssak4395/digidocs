package com.example.digitaldocs.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

public class CameraActivity extends AppCompatActivity {
    private ImageButton camera;
    private  ImageButton profile;
    private ImageButton settings;
    private ImageButton receipt;
    private ImageButton search;
    private TextView accessGallery;
    private String ABN;
    private String totalDouble;
    private String companyName;
    private LoadingActivity loadingDialog;
    byte[] byteArray;
    Context context = this;
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    ImageView mCaptureBtn;
    String encodedBase64 ="";
    Uri image_uri;
    Bitmap image;
    final static String guid =  "411e2117-1fe8-4876-a8a8-5e3150e22eda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_display);
        setScene();
        linkCamera();
        linkProfile();
        linkReceipt();
        linkSetting();
        linkSearch();

        accessGallery = findViewById(R.id.accessGallery);
        mCaptureBtn = findViewById(R.id.capture_image_btn);
        loadingDialog = new LoadingActivity(this);

        accessGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select a picture"),PICK_IMAGE);
            }
        });
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //request runtime premission on newer systems
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        //permission granted
                        openCamera();
                    }
                } else {
                    //system os < marshmellow
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        //this is where the image URI is saved for now to be used later
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user pressed allowed or deny from permission request group
        switch (requestCode) {
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
               System.out.println("IT WORKED");
        }

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            image_uri = data.getData();
            try{
                image = MediaStore.Images.Media.getBitmap(getContentResolver(),image_uri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byteArray = byteArrayOutputStream .toByteArray();
                encodedBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
               new tryCloudVisionAPI().execute(byteArray);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setScene() {
        camera = findViewById(R.id.camera_widget2);
        profile = findViewById(R.id.profile_widget2);
        settings = findViewById(R.id.setting_widget2);
        receipt = findViewById(R.id.receipt_widget2);
        search = findViewById(R.id.search_widget2);
    }

    private void linkCamera() {
        final Intent intent = new Intent(this, CameraActivity.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.this.startActivity(intent);
            }
        });
    }

    private void linkProfile() {
        final Intent intent = new Intent(this, ProfileActivity.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.this.startActivity(intent);
            }
        });
    }
    private void linkSearch() {
        final Intent intent = new Intent(this, SearchActivityPage.class);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.this.startActivity(intent);
            }
        });
    }

    private void linkSetting() {
        final Intent intent = new Intent(this, SettingsActivity.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.this.startActivity(intent);
            }
        });
    }

    private void linkReceipt() {
        final Intent intent = new Intent(this, ReceiptActivity.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraActivity.this.startActivity(intent);
            }
        });
    }

//    private void detectTextFromImage()
//    {
//        StringBuilder sb = new StringBuilder();
//        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
//
//        if(!textRecognizer.isOperational())
//        { System.out.println("THIS METHOD HAS FAILED");}
//        else
//        {
//            Frame frame = new Frame.Builder().setBitmap(image).build();
//            SparseArray<TextBlock> items = textRecognizer.detect(frame);
//
//             sb = new StringBuilder();
//
//            for(int i = 0;  i<items.size(); ++i)
//            {
//                TextBlock itemBlock = items.valueAt(i);
//                sb.append(itemBlock.getValue());
//                sb.append("\n");
//            }
//            System.out.println(sb.toString());
//
//        }

    /*
    You can delete this but you never know when we might needed,
    basically this uses mobile vision which
    is essentially cloud vision for mobiles

     */
    public void detectTextFromFirebase()
    {
       /*FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(image);
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance().getCloudTextRecognizer();
        firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
               //String TotalPrice = displayTextFromImage(firebaseVisionText);
              // getABNFromImage(firebaseVisionText);
              // Intent intent =  new Intent(context,onImageTaken.class);
             //  intent.putExtra("total",TotalPrice);
            //   startActivity(intent);

                try {
                    tryCloudVisionAPI();
                } catch (IOException e) {
                    e.printStackTrace();
                }

              *//*  try {
                    //String s = AbnSearchWSHttpGet.searchByABN("411e2117-1fe8-4876-a8a8-5e3150e22eda",getABNFromImage(firebaseVisionText),false).getOrganisationName();
                    System.out.println(s);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
*//*
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });*/
    }



//    public String displayTextFromImage(FirebaseVisionText texts)
//    {
//        String text = "";
//        double isConvertableValue = 0.0;
//
//
//        List<FirebaseVisionText.TextBlock> blockList = texts.getTextBlocks();
//        List<FirebaseVisionText.Line> lines;
//        List<FirebaseVisionText.Element> words;
//
//        if(blockList.size() == 0)
//        {
//            Toast.makeText(this,"No Text found dorry",Toast.LENGTH_LONG);
//
//        }
//        else
//        {
//            List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
//            for(int i = 0; i<blocks.size(); ++i)
//            {
//               if(blocks.get(i).getText().trim().equalsIgnoreCase("Total"))
//               {
//                   for(int j = i+1; j<blocks.size(); ++j)
//                   {
//                       try{
//                           text = blocks.get(j).getText();
//                           isConvertableValue = Double.parseDouble(text.replace("$","").trim());
//                          return Double.toString(isConvertableValue);
//                       }catch (NumberFormatException e)
//                       {
//                           continue;
//                       }
//                   }
//               }
//            }
//
//
//        }
//        return  Double.toString(isConvertableValue);
//    }

//    public String getABNFromImage(FirebaseVisionText texts){
//        List<FirebaseVisionText.TextBlock> blockList = texts.getTextBlocks();
//        List<FirebaseVisionText.Line> lines;
//        List<FirebaseVisionText.Element> words;
//
//        for(int i = 0; i<blockList.size(); ++i){
//            if(blockList.get(i).getText().contains("ABN"))
//            {
//                words = blockList.get(i).getLines().get(i).getElements();
//                for(int j = 0; j<words.size(); ++j)
//                {
//                    System.out.println(words.get(j).getText());
//                }
//            }
//        }
//        return "88000014675";
//    }

    public class tryCloudVisionAPI extends AsyncTask<byte[],String,String>{
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
            Feature desiredFeature = new Feature();
            desiredFeature.setType("TEXT_DETECTION");

            List<EntityAnnotation> results = new ArrayList<>();
            AnnotateImageRequest request = new AnnotateImageRequest();
            request.setImage(image);
            request.setFeatures(Arrays.asList(desiredFeature));
            BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
            batchRequest.setRequests(Arrays.asList(request));
            BatchAnnotateImagesResponse batchResponse = null;
            try {
                batchResponse = vision.images().annotate(batchRequest).execute();

                for(AnnotateImageResponse res : batchResponse.getResponses())
                {

                    results = res.getTextAnnotations();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            algorithms algorithms = new algorithms();

            try {
                totalDouble = Double.toString(algorithms.startTotalStrategy(results));
                ABN = algorithms.startABNStrategy(results);
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
            final Intent intent = new Intent(context, ImageTakenActivity.class);
            System.out.println("The total price " + "$"+ totalDouble);
            System.out.println("The ABN Number of this business is: " + ABN);
            intent.putExtra("total",totalDouble);
            intent.putExtra("abn",ABN);
            intent.putExtra("company",companyName);

            loadingDialog.startLoadingAnimation();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismissDialog();
                    startActivity(intent);
                }
            },4000);
        }
    }



}
