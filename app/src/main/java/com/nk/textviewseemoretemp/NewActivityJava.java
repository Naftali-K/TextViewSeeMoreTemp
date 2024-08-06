package com.nk.textviewseemoretemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nk.textviewseemoretemp.adapters.TestRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewActivityJava extends AppCompatActivity {

    private TextView textView;
    private EditText addItemTextView;
    private Button updateTextBtn, addItemBtn;

    private TestRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_java);
        setContentView(R.layout.activity_new);
        setReferences();

//        setShowMoreView(textView, getString(R.string.long_text));
        setShowMoreView(textView, getString(R.string.long_text), R.color.red, R.color.red);

        updateTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setShowMoreView(textView, "Updated text" + getString(R.string.long_text));
                setShowMoreView(textView, "Updated text" + getString(R.string.long_text), R.color.red, R.color.blue);
            }
        });


        adapter = new TestRecyclerViewAdapter(createList(4), getBaseContext());
        recyclerView.setAdapter(adapter);


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItemToList(addItemTextView.getText().toString());
                addItemTextView.setText(null);
            }
        });
    }

    private void setReferences() {
        textView = findViewById(R.id.text_view);
        updateTextBtn = findViewById(R.id.updateTextBtn);
        recyclerView = findViewById(R.id.recycler_view);
        addItemTextView = findViewById(R.id.add_item_text_view);
        addItemBtn = findViewById(R.id.add_item_btn);
    }

    private void setShowMoreView(TextView textView, String text) {
        ShowMoreHelper.Builder builder = new ShowMoreHelper.Builder(textView.getContext());
        builder.showMoreLessUnderlined(true);
//        builder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.invoke(message);
//            }
//        });
//        builder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        builder.setOnClickListener(v -> listener.invoke(message));
        ShowMoreHelper helper = builder.build();
//        Log.d("TAG", "onCreate: TextView: " + textView.toString());
        helper.addShowMoreLess(textView, text, false);
    }

    private void setShowMoreView(TextView textView, String text, int colorSeeMore, int colorSeeLess) {
        ShowMoreHelper.Builder builder = new ShowMoreHelper.Builder(textView.getContext());
            builder.showMoreLessUnderlined(true);
//            builder.setMoreLabelColor(colorSeeMore);
            builder.showMoreLabelColor(colorSeeMore);
//            builder.setLessLabelColor(colorSeeLess);
            builder.showLessLabelColor(colorSeeLess);

        ShowMoreHelper helper = builder.build();
//        Log.d("TAG", "onCreate: TextView: " + textView.toString());
        helper.addShowMoreLess(textView, text, false);
    }

    private List<String> createList(int numberItems) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= numberItems; i++) {
            String string = "Position "  + i + " " + getString(R.string.long_text);
            list.add(string);
        }

        return list;
    }
}