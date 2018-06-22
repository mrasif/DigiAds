package in.mrasif.app.digiads.activites;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
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
    private VideoView vView;
    File rootDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        dialog=new ProgressDialog(this);
        vView=findViewById(R.id.vView);

        rootDir=new File(Environment.getExternalStorageDirectory()+"/DigiAds");
        if (!rootDir.exists()){
            try{
                rootDir.mkdir();
            }catch (Exception e){}
        }

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
            Toast.makeText(this, (videoID)+" files downloaded.", Toast.LENGTH_SHORT).show();
            System.out.println("###################################");
            System.out.println(videos);
            System.out.println("###################################");
            // TODO Play Videos
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
        Toast.makeText(this, "Playing video id: "+playId, Toast.LENGTH_SHORT).show();
        play(file_path);
        new CountDownTimer(seconds*1000, 1000) {
            int time=(int)seconds;

            /*public String checkDigit(int number) {
                return number <= 9 ? "0" + number : String.valueOf(number);
            }*/

            /*public String formatAsClock(int number){
                int m=number/60;
                int s=number%60;
                return checkDigit(m)+":"+checkDigit(s);
            }*/

            public void onTick(long millisUntilFinished) {
//                tvTimer.setText(formatAsClock(time));
                time--;

            }

            public void onFinish() {
                playId++;
                playVideos();
            }

        }.start();
    }

    private void play(String file_path){
        vView.setVideoPath(file_path);
        vView.start();
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
        /*videoUrls.add(file.toString());
        play();
        if (videoID<videos.size()){
            VideoModel videoModels=videos.get(videoID++);
            downloadNow(videoModels.getRender_details().getPath());
        }*/
        VideoModel videoModels=videos.get(videoID);
        videoModels.setFile(file.toString());
        videoID++;
        makeCache();
    }
}
