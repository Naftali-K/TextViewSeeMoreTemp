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

import io.github.glailton.expandabletextview.ExpandableTextView;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/06/04
 */
public class FromIndiaTestRecyclerViewAdapter extends RecyclerView.Adapter<FromIndiaTestRecyclerViewAdapter.TestRecyclerViewAdapterViewHolder> {

    private List<String> itemList = new ArrayList<>();
    private Context context;

    public FromIndiaTestRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public FromIndiaTestRecyclerViewAdapter(List<String> itemList, Context context) {
        this(context);
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TestRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_from_india, parent, false);
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


        private ConstraintLayout container;
        private ExpandableTextView textView;

        public TestRecyclerViewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            textView = itemView.findViewById(R.id.text_view);
        }

        void bind(String item, int position) {

            textView.setText(item);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "onClick: Container Clicked TextView position " + position);
                    Toast.makeText(context, "Container Clicked TextView position " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
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
