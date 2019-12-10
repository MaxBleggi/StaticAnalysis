package com.amazon.device.ads.aftv;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

class KSOServiceBinder {
    private static final String KSO_INTENT_ENDPOINT = "com.amazon.kso.blackbird.GET_BID";
    private static final String KSO_PACKAGE_NAME = "com.amazon.kso.blackbird";
    private static final String LOGTAG = "KSOServiceBinder";
    private AmazonFireTVAdRequest adRequest;
    private Bundle bundleForKso;
    private Messenger messengerService;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            KSOServiceBinder.this.messengerService = new Messenger(iBinder);
            Log.i(KSOServiceBinder.LOGTAG, "APS: Connected to kso");
            KSOServiceBinder.this.sendMessage();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            KSOServiceBinder.this.messengerService = null;
            Log.i(KSOServiceBinder.LOGTAG, "APS: Disconnected from kso");
        }
    };

    static class KsoResponseHandler extends Handler {
        final AmazonFireTVAdCallback adCallback;

        KsoResponseHandler(Looper looper, AmazonFireTVAdCallback amazonFireTVAdCallback) {
            super(looper);
            this.adCallback = amazonFireTVAdCallback;
        }

        public void handleMessage(Message message) {
            try {
                super.handleMessage(message);
                message = KSOMessageProcessor.handleKSOResponseMessage(message);
                if (this.adCallback == null) {
                    return;
                }
                if (message.hasResults()) {
                    this.adCallback.onSuccess(message);
                } else {
                    this.adCallback.onFailure(message);
                }
            } catch (Message message2) {
                String access$100 = KSOServiceBinder.LOGTAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("APS: Unknown exception occurred ");
                stringBuilder.append(message2.toString());
                Log.e(access$100, stringBuilder.toString());
            }
        }
    }

    KSOServiceBinder() {
    }

    void getAds(AmazonFireTVAdRequest amazonFireTVAdRequest) {
        String str = LOGTAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(amazonFireTVAdRequest.getSlots().size());
        stringBuilder.append(" layout(s) in the request ");
        Log.d(str, stringBuilder.toString());
        this.bundleForKso = BundleFactory.newBundleInstance();
        KSOMessageProcessor.prepareRequestMessageObjectForKSO(this.bundleForKso, amazonFireTVAdRequest);
        this.adRequest = amazonFireTVAdRequest;
        Intent intent = new Intent(KSO_INTENT_ENDPOINT);
        intent.setPackage(KSO_PACKAGE_NAME);
        amazonFireTVAdRequest = amazonFireTVAdRequest.getContext().bindService(intent, this.serviceConnection, 1);
        str = LOGTAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("APS: sending bundle ");
        stringBuilder.append(this.bundleForKso);
        Log.d(str, stringBuilder.toString());
        str = LOGTAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("APS : Bind status is ");
        stringBuilder.append(amazonFireTVAdRequest);
        Log.i(str, stringBuilder.toString());
    }

    private void sendMessage() {
        Message obtain = Message.obtain();
        obtain.what = ((int) (Math.random() * 1000.0d)) + 1;
        obtain.setData(this.bundleForKso);
        obtain.replyTo = new Messenger(new KsoResponseHandler(Looper.getMainLooper(), this.adRequest.getCallback()));
        try {
            if (this.messengerService != null) {
                this.messengerService.send(obtain);
            } else {
                Log.d(LOGTAG, "APS: messenger object was null");
            }
        } catch (Exception e) {
            String str = LOGTAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("APS: Exception  occurred while sending message to kso ");
            stringBuilder.append(e.toString());
            Log.e(str, stringBuilder.toString());
        }
    }
}
