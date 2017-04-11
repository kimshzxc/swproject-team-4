#include <iostream>
#include <cstdio>
#include <string.h>
#include <WinSock2.h>
#pragma comment (lib, "ws2_32.lib")
using namespace std;
#define MAX 9999
int c=0;
void udpServer(char *portNumber)
{
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
	FILE *FP =fopen("C:/Users/alps/Documents/Visual Studio 2010/Projects/server/Debug/abc.txt","w");

	recvfrom(hServSock, buf, MAX, 0, (struct sockaddr *) &hClntAddr, &clen);
	fprintf(FP,"%s",buf);
	fclose(FP);
}

void main()
{
	char a[101];
	cout<<"포트넘버:";
	cin>>a;


	udpServer(a);

}