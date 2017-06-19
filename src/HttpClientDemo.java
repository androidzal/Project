import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class HttpClientDemo {
    public static String get(final String url) {
        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                if (response != null
                        && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println(response.getStatusLine());

                    HttpEntity entity = response.getEntity();

                    result = readResponse(entity, "utf-8");
                }
            } finally {
                httpClient.close();
                response.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String readResponse(HttpEntity resEntity, String charset) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;

        try {
            if (resEntity == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(
                    resEntity.getContent(), charset
            ));
            String line = null;
            while ((line = reader.readLine()) != null)
                buffer.append(line);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return buffer.toString();

    }
    public static void main(final String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://www.haodou.com/recipe/all/61162/");


        System.out.println("executing request " + httpGet.getURI());

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            System.out.println(response.getStatusLine().toString());

            Header[] headers = response.getAllHeaders();

            System.out.println(response.getHeaders("Content-Type"));

            for (Header header : headers) {
                System.out.println(header.getName() + " " + header.getValue());
            }
        }   finally {
            httpClient.close();
        }
    }
}
