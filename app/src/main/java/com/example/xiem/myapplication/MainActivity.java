package com.example.xiem.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {

    private EditText editText1;
    private TextView textView1;
    Button button1;
    byte[] key={0x38,0x36,0x36,0x39,0x36,0x32,0x30,0x32,0x34,0x31,0x39,0x34,0x38,0x34,0x31};

    //private static DB_helper myDb_helper;
    private static SQLiteDatabase mdb;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText)findViewById(R.id.editText);
        textView1 = (TextView)findViewById(R.id.textView);

        button1 = (Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = editText1.getText().toString();
                String[] arry = str.split(" ");
                int strLength = arry.length;
                byte[] byteText = new byte[strLength];
                Integer[] intArry = new Integer[strLength];

                try{
                    for(int i=0; i<strLength; i++){

                        intArry[i] = Integer.valueOf(arry[i],16);   //接受16进制数

                        byteText[i] = (byte)(intArry[i] ^ key[i%15]);
                    }
                    textView1.setText(new String(byteText,"UTF-8"));

                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        });

        if(isExternalStorageWritable()==true) {
            Toast.makeText(this,"sd_card is avaliable",Toast.LENGTH_LONG).show();
            File newfile = getMyStorageDir("newfile4");
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getMyStorageDir(String Name) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                "/storage/sdcard1"), Name);
        if (!file.mkdirs()) {
            Log.e("Tag", "Directory not created");
            Toast.makeText(this,"创建目录失败",Toast.LENGTH_SHORT);
        }
        return file;
    }

}
