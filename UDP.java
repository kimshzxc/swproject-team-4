package client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;

public class UDP{
   public static void main(JTextArea text){
       OutputStream out;
       FileInputStream fin;
       
       try{
           Socket soc = new Socket("127.0.0.1",11111); //127.0.0.1�� ������ �����Ƿ� �ڽ��� �����Ǹ� ��ȯ���ְ�,
           text.append("Server Start!\n");        //11111�� �������� ��Ʈ�Դϴ�.
           out =soc.getOutputStream();                 //������ ����Ʈ������ �����͸� ������ ��Ʈ���� �����մϴ�.
           DataOutputStream dout = new DataOutputStream(out); //OutputStream�� �̿��� ������ ������ ������ ��Ʈ���� �����մϴ�.
           
             //���� �̸��� �Է¹ޱ����� ��ĳ�ʸ� �����մϴ�.
           
           
           while(true){
               String filename = "test.wmv";    //��ĳ�ʸ� ���� ������ �̸��� �Է¹ް�,
           fin = new FileInputStream(new File(filename)); //FileInputStream - ���Ͽ��� �Է¹޴� ��Ʈ���� �����մϴ�.
           
       byte[] buffer = new byte[1024];        //����Ʈ������ �ӽ������ϴ� ���۸� �����մϴ�.
       int len;                               //������ �������� ���̸� �����ϴ� �����Դϴ�.
       int data=0;                            //����Ƚ��, �뷮�� �����ϴ� �����Դϴ�.
       
       while((len = fin.read(buffer))>0){     //FileInputStream�� ���� ���Ͽ��� �Է¹��� �����͸� ���ۿ� �ӽ������ϰ� �� ���̸� �����մϴ�.
           data++;                        //�������� ���� �����մϴ�.
       }
       
       int datas = data;                      //�Ʒ� for���� ���� data�� 0�̵Ǳ⶧���� �ӽ������Ѵ�.

       fin.close();
       fin = new FileInputStream(filename);   //FileInputStream�� ����Ǿ����� ���Ӱ� �����մϴ�.
       dout.writeInt(data);                   //������ ����Ƚ���� ������ �����ϰ�,
       dout.writeUTF(filename);               //������ �̸��� ������ �����մϴ�.
       
        len = 0;
       
       for(;data>0;data--){                   //�����͸� �о�� Ƚ����ŭ FileInputStream���� ������ ������ �о�ɴϴ�.
           len = fin.read(buffer);        //FileInputStream�� ���� ���Ͽ��� �Է¹��� �����͸� ���ۿ� �ӽ������ϰ� �� ���̸� �����մϴ�.
           out.write(buffer,0,len);       //�������� ������ ����(1kbyte��ŭ������, �� ���̸� �����ϴ�.
       }
       text.append("�� "+datas+" kbps \n");
       text.append("UDP �������� �Ϸ�!\n");
           }
       }catch(Exception e){
       }
   }
}