package com.myha.internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    EditText etUserName;
    Button btLuckySpin;
    TextView tvluckyValue;
    TextView tvluckyValueData;

    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;

    String fileName = "InternalStorage.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = findViewById(R.id.et_user_name);
        btLuckySpin = findViewById(R.id.bt_lucky_spin);
        tvluckyValue = findViewById(R.id.tv_spin_value);
        tvluckyValueData = findViewById(R.id.tv_spin_value_data);

        btLuckySpin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(etUserName.getText())) {
                    etUserName.setError("Please input your name");
                    etUserName.requestFocus();
                }
                else {
                    Random r = new Random();
                    int luckyValue = r.nextInt(101 - 60) + 60 ;
                    tvluckyValue.setText(String.valueOf(luckyValue));
                    String usernameToWrite = etUserName.getText().toString();
                    usernameToWrite += " has " + luckyValue + " lucky points";
                    usernameToWrite += "\n";
                    byte[] bytesOfUserName = usernameToWrite.getBytes();
                    try {
                        fileOutputStream = openFileOutput(fileName, Context.MODE_APPEND);
                        fileOutputStream.write(bytesOfUserName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally
                    {
                        try {
                            fileOutputStream.close();
                            etUserName.setText("");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        fileInputStream = openFileInput(fileName);
                        StringBuffer stringBuffer = new StringBuffer();
                        int read = -1;
                        while ((read = fileInputStream.read()) != -1) {
                            stringBuffer.append((char) read);
                        }
                        tvluckyValueData.setText(stringBuffer.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally
                    {
                        try {
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        });
    }
}
