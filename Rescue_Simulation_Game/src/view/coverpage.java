package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.GapContent;

public class coverpage extends JFrame implements MyWindow{
	private ActionListener emergencyService;
	private JPanel cover;
	private JPanel choose;
	private JLabel photo1;
	private JLabel photo2;
	private JLabel photo3;
	private JLabel photo4;
	private JLabel photo5;
	private JLabel photo6;
	private JButton start;
	private JButton story;
	private JButton exit;
	private JPanel first;
	private JPanel second;
	private JLabel save;
	private JPanel choice1;
	private JPanel choice2;
	private JLabel chooseplayer;
	private JButton spongebob;
	private JButton patrick;
	private JButton squidward;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel x;
	private JButton back1;
	private JButton back2;
	private JTextArea thestory;
	private JPanel panel;
	private JScrollPane scroll;
	
	
	
	public coverpage(ActionListener emergencyService) {
		super();
		this.emergencyService = emergencyService;
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		cover=new JPanel();
		second=new JPanel();
		choose=new JPanel();
		choice1=new JPanel();
		choice2=new JPanel();
		x=new JLabel();
		chooseplayer=new JLabel("CHOOSE YOUR PLAYER: ",JLabel.CENTER);
		spongebob=new JButton("Spongebob");
		patrick=new JButton("Patrick");
		squidward=new JButton("Squidward");
		label1=new JLabel();
		label2=new JLabel();
		label3=new JLabel();
		photo6 =new JLabel();
		photo1 =new JLabel();
		photo2 =new JLabel();
		photo3 =new JLabel();
		photo4 =new JLabel();
		photo5 =new JLabel();
		start=new JButton();
		story=new JButton();
		exit=new JButton();
		first=new JPanel();
		save=new JLabel();
		back1=new JButton();
		back2=new JButton();
		thestory=new JTextArea();
		panel=new JPanel();
		setContentPane(cover);
		start.setText("START");
		start.setForeground(Color.black);
		start.setFont(new Font("Centaur", Font.BOLD, 44));
		start.addActionListener(emergencyService);
		story.setText("HOW TO PLAY");
		story.setForeground(Color.black);
		story.setFont(new Font("Centaur", Font.BOLD, 44));
		story.addActionListener(emergencyService);
		exit.setText("EXIT");
		exit.setForeground(Color.black);
		exit.setFont(new Font("Centaur", Font.BOLD, 44));
		exit.addActionListener(emergencyService);
		start.setBorderPainted(false);
		start.setBackground(new Color(153,204,255));
		story.setBorderPainted(false);
		story.setBackground(new Color(153,204,255));
		exit.setBorderPainted(false);
		exit.setBackground(new Color(153,204,255));
		first.setLayout(new GridLayout(1,3));
		first.setPreferredSize(new Dimension(1370,150));
		first.add(start);
		first.add(story);
		first.add(exit);
		second.setLayout(new GridLayout(1,4));
		ImageIcon img1 = new ImageIcon("spongee.png");
		Image z1= img1.getImage();
		Image newimg1 = z1.getScaledInstance(300, 250, Image.SCALE_SMOOTH);
		img1 = new ImageIcon(newimg1);
		photo1.setIcon(img1);
		ImageIcon img2 = new ImageIcon("patrick.png");
		Image z2 = img2.getImage();
		Image newimg2 = z2.getScaledInstance(300, 250, Image.SCALE_SMOOTH);
		img2 = new ImageIcon(newimg2);
		photo2.setIcon(img2);
		ImageIcon img3 = new ImageIcon("squid.png");
		Image z3 = img3.getImage();
		Image newimg3 = z3.getScaledInstance(300, 250, Image.SCALE_SMOOTH);
		img3 = new ImageIcon(newimg3);
		photo3.setIcon(img3);
		ImageIcon img4 = new ImageIcon("shamshoon.png");
		Image z4 = img4.getImage();
		Image newimg4 = z4.getScaledInstance(350, 250, Image.SCALE_SMOOTH);
		img4 = new ImageIcon(newimg4);
		photo4.setIcon(img4);
		second.add(photo1);
		second.add(photo2);
		second.add(photo3);
		second.add(photo4);
		ImageIcon img = new ImageIcon("download.jpg");
		Image z = img.getImage();
		Image newimg = z.getScaledInstance(1370, 300, Image.SCALE_SMOOTH);
		img = new ImageIcon(newimg);
		photo6.setIcon(img);
		back2.setText("Back");
		back2.setBackground(new Color(51,51,255));
		back2.setFont(new Font("Centaur", Font.BOLD, 50));
		back2.setForeground(Color.black);
		back2.setBorderPainted(true);
		back2.addActionListener(emergencyService);
		back2.setBorder(new LineBorder(Color.black, 2));
		cover.setBackground(new Color(153,204,255));
		second.setBackground(new Color(153,204,255));
		back1.addActionListener(emergencyService);
		back1.setText("Back");
		back1.setBackground(new Color(51,51,255));
		back1.setForeground(Color.orange);
		back1.setFont(new Font("Centaur", Font.BOLD, 32));
		back1.setPreferredSize(new Dimension(100,50));
		back1.setBorderPainted(false);
		cover.add(photo6,BorderLayout.NORTH);
		cover.add(second,BorderLayout.CENTER);
		cover.add(first,BorderLayout.SOUTH);
		ImageIcon gifImage = new ImageIcon("giphy1.gif");
		save = new JLabel(gifImage);
		
		
		
		
		
		ImageIcon imga = new ImageIcon("spongee.png");
		Image za = imga.getImage();
		Image newimga = za.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		imga = new ImageIcon(newimga);
		label1.setIcon(imga);
		
		ImageIcon imgb = new ImageIcon("patrick.png");
		Image zb = imgb.getImage();
		Image newimgb = zb.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		imgb = new ImageIcon(newimgb);
		label2.setIcon(imgb);
		
	
		ImageIcon imgc = new ImageIcon("squid.png");
		Image zc = imgc.getImage();
		Image newimgc = zc.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		imgc = new ImageIcon(newimgc);
		label3.setIcon(imgc);
		
		
		
		chooseplayer.setForeground(Color.orange);
		chooseplayer.setFont(new Font("Centaur", Font.BOLD, 52));
		
		spongebob.setForeground(Color.BLACK);
		spongebob.setFont(new Font("Centaur", Font.BOLD, 50));
		spongebob.addActionListener(emergencyService);
		
		patrick.setForeground(Color.BLACK);
		patrick.setFont(new Font("Centaur", Font.BOLD, 50));
		patrick.addActionListener(emergencyService);
		
		squidward.setForeground(Color.BLACK);
		squidward.setFont(new Font("Centaur", Font.BOLD, 50));
		squidward.addActionListener(emergencyService);
		
		choose.setLayout(new GridLayout(1,2));;
		choice1.setLayout(new GridLayout(4,1));
		choice1.add(chooseplayer);
		choice1.add(spongebob);
		choice1.add(patrick);
		choice1.add(squidward);
		choice2.setLayout(new GridLayout(3,1));	
		x.setForeground(new Color(51,51,255));
		x.setFont(new Font("Centaur", Font.BOLD, 32));
		x.setBackground(new Color(51,51,255));
		choice2.add(x);
		choice2.add(save);
		choice2.add(back1);
		
		spongebob.setBackground(new Color(51,51,255));
		spongebob.setBorderPainted(false);
		spongebob.add(label1);
		
		patrick.setBackground(new Color(51,51,255));
		patrick.setBorderPainted(false);
		patrick.add(label2);
		
		squidward.setBackground(new Color(51,51,255));
		squidward.setBorderPainted(false);
		squidward.add(label3);
		
		choice1.setBackground(new Color(51,51,255));
		choice2.setBackground(new Color(51,51,255));
		choose.add(choice1);
		choose.add(choice2);
	
	thestory.setText("The game consists of a 10x10 grid in which the simulation occurs,"
			+ "\nyou are required to save Bikini Bottom from Plankton's nasty plan"
			+"\nto destroy the whole city, You have 5 Emergency Units of which you can use to prevent"
			+ "\nthe death of your citzen (your player) and the destruction of the buildings"
			+"\nThe Emergency Units are: "+
			"\nAmbulances to save injured citizens"+
			"\nDisease Control Units to save  infected citizens"
			+"\nFire Trucks to save buildings on fire"+
			"\nGas Control Units to save building with gas leaks"
			+"\nEvacuators to save the occupants that are in a building that is about to collapse"
			+"\nThe information of all the available units will be shown to you on the units panel "
			+ "\nin the left of the screen when you click on the unit you want to view its information"
			+"\nThe information of the buildings and citizens will be shown to you in the building/citizen"
			+ "\ninformation panel in the right of the screen when you click on the building or citizen you"
			+ "\nwant to view its information.Everytime a disaster strikes or a citizen dies "
			+ "\nthe log in the top right corner will be updated "
			+"\nIn order to send a Unit to a building or citizen in danger, click on the icon first and then on the unit"
			+"\n                     Good Luck and save Bikini Bottom!                ");
	
		thestory.setEditable(false);
		thestory.setFont(new Font("Centaur", Font.BOLD, 30));
		thestory.setForeground(Color.black);
		thestory.setBackground(new Color(153,204,255));
		panel.setBackground(new Color(153,204,255));
	
		
		panel.add(thestory);
		panel.add(back2);
		
		
		
		
		setVisible(true);
		revalidate();	
	}
	
	public JButton getBack2() {
		return back2;
	}
	
	public JScrollPane getScroll4() {
		return scroll;
	}

	public JTextArea getThestory() {
		return thestory;
	}

	public JPanel getS() {
		return panel;
	}

	public JButton getBack() {
		return back1;
	}

	public JLabel getPhoto5() {
		return photo5;
	}

	public JLabel getSave() {
		return save;
	}

	public JPanel getChoose1() {
		return choice1;
	}

	public JPanel getChoose2() {
		return choice2;
	}

	public JLabel getChooseplayer() {
		return chooseplayer;
	}

	public JButton getSpongebob() {
		return spongebob;
	}

	public JButton getPatrick() {
		return patrick;
	}

	public JButton getSquidward() {
		return squidward;
	}

	public JLabel getL1() {
		return label1;
	}

	public JLabel getL2() {
		return label2;
	}

	public JLabel getL3() {
		return label3;
	}

	public JPanel getChoose() {
		return choose;
	}

	public JLabel getPhoto1() {
		return photo1;
	}

	public JLabel getPhoto2() {
		return photo2;
	}

	public JLabel getPhoto3() {
		return photo3;
	}

	public JLabel getPhoto4() {
		return photo4;
	}

	public JPanel getFirst() {
		return first;
	}

	public JPanel getSecond() {
		return second;
	}

	public ActionListener getEmergencyService() {
		return emergencyService;
	}

	public JPanel getCover() {
		return cover;
	}

	public JLabel getPhoto() {
		return photo6;
	}
	
	public JButton getStart() {
		return start;
	}

	public JButton getStory() {
		return story;
	}

	public JButton getExit() {
		return exit;
	}
}
