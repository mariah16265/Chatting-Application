package chatting_application;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.* ;
import java.text.SimpleDateFormat;
import java.util.*;
import java.net.*;

public class Server implements ActionListener{
	
	JPanel p1,a1;
	ImageIcon i1,i3,i6,i4,i7,i9,i10,i12,i13,i15;
	Image i2,i5,i8,i11,i14;
	JLabel back,profile,video,phone,morevert,name,status;
	JTextField text;
	JButton send;
	//So messages come one after the other
	static Box vertical = Box.createVerticalBox();
	static JFrame f =new JFrame();
	public static DataOutputStream dout;
	public static DataInputStream din;

	
	Server(){
		f.setLayout(null);
		//for dividing things over the frame we can use JPanel
		p1=new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);
		p1.setLayout(null);
		f.add(p1);
		
		i1=new ImageIcon(ClassLoader.getSystemResource("3.png"));
		i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
		i3=new ImageIcon(i2);
		back=new JLabel(i3);
		back.setBounds(5,20,25,25);
		p1.add(back);
		
		//when we press back button frame should close
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae){
				f.setVisible(false);		
			}
		 });
		
		i4=new ImageIcon(ClassLoader.getSystemResource("1.jpeg"));
		i5=i4.getImage().getScaledInstance(50,50 ,Image.SCALE_DEFAULT);
		i6=new ImageIcon(i5);
		profile=new JLabel(i6);
		profile.setBounds(40,10,50,50);
		p1.add(profile);
		
		i7=new ImageIcon(ClassLoader.getSystemResource("video.png"));
		i8=i7.getImage().getScaledInstance(50,50 ,Image.SCALE_DEFAULT);
		i9=new ImageIcon(i8);
		video=new JLabel(i9);
		video.setBounds(300,20,30,30 );
		p1.add(	video);
	
		i10=new ImageIcon(ClassLoader.getSystemResource("video.png"));
		i11=i10.getImage().getScaledInstance(35,30 ,Image.SCALE_DEFAULT);
		i12=new ImageIcon(i11);
		phone=new JLabel(i12);
		phone.setBounds(360,20,30,30 );
		p1.add(	phone);

		i13=new ImageIcon(ClassLoader.getSystemResource("3icon.png"));
		i14=i13.getImage().getScaledInstance(10,25 ,Image.SCALE_DEFAULT);
		i15=new ImageIcon(i14);
		morevert=new JLabel(i15);
		morevert.setBounds(420,20,10,25 );
		p1.add(	morevert);
		
		name=new JLabel("Charlotte");
		name.setBounds(110,15,100,18);
		name.setForeground(Color.white);
		name.setFont(new Font("SAN SERIF",Font.BOLD,18));
		p1.add(name);
		
		status=new JLabel("Active Now");
		status.setBounds(110,35,100,18);
		status.setForeground(Color.white);
		status.setFont(new Font("SAN SERIF",Font.BOLD,14));
		p1.add(status);
		
		a1=new JPanel();
		a1.setBounds(5,75,440,570);
		
		f.add(a1);
		
		text =new JTextField();
		text .setBounds(5,655,310,40);
		text.setFont(new Font("SAN SERIF",Font.PLAIN,16));
		f.add(text );
		
		
		send=new JButton("Send");
		send.setBounds(320,655,123,40);
		send.setBackground(new Color(7,94,84));
		send.setFont(new Font("SAN SERIF",Font.PLAIN,16));
		send.setForeground(Color.white);
		send.addActionListener(this);
		f.add(send);
		
		f.setSize(450,700);
		f.setLocation(200,50);
		f.getContentPane().setBackground(Color.WHITE);
		
		f.setUndecorated(true);//removes taskbar on top
		f.setVisible(true);
		
	}
	public void actionPerformed(ActionEvent ae) {
		//when using server put in try catch block
		try {
			String out=text.getText();
			
			JPanel p2=formatLabel(out);
					
			//BorderLayout places elements top,bottom,left and right;
			a1.setLayout(new BorderLayout());
			//Aligning messAGES to right, where line ends
			
			JPanel right=new JPanel(new BorderLayout());
			right.add(p2,BorderLayout.LINE_END);
			vertical.add(right);
			//space between 2 messages is 15
			vertical.add(Box.createVerticalStrut(15));
			//vertical should start at start of page, and messages aligned to the end of line
			a1.add(vertical,BorderLayout.PAGE_START);
			//to send messages
			// Now you can use dout here
            if (dout != null) {
                dout.writeUTF(out);
            }
			
			text.setText(""); //So typing bar is empty once message is sent
			
			f.repaint();
			f.invalidate();
			f.validate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static JPanel formatLabel(String out) {
		JPanel panel=new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JLabel output =new JLabel("<html><p style=\"width:150px\">"+out+"</p></html>");
		output.setFont(new Font("Tehoma",Font.PLAIN,16));
		output.setBackground(new Color(37,211,102));
		output.setOpaque(true);	//for background color to be visible
		//padding
		output.setBorder(new EmptyBorder(15,15,15,50));
		panel.add(output);
	
		Calendar cal =Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
	
		JLabel time=new JLabel();
		time.setText(sdf.format(cal.getTime()));//to dynamically set values
		panel.add(time);
				  
		return panel;
	}

	public static void main(String[] args) {
		new Server();
		dout=null;
		din=null;
		//make my server using server socket class
		try {
			//6001 is port number
			ServerSocket skt = new ServerSocket(6014); // Use a different port number
			
			//accept all my messages infinitely
			while(true) {
				Socket s=skt.accept();
				//Receive messages
				din=new DataInputStream(s.getInputStream());
				//Send messages
				dout=new DataOutputStream(s.getOutputStream());
				
				//infinitely read and receive messages using UTF
				while(true) {
					//reading from input stream
					String msg=din.readUTF();
				
					//to display these messages
					JPanel panel=formatLabel(msg);
					
					//put  message on Panel's left
					JPanel left=new JPanel(new BorderLayout());
					left.add(panel,BorderLayout.LINE_START);
					vertical.add(left);
					f.validate();//to refresh frame
				}
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
