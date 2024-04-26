package com.example.spamfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Details extends AppCompatActivity {
    ListView text;
    Intent i;
    Map<String, List<String>> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        text=findViewById(R.id.finalText);

        i=getIntent();
        String result=i.getStringExtra("Value");
        String finalarray[] = result.split("\n");
        final int n=finalarray.length;
        String number[]=new String[n];
        String message[]=new String[n];
        try {
            for (int i = 0; i < n; i++) {
                String temp[] = finalarray[i].split(",");
                number[i] = temp[0];
                message[i] = temp[1];
            }
            for(int i=0;i<n;i++)
            {
                if (!map.containsKey(number[i]))
                    map.put(number[i], new LinkedList<String>());
                map.get(number[i]).add(message[i]);
            }
//        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,finalarray);
//        text.setAdapter(adapter);
            int map_size=map.size();
            final String num[]=new String[map_size];
            final ArrayList<String> msg=new ArrayList<String>();
            int index = 0;
            for (Map.Entry<String, List<String>> mapEntry : map.entrySet()) {
                num[index] = mapEntry.getKey();
                msg.add(mapEntry.getValue().toString());
                index++;
            }
            CustomAdapter customAdapter = new CustomAdapter(num);
            text.setAdapter(customAdapter);
            text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent i=new Intent(Details.this,Personal.class);
                    String s=msg.get(position);
                    String temp="";
                    for(int j=1;j<(s.length()-1);j++)
                        temp+=s.charAt(j);
                    i.putExtra("Display",temp);
                    i.putExtra("Mobile",num[position]);
                    startActivity(i);
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    class CustomAdapter extends BaseAdapter {
        String[] number;
        public CustomAdapter(String[] num)
        {
            this.number=num;
        }
        @Override
        public int getCount() {
            return number.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.custom_layout, null);
            ImageView image = view.findViewById(R.id.img);
            TextView title = view.findViewById(R.id.textView_title);
            image.setImageResource(R.drawable.ic_person_black_24dp);
            title.setText(number[position]);
            return view;
        }
    }
}
