package in.mrasif.app.digiads.activites;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import in.mrasif.app.digiads.R;
import in.mrasif.app.digiads.models.Position;
import in.mrasif.app.digiads.models.SetupData;
import in.mrasif.app.digiads.models.Template;
import in.mrasif.app.digiads.utils.AllKeys;

public class AdsActivity extends AppCompatActivity {

    private static final String TAG = "AdsActivity";
    private SharedPreferences preferences;
    private SetupData setupData;
    private RelativeLayout mainLayout;
    private RelativeLayout vGraphic, vText, vTicker, vTime;
    private int WIDTH;
    private int HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
        preferences=getSharedPreferences(AllKeys.SP_NAME,MODE_PRIVATE);
        setupData=new Gson().fromJson(preferences.getString(AllKeys.SP_SETUP_DATA,new Gson().toJson(new SetupData())),SetupData.class);

        WIDTH=getWindowManager().getDefaultDisplay().getWidth();
        HEIGHT=getWindowManager().getDefaultDisplay().getHeight();

        System.out.println("Width: "+WIDTH+"\tHeight: "+HEIGHT);
        mainLayout=findViewById(R.id.mainLayout);

        vGraphic=(RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_graphic,null,false);
        vText=(RelativeLayout)  LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_text,null,false);
        vTicker=(RelativeLayout)  LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_ticker,null,false);
        vTime=(RelativeLayout)  LayoutInflater.from(getApplicationContext()).inflate(R.layout.comp_time,null,false);

        /*mainLayout.addView(vGraphic);
        mainLayout.addView(vText);
        mainLayout.addView(vTicker);
        mainLayout.addView(vTime);*/

        if (null!=setupData) {
            loadViews(setupData);
        }

    }

    private void loadViews(SetupData setupData) {
        Template template=setupData.getTemplate();

        for (Position position:template.getPositions()){
            setupPosition(position);
        }
    }

    private void setupPosition(Position position) {
        double left=Double.parseDouble(position.getLeft())*WIDTH;
        double right=WIDTH-Double.parseDouble(position.getRight())*WIDTH;
        double top=Double.parseDouble(position.getTop())*HEIGHT;
        double bottom=HEIGHT-Double.parseDouble(position.getBottom())*HEIGHT;

        System.out.println(left+"\t"+top+"\t"+right+"\t"+bottom);

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins( (int)left, (int)top, (int)right, (int)bottom);



        if (position.getField().getName().equalsIgnoreCase("graphics")){
//            vGraphic.setLayoutParams(layoutParams);

            TextView textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.RED);
            textView.setText("Graphics");
            mainLayout.addView(textView);
        }
        else if (position.getField().getName().equalsIgnoreCase("Ticker")){
//            vTicker.setLayoutParams(layoutParams);
            TextView textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.GREEN);
            textView.setText("Ticker");
            mainLayout.addView(textView);
        }
        else if (position.getField().getName().equalsIgnoreCase("text")){
//            vText.setLayoutParams(layoutParams);
            TextView textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.BLUE);
            textView.setText("Text");
            mainLayout.addView(textView);
        }
        else if (position.getField().getName().equalsIgnoreCase("time")){
//            vTime.setLayoutParams(layoutParams);
            TextView textView=new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundColor(Color.YELLOW);
            textView.setText("Time");
            mainLayout.addView(textView);
        }
    }

}
