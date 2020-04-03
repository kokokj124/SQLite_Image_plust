package com.example.sqlite_image_plust;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    Button btThem;
    public static Database database;
    ListView listView;
    Adapter_DoVat adapter_doVat;
    ArrayList<DoVat> arrayList;
    int MATCH_PARENT = (int) 1e9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btThem = findViewById(R.id.btThem);
        listView = findViewById(R.id.listDoVat);
        arrayList = new ArrayList<>();
        database = new Database(this,"data.sqlite",null,1);
        database.queryData("create table if not exists DoVat(Ten varchar(50)  primary key, MoTa varchar(50), HinhAnh blob)");
        //get data
//        database.queryData("DROP TABLE DoVat");
        Cursor curson = database.getData("select * from DoVat");
        while (curson.moveToNext()){
            arrayList.add(new DoVat(
                    curson.getString(0),
                    curson.getString(1),
                    curson.getBlob(2)));
        }
        adapter_doVat = new Adapter_DoVat(this,arrayList,R.layout.list_do_vat);
        listView.setAdapter(adapter_doVat);
        btThem.setOnClickListener((v) -> {
            startActivity(new Intent(MainActivity.this,ThemDoVatActivity.class));
            Toast.makeText(this,"aaa",Toast.LENGTH_LONG).show();
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        MenuItem searchMenu = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setMaxWidth(MATCH_PARENT);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_doVat.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
