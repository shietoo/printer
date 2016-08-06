package com.example.shietoo.printer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Myprinter myprinter;
    static final int REQUEST_WRITE_EXTERNAL_STORAGE =2;
    static final int REQUEST_CAMERA = 4;
    static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myprinter = (Myprinter) findViewById(R.id.myPrint);




        try {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
                }
            }

        } catch (Exception e) {
            Log.i("aaa", e.toString());
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 取得權限
                } else {
                    // 未取得權限
                }
            case REQUEST_CAMERA:
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 取得權限
                } else {
                    // 未取得權限
                }
                return;
        }

    }

    public void clear(View view){
        myprinter.clear();
    }
    public void undo(View view){
        myprinter.undo();
    }
    public void redo(View view){
        myprinter.redo();
    }
    public void save(View view){
        myprinter.save();
    }
    public void colorpicker(View view){

        try {
            new ColorPickerDialog(MainActivity.this, new ColorPickerDialog.OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    myprinter.setColor(color);
                }
            },myprinter.getColor()).show();

        }catch (Exception e){
            Log.i("aaa",e.toString());
        }

    }
    public void bgcolor(View view){

        try {
            new ColorPickerDialog(MainActivity.this, new ColorPickerDialog.OnColorChangedListener() {
                @Override
                public void colorChanged(int color) {
                    myprinter.setBGColor(color);
                }
            },myprinter.getBGColor()).show();

        }catch (Exception e){
            Log.i("aaa",e.toString());
        }

    }
    public void getstroke(View view){
       try {
           final String[] stroke = {"4","6","8","10","12","14","16"};
           boolean[] check = {false,false,false,false,false};
           AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
           dialogBuilder.setTitle("stroke");
           dialogBuilder.setItems(stroke, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int item) {
                   String selectedText = stroke[item].toString();  //Selected item in listview
                   myprinter.getStoke(Integer.parseInt(selectedText));
               }
           });
           //Create alert dialog object via builder
           AlertDialog alertDialogObject = dialogBuilder.create();
           //Show the dialog
           alertDialogObject.show();
       }catch (Exception e){
           Log.i("aaa",e.toString());
       }

    }
}
