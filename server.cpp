#include <iostream>
#include <cstdio>
#include <WinSock2.h>
#pragma comment(lib, "ws2_32.lib")
using namespace std;

#define MAX 1025

void udpServer(char *portNumber)
{
	char a[101],ch[5];
	int num;
	WSADATA wsaData;
	SOCKET hServSock;
	struct sockaddr_in hServAddr, hClntAddr;
	char buf[MAX];
	int clen = sizeof(hClntAddr);

	if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0)
	{
		fprintf(stderr, "WSAStartup() failed");
		exit(1);
	}

	if ((hServSock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0)
	{
		fprintf(stderr, "socket() failed");
		WSACleanup();
		exit(1);
	}

	memset(&hServAddr, 0, sizeof(hServAddr));
	hServAddr.sin_family = AF_INET;
	hServAddr.sin_addr.s_addr = INADDR_ANY;
	hServAddr.sin_port = htons(atoi(portNumber));

	if (bind(hServSock, (struct sockaddr *)&hServAddr, sizeof(hServAddr)) == SOCKET_ERROR)
	{
		cout<<"bind() error!\n";
		WSACleanup();
		closesocket(hServSock);
		exit(1);
	}
	cout<<"파일:";
	cin>>a;
	FILE *fp=fopen(a,"wb");
	while(1)
	{
	recvfrom(hServSock, ch, 5, 0, (struct sockaddr *) &hClntAddr, &clen);
	if(!strcmp(ch,"endf"))
	break;
	num=atoi(ch);

	recvfrom(hServSock, buf, num, 0, (struct sockaddr *) &hClntAddr, &clen);
	
	fwrite(buf,1,num,fp);
	}
}

void main()
{
	char a[101];
	cout<<"포트번호:";
	cin>>a;


	udpServer(a);

}
