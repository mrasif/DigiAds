package in.mrasif.app.digiads.activites;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.mrasif.app.digiads.R;
import in.mrasif.app.digiads.apis.ApiDao;
import in.mrasif.app.digiads.models.VideoModel;
import in.mrasif.app.digiads.utils.AllMethods;
import in.mrasif.app.digiads.utils.DownloadHandler;
import in.mrasif.app.digiads.utils.Downloader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoActivity extends AppCompatActivity implements DownloadHandler{

    private static final String TAG = "VideoActivity";

    List<VideoModel> videos;
    ProgressDialog dialog;
    int videoID;
    int playId;
    List<String> videoUrls;
    private VideoView vView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        dialog=new ProgressDialog(this);
        videoUrls=new ArrayList<>();
        vView=findViewById(R.id.vView);
        vView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                mp=null;
                Toast.makeText(VideoActivity.this, "Completed : "+playId, Toast.LENGTH_SHORT).show();
                play();
            }
        });
        vView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        loadDatas();

    }

    private void loadDatas() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        ApiDao.getApis().getVideos().enqueue(new Callback<List<VideoModel>>() {
            @Override
            public void onResponse(Call<List<VideoModel>> call, Response<List<VideoModel>> response) {
                videos=response.body();
                System.out.println("###########################################");
                System.out.println(new Gson().toJson(videos));
                System.out.println("###########################################");
                videoID=0;
                dialog.dismiss();

                makeCache();

            }

            @Override
            public void onFailure(Call<List<VideoModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
            }
        });
    }

    private void makeCache() {

        if (videoID<videos.size()){
            VideoModel videoModels=videos.get(videoID++);
            downloadNow(videoModels.getRender_details().getPath());
        }
    }

    private void play(){
        Toast.makeText(this, ""+videoUrls.size(), Toast.LENGTH_SHORT).show();
        if (playId<videoUrls.size()) {
            String url=videoUrls.get(playId++);
            System.out.println(url);
            Uri vidUri = Uri.parse(url);
            vView.stopPlayback();
            vView.setVideoURI(vidUri);
            vView.start();
            Toast.makeText(VideoActivity.this, "Prepared called", Toast.LENGTH_SHORT).show();
            // Clear the background resource when the video is prepared to play.
            vView.setBackgroundResource(0);
        }
        if (playId>=videoUrls.size()){
            playId=0;
        }
    }

    private void downloadNow(String url){
        File file=new File(Environment.getExternalStorageDirectory()+"/myapp");
        if (!file.exists()){
            try{
                file.mkdir();
            }catch (Exception e){}
        }
        File filePath=new File(file+ "/"+AllMethods.getFileNameFromUrl(url));
        if (filePath.exists()) {
            videoUrls.add(filePath.toString());
            play();
        }
        else {
            new Downloader(this, url, filePath).execute();
        }
    }

    @Override
    public void downloadProgressShow() {
        dialog.setTitle("Downloading");
        dialog.setTitle("Wait until download complete.");
        dialog.setMax(100);
        dialog.setIndeterminate(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void downloadProgressUpdate(int progress) {
        dialog.setProgress(progress);
        Log.d(TAG, "downloadProgressUpdate: "+progress);
    }

    @Override
    public void downloadProgressDismiss(File file) {
        dialog.dismiss();
        Toast.makeText(this, "Downloaded: "+file.getName(), Toast.LENGTH_SHORT).show();
//        play(file.toString(),file.getName());
        videoUrls.add(file.toString());
        play();
        if (videoID<videos.size()){
            VideoModel videoModels=videos.get(videoID++);
            downloadNow(videoModels.getRender_details().getPath());
        }
    }
}
