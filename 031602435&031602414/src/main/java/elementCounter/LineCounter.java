package elementCounter;
import java.io.*;
public class LineCounter {
	 public static long countLine(String fileName) {
	        InputStreamReader inputStreamReader = null;
	        BufferedReader bufferedReader = null;
	        String in = null;
	        long lineNum = 0;

	        //�����ļ�
	        try {
	            inputStreamReader = new InputStreamReader(new FileInputStream(fileName));
	        } catch (FileNotFoundException e) {
	            System.out.println("�Ҳ������ļ�");
	            e.printStackTrace();
	        }
	        if (inputStreamReader != null) {
	            bufferedReader = new BufferedReader(inputStreamReader);
	        }
	        //��������
	        try {
	            while ((in = bufferedReader.readLine()) != null) {
	                if (!in.equals("") && (in.toString().charAt(0) =='T' || in.toString().charAt(0) == 'A') ) lineNum++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                inputStreamReader.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return lineNum;
	}
}
