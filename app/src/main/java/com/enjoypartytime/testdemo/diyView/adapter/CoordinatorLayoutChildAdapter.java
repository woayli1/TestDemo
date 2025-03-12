package com.enjoypartytime.testdemo.diyView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoypartytime.testdemo.R;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/12/11
 */
public class CoordinatorLayoutChildAdapter extends RecyclerView.Adapter<CoordinatorLayoutChildAdapter.CoordinatorViewHolder> {


    private final Context context;
    private final List<String> stringList;

    public CoordinatorLayoutChildAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public CoordinatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_coordinator_layout_child, parent, false);
        return new CoordinatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinatorViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.item_text);
        textView.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class CoordinatorViewHolder extends RecyclerView.ViewHolder {

        public CoordinatorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
