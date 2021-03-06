/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.httpmodule.http;

import android.widget.Toast;

import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by Yan Zhenjie on 2016/12/17.
 */
public class DefaultResponseListener<T> implements OnResponseListener<Result<T>> {

    private HttpListener<T> httpListener;
    private AbstractRequest<T> abstractRequest;

    public DefaultResponseListener(HttpListener<T> httpListener, AbstractRequest<T> abstractRequest) {
        this.httpListener = httpListener;
        this.abstractRequest = abstractRequest;
    }

    @Override
    public void onStart(int what) {
        // TODO 显示dialog。
    }

    @Override
    public void onSucceed(int what, Response<Result<T>> response) {
        // http层的请求成功，响应码可能是200、400、500。
        if (httpListener != null && !abstractRequest.isCanceled())
            httpListener.onSucceed(what, response.get());
    }

    @Override
    public void onFailed(int what, Response<Result<T>> response) {
        Exception exception = response.getException();
        if (exception instanceof TimeoutError) { // 超时。
            // Toast
        }
        if (httpListener != null && !abstractRequest.isCanceled())
            httpListener.onFailed(what);
    }

    @Override
    public void onFinish(int what) {
        // TODO 关闭dialog。
        if (httpListener != null)
            httpListener.onFinish(what);
    }
}
