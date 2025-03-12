package com.enjoypartytime.testdemo.opengl.image.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.enjoypartytime.testdemo.R;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 * author gc
 * company enjoyPartyTime
 * date 2025/3/12
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context context;
    private final List<String> stringList;
    private final String type;
    private final boolean isLocal, isOrigin;

    public ImageAdapter(Context context, List<String> stringList, String type, boolean isLocal, boolean isOrigin) {
        this.context = context;
        this.stringList = stringList;
        this.type = type;
        this.isLocal = isLocal;
        this.isOrigin = isOrigin;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        ImageView ivImage = holder.itemView.findViewById(R.id.item_iv_image);
        TextView tvImageName = holder.itemView.findViewById(R.id.item_tv_image_name);

        int src;
        String srcStr;
        if (type.equals("jpg")) {
            //jpg
            src = R.raw.jpg4;
            srcStr = "https://www.gstatic.cn/webp/gallery/4.jpg";
        } else if (type.equals("png")) {
            //png
            src = R.raw.png4;
            srcStr = "https://www.gstatic.cn/webp/gallery/4.png";
        } else {
            //webp
            src = R.raw.webp4;
//            srcStr = "https://www.gstatic.cn/webp/gallery/4.webp";
            srcStr = "https://isparta.github.io/compare-webp/image/gif_webp/webp/1.webp";
        }

        if (isOrigin) {
            //原图
            tvImageName.setText("原图");
            Glide.with(context).load(isLocal ? src : srcStr).into(ivImage);
        } else {
            int remainder = position % 20;
            if (remainder == 0) {
                //原图
                tvImageName.setText("原图");
                Glide.with(context).load(isLocal ? src : srcStr).into(ivImage);
            } else if (remainder == 1) {
                //矩形剪裁
                tvImageName.setText("矩形剪裁");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new CropTransformation(500, 300)))
                        .into(ivImage);
            } else if (remainder == 2) {
                //圆形
                tvImageName.setText("圆形");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.circleCropTransform())
                        .into(ivImage);
            } else if (remainder == 3) {
                //圆形带边框
                tvImageName.setText("圆形带边框");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new CropCircleWithBorderTransformation(2, Color.RED)))
                        .into(ivImage);
            } else if (remainder == 4) {
                //正方形
                tvImageName.setText("正方形");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new CropSquareTransformation()))
                        .into(ivImage);
            } else if (remainder == 5) {
                //圆角剪裁
                tvImageName.setText("圆角剪裁");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(20, 4)))
                        .into(ivImage);
            } else if (remainder == 6) {
                //颜色滤镜
                tvImageName.setText("颜色滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new ColorFilterTransformation(0x3000FF00)))
                        .into(ivImage);
            } else if (remainder == 7) {
                //灰色滤镜
                tvImageName.setText("灰色滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new GrayscaleTransformation()))
                        .into(ivImage);
            } else if (remainder == 8) {
                //高斯模糊
                tvImageName.setText("高斯模糊");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(8, 1)))
                        .into(ivImage);
            } else if (remainder == 9) {
                //遮罩
                tvImageName.setText("遮罩");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new MaskTransformation(R.raw.webp2)))
                        .into(ivImage);
            } else if (remainder == 10) {
                //卡通滤镜
                tvImageName.setText("卡通滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new ToonFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 11) {
                //乌墨色滤镜
                tvImageName.setText("乌墨色滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new SepiaFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 12) {
                //对比滤镜
                tvImageName.setText("对比滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new ContrastFilterTransformation(3.0f)))
                        .into(ivImage);
            } else if (remainder == 13) {
                //反转滤镜
                tvImageName.setText("反转滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new InvertFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 14) {
                //像素化滤镜
                tvImageName.setText("像素化滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new PixelationFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 15) {
                //素描滤镜
                tvImageName.setText("素描滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new SketchFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 16) {
                //旋转滤镜
                tvImageName.setText("旋转滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new SwirlFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 17) {
                //亮度滤镜
                tvImageName.setText("亮度滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new BrightnessFilterTransformation(0.1f)))
                        .into(ivImage);
            } else if (remainder == 18) {
                //Kuwahara滤镜
                tvImageName.setText("Kuwahara滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new KuwaharaFilterTransformation()))
                        .into(ivImage);
            } else if (remainder == 19) {
                //装饰图滤镜
                tvImageName.setText("装饰图滤镜");
                Glide.with(context).load(isLocal ? src : srcStr)
                        .apply(RequestOptions.bitmapTransform(new VignetteFilterTransformation()))
                        .into(ivImage);
            } else {
                //原图
                tvImageName.setText("原图");
                Glide.with(context).load(src).into(ivImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
