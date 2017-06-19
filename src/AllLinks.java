import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class AllLinks {
    /*public static void main(final String[] args) throws IOException {
        String url = "http://www.haodou.com/recipe/all/2808/";
        getAllLinks(url);
    }*/

    public static void getAllLinks(final String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
//        System.out.println(links.size());
        int i = 0;
        for (org.jsoup.nodes.Element link : links) {
            if (!retrieveLink(link))
                continue;
            i++;
            if (i <= 3 || i % 2 == 1)
                continue;

//            result 就是 id + 文本
            StringBuilder id = new StringBuilder();
            id.append(link.attr("abs:href").replace("http://www.haodou.com/recipe/", ""));
//            System.out.println(link.attr("abs:href") + "  " + link.text());
            String result = id.substring(0, id.length() - 1) +  "," + GetDetail.detail(link.attr("abs:href"));


//            以追加的方式保存到文本
            FileWriter writer = null;
            try {
                File file = new File("G:\\newData.txt");
                writer = new FileWriter(file, true);
                writer.write(result);
                writer.write("\r\n");
            } finally {
//                关闭流
                writer.close();
            }


        }
    }
    public static boolean retrieveLink(org.jsoup.nodes.Element link) {
        String url = "http://www.haodou.com/recipe/";
        if (link.attr("abs:href").contains(url)
                && !link.attr("abs:href").contains("#")
                && !link.attr("abs:href").contains("album")
                && !link.attr("abs:href").contains("knowledge")
                && !link.attr("abs:href").contains("all")
                && !link.attr("abs:href").contains("create")
                && !link.attr("abs:href").contains("food")
                && !link.attr("abs:href").contains("category")
                && !link.attr("abs:href").contains("top")
                && !link.attr("abs:href").contains("expert"))
            return true;
        return false;

    }
}
