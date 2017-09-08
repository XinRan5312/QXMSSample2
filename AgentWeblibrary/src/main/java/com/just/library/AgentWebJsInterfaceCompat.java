package com.just.library;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import java.lang.ref.WeakReference;

/**
 * Created by cenxiaozhong on 2017/5/24.
 */

public class AgentWebJsInterfaceCompat implements AgentWebCompat, FileUploadPop<IFileUploadChooser> {

    private WeakReference<AgentWeb> mReference = null;
    private IFileUploadChooser mIFileUploadChooser;
    private WeakReference<Activity> mActivityWeakReference = null;

    AgentWebJsInterfaceCompat(AgentWeb agentWeb, Activity activity) {
        mReference = new WeakReference<AgentWeb>(agentWeb);
        mActivityWeakReference = new WeakReference<Activity>(activity);
    }


    @JavascriptInterface
    public void uploadFile() {


        if (mActivityWeakReference.get() != null&&mReference.get()!=null) {
            mIFileUploadChooser = new FileUpLoadChooserImpl(mActivityWeakReference.get(), new FileUpLoadChooserImpl.JsChannelCallback() {
                @Override
                public void call(String value) {

                    if (mReference.get() != null)
                        mReference.get().getJsEntraceAccess().quickCallJs("uploadFileResult", value);
                }
            }, mReference.get().getDefaultMsgConfig().getChromeClientMsgCfg().getFileUploadMsgConfig());
            mIFileUploadChooser.openFileChooser();
        }


    }

    @Override
    public IFileUploadChooser pop() {
        IFileUploadChooser mIFileUploadChooser = this.mIFileUploadChooser;
        this.mIFileUploadChooser = null;
        return mIFileUploadChooser;
    }
}
