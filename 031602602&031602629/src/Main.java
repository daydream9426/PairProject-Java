import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;	
public class Main {
	
		@SuppressWarnings("deprecation")
		public static void main(String[] arg)throws Exception{
	       String input=null,output=null,s;;
	       int w0=0,m0=1,n0=10,i,j;
	       Map<String,Integer>test=new HashMap<String,Integer>();
		     for(i=1;i<arg.length;i+=2)
		     {
	            if(arg[i-1].equals("-i"))
	            	input=arg[i];
	            else if(arg[i-1].equals("-o"))output=arg[i];
	            else if(arg[i-1].equals("-w"))w0=Integer.parseInt(arg[i]);
	            else if(arg[i-1].equals("-m"))m0=Integer.parseInt(arg[i]);
	            else if(arg[i-1].equals("-n"))n0=Integer.parseInt(arg[i]);
		    }
	     DataInputStream in=new DataInputStream(new FileInputStream(input));
	    
	    
	     int lines=0,words=0,c_length=-1;
	     for(;(s=in.readLine())!=null;)
	     {
		    Matcher m=Pattern.compile("(Title|Abstract): (.*)").matcher(s);//ƥ������
		    int flag=0;
		    if(m.find()){
	     if(s.matches("Title: .*"))//ƥ����ⲿ��
	    	 flag=1;//
	     lines++;
	     s=m.group(2).toLowerCase();//��������
	     c_length+=1+s.length();
	     m=Pattern.compile("(.*?)[a-z]{0,3}[0-9]+.*?[^a-z0-9]").matcher(s+" 1 ");//ƥ�䲻�Ϸ����ʣ�
	     //ͨ�����Ϸ����ʽ������и��һ�ζ�
	    for(;m.find();)
	    {
		Matcher m2=Pattern.compile("([a-z]{4}[a-z0-9]*)([^a-z0-9]+)").matcher(m.group(1));
		//m2������ָ���
		List<String>words_group=new ArrayList<String>(),//���µ���
		char_group=new ArrayList<String>();//���·ָ���
		for(;m2.find();words++)
		{
			words_group.add(m2.group(1));
			char_group.add(m2.group(2));
		}
		for(i=0;i<=words_group.size()-m0;i++)
		{
	        for(s=words_group.get(i),j=0;j<m0-1;j++)
	           s+=char_group.get(i+j)+words_group.get(i+j+1);//ͨ��-m�ж��Ƿ񽫵��ʴ�����
	         j=1+9*w0*flag;
	        if(!test.containsKey(s))test.put(s,j);
	        else 
	        	test.put(s,test.get(s)+j);
		}
	    }
		}
	  }
	     
	@SuppressWarnings({ "rawtypes", "unchecked" })
	List<Entry<String,Integer>>l=new ArrayList(test.entrySet());
	Collections.sort(l,new Comparator<Entry<String,Integer>>()
	{
		public int compare(Entry<String,Integer>t1
		,Entry<String,Integer>t2)
		{
	int k=-(t1.getValue()-t2.getValue());
	if(k==0)return t1.getKey().compareTo(t2.getKey());
	return k;
		}
	});
	PrintWriter out=new PrintWriter(output);
	out.println("characters: "+c_length+"\nwords: "+words+"\nlines: "+lines);
	for(i=0;i<Math.min(n0,l.size());i++)
	out.println("<"+l.get(i).getKey()+">: "+l.get(i).getValue());
	out.close();in.close();
		}
	
}