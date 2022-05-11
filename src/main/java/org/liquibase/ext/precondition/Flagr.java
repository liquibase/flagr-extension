package org.liquibase.ext.precondition;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Objects;

public class Flagr {
    private final String url;
    private FlagrFlag[] flagrFlags;

    public Flagr(String url) {
        this.url = url;
    }

    private void fetchFlags() throws IOException {
        HttpGet request = new HttpGet(this.url + "/api/v1/flags/");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Unable to connect to Flagr API.");
            }

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                Gson gson = new Gson();
                this.flagrFlags = gson.fromJson(result, FlagrFlag[].class);
            }
        }
    }

    public Boolean Enabled(String key) throws IOException {
        if (this.flagrFlags == null) {
            this.fetchFlags();
        }
        for (FlagrFlag flagrFlag : this.flagrFlags) {
            if (Objects.equals(flagrFlag.key, key)) {
                return Boolean.TRUE.equals(flagrFlag.enabled);
            }
        }
        return false;
    }
}

class FlagrFlag {
    public Boolean dataRecordsEnabled;
    public String description;
    public Boolean enabled;
    public Integer id;
    public String key;
}
