package client;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


public class GUI{
	JTextArea text;
	
	public static void main(String[] args){
		GUI gui = new GUI();
		gui.go();
		
	}
	
	



public void go() {
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	
	JButton button1 = new JButton("TCP 전송");
	JButton button2 = new JButton("UDP 전송");
	Box buttonBox = new Box(BoxLayout.Y_AXIS);
	
	button1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {

			client.main(text);
		}	
	});
	
	button2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {

			UDP.main(text);
		}	
	});

	
	buttonBox.add(button1);
	buttonBox.add(button2);
	text = new JTextArea(10,20);
	text.setLineWrap(true);
	
	
	JScrollPane scroller = new JScrollPane(text);
	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	panel.add(scroller);
	
	panel.setBackground(Color.darkGray);
	//frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
	frame.getContentPane().add(BorderLayout.CENTER,panel);
	frame.add(BorderLayout.SOUTH,buttonBox);
	frame.setSize(350,300);
	frame.setVisible(true);
	
}

}



