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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import com.mysql.jdbc.exceptions.DeadlockTimeoutRollbackMarker;

import test.DBHelper;

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
        String url = "http://www.haodou.com/recipe/326177/";
       // DBHelper db = new DBHelper("select * from recipe");
//        getAll("869836",url,db);
        getAll("869836",url);
    }

    public static String getAll(String id,String url) {
        try {
        	String r_picture ="";
        	List<String> rs_picture = new ArrayList<>();
        	
        	String r_title ="";
        	String r_desc ="";
        	List<String> rm_matName = new ArrayList<>();
        	List<String> rm_amount = new ArrayList<>();
        	List<String> rs_detail = new ArrayList<>();
        	List<String> rl_label=new ArrayList<>();
        	String r_pTime ="",r_mTime="",r_nMembers="";
        	
        	
        	
           // url = "http://www.haodou.com/recipe/869836/";
            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
//            成品图
            Elements elements = doc.select(".recipe_cover");
            for (Element element : elements)
                //System.out.println(element.attr("abs:src"));
            	r_picture = element.attr("abs:src");
//            步骤图
            elements = doc.select(".imit_m > img");
            for (Element element : elements)
                //System.out.println(element.attr("abs:src"));
            	rs_picture.add(element.attr("abs:src"));
//            小贴士
            elements = doc.select(".data");
            for (Element element : elements)
                System.out.println(element.text());
//            做菜步骤
            Element prompt = doc.select(".prompt span").first();
            //System.out.println(prompt.text());
//            获取菜名
            Element title = doc.select("h1").first();
//            result.append(link.text());
            //System.out.println(title.text());
            r_title = title.text();
//            菜的简介
            title = doc.select("[data]").first();
//            result.append(link.text());
            //System.out.println(title.text());
            if(title != null && !"".equals(title.text()))
            	;
            else
            	title = doc.select(".des span").last();
            r_desc = title.text();

//            食材
            Elements links = doc.select(".ingtmgr span");
            Elements matName = doc.select(".ingtmgr	p");
            for (Element element : matName)
            	//System.out.println(element.text());
            	rm_matName.add(element.text());
            for (Element element : links)
//                result.append(element.text());
                //System.out.println(element.text());
            	rm_amount.add(element.text());
            links = doc.select(".ingtbur span");
            matName = doc.select(".ingtbur	p");
            for (Element element : matName)
            	//System.out.println(element.text());
            	rm_matName.add(element.text());
            for (Element element : links)
//                result.append(element.text());
            	//System.out.println(element.text());
            	rm_amount.add(element.text());

//            步骤
            links = doc.select(".sstep");
            for (Element element : links)
//              result.append(element.text());
                //System.out.println(element.text());
            	rs_detail.add(element.text());
//            标签
            links = doc.select("p > a[href]");
            
			for (Element element : links) {

				if (element.attr("abs:href").contains("http://www.haodou.com/recipe/all/")) {
					// result.append(element.text());
					if (!"菜谱大全".equals(element.text()))
						// System.out.println(element.text());
						rl_label.add(element.text());
				}
			}
	        if(rl_label.size()>0){
	        	;
	        }else{
            	links = doc.select(".pop_tags a");
                for (Element element : links)
                    rl_label.add(element.text());

            }
            
            
            //时间
            links = doc.select(".triplet span");
            if(links.size() == 3){
	            r_pTime = links.get(0).text();
	            r_mTime = links.get(1).text();
	            r_nMembers = links.get(2).text();
            }else{
            	
            	links = doc.select(".quantity li");
            	r_pTime = links.get(0).text().split("：")[1];
	            r_mTime = links.get(1).text().split("：")[1];
	            r_nMembers = links.get(2).text().split("：")[1];
	            
            }
            
            
            //recipe
            String sqlAll="";
            sqlAll=String.format("insert into recipe(rno,preparationtime,"
            		+ "maketime,mealsnumbers,rname,remark,"
            		+ "introduction,picture) values("
            		+"'%s','%s','%s','%s','%s','%s','%s','%s');"
            		,id,r_pTime,r_mTime,r_nMembers,r_title,prompt != null ?prompt.text():"",r_desc,r_picture);
           // System.out.println(sqlAll);
           // db.pst.execute(sqlAll);
            
            //recipelabel
            for (String label: rl_label){
            	String sql = "";
            	sql = String.format("insert into recipelabel(rno,label) values("
            			+ "'%s','%s');", id,label);
            	//System.out.println(sql);
            	sqlAll +="\n"+ sql;
            	//db.pst.execute(sql);
            }
            //System.out.println(sqlAll);
            
            
            //recipestep
            int i=0;
            for (String pic:rs_picture){
            	String sql = "";
            	sql = String.format("insert into recipestep(rno,stepno,stepdetail,steppicture) values("
            			+ "'%s',%d,'%s','%s');", id,i+1,rs_detail.get(i),pic);
            	i++;
            	//System.out.println(sql);
            	sqlAll +="\n"+ sql;
            	//db.pst.execute(sql);
            }
            //System.out.println(sqlAll);
            
            //recipematerial
            i=0;
            for (String name:rm_matName){
            	String sql = "";
            	sql = String.format("insert into recipematerial(rno,material,amount) values("
            			+ "'%s','%s','%s');", id,name,rm_amount.get(i));
            	i++;
            	//System.out.println(sql);
            	sqlAll +="\n"+ sql;
            	//db.pst.execute(sql);
            }
           // System.out.println(rl_label.size());
          //  System.out.println(sqlAll);
            return sqlAll;
            

        } catch (IOException e) {
            e.printStackTrace();
        }
		return url;
    }

    public static String detail(String url) {
        StringBuilder result = new StringBuilder();
        try {
//            获取整个网页 doc节点进行筛选 element
            Document doc = Jsoup.connect(url).get();
//            获取菜名
            Element link = doc.select("h1").first();
            result.append(link.text());
            link = doc.select("[data]").first();
            result.append(link.text());
//            食材
            Elements links = doc.select(".ingtbur");
            for (Element element : links)
                result.append(element.text());

//            步骤
            links = doc.select(".sstep");
            for (Element element : links)
                result.append(element.text());
//            标签
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
