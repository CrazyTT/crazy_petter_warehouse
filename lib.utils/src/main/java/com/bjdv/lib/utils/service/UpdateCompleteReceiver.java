package com.bjdv.lib.utils.service;

/**
 * Created by liuliuchen on 16/3/24.
 */

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.bjdv.lib.utils.util.SharedPreferencesUtil;

public class UpdateCompleteReceiver extends BroadcastReceiver {
    SharedPreferencesUtil sp;

    @Override
    public void onReceive(Context context, Intent intent) {
        sp = new SharedPreferencesUtil(context);
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            long refernece = sp.getLong("update");
            if (refernece == id) {
                DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Intent install = new Intent(Intent.ACTION_VIEW);
                Uri downloadFileUri = dManager.getUriForDownloadedFile(id);
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
        }
        context.unregisterReceiver(this);
    }
}
