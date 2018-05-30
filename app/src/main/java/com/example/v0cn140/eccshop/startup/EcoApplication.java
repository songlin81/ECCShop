package com.example.v0cn140.eccshop.startup;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class EcoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
        Fresco.initialize(this);
    }

}
