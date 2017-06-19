import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.*;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class HttpGetUtils {
    public static void main(final String[] args) throws Exception{

        String url = "http://www.haodou.com/recipe/category/";
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        System.out.println(links.size());
        int i = 0;
        for (org.jsoup.nodes.Element link : links) {

            if (!link.attr("abs:href").contains("http://www.haodou.com/recipe/all/"))
                continue;
            i++;
            if (i <= 2)
                continue;
            AllLinks.getAllLinks(link.attr("abs:href"));
        }
    }
}
