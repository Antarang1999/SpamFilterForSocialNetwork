package com.example.spamfilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Homepage extends AppCompatActivity {
    CheckBox corona,science,politics,greetings,publicity;
    Button next,out;
    String co;
    String po;
    String gr;
    String sc;
    String pu;
    String message="number,validate\n";
    String path="";

    private static Homepage inst;
    public static Homepage instance() {
        return inst;
    }
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        try {
            addListenerOnButtonCLick();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            // If Permission Granted Then Show SMS
            //Toast.makeText(getApplicationContext(),"Start",Toast.LENGTH_LONG).show();
            refreshSmsInbox();
            download("test.csv");
        }
        else{
            //  Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }
    public void onBackPressed() {
    }

    public void addListenerOnButtonCLick() throws IOException {
        corona=findViewById(R.id.corona);
        science=findViewById(R.id.science);
        politics=findViewById(R.id.politics);
        greetings=findViewById(R.id.greeting);
        next=findViewById(R.id.next);
        out=findViewById(R.id.signout);
        publicity=findViewById(R.id.publicity);

        download("corona.txt");
        download("science.txt");
        download("politics.txt");
        download("greetings.txt");
        download("publicity.txt");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String message="";
                StringBuilder result=new StringBuilder();
                if(corona.isChecked())
                {
                    result.append(co);
                }
                if(science.isChecked())
                {
                    result.append(sc);
                }
                if(politics.isChecked())
                {
                    result.append(po);
                }
                if(greetings.isChecked())
                {
                    result.append(gr);
                }
                if(publicity.isChecked())
                {
                    result.append(pu);
                }
                Intent next=new Intent(Homepage.this,Details.class);
                next.putExtra("Value",result.toString());
                startActivity(next);

            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Homepage.this,Admin.class);
                startActivity(i);
            }
        });
    }

    void download(String s)
    {
        StorageReference sr = FirebaseStorage.getInstance().getReference().child(s);
        try {
            Uri.Builder uri = new Uri.Builder();
            uri.appendEncodedPath(getFilesDir()+"/"+s);
            path=getFilesDir()+"/"+s;
            Uri u = uri.build();
            sr.getFile(u)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        } catch (Exception e) {
           // Toast.makeText(getApplicationContext(),e.toString()+"\nException" + s,Toast.LENGTH_LONG).show();
        }
        if(s.compareTo("test.csv")==0)
        {
            try {
                FileOutputStream fos = openFileOutput(s, Context.MODE_PRIVATE);
                fos.write(message.getBytes());
                //Toast.makeText(getApplicationContext(),"Written",Toast.LENGTH_LONG).show();
                fos.close();
            }
            catch (IOException e) {
                //Toast.makeText(getApplicationContext(),e.toString()+"\nUpload Exception",Toast.LENGTH_LONG).show();
            }
            upload(s);
        }
        else
            openFile(s);
    }

    void openFile(String s) {
        FileInputStream fis = null;
        try {
            if(s.compareTo("corona.txt")==0) {
                fis = openFileInput(s);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                co = sb.toString();
            }
            if(s.compareTo("politics.txt")==0) {
                fis = openFileInput(s);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                po = sb.toString();
            }
            if(s.compareTo("science.txt")==0) {
                fis = openFileInput(s);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                sc = sb.toString();
            }
            if(s.compareTo("greetings.txt")==0) {
                fis = openFileInput(s);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                gr = sb.toString();
            }
            if(s.compareTo("publicity.txt")==0) {
                fis = openFileInput(s);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                pu = sb.toString();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode==123){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Granted",Toast.LENGTH_SHORT).show();
                refreshSmsInbox();
            }
            else{
                Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        //int indexDate= smsInboxCursor.getColumnIndex("date");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        int count=0;
        do {
//                String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
//                        "\n" + smsInboxCursor.getString(indexBody) + "\n";
            String num=smsInboxCursor.getString(indexAddress);
            String str = smsInboxCursor.getString(indexBody);
            str = str.replaceAll(",", " ");
            message+=num+","+str+"\n";
            count++;
        } while (smsInboxCursor.moveToNext() && count<10);
    }
    void upload(String s)
    {
        Uri file = Uri.fromFile(new File(path));
        StorageReference sr = FirebaseStorage.getInstance().getReference().child(s);
        try {
            sr.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //Toast.makeText(getApplicationContext(),exception.toString()+"Failure Upload",Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
