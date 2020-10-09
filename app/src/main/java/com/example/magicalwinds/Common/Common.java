package com.example.magicalwinds.Common;

import com.example.magicalwinds.Remote.APIService;
import com.example.magicalwinds.Remote.RetrofitClient;

public class Common {

    private static final String BASE_URL="https://fcm.googleapis.com/";

    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
