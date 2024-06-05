package com.nk.textviewseemoretemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.colormoon.readmoretextview.ReadMoreTextView;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * For work well "See More", must be default long text in xml, other not gonna hide text and show "See more"
 */

public class MainActivity extends AppCompatActivity {

    private TextView textView, text2View;
    private ReadMoreTextView readMoreTextView;
    private Button updateTextBtn, openNewActivityBtn, openNewActivityJavaBtn, fromIndiaBtn;
    private com.borjabravo.readmoretextview.ReadMoreTextView readMoreTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();

        readMoreTextView.setCollapsedText(getString(R.string.see_more));
        readMoreTextView.setExpandedText(getString(R.string.see_less));
        readMoreTextView.setCollapsedTextColor(R.color.magenta);
        readMoreTextView.setExpandedTextColor(R.color.red);
        readMoreTextView.setTrimLines(2);

        updateTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readMoreTextView.setText("Updated " + getString(R.string.long_text));
                readMoreTextView.setTrimLength(20); //this code need for close long text, other without this text will be opened and without "See more"


                readMoreTextView2.setText("Updated " + getString(R.string.long_text));
                readMoreTextView2.setTrimLength(20); //this code need for close long text, other without this text will be opened and without "See more"
            }
        });

        openNewActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NewActivity.class));
            }
        });

        openNewActivityJavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NewActivityJava.class));
            }
        });

        fromIndiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), FromIndiaActivity.class));
            }
        });
    }

    private void setReferences() {
        textView = findViewById(R.id.text_view);
        text2View = findViewById(R.id.text2_view);
        readMoreTextView = findViewById(R.id.readMoreTextView);
        updateTextBtn = findViewById(R.id.updateTextBtn);
        readMoreTextView2 = findViewById(R.id.read_more_text_view);
        openNewActivityBtn = findViewById(R.id.open_new_activity_btn);
        openNewActivityJavaBtn = findViewById(R.id.open_new_activity_java_btn);
        fromIndiaBtn = findViewById(R.id.from_india_btn);
    }
}