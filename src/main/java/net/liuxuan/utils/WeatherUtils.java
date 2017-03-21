package net.liuxuan.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.WeatherUtils
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/24 16:14
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/24  |    Moses       |     Created
 */
public class WeatherUtils {

    public static void getWeather() throws URISyntaxException, IOException, XPathExpressionException {
        String stubsApiBaseUri = "http://localhost:7819/RTCP/rest/stubs/";
        String domain = "default";
        String environment = "addNumbers";
        String stubName = "1+1=2";
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(stubsApiBaseUri);
        builder.addParameter("domain", domain);
        builder.addParameter("env", environment);
        builder.addParameter("stub", stubName);
        String listStubsUri = builder.build().toString();
        HttpGet getStubMethod = new HttpGet(listStubsUri);
        HttpResponse getStubResponse = client.execute(getStubMethod);
        int getStubStatusCode = getStubResponse.getStatusLine()
                .getStatusCode();
        if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
            // Handle non-2xx status code
            return;
        }
        String responseBody = EntityUtils
                .toString(getStubResponse.getEntity());
        // Assuming only one stub matches
        String stubRelativeUri = XPathFactory
                .newInstance()
                .newXPath()
                .evaluate("/stubs/stub/@href",
                        new InputSource(new StringReader(responseBody)));
        String stubAbsoluteUri = new URI(stubsApiBaseUri).resolve(
                stubRelativeUri).toString();

        HttpPost startStubMethod = new HttpPost(stubAbsoluteUri);
        ContentType contentType = ContentType.APPLICATION_XML
                .withCharset("utf-8");
        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<start-stub />";
        startStubMethod.setEntity(new ByteArrayEntity(data
                .getBytes(contentType.getCharset()), contentType));
        HttpResponse startStubResponse = client.execute(startStubMethod);
        int startStubStatusCode = startStubResponse.getStatusLine()
                .getStatusCode();
        if (startStubStatusCode < 200 || startStubStatusCode >= 300) {
            // Handle non-2xx status code
            return;
        }
        // If you want to check the status of the stub that is starting, you
        // can use the response data to get the stub instance URI and poll it
        // for updates
        System.out.println(startStubStatusCode);
        String startStubResponseBody = EntityUtils.toString(startStubResponse
                .getEntity());
        System.out.println(startStubResponseBody);

    }
}
