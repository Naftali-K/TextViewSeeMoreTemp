package com.nk.textviewseemoretemp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nk.textviewseemoretemp.R;
import com.nk.textviewseemoretemp.ShowMoreHelper;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/06/04
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.TestRecyclerViewAdapterViewHolder> {

    private List<String> itemList = new ArrayList<>();
    private Context context;

    public TestRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public TestRecyclerViewAdapter(List<String> itemList, Context context) {
        this(context);
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TestRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_view, parent, false);
        return new TestRecyclerViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestRecyclerViewAdapterViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class TestRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ConstraintLayout container;

        public TestRecyclerViewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            container = itemView.findViewById(R.id.container);
        }

        void bind(String item, int position) {
//            textView.setText(item);
            setShowMoreView(textView, item);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("TAG", "onClick: Clicked TextView position " + position);
//                    Toast.makeText(context, "Clicked TextView position " + position, Toast.LENGTH_SHORT).show();
//                }
//            });

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "onClick: Container Clicked TextView position " + position);
                    Toast.makeText(context, "Container Clicked TextView position " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setShowMoreView(TextView textView, String text) {
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("TAG", "onClick: Text was clicked");
//            }
//        });

        ShowMoreHelper.Builder builder = new ShowMoreHelper.Builder(textView.getContext());
        builder.showMoreLessUnderlined(true);
//            builder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//            builder.setOnClickListener(new Function0<Unit>() {
//                @Override
//                public Unit invoke() {
//                    return null;
//                }
//            });
//            builder.setOnClickListener((Function0<Unit>) () -> {
//
//
//                return null;
//            });
//            builder.setOnClickListenerNew(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

//        ShowMoreHelper.Builder builder = new ShowMoreHelper.Builder(textView.getContext())
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                })
//                .build();

        ShowMoreHelper helper = builder.build();
//        Log.d("TAG", "onCreate: TextView: " + textView.toString());
        helper.addShowMoreLess(textView, text, false);
    }

    public void setItemList(List<String> itemList) {
        if (itemList != null) {
            this.itemList = itemList;
            notifyDataSetChanged();
        }
    }

    public void addItemToList(String item) {
        if (item != null) {
            this.itemList.add(item);
            notifyDataSetChanged();
        }
    }
}
