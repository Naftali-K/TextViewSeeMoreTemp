package com.nk.textviewseemoretemp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.colormoon.readmoretextview.ReadMoreTextView;
//import com.skyhope.showmoretextview.ShowMoreTextView;

public class MainActivity extends AppCompatActivity {

    private ReadMoreTextView readMoreTextView;
//    private ShowMoreTextView showMoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();

        readMoreTextView.setCollapsedText(getString(R.string.see_more));
        readMoreTextView.setExpandedText(getString(R.string.see_less));
        readMoreTextView.setCollapsedTextColor(R.color.magenta);
        readMoreTextView.setExpandedTextColor(R.color.red);
        readMoreTextView.setTrimLines(5);

//        showMoreTextView.setShowingLine(5);
//        showMoreTextView.addShowMoreText(getString(R.string.see_more));
//        showMoreTextView.addShowLessText(getString(R.string.see_less));
    }

    private void setReferences() {
        readMoreTextView = findViewById(R.id.readMoreTextView);
//        showMoreTextView = findViewById(R.id.showMoreTextView);
    }
}