#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <WinSock2.h>
#pragma comment(lib, "ws2_32.lib")
using namespace std;

#define MAX 1025

void udpClient(char *IPaddress, char *portNumber)
{
	WSADATA wsaData;
	char a[101],buf[MAX],ch[5];
	int n=0;
	SOCKET hServSock;
	struct sockaddr_in servAddr;

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

	memset(&servAddr, 0, sizeof(servAddr));
	servAddr.sin_family = AF_INET;
	servAddr.sin_addr.s_addr = inet_addr(IPaddress);
	servAddr.sin_port = htons(atoi(portNumber));
	cout<<"파일:";

	cin>>a;
	FILE* fp=fopen(a,"rb");
	while(0< (n=fread(buf,1,1024,fp)))
	{
		itoa(n,ch,10);
		sendto(hServSock, ch, 5, 0, (struct sockaddr *) &servAddr, sizeof(servAddr));
		sendto(hServSock, buf, n, 0, (struct sockaddr *) &servAddr, sizeof(servAddr));
	}
	sendto(hServSock, "endf", 5, 0, (struct sockaddr *) &servAddr, sizeof(servAddr));
	
}

void main()
{
	char a[101],b[101];
	cout<<"주소:";
	cin>>a;
	cout<<"포트번호:";
	cin>>b;
	getchar();

	udpClient(a,b);
	
}
