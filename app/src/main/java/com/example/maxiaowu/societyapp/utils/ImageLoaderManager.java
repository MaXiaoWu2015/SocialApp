package com.example.maxiaowu.societyapp.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.maxiaowu.societyapp.utils.stackblur.StackBlurManager;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;

/**
 * Created by matingting on 2017/4/28.
 */

public class ImageLoaderManager {
    public static void loadImage(String url,SimpleDraweeView draweeView,boolean isGif){
        loadImage(url,draweeView,null,null,isGif);
    }

    public static void loadImage(String url,SimpleDraweeView draweeView,ControllerListener listener,boolean isGif){
        loadImage(url,draweeView,listener,null,isGif);
    }

    public static void loadImage(String url,SimpleDraweeView draweeView,Postprocessor postprocessor,boolean isGif){
        loadImage(url,draweeView,null,postprocessor,isGif);
    }
    public static void loadImage(String url,SimpleDraweeView draweeView,ControllerListener listener,Postprocessor postprocessor,boolean isGIF){
        ImageRequestBuilder requestBuilder= ImageRequestBuilder.newBuilderWithSource(Uri.parse(url));
        if (postprocessor!=null){
            requestBuilder.setPostprocessor(postprocessor);
        }
        ImageRequest request=requestBuilder.build();
        AbstractDraweeControllerBuilder draweeControllerBuilder= Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(request)
                                    .setAutoPlayAnimations(isGIF);
        if (listener!=null){
            draweeControllerBuilder.setControllerListener(listener);
        }
        DraweeController controller=draweeControllerBuilder.build();
        draweeView.setController(controller);
    }

    /***
     *   获取图片大小
     */

    public int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            return bitmap.getAllocationByteCount();
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR1){
            return bitmap.getByteCount();
        }else{
            return bitmap.getRowBytes()*bitmap.getHeight();
        }
    }

    public static void loadBlurImg(final ImageView view, final String url){
        if (view == null || TextUtils.isEmpty(url))return;

        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<ImageView>(view);

        getFrescoBitmap(url, new ImageListener() {
            @Override
            public void onResponse(Bitmap bitmap, String msg) {
                if (bitmap != null && url.equals(msg)){
                    StackBlurManager stackBlurManager = new StackBlurManager(bitmap);
                    final Bitmap blurBitmap = stackBlurManager.process(80);

                    (new Handler(Looper.getMainLooper())).post(new Runnable() {
                        @Override
                        public void run() {
                            setImageView(imageViewWeakReference,blurBitmap);
                        }
                    });

                }
            }

            @Override
            public void onError(int code) {

            }
        },new IterativeBoxBlurPostProcessor(14));



    }

    private static void setImageView(WeakReference<ImageView> imageViewWeakReference, Bitmap blurBitmap) {

        if (imageViewWeakReference != null && imageViewWeakReference.get() !=null){
            imageViewWeakReference.get().setImageBitmap(blurBitmap);
        }

    }


    public static void getFrescoBitmap(final String url, final ImageListener listener,Postprocessor postprocessor){

        final ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .setPostprocessor(postprocessor)
                .build();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, null);

        dataSource.subscribe(new BaseDataSubscriber<CloseableReference<CloseableImage>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//                CloseableReference<CloseableImage> imageReference = dataSource.getResult();
//                if (imageReference != null){
//                    CloseableImage image = imageReference.get();
//                    if (image != null && image instanceof CloseableStaticBitmap){
//                        CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) image;
//                        Bitmap bitmap = closeableStaticBitmap.getUnderlyingBitmap();
//                        if (bitmap != null && !bitmap.isRecycled() && listener != null){
//                            listener.onResponse(bitmap,url);
//
//                        }
//                    }else if (listener != null){
//                        listener.onError(0);
//                    }
//                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                if (listener != null){
                    listener.onError(0);
                }
            }
        }, Executors.newFixedThreadPool(2));

    }

}
