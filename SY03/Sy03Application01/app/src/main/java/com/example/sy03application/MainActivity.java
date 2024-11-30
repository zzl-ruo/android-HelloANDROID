package com.example.sy03application;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] animalImages = {
            R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        List<String> dataList = new ArrayList<>();
        Set<String> addedAnimals = new HashSet<>();
        for (String name : animalNames) {
            if (!addedAnimals.contains(name)) {
                dataList.add(name);
                addedAnimals.add(name);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_item, dataList) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    view = inflater.inflate(R.layout.simple_item, parent, false);
                }
                ImageView imageView = view.findViewById(R.id.imageView);
                TextView textView = view.findViewById(R.id.textView);
                // - Get the item from the adapter using the position
                String animalName = getItem(position);
                textView.setText(animalName);
                imageView.setImageResource(animalImages[position]);
                return view;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = dataList.get(position);
                Toast.makeText(MainActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }
}