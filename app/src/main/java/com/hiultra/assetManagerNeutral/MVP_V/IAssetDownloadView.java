package com.hiultra.assetManagerNeutral.MVP_V;

import com.hiultra.assetManagerNeutral.domain.DownloadInfo;

public interface IAssetDownloadView extends IView {
    
    void updateProgress(DownloadInfo downloadInfo);
    void onStateChanged(DownloadInfo downloadInfo);
    void onMaxObtain(DownloadInfo downloadInfo);
    
}
