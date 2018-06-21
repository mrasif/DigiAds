package in.mrasif.app.digiads.activites;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import in.mrasif.app.digiads.R;
import in.mrasif.app.digiads.apis.ApiDao;
import in.mrasif.app.digiads.models.SetupData;
import in.mrasif.app.digiads.utils.AllKeys;
import in.mrasif.app.digiads.utils.AskPermission;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText etId;
    private Button btnLoadAds, btnPlayVideos;
    private SharedPreferences preferences;
    AskPermission askPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences(AllKeys.SP_NAME,MODE_PRIVATE);
        askPermission=new AskPermission(this);
        etId=findViewById(R.id.etId);
        btnLoadAds=findViewById(R.id.btnLoad);
        btnPlayVideos=findViewById(R.id.btnPlayVideos);
        btnLoadAds.setOnClickListener(this);
        btnPlayVideos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoad: {
                int id=Integer.parseInt(etId.getText().toString());
                if (id>=0){
                    ApiDao.getApis().getSetupData(id).enqueue(new Callback<SetupData>() {
                        @Override
                        public void onResponse(Call<SetupData> call, Response<SetupData> response) {
                            System.out.println("###########################################");
                            System.out.println(new Gson().toJson(response));
                            System.out.println("###########################################");
                            SetupData setupData=response.body();
                            preferences.edit().putString(AllKeys.SP_SETUP_DATA,new Gson().toJson(setupData)).commit();
                            startActivity(new Intent(MainActivity.this,AdsActivity.class));
                        }

                        @Override
                        public void onFailure(Call<SetupData> call, Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                        }
                    });
                }
            } break;
            case R.id.btnPlayVideos: {

                askPermission.askPermission(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MEDIA_CONTENT_CONTROL},2);
                boolean asked=askPermission.isPermissionAllowed(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (asked){
                    startActivity(new Intent(MainActivity.this,VideoActivity.class));
                }
                else {
                    Toast.makeText(this, "Please give permission !", Toast.LENGTH_SHORT).show();
                }
            } break;
        }
    }
}
