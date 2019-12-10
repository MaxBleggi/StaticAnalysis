package com.xumo.xumo.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.xumo.xumo.util.LogUtil;

public class XumoFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Firebase Token: ");
        stringBuilder.append(token);
        LogUtil.d(stringBuilder.toString());
    }
}
