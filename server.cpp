#include <iostream>
#include <cstdio>
#include <WinSock2.h>

using namespace std;

#define MAX 512
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

	while(1)
	{
	recvfrom(hServSock, buf, MAX, 0, (struct sockaddr *) &hClntAddr, &clen);
	cout<<"메시지:"<<buf<<'\n';
	}
}

void main()
{
	char a[101];
	cout<<"포트번호:";
	cin>>a;


	udpServer(a);

}