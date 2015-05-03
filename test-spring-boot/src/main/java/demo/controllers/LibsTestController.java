package demo.controllers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.ByteString;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import demo.ext.BaseController;

@Controller
@RequestMapping("/test/libs")
public class LibsTestController extends BaseController {

    @RequestMapping("")
    public String index() {
        return "/test/libs/index";
    }

    @RequestMapping("/okio")
    @ResponseBody
    public String testOkio() {
        ByteString bs = ByteString.of("hello".getBytes());
        return bs.hex();
    }

    @RequestMapping("/okhttp")
    public String testOkhttp(ModelMap model) throws IOException {
        Request request = new Request.Builder().url("http://www.baidu.com")
        // .addHeader("token", "somevalue").post(jsonBody(list))
                .build();

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        httpClient.setReadTimeout(10, TimeUnit.SECONDS);

        Response resp = httpClient.newCall(request).execute();

        model.put("baidu", resp.body().string());

        return "/test/libs/okhttp";
    }
}
