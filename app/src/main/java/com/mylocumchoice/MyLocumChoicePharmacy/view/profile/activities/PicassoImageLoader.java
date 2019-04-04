package com.mylocumchoice.MyLocumChoicePharmacy.view.profile.activities;

import android.content.Context;
import android.widget.ImageView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.io.File;

public class PicassoImageLoader implements MediaLoader {
    private String url;
    private Integer thumbnailWidth;
    private Integer thumbnailHeight;
    private File file;

    public PicassoImageLoader(File file) {
        this.file = file;
    }

    public PicassoImageLoader(String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final SuccessCallback callback) {
        Picasso.with(context)
                .load(file)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView, new ImageCallback(callback));
    }

    @Override
    public void loadThumbnail(Context context, final ImageView thumbnailView, final SuccessCallback callback) {
        Picasso.with(context)
                .load(file)
                .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                        thumbnailHeight == null ? 100 : thumbnailHeight)
                .placeholder(R.drawable.placeholder_image)
                .centerInside()
                .into(thumbnailView, new ImageCallback(callback));
    }

    private static class ImageCallback implements Callback {
        private  SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        public ImageCallback() {
            super();
        }


        @Override
        public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}