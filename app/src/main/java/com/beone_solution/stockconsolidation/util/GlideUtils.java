package com.beone_solution.stockconsolidation.util;

import android.graphics.PorterDuff;
import android.support.v4.widget.CircularProgressDrawable;
import android.widget.ImageView;

import com.beone_solution.stockconsolidation.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils
{
    public static void loadImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(imageView.getContext());
        progressDrawable.setStrokeWidth(imageView.getContext().getResources().getDimensionPixelSize(R.dimen.stroke_width_size));
        progressDrawable.setCenterRadius(imageView.getContext().getResources().getDimensionPixelSize(R.dimen.center_radius_size));
        progressDrawable.setColorFilter(imageView.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        progressDrawable.start();
        requestOptions = requestOptions.placeholder(progressDrawable);
        Glide.with(imageView).load(imageUrl).apply(requestOptions).into(imageView);
    }
}
