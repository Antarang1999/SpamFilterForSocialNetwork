package com.example.spamfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Personal extends AppCompatActivity {
    ListView display;
    Intent i;
    String number,msg;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        i=getIntent();
        number=i.getStringExtra("Mobile");
        msg=i.getStringExtra("Display");

        display=findViewById(R.id.finalMsg);
        txt=findViewById(R.id.number);
        txt.setText(number);
        String a[]=msg.split(", ");
        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,a);
        display.setAdapter(adapter);

    }
}
