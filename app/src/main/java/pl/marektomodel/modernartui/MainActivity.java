package pl.marektomodel.modernartui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int[] viewsNames = new int[] {R.id.view7, R.id.view8, R.id.view9,
            R.id.view10, R.id.view11, R.id.view12 };
    private HashMap<Integer,View> views = new HashMap<Integer, View>();
    private HashMap<Integer, Integer> viewsBaseColors = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        initBaseColors();

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int progressChanged;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;

                for (Map.Entry<Integer, Integer> entry : viewsBaseColors.entrySet()) {
                    setProgressBasedBackgroundColor(views.get(entry.getKey()), entry.getValue());
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            private void setProgressBasedBackgroundColor(View view, int BaseColor) {
                float[] hsvColor = new float[3];
                Color.colorToHSV(BaseColor, hsvColor);
                hsvColor[0] = hsvColor[0] + progressChanged;
                hsvColor[0] = hsvColor[0] % 360;
                view.setBackgroundColor(Color.HSVToColor(Color.alpha(BaseColor), hsvColor));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_more_information) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_button_positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.moma.org/collection/works/187158?locale=en"));
                    startActivity(intent);
                }
            })
            .setNegativeButton(R.string.dialog_button_negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        for (int viewName : viewsNames) {
            views.put(viewName, findViewById(viewName));
        }
    }

    private void initBaseColors() {
        for (int viewName : viewsNames) {
            viewsBaseColors.put(viewName, getColorFromView(views.get(viewName)));
        }
    }

    private static int getColorFromView(View view) {
        ColorDrawable buttonColor = (ColorDrawable) view.getBackground();
        return buttonColor.getColor();
    }
}
