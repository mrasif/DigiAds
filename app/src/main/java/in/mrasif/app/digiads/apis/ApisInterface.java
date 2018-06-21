package in.mrasif.app.digiads.apis;



import java.util.List;

import in.mrasif.app.digiads.models.SetupData;
import in.mrasif.app.digiads.models.VideoModel;
import in.mrasif.app.digiads.utils.AllUrls;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by asif on 16/6/18.
 */

public interface ApisInterface {

    @GET(AllUrls.SETUP)
    Call<SetupData> getSetupData(@Path("id") int id);

    @GET(AllUrls.VIDEOS)
    Call<List<VideoModel>> getVideos();

}
