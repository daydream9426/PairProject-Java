
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Main {
 public static void getContent(String URL) {
    	try
    	{
    		//��������ļ�
    		 File file = new File("result.txt");
	         BufferedWriter bi = new BufferedWriter(new FileWriter(file));
       //��ȡ��������
    	     Document document1 =Jsoup.connect(URL)
    	    		 .header("Accept-Encoding", "gzip, deflate")
    	    		    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
    	    		    .maxBodySize(0)
    	    		    .timeout(600000)
    	    		    .get();
    		 Elements element3 =  document1.select("[class=ptitle]");
    		 Elements hrefs = element3.select("a[href]");
    		 long  count=0;
    		 for(Element element6:hrefs){
    		 String url=element6.absUrl("href");
    		 //������ȡ�����ӽ��ж�����ȡ
    		 Document document2 =Jsoup.connect(url)
    				.header("Accept-Encoding", "gzip, deflate")
 	    		    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
 	    		    .maxBodySize(0)
 	    		    .timeout(600000)
 	    		    .get();;
 	    		    //��������Ҫ��ȡ������
    		 Elements elements1 =  document2.select("[id=papertitle]");
    		 String title=elements1.text();
    		  bi.write(count+"\r\n");
	          bi.write("Title: "+title+"\r\n");
    		 Elements elements2 =  document2.select("[id=abstract]");
    		 String abstract1=elements2.text();
    
    		 bi.write("Abstract: "+abstract1+"\r\n"+"\r\n"+"\r\n");
    		 count++;
    		 }
    		 bi.close();
        }catch(Exception e)
    	{
    	e.printStackTrace();
    	}
    }


   public static void main(String[] args) throws IOException
    {
   // long startTime = System.currentTimeMillis();
	 String url="http://openaccess.thecvf.com/CVPR2018.py";
	 getContent(url);
	//long endTime = System.currentTimeMillis();
	//System.out.println("����ʱ��:" + (endTime - startTime) + "ms");
    }
}