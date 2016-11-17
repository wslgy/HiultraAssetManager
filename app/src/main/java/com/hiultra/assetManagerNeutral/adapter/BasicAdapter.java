package com.hiultra.assetManagerNeutral.adapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import com.hiultra.assetManagerNeutral.holder.BaseHolder;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public abstract class BasicAdapter<T> extends BaseAdapter {
    
    protected LayoutInflater inflater;
    protected ArrayList<T> list;
    
    /** 是否显示动画 */
    protected boolean show = false;
    /** 动画是否显示的默认配置 */
    private boolean defaultShowAnimation = true;
    
    public BasicAdapter(Context c, ArrayList<T> list) {
        inflater = LayoutInflater.from(c);
        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }
    
    @Override
    public T getItem(int position) {
        return list.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> holder = null;
        T t = getItem(position);
        if (convertView == null) {
            holder = getHolder(inflater, t);
        } else {
            holder = (BaseHolder<T>) convertView.getTag();
        }
        holder.bindData(t);
        View holderView = holder.getHolderView();
        
        // 添加动画效果
        if (show) {
            ViewHelper.setScaleX(holderView, 0.5f);
            ViewHelper.setScaleY(holderView, 0.5f);
            ViewPropertyAnimator.animate(holderView).scaleX(1.0f).setDuration(350).setInterpolator(new OvershootInterpolator())
                    .start();
            ViewPropertyAnimator.animate(holderView).scaleY(1.0f).setDuration(350).setInterpolator(new OvershootInterpolator())
                    .start();
        }
        
        return holderView;
    }
    
    /** 子类根据自身需求设置不同的Holder对象 
     * @param t */
    protected abstract BaseHolder<T> getHolder(LayoutInflater inflater, T t);
    
    @Override
    public void notifyDataSetChanged() {
        show = defaultShowAnimation;
        super.notifyDataSetChanged();
    }
    
    private Timer timer = new Timer();
    
    public void notifyDataSetChanged(boolean b) {
        show = b;
        super.notifyDataSetChanged();
        if (!b) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    show = defaultShowAnimation;
                }
            }, 1000);
        }
    }
    
}
