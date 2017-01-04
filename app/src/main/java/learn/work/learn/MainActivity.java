package learn.work.learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.crash.FirebaseCrash;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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


    }
}
