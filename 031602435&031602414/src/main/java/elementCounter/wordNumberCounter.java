package elementCounter;
import java.io.*;
public class wordNumberCounter {
	public static long countWord(String fileName) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int in = 0;
        long wordNum = 0;
        char temp;
        int state = 0;
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
        //���㵥����
        try {
            while ((str = bufferedReader.readLine()) != null) {
            	if (str.length() > 0) {
            		int i = 0;
                	if (str.charAt(0) == 'T') {
    					i += 7;		
    				}
                	else if (str.charAt(0) == 'A') {
    					i += 10;
    				}
                	for(;i < str.length();i++) {
                		//���ݷָ����ָ�
                        temp = str.charAt(i);
                        if ((temp >= 65) && (temp <= 90)) {
                            temp += 32;
                        }

                        //�Զ���״̬ת��
                        switch (state) {
                            case 0: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    state = 1;
                                }
                                break;
                            }
                            case 1: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    state = 2;
                                } else {
                                    state = 0;
                                }
                                break;
                            }
                            case 2: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    state = 3;
                                } else {
                                    state = 0;
                                }
                                break;
                            }
                            case 3: {
                                if ((temp >= 97) && (temp <= 122)) {
                                    state = 4;
                                } else {
                                    state = 0;
                                }
                                break;
                            }
                            case 4: {
                                if (!((temp >= 97) && (temp <= 122)) || ((temp >= '0') && (temp <= '9'))) {
                                    wordNum++;
                                    state = 0;
                                }
                                break;
                            }
                        }
                    }
                    if (state == 4) {
                        wordNum++;
                    }
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
        return wordNum;
}
}
