package com.example.projet;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ConsulterActivity extends AppCompatActivity {

    TextView user;
    DBscore mydb ;
    ListView scoreelist;
    ImageButton btn_delete;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter);
        mydb= new DBscore(this);
        user=(TextView)findViewById(R.id.TextUSER);
        listItem=new ArrayList<>();
        scoreelist=(ListView)findViewById(R.id.score_list);
        btn_delete=(ImageButton)findViewById(R.id.deleteAll) ;
        Bundle bundle = getIntent().getExtras();
        String txt1 = bundle.getString("usernameIntent");
        user.setText("user=" + txt1 );
        viewdata();
        adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,listItem);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mydb.removeAll();
                adapter.notifyDataSetChanged();
                scoreelist.setAdapter(null);


            }
        });
    }

    private void viewdata() {
        Cursor cursor=mydb.viewDatasorted();
        if (cursor.getCount()==0)
        {Toast.makeText(this,"no data to show", Toast.LENGTH_SHORT).show();}
        else
        {while(cursor.moveToNext()){listItem.add(cursor.getString(0)+": "+cursor.getString(1));
        }
            adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,listItem);
            scoreelist.setAdapter(adapter);
        }
    }




}
