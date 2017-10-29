package com.example.maxiaowu.societyapp.router;

import com.example.router.IntentParam;
import com.example.router.UriParam;

/**
 * Created by maxiaowu on 2017/10/29.
 */

public interface RouterService {

    public void startMainActivity(@UriParam("island") String uriParam, @IntentParam("desc") String desc) ;


}
