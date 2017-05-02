package com.example.maxiaowu.societyapp.utils;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

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
}