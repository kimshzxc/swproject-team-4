#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <WinSock2.h>
#pragma comment (lib, "ws2_32.lib")
using namespace std;

#define MAX 9999

void udpClient(char *IPaddress, char *portNumber)
{
	WSADATA wsaData;
	SOCKET hServSock;
	struct sockaddr_in servAddr;
	char b[MAX] = {};
	char a[MAX];
	int n=0;

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
	cout<<"����:";
	cin>>a;
	FILE* FP =fopen(a,"r");

	while( (n = fread(b, 1, MAX, FP)) > 0 )
	b[n] = 0;
	
	fclose(FP);

	sendto(hServSock, b, MAX, 0, (struct sockaddr *) &servAddr, sizeof(servAddr));
}

void main()
{
	char a[101],b[101];
	cout<<"�ּ�:";
	cin>>a;
	cout<<"��Ʈ��ȣ:";
	cin>>b;


	udpClient(a,b);
	
}