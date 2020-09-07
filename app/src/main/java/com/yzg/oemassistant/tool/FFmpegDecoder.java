package com.yzg.oemassistant.tool;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FFmpegDecoder implements SurfaceHolder.Callback {

    static {
        System.loadLibrary("ffmpegdecoder");
    }
    private String mDataSource;
    private SurfaceHolder mSurfaceHolder;
    private OnPrepareListener mOnPrepareListener;
    private OnErrorListener mOnErrorListener;

    public void setDataSource(String dataSource){
        mDataSource = dataSource;
    }


    public void setSurfaceView(SurfaceView surfaceView){
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }


    public void onError(int errorCode){
        if (mOnErrorListener != null){
            mOnErrorListener.onError(errorCode);
        }
    }

    public void onPrepare(){
        if (null != mOnPrepareListener){
            mOnPrepareListener.onPrepare();
        }
    }

    public void setOnPrepareListener(OnPrepareListener onPrepareListener){
        this.mOnPrepareListener = onPrepareListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener){
        mOnErrorListener = onErrorListener;
    }


    public interface OnPrepareListener{
        void onPrepare();
    }

    public interface OnErrorListener{
        void onError(int errorCode);
    }

    public void start(){
        native_start();
    }

    public void stop(){
        native_stop();
    }

    public void release(){
        if (mSurfaceHolder != null){
            mSurfaceHolder.removeCallback(this);
        }
        native_release();
    }

    public void prepare(){
        native_prepare(mDataSource);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        native_render(holder.getSurface());

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        holder.getSurface().release();
    }

    native void native_prepare(String dataSource);

    native void native_start();

    native void native_stop();

    native void native_render(Surface surface);

    native void native_release();

}
