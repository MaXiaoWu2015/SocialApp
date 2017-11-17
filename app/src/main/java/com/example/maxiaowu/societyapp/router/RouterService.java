package com.example.maxiaowu.societyapp.router;

import com.example.annotation.IntentParam;
import com.example.annotation.RouteUri;
import com.example.annotation.UriParam;

/**
 * Created by maxiaowu on 2017/10/29.
 */

public interface RouterService {
  //learn:通过Uri启动Activity,host不能定义为router/main_activity这种带‘/’的,会出现找不到Activity的情况
    @RouteUri("xw://router.main_activity")
    public void startMainActivity(@UriParam("island") String uriParam, @IntentParam("desc") String desc) ;


}
