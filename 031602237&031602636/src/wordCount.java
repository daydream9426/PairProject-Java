

import java.io.IOException;

public interface wordCount {
	/**
	 * ��������
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	int linesCount(String filepath) throws IOException;
	
	/**
	 * ���غϷ�������
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	int wordsCount(String filepath) throws IOException;
	
	/**
	 * �����ַ���
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	int charsCount(String filepath) throws IOException;
	
	/**
	 * ��Ƶǰn�ĵ���
	 * @param filepath
	 * @throws IOException
	 */
	void wordDetail(String filepath, Parser parser, WordsCount wordsCount) throws IOException;
}
