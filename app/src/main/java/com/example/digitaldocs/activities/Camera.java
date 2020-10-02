package com.example.digitaldocs.activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digitaldocs.R;
import com.example.digitaldocs.utilities.AbnSearchWSHttpGet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class Camera extends AppCompatActivity {
    private ImageButton camera;
    private  ImageButton profile;
    private ImageButton settings;
    private ImageButton receipt;
    private TextView accessGallery;
    private ImageView test12345;
    Context context = this;
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    ImageView mCaptureBtn;

    Uri image_uri;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_display);
        setScene();
        link_camera();
        link_profile();
        link_receipt();
        link_setting();

        mCaptureBtn = findViewById(R.id.capture_image_btn);
accessGallery = findViewById(R.id.accessGallery);

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
        //this method is called, when user pressed allowed or deny from premission request group
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
                detectTextFromFirebase();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void setScene()
    {
        camera = findViewById(R.id.camera_widget2);
        profile = findViewById(R.id.profile_widget2);
        settings = findViewById(R.id.setting_widget2);
        receipt = findViewById(R.id.receipt_widget2);
    }

    private void link_camera()
    {
        final Intent intent = new Intent(this, Camera.class);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.this.startActivity(intent);

            }
        });
    }

    private void link_profile()
    {
        final Intent intent = new Intent(this, Profile.class);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.this.startActivity(intent);

            }
        });
    }
    private void link_setting()
    {
        final Intent intent = new Intent(this, Settings.class);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.this.startActivity(intent);

            }
        });
    }
    private void link_receipt()
    {
        final Intent intent = new Intent(this, Receipt.class);

        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.this.startActivity(intent);

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




    public void detectTextFromFirebase()
    {
       FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(image);
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance().getCloudTextRecognizer();
        firebaseVisionTextRecognizer.processImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
               String TotalPrice = displayTextFromImage(firebaseVisionText);
               getABNFromImage(firebaseVisionText);
               Intent intent =  new Intent(context,onImageTaken.class);
               intent.putExtra("total",TotalPrice);
               startActivity(intent);

              /*  try {
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
*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }



    public String displayTextFromImage(FirebaseVisionText texts)
    {
        String text = "";
        double isConvertableValue = 0.0;


        List<FirebaseVisionText.TextBlock> blockList = texts.getTextBlocks();
        List<FirebaseVisionText.Line> lines;
        List<FirebaseVisionText.Element> words;

        if(blockList.size() == 0)
        {
            Toast.makeText(this,"No Text found dorry",Toast.LENGTH_LONG);

        }
        else
        {
            List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();
            for(int i = 0; i<blocks.size(); ++i)
            {
               if(blocks.get(i).getText().trim().equalsIgnoreCase("Total"))
               {
                   for(int j = i+1; j<blocks.size(); ++j)
                   {
                       try{
                           text = blocks.get(j).getText();
                           isConvertableValue = Double.parseDouble(text.replace("$","").trim());
                          return Double.toString(isConvertableValue);
                       }catch (NumberFormatException e)
                       {
                           continue;
                       }
                   }
               }
            }


        }
        return  Double.toString(isConvertableValue);
    }

    public String getABNFromImage(FirebaseVisionText texts){
        List<FirebaseVisionText.TextBlock> blockList = texts.getTextBlocks();
        List<FirebaseVisionText.Line> lines;
        List<FirebaseVisionText.Element> words;

        for(int i = 0; i<blockList.size(); ++i){
            if(blockList.get(i).getText().contains("ABN"))
            {
                words = blockList.get(i).getLines().get(i).getElements();
                for(int j = 0; j<words.size(); ++j)
                {
                    System.out.println(words.get(j).getText());
                }
            }
        }
        return "88000014675";
    }
}
