
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String CVPR_URL = "http://openaccess.thecvf.com/CVPR2018.py";

    public static void main(String[] args) throws IOException {
        System.out.println("������ȡ���ģ�Ԥ��ʱ��3����");
        htmlFilter(getHtmlData(CVPR_URL));
        System.out.println("������ȡ���");
    }

    /**
     * @param urlString
     * @return
     * @throws IOException
     */
    public static String getHtmlData(String urlString) throws IOException {
        /* ����GET���� */
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // httpURLConnection.setConnectTimeout(10000);
        // httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");

        /* ��ȡ����������ȡ */
        InputStream inputStream = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        /* ��ȡ���ݵ�stringBuilder */
        StringBuilder stringBuilder = new StringBuilder();
        String current = null;
        while ((current = bufferedReader.readLine()) != null) {
            stringBuilder.append(current);
        }

        /* �ر����Ӻ������� */
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        httpURLConnection.disconnect();

        return (stringBuilder.toString());
    }

    /**
     * @param html
     * @throws IOException
     */
    public static void htmlFilter(String html) throws IOException {
        /* ���ݴ���dt class="ptitle">�м� */
        //Pattern pattern = Pattern.compile("<dt class=\"ptitle\"><br><a href=\"([^\"]*)\">([^<]*)");
        Pattern pattern = Pattern.compile("<dt class=\"ptitle\"><br><a href=\"(.*?)\">([^<]*)");
        Matcher matcher = pattern.matcher(html);
        File file = new File("result.txt");

        if (file.exists()) file.createNewFile();

        PrintWriter printWriter = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

        int paperCount = 0;
        while (matcher.find()) {
            /* ��ȡ����Abstract */
            String paperAbstract = getPaperAbstract("http://openaccess.thecvf.com/" + matcher.group(1));

            printWriter.println(paperCount);
            /* д������title */
            printWriter.println("Title: " + matcher.group(2));
            /* д������Abstract */
            printWriter.println("Abstract: " + paperAbstract);
            /* д���������з� */
            printWriter.println();
            printWriter.println();

            System.out.print("��ȡ��" + String.valueOf(paperCount) + "ƪ���\r");

            paperCount += 1;
        }
        System.out.println();
        printWriter.flush();
        printWriter.close();
    }

    public static String getPaperAbstract(String paperURL) throws IOException {
        String paperHtml = getHtmlData(paperURL);
        //Pattern pattern = Pattern.compile("<div id=\"abstract\" >([^<]*[^//])");
        Pattern pattern=Pattern.compile("<div id=\"abstract\" >(.*?)</div>");
        Matcher matcher = pattern.matcher(paperHtml);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
