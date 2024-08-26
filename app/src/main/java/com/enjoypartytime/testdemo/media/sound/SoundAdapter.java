package com.enjoypartytime.testdemo.media.sound;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/26
 */
public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private final List<SoundBean> soundList;
    private final Context context;

    private OnItemClickListener clickListener;

    public SoundAdapter(List<SoundBean> soundList, Context context) {
        this.soundList = soundList;
        this.context = context;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 18;
        layoutParams.leftMargin = 18;
        textView.setLayoutParams(layoutParams);
        return new SoundViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(soundList.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            if (ObjectUtils.isNotEmpty(clickListener)) {
                clickListener.onItemClick(holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return soundList.size();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder {

        public SoundViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
