import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class GetDetail {

    public static void main(final String[] args) throws IOException {
        /*String url = "http://www.haodou.com/recipe/869836/";
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        File file = new File("G:\\get.txt");
        PrintWriter printWriter = null;
        printWriter = new PrintWriter(file);
        printWriter.write(doc.toString());
        printWriter.flush();
        printWriter.close();*/
        String url = "http://www.haodou.com/recipe/869836/";
        getAll(url);
    }

    public static void getAll(String url) {
        try {
            url = "http://www.haodou.com/recipe/869836/";
            url = "http://www.haodou.com/recipe/326177/";
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
//            ��Ʒͼ
            Elements elements = doc.select(".recipe_cover");
            for (Element element : elements)
                System.out.println(element.attr("abs:src"));
//            ����ͼ
            elements = doc.select(".imit_m > img");
            for (Element element : elements)
                System.out.println(element.attr("abs:src"));
//            С��ʿ
            elements = doc.select(".data");
            for (Element element : elements)
                System.out.println(element.text());
            System.out.println("aaaa");
//            ���˲���
            Element prompt = doc.select(".prompt span").first();
            System.out.println(prompt.text());
//            ��ȡ����
            Element title = doc.select("h1").first();
//            result.append(link.text());
            System.out.println(title.text());
//            �˵ļ��
            title = doc.select("[data]").first();
//            result.append(link.text());
            System.out.println(title.text());
//            ʳ��
            Elements links = doc.select(".ingtbur");
            for (Element element : links)
//                result.append(element.text());
                System.out.println(element.text());

//            ����
            links = doc.select(".sstep");
            for (Element element : links)
//                result.append(element.text());
                System.out.println(element.text());
//            ��ǩ
            links = doc.select("p > a[href]");

            int i = 0;
            for (Element element : links) {
                i++;
                if (element.attr("abs:href").contains("http://www.haodou.com/recipe/all")) {
                    System.out.println(element.toString());
                }
            }
            elements = doc.select(".quantity");
            for (Element element : elements)
                System.out.println(element.text());
            elements = doc.select(".pop_tags a");
            for (Element element : elements)
                System.out.println(element.text());
            Element element = doc.select(".des span").last();
            System.out.println(element.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String detail(String url) {
        StringBuilder result = new StringBuilder();
        try {
//            ��ȡ������ҳ doc�ڵ����ɸѡ element
            Document doc = Jsoup.connect(url).get();
//            ��ȡ����
            Element link = doc.select("h1").first();
            result.append(link.text());
            link = doc.select("[data]").first();
            result.append(link.text());
//            ʳ��
            Elements links = doc.select(".ingtbur");
            for (Element element : links)
                result.append(element.text());

//            ����
            links = doc.select(".sstep");
            for (Element element : links)
                result.append(element.text());
//            ��ǩ
            links = doc.select("p > a[href]");
            int i = 0;
            for (Element element : links) {
                i++;
                if (i <= 1)
                    continue;
                if (element.attr("abs:href").contains("http://www.haodou.com/recipe/all")) {
                    result.append(element.text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
