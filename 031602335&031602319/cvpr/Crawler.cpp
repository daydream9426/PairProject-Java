#include<iostream>
#include<string>
#include"winsock2.h"
#include<fstream>
#include<queue>
#pragma comment(lib, "ws2_32.lib")
using namespace std;
//�����ȡ��ҳʱ�������ĳ�ʼ��С
#define DEFAULT_PAGE_BUF_SIZE 1048576
//�ö��д洢����������ҳ��URL�е��ļ�·������ҳ������Ҫ�ǹ������ĵĽ��ܸ��ݴ���ҳ��ȡ��ͷ��ժҪ��
queue<string> Texturl;     
//WSA�������ִ�гɹ��ɵ���Windows Sockets API
void startupWSA()
{
	WSADATA wsadata;
	//������ɹ�����0
	if (WSAStartup(MAKEWORD(2, 0), &wsadata) != 0)
	{
		cerr << "WSA����ʧ��" << endl;
		exit(1);
	}
}
//�ͷ�Ӧ�ó�����Դ
inline void cleanupWSA()
{
	WSACleanup();
}
//����һ���ַ���ָ�����ҳ�ļ�·����������ר����2018������ȡ���������������ǹ̶��ģ�
//ͨ���ļ�·����ȡ��ҳ���ݴ����ַ���ָ��
void GO(char * &response, string source)
{
	string host, resource;
	host = "openaccess.thecvf.com"; //�洢2018���ĵķ������������ڸ÷���������2018�����ĵ�������Ϣ
	resource = source;
	struct hostent * hp = gethostbyname(host.c_str());
	if (hp == NULL)
	{
		//�Ҳ�����������
		cerr << "can not find address";
		exit(1);
	}
	SOCKET sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

	if (sock == -1 || sock == -2) {
		//�������Ӷ˿�ʧ��
		cout << "Can not create sock." << endl;
		exit(1);
	}
	SOCKADDR_IN sa;
	sa.sin_family = AF_INET;
	sa.sin_port = htons(80);
	memcpy(&sa.sin_addr, hp->h_addr, 4);
	connect(sock, (SOCKADDR*)&sa, sizeof(sa));
	//http��������
	string request = "GET " + resource + " HTTP/1.1\r\nHost:" + host + "\r\nConnection:Close\r\n\r\n";
	//��������
	send(sock, request.c_str(), request.size(), 0);
	int m_nContentLength = DEFAULT_PAGE_BUF_SIZE;
	int bytesRead = 0;
	int ret = 1;
	//������������С���ַ���ָ��
	char *pageBuf = (char *)malloc(m_nContentLength);
	char *temp = NULL;
	//��������ʼ��
	if(pageBuf!=0)
	memset(pageBuf, 0, m_nContentLength);
	while (ret > 0)
	{
		//���շ��ص���Ϣ��retΪ������ֽ�����Ϊ0ʱ��ʾ��ɶ�ȡ��������ֹ
		if(pageBuf+bytesRead!=0)
		ret = recv(sock, pageBuf + bytesRead, m_nContentLength - bytesRead, 0);
		if (ret > 0)
		{
			//��¼��������ֽ���
			bytesRead += ret;
		}
		if (m_nContentLength - bytesRead < 100)
		{
			//����������������,�ɼӿ��ȡ�ٶ�
			m_nContentLength *= 2;
			temp = (char*)realloc(pageBuf, m_nContentLength);
			if (temp != NULL)
				pageBuf = temp;
		}
	}
	//�ַ���������
	pageBuf[bytesRead] = '\0';
	response = pageBuf;
	closesocket(sock);
}
//ȥ��URL�д��ڵ�\r��
string Durl(string turl)
{
	string u;
	const char *urlo = turl.c_str();
	char ab[100];
	int j = 0;
	for (unsigned int k = 0; k < strlen(urlo); k++)
		{
			if (urlo[k] != '\r')
			{
				ab[j] = urlo[k];
				j++;
			}
		}
	ab[j] = '\0';
	u = ab;
	return u;
}
//ͨ�����������ҳ�õ��������ĵ�URL���������
void GetTexturl()
{
	int q;
	char *response=NULL;
	//2018���Ĺ�����ҳ�ļ�
	string source = "/CVPR2018.py";
	//response����2018���Ĺ�����ҳ����
	GO(response, source);
	//���Ե����Ľ����ļ��ڷ����������ĸ�Ŀ¼�£��ɴ˿�ƥ�䵽�������ĵ�URL��ַ
	//�������ĵ�URL��ַ���ļ�·�����Ը��ַ�����ͷ��.html����
	const char *t = "content_cvpr_2018/html/";
	const char *result = strstr(response, t);
	string turl, gurl;
	while (result)
	{
		const char * nextQ = strstr(result, "\"");
		if (nextQ) {
			char * url = new char[nextQ - result + 1];
			q=sscanf(result, "%[^\"]", url);
			result += strlen(url);
			//����������URL�д���ĳЩ����Ĵ���
			const char *urlp = url;
			const char *nextq = strstr(urlp, "\n");
			if (nextq)
			{
				char * furl = new char[nextq - urlp + 1];
				q=sscanf(urlp, "%[^\n]", furl);
				turl = furl;
				urlp += strlen(furl);
				urlp++;
				const char * next1 = strstr(urlp, "\n");
				if (next1)
				{
					char * furll = new char[next1 - urlp + 1];
					q=sscanf(urlp, "%[^\n]", furll);
					urlp += strlen(furll);
					urlp++;
					gurl = urlp;
					turl += gurl;
					delete[] furll;
				}
				delete[] furl;
			}
			else
			{
				turl = urlp;
			}
			turl = Durl(turl);
			turl = "/" + turl;
			//�������
			Texturl.push(turl);
			delete[] url;
		}
		result = strstr(result, t);
	}
	free(response);
}
//ͨ��������������������URL���õ���ͷ��ժҪ����result.txt��
void GetText()
{
	int q;
	//��ͷ�Ŀ�ʼƥ���ַ���
	const char * t = "papertitle\">";
	//ժҪ�Ŀ�ʼƥ���ַ���
	const char * a = "abstract\" >";
	int n = Texturl.size();
	string u;
	ofstream outfile("result.txt");
	if (!outfile)
	{
		cerr << "open error!" << endl;
		exit(1);
	}
	for (int i = 0; i < n; i++)
	{
		u = Texturl.front();
		//��ȡ���Ľ��ܵ���ҳ
		char * response = NULL;
		GO(response, u);
		//�����Ľ�����ҳ����ȡ��ͷ��ժҪ
		const char * result = strstr(response, t);
		if (result)
		{
			result += strlen(t);
			result++;
			const char * next = strstr(result, "<");
			char * title = new char[next - result + 1];
			q=sscanf(result, "%[^<]", title);
			result = strstr(result, a);
				if (result)
				{
					result += strlen(a);
					result++;
					const char * next2 = strstr(result, "<");
					char * abstract = new char[next2 - result + 1];
					q = sscanf(result, "%[^<]", abstract);
					outfile << i << endl;
					outfile << "Title: " << title << endl;
					outfile << "Abstract: " << abstract << endl;
					if (i != n - 1)
					{
						outfile << endl;
						outfile << endl;
					}
					delete[] abstract;
				}
			delete[] title;
		}
		free(response);
		Texturl.pop();
	}
	outfile.close();
}
//������
int main()
{
	startupWSA();
	GetTexturl();
	GetText();
	cleanupWSA();
	system("pause");
	return 0;
}