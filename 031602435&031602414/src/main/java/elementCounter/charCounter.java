package elementCounter;
import java.io.*;
public class charCounter {
	public static long countChar(String fileName) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int in = 0;
        long charNum = 0;
        String str = null;
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
        //�����ַ���
        try {
        	while ((str = bufferedReader.readLine()) != null) {
	
				
                if(str.length() > 0) {
                	int i = 0;
                	if (str.charAt(0) == 'T') {
    					i += 7;
    				}
                	else if (str.charAt(0) == 'A') {
    					i += 10;
    				}
                	charNum += str.length() - i;
                }
            	
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
        return charNum;
}
}
