package com.nk.textviewseemoretemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nk.textviewseemoretemp.adapters.FromIndiaTestRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.glailton.expandabletextview.ExpandableTextView;

public class FromIndiaActivity extends AppCompatActivity {

    private ExpandableTextView textView;
    private EditText addItemTextView;
    private Button updateTextBtn, addItemBtn;

    private FromIndiaTestRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_india);
        setReferences();

        updateTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Updated Text " + getString(R.string.long_text));
                    textView.setAnimationDuration(500);
                    textView.setReadMoreText("View More");
                    textView.setReadLessText("View Less");
                    textView.setCollapsedLines(3);
                    textView.setIsExpanded(true);
                    textView.setIsUnderlined(true);
                    textView.setEllipsizedTextColor(getColor(R.color.purple_200));
            }
        });


        adapter = new FromIndiaTestRecyclerViewAdapter(createList(4), getBaseContext());
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

    private List<String> createList(int numberItems) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= numberItems; i++) {
            String string = "Position "  + i + " " + getString(R.string.long_text);
            list.add(string);
        }

        return list;
    }
}