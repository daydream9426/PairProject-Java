

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class export1 {
	public static int export1(int character,int wordcount,int lines,String Opathname)
	{
		File file = new File(Opathname);
		try {
			BufferedWriter bi = new BufferedWriter(new FileWriter(file));
			bi.append("characters: "+character+"\r\n");
			bi.append("words: "+wordcount+"\r\n");
			bi.append("lines: "+lines+"\r\n");
			bi.close();
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		return 0;
	}
}
