
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class writeInTxt {
	public static void writeTxt(String str,String o) {
		FileWriter fileWriter = null;
		try {
			//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			File file = new File(o);
			file.createNewFile();
			fileWriter = new FileWriter(file,true);
		}catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pWriter = new PrintWriter(fileWriter);
		pWriter.println(str);
		pWriter.flush();
		try {
			fileWriter.flush();
			pWriter.close();
			fileWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
