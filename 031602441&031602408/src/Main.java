

import java.util.Map;
import java.util.TreeMap;

public class Main {
	public static void main(String[] argv)
	{
		String pathname=null;
		pathname="C:\\Users\\Mac\\Desktop\\031602408\\result.txt";
		String Opathname ="output.txt";
		int Wflag=0;
		int Numflag=1;
		int Comflag =10;
		for(int i=0;i<argv.length;i++)
		{
			if(argv[i].equals("-i"))
			{
				i=i+1;
				pathname = argv[i];
			}
			if(argv[i].equals("-o"))
			{
				i+=1;
				Opathname = argv[i];
			}
			if(argv[i].equals("-w"))
			{
				i+=1;
				Wflag=Integer.parseInt(argv[i]);
			}
			if(argv[i].equals("-m"))
			{
				i+=1;
				Numflag=Integer.parseInt(argv[i]);
			}
			if(argv[i].equals("-n"))
			{
				i+=1;
				Comflag=Integer.parseInt(argv[i]);
			}
		}
		Map<String,Integer> words = new TreeMap<String,Integer>();
		words=Readwords.read(pathname, Wflag, Numflag, Opathname);
		sort.sort1(words, Comflag, Opathname);
		/*pathname="C:\\Users\\Mac\\Desktop\\1234.txt";
		Numflag=3;
		Comflag=10;
		Wflag=1;
		File fr = new File(pathname);
		int weight;
		int wordcount=0;
		int lines=0;
		if(Wflag==1)
		{
			weight=10;
		}
		else weight=0;
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fr),"utf-8"));
			String s="";
			Map<String,Integer> words = new TreeMap<String,Integer>();
			Pattern p1 = Pattern.compile("[0-9|a-z|A-Z]+");//�ַ���
			Pattern p = Pattern.compile("(^[a-z|A-Z]{4}[a-z|A-Z|0-9]*)");//����
			Pattern p3 = Pattern.compile("\\s*");//�Ƿ��ǿ���
			String s2="";
			int character=0;
			String Sflag="";
			StringBuffer s4=new StringBuffer();
			Matcher m;
			Matcher m1;
			int START1=1;
			int END1=1;
			int i=0;
			String s1="";
			String re="";
			
			while((s=bf.readLine())!=null)
			{
				if(s.length()!=0)
				{
					
					if(s.charAt(0)=='T')//title
					{
						s=s.substring(7);
						m = p3.matcher(s);
						m.find();
						if(m.end()!=s.length())//�жϿ���
						{
						lines++;
						character+=s.length();
						}
						m = p1.matcher(s);
						while(m.find())
						{
							s4=new StringBuffer();
							s1 = m.group();//s1�ҵ���һ������
							m1 = p.matcher(s1);
							if(m1.find())//�жϵ���
							{
								wordcount++;
								s4.append(s1);//���ҵ���һ�����ʲ��������
								s2=s.substring(m.end());//������ʣ�µĲ���
								Matcher m2 = p1.matcher(s2);//�Ҷ��������ɷ�
								START1=0;
								for(i=1;i<Numflag;i++)//�ж϶���
								{	
									if(m2.find())
									{
										String s3=m2.group();//����ڶ�����
										m1=p.matcher(s3);//�ж��Ƿ��ǵ���
										if(m1.find())
										{
											END1=m2.end();
											//System.out.println(START1+" "+END1);
											re=s2.substring(START1,END1);
											//�ǵ��ʲ������
											s4.append(re);
											START1=m2.end();
										}
										else 
										{
											break;
										}
									}
									else break;
								}
								if(i==Numflag)//�������Ҫ��,����map
								{
									if(words.get(s4.toString())!=null)
									{
										words.put(s4.toString(), words.get(s4.toString())+weight);
									}
									else
									words.put(s4.toString(), weight);
								}
							}
						}
					}
					if(s.charAt(0)=='A')//abstract
					{
						s=s.substring(10);//abstract֮��
						m = p3.matcher(s);
						m.find();
						if(m.end()!=s.length())
						lines++;
						character+=s.length()+1;
						m = p1.matcher(s);
						while(m.find())
						{
							
							s4=new StringBuffer();
							s1 = m.group();//s1�ҵ���һ������
							m1 = p.matcher(s1);
							if(m1.find())//�жϵ���
							{
							wordcount++;
							s4.append(s1);//���ҵ���һ�����ʲ��������
							s2=s.substring(m.end());//������ʣ�µĲ���
							
							Matcher m2 = p1.matcher(s2);//�Ҷ��������ɷ�
							START1=0;			
							for(i=1;i<Numflag;i++)//�ж϶���
							{	
								if(m2.find())
								{
									String s3=m2.group();//����ڶ�����
												
									m1=p.matcher(s3);//�ж��Ƿ��ǵ���
									if(m1.find())
									{
										END1=m2.end();		
										//�ǵ��ʲ������
										re=s2.substring(START1,END1);
										s4.append(re);
										START1=m2.end();		
									}
									else 
									{
										break;
									}
								}
								else break;
							}
							if(i==Numflag)//�������Ҫ��,����map
							{
								
								if(words.get(s4.toString())!=null)
								{
									words.put(s4.toString(), words.get(s4.toString())+1);
								}
								else
								words.put(s4.toString(), 1);
							}
							}
						}
					}
				}Sflag=s;
			}
			if(Sflag.length()!=0)
			{
				character-=1;
			}
			//����¼��
			//File file = new File("result.txt");
			//BufferedWriter bi = new BufferedWriter(new FileWriter(file));
			bf.close();
			export1.export1(character, wordcount, lines,Opathname);//�����Opathname
			sort.sort1(words, Comflag, Opathname);//����
			int []a=new int[Comflag];
			String []b=new String[Comflag];
			for(i=0;i<Comflag;i++)
			{
				a[i]=-1;
				b[i]= null;
				Iterator<Map.Entry<String,Integer>> entries = words.entrySet().iterator();
				while(entries.hasNext())
				{
					Map.Entry<String, Integer> entry = entries.next();
					if(a[i]==entry.getValue())
					{
						if(entry.getKey().compareTo(b[i])<0)
						{
							b[i]=entry.getKey();
						}
					}
					else if(a[i]<entry.getValue())
					{
						a[i]=entry.getValue();
						b[i]=entry.getKey();
					}
				}
				if(b[i]!=null)
				words.put(b[i], 0);
			}
			File file = new File(Opathname);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			for(i=0;i<Comflag;i++)
			{
				if(a[i]!=0&&a[i]!=-1)
				bw.write("<"+b[i]+">: "+a[i]+"\r\n");
			}
			bw.close();*/
		} 

}


