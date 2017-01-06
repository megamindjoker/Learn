package learn.work.learn;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private Button uploadButton;
    private Button downloadButton;
    private Bitmap imageBitmap;
    private Uri filePath;
    private ProgressDialog progessBar;
    private FirebaseStorage mStorageRef;
    StorageReference storage;
    StorageReference imageRef;
    private Uri downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
//        String string = getString(R.string.banner_ad_unit_id);
//        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//        MobileAds.initialize(getApplicationContext(),string);
//
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
//                .build();
//        mAdView.loadAd(adRequest);


        setContentView(R.layout.firebase);

        String string = FirebaseInstanceId.getInstance().getToken();
        Log.d("hwllooooooooooooooooooo", string);

        mStorageRef = FirebaseStorage.getInstance();
        storage = mStorageRef.getReferenceFromUrl("gs://learn-f72e2.appspot.com/Images"); // actual location
        imageRef = storage.child("hire");
        progessBar = new ProgressDialog(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        uploadButton = (Button) findViewById(R.id.upload);
        downloadButton = (Button) findViewById(R.id.download);
        imageButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        downloadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)

    {
        switch (v.getId()) {

            case R.id.imageButton:
                cameraActivity();
                break;

            case R.id.upload:
                uploadFile();
                break;

            case R.id.download:
                try {
                    downloadFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;

        }
    }

    private void downloadFile() throws IOException

    {
        progessBar.setTitle("Downloading");
        progessBar.show();
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                progessBar.dismiss();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageButton.setImageBitmap(bitmap);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void uploadFile() {
        Log.e("this is the upload file", "$$$");


        if (imageBitmap != null) {
            Log.e("this is the upload file", "$$$&&&&");
            progessBar.setTitle("Uploading");
            progessBar.show();

            //filename of the image uploadedd


            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream);
            byte[] data = byteStream.toByteArray();

            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    progessBar.dismiss();
                    Toast.makeText(MainActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();


                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    progessBar.dismiss();
                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    downloadUrl = taskSnapshot.getDownloadUrl();
                    Log.e("this is the downloadURL", downloadUrl + "");
                    // Do what you want

                }
            });


        }
        Log.e("this is the upload file", "$$$");

    }

    private void cameraActivity() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        // start the image capture Intent
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {


                // successfully captured the image
                // display it in image view
                previewCapturedImage(data);
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    private void previewCapturedImage(Intent data) {
        Bundle bundle = data.getExtras();
        imageBitmap = (Bitmap) bundle.get("data");
        imageButton.setImageBitmap(imageBitmap);


    }


}
