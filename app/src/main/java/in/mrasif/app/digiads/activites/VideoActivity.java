package in.mrasif.app.digiads.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SyncParams;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
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

public class VideoActivity extends AppCompatActivity implements DownloadHandler, SurfaceHolder.Callback {

    private static final String TAG = "VideoActivity";

    File rootDir;
    List<VideoModel> videos;
    ProgressDialog dialog;
    int videoID;
    int playId;

    Display currentDisplay;
    SurfaceView svVideo;
    SurfaceHolder surfaceHolder;
    MediaPlayer player;
    int screen_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currentDisplay = getWindowManager().getDefaultDisplay();

        setContentView(R.layout.activity_video);
        dialog=new ProgressDialog(this);
        configCacheDir();
        Intent intent=getIntent();
        screen_id=intent.getIntExtra("screen_id",0);

        svVideo=findViewById(R.id.svVideo);

        surfaceHolder = svVideo.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        player = new MediaPlayer();



        loadDatas(screen_id);

    }

    private void configCacheDir() {
        rootDir=new File(Environment.getExternalStorageDirectory()+"/DigiAds");
        if (!rootDir.exists()){
            try{
                rootDir.mkdir();
            }catch (Exception e){}
        }
    }

    public void triggerDownload(){
        Log.d(TAG, "trigerDownload: Download triggered.");
        final int seconds=60;
        new CountDownTimer(seconds * 1000, 1000) {
            int time = (int) seconds;
            public void onTick(long millisUntilFinished) {
                time--;
            }

            public void onFinish() {
                loadDatas(screen_id);
            }
        }.start();
    }

    private void loadDatas(int screen_id) {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        ApiDao.getApis().getVideos(screen_id).enqueue(new Callback<List<VideoModel>>() {
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
            VideoModel videoModels=videos.get(videoID);
            File filePath=new File(rootDir+ "/"+AllMethods.getFileNameFromUrl(videoModels.getRender_details().getPath()));
            if (filePath.exists()) {
                videoModels.setFile(filePath.toString());
                videoID++;
                makeCache();
            }
            else {
                new Downloader(this, videoModels.getRender_details().getPath(), filePath).execute();
            }
        }
        else {
            System.out.println("###################################");
            System.out.println((videoID)+" files downloaded.");
            System.out.println(videos);
            System.out.println("###################################");

            playId=0;
            playVideos();
        }
    }

    private void playVideos() {
        if (playId<videos.size()) {

            VideoModel videoModel = videos.get(playId);
            playVideo(videoModel.getFile(), videoModel.getDuration());
        }
        else {
            playId=0;
            playVideos();
        }
    }

    private void playVideo(String file_path, final long seconds) {
        System.out.println("########################################");
        System.out.println("Playing video id: "+playId);
        System.out.println("########################################");
        if(TextUtils.isEmpty(file_path)){
            playId++;
            playVideos();
        }
        else {
            play(file_path);
            new CountDownTimer(seconds * 1000, 1000) {
                int time = (int) seconds;
                public void onTick(long millisUntilFinished) {
                    time--;
                }

                public void onFinish() {
                    playId++;
                    playVideos();
                }
            }.start();
        }
    }

    private void play(String file_path){
        try {
            try {
                player.reset();
            }catch (Exception e){
            }
            player.setDataSource(file_path);
            player.setDisplay(surfaceHolder);
            player.prepare();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.start();
//        vView.setVideoPath(file_path);
//        vView.start();
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
    }

    @Override
    public void downloadProgressDismiss(File file) {
        dialog.dismiss();
        System.out.println("###########################################");
        System.out.println("Downloaded: "+file.getName());
        System.out.println("###########################################");
        VideoModel videoModels=videos.get(videoID);
        videoModels.setFile(file.toString());
        videoID++;
        makeCache();
        triggerDownload();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        /*mediaPlayer.setDisplay(holder);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            finish();
        }*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
