package com.hiultra.assetManagerNeutral.MVP_P;

import com.hiultra.assetManagerNeutral.MVP_M.DownloadModel;
import com.hiultra.assetManagerNeutral.MVP_V.IAssetDownloadView;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo;
import com.hiultra.assetManagerNeutral.domain.DownloadInfo.ItemInfo;

public class DownloadPresenter extends BasePresenter<IAssetDownloadView, DownloadModel> {
    
    private DownloadModel.DownloadObserver observer;
    
    public DownloadPresenter(IAssetDownloadView view) {
        super(view);
    }
    
    @Override
    protected DownloadModel initModel() {
        return DownloadModel.getInstance();
    }
    
    public void download(ItemInfo widgetInfo) {
        register();
        mModel.download(widgetInfo);
    }
    
    public void register() {
        if(observer == null) {
            observer = new DownloadModel.DownloadObserver() {
                
                @Override
                public void onStateChanged(DownloadInfo downloadInfo) {
                    if (isViewAttached()) mView.onStateChanged(downloadInfo);
                }
                
                @Override
                public void onProgressChanged(DownloadInfo downloadInfo) {
                    if (isViewAttached()) {
                        mView.onMaxObtain(downloadInfo);
                        mView.updateProgress(downloadInfo);
                    }
                }
                
                @Override
                public void onMaxObtain(DownloadInfo downloadInfo) {
                    if (isViewAttached()) mView.onMaxObtain(downloadInfo);
                }
            };
        }
        mModel.registerObserver(observer);
    }
    
}
