package com.enjoypartytime.testdemo.opengl.camera.camera2.adapter;

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
 * date 2025/1/6
 */
public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.CameraViewHolder> {

    private final Context context;
    private final List<String> stringList;

    private final OnClickListener onClickListener;

    public CameraAdapter(Context context, List<String> stringList, OnClickListener onClickListener) {
        this.context = context;
        this.stringList = stringList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CameraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_camera_layout, parent, false);
        return new CameraAdapter.CameraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CameraViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.item_text);
        textView.setText(String.format("镜头%s", stringList.get(position)));
        textView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(stringList.get(holder.getAbsoluteAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class CameraViewHolder extends RecyclerView.ViewHolder {

        public CameraViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnClickListener {

        void onClick(String cameraId);
    }
}
