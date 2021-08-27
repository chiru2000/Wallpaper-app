package com.example.bestwall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUs extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Chiranth A J", "Name 2", "Name 3"};
    String mDescription[] = {"", "", ""};
    int image[] = {R.drawable.ic_profile1, R.drawable.ic_profile2, R.drawable.ic_profile3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        listView = findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this,mTitle,mDescription,image);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Uri uri = Uri.parse( "http://www.github.com/chiru2000" );
                    startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                    // Toast.makeText(AboutUs.this,"Hi! Chiranth",Toast.LENGTH_SHORT).show();
                }
                if(position == 1) {
                    // Uri uri = Uri.parse( "https://www.instagram.com/mr._.manuu/" );
                    // startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                    Toast.makeText(AboutUs.this,"Hi! Profile2",Toast.LENGTH_SHORT).show();
                }
                if(position == 2) {
                    // Uri uri = Uri.parse( "https://www.instagram.com/mr_kalyan" );
                    // startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                    Toast.makeText(AboutUs.this,"Hi! Profile3",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImg[];

        MyAdapter (Context c, String title[], String Description[], int imgs[]) {
            super(c, R.layout.row,R.id.TextView1,title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = Description;
            this.rImg = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = row.findViewById(R.id.image);
            TextView Mytitle = row.findViewById(R.id.TextView1);
            // TextView Mydescription = row.findViewById(R.id.TextView2);
            images.setImageResource(rImg[position]);
            Mytitle.setText(rTitle[position]);
            // Mydescription.setText(rDescription[position]);
            return row;
        }
    }
}
