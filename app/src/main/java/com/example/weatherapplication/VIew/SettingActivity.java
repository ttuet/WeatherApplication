package com.example.weatherapplication.VIew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.weatherapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    String[] list;
    ImageView iconBack;
    LinearLayout timeChoose, tempChoose;
    TextView doC, timePeriodic;
    SharedPreferences Var;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControl();
        Var = getApplicationContext().getSharedPreferences("Vari",MODE_PRIVATE);
        String o = Var.getString("doC","C");
        doC.setText(o);
        int i = Var.getInt("periodic",1);
        timePeriodic.setText(i+"giờ một lần");
        tempChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               list = new String[]{"°C","°F"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this);
                mBuilder.setTitle("Chọn độ đo nhiệt độ: ");
                mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            doC.setText(list[i]);
                            Var.edit().putString("doC",list[i]).apply();
                            dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.create();
                mBuilder.show();

            }
        });
        timeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new String[]{"1","2","4","12","24"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingActivity.this);
                mBuilder.setTitle("Chọn khoảng giờ cập nhật :");
                mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        timePeriodic.setText(list[i]+"giờ một lần");
                        Var.edit().putInt("periodic",Integer.parseInt(list[i])).apply();
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.create();
                mBuilder.show();

            }
        });
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void addControl() {
        iconBack = findViewById(R.id.back);
        timeChoose = findViewById(R.id.lnTimeChoose);
        tempChoose = findViewById(R.id.lnTempChoose);
        doC = findViewById(R.id.doC);
        timePeriodic = findViewById(R.id.periodictime);
    }
}
