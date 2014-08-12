package test.testavospush.client.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

import test.testavospush.client.AVOSClient;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class AVOSClientImpl implements AVOSClient {

    private static final String URL = "https://cn.avoscloud.com/1/push";

    private final String key;
    private final String id;

    private final RestTemplate restTemplate = new RestTemplate() {
        @Override
        protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
            final ClientHttpRequest request = super.createRequest(url, method);
            request.getHeaders().add("X-AVOSCloud-Application-Id", id);
            request.getHeaders().add("X-AVOSCloud-Application-Key", key);
            request.getHeaders().add("Content-Type", "application/json");

            return request;
        }
    };

    @Inject
    public AVOSClientImpl(@Named("id") String id, @Named("key") String key) {
        this.id = id;
        this.key = key;
    }

    /**
     * curl -X POST \ -H
     * "X-AVOSCloud-Application-Id: jywv3geheua9tpctjf1we0l5i32ya2lapso3wlth5trkw0yd"
     * \ -H
     * "X-AVOSCloud-Application-Key: bpirier99hqdkws3sqent2tj6g02idlcg8cw4sx0du58jk1d"
     * \ -H "Content-Type: application/json" \ -d '{ "where":{
     * "installationId":"57234d4c-752f-4e78-81ad-a6d14048020d" } "data": {
     * "alert": "Hello From AVOS Cloud." } }' \ https://cn.avoscloud.com/1/push
     */
    @Override
    public String pushToInstallationId(String installationId, String msg) {
        String s = String.format("{ \"where\":{ \"installationId\":\"%s\" }, \"data\": { \"alert\": \"%s" + new Date()
                + "\" } }", installationId, msg);
        String ret = restTemplate.postForObject(URL, s, String.class);
        return ret;
    }
}
