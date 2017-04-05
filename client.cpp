#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <WinSock2.h>

using namespace std;

#define MAX 512

void udpClient(char *IPaddress, char *portNumber)
{
	WSADATA wsaData;
	SOCKET hServSock;
	struct sockaddr_in servAddr;
	char buf[MAX];

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
	
	cout<<"메시지:";
	gets_s(buf, MAX);

	if(!strcmp(buf,"\"-1\""))
	exit(1);
	else
	sendto(hServSock, buf, MAX, 0, (struct sockaddr *) &servAddr, sizeof(servAddr));
}

void main()
{
	char a[101],b[101];
	cout<<"주소:";
	cin>>a;
	cout<<"포트번호:";
	cin>>b;
	getchar();
	cout<<"종료 \"-1\""<<'\n';

	while(1)
	udpClient(a,b);
	
}