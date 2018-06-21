package in.mrasif.app.digiads.utils;

public class AllMethods {
    public static String getFileNameFromUrl(String url){
        // http://parxsys.com/files/DMS/CP/1_Charge_Your_Phone_Here_10_Sec.mp4?autoplay=1&end=11&controls=0&http_video=true&html5=1
        url=url.split("\\?")[0];
        String[] urlParts=url.split("/");
        if (urlParts.length>=2){
            return urlParts[urlParts.length-1];
        }
        else {
            return null;
        }
    }
}
