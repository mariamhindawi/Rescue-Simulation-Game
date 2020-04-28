package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import controller.CommandCenter;
import model.disasters.Fire;
import model.units.Unit;

public class Squidward extends JFrame implements MyWindow {
	private JPanel UnitsPanel;
	private JPanel RescuePanel;
	private JPanel InfoPanel;
	private JPanel r1;
	private JLabel currentcycle;
	private JLabel noofcasualties;
	private JButton nextcycle;
	private JPanel Ambulance;
	private JPanel DiseaseControlUnit;
	private JPanel FireTruck;
	private JPanel GasControlUnit;
	private JPanel Evacuator;

	private JTextArea Gameplay;
	private JTextArea information;
	private JButton[][] arr;
	private JTextArea UnitsPanelInfo;
	private JPanel UnitsPanel2;
	private JLabel Background;
	private JDialog GameOver;
	private JLabel gameo2;
	private JButton close;
	private JButton playagain;
	private ArrayList<JButton> units;
	private JScrollPane scroll4;
	private JPanel hintpanel;
	private JPanel hintpanel2;
	private JPanel hintpanel3;
	private JButton hint;

	
	private JTextArea hinttext;
	

	private ActionListener emergencyService;

	public Squidward(ActionListener emergencyService) {

		super();
		this.emergencyService = emergencyService;
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("SAVE SQUIDWARD");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		hint=new JButton();
		hinttext=new JTextArea();
		hintpanel=new  JPanel();
		hintpanel2=new JPanel();
		hintpanel3=new JPanel();
		
		
		
		GameOver = new JDialog(this);
		GameOver.setUndecorated(true);

		getGameOver().setResizable(false);
		JPanel gameoverpanel = new JPanel();
		close=new JButton("Close");
		
		close.setBackground(new Color(153,204,255));
		close.setOpaque(true);
		close.setBorderPainted(false);
		close.setFont(new Font("Centaur", Font.BOLD, 22));
		close.setForeground(Color.black);
		
		playagain=new JButton("play again");
		
		playagain.setBackground(new Color(153,204,255));
		playagain.setOpaque(true);
		playagain.setBorderPainted(false);
		playagain.setFont(new Font("Centaur", Font.BOLD, 22));
		playagain.setForeground(Color.black);
		
		gameoverpanel.setLayout(new GridLayout(1,2));
		gameoverpanel.add(playagain);
		
		gameoverpanel.add(close);
		
		
				
		GameOver.setSize(720, 400);
		//JLabel gameo = new JLabel("HAHA Better Luck Next Time");
		//gameo.setForeground(Color.orange);
		//gameo.setBackground(Color.black);
		//gameo.setOpaque(true);
		gameo2 =new JLabel();
		
		gameo2.setBackground (new Color(153,204,255));
		gameo2.setOpaque(true);
		gameo2.setFont(new Font("Centaur", Font.BOLD, 24));
		gameo2.setForeground(Color.black);
		
		JLabel gameo3=new JLabel();
		
		ImageIcon img7 = new ImageIcon("squidwardcrying.png");
		Image z7 = img7.getImage();
		Image newimg7 = z7.getScaledInstance(720, 300, Image.SCALE_SMOOTH);
		img7 = new ImageIcon(newimg7);
		gameo3.setIcon(img7);
		GameOver.add(gameo3,BorderLayout.NORTH);
		GameOver.add(gameo2, BorderLayout.CENTER);
		GameOver.add(gameoverpanel,BorderLayout.SOUTH);
		
		
		playagain.addActionListener(emergencyService);
		close.addActionListener(emergencyService);
		GameOver.setLocation(300, 200);
		GameOver.setModal(true);
		
		UnitsPanel2 = new JPanel();
		UnitsPanelInfo = new JTextArea();
		r1 = new JPanel();
		UnitsPanel = new JPanel(new GridLayout(5, 1));
		RescuePanel = new JPanel(new GridLayout(10, 10));

		InfoPanel = new JPanel(new GridLayout(2, 1));
		Gameplay = new JTextArea();
		Gameplay.setBorder(new LineBorder(Color.black, 2));
		Gameplay.setEditable(false);

		information = new JTextArea();
		information.setEditable(false);
		information.setBorder(new LineBorder(Color.black, 2));
		currentcycle = new JLabel("Current Cycle:    ", JLabel.CENTER);
		currentcycle.setBorder(new LineBorder(Color.black, 2));
		noofcasualties = new JLabel("No. of casualies:    ", JLabel.CENTER);
		noofcasualties.setBorder(new LineBorder(Color.black, 2));
		nextcycle = new JButton("Next Cycle");
		nextcycle.setBorder(new LineBorder(Color.black, 2));
		nextcycle.addActionListener(emergencyService);
		Ambulance = new JPanel(new FlowLayout());
		Ambulance.setBackground(Color.white);
		
		DiseaseControlUnit = new JPanel(new FlowLayout());
		DiseaseControlUnit.setBackground(Color.white);
		FireTruck = new JPanel(new FlowLayout());
		FireTruck.setBackground(Color.white);
		GasControlUnit = new JPanel(new FlowLayout());
		GasControlUnit.setBackground(Color.white);
		
		Evacuator = new JPanel(new FlowLayout());
		Evacuator.setBackground(Color.white);
		UnitsPanel.add(Ambulance);
		UnitsPanel.add(DiseaseControlUnit);
		UnitsPanel.add(Evacuator);
		UnitsPanel.add(FireTruck);
		UnitsPanel.add(GasControlUnit);
		
		units=new ArrayList<JButton>();
		
		
		JScrollPane scroll4 = new JScrollPane(UnitsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scroll = new JScrollPane(information, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scroll2 = new JScrollPane(Gameplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JScrollPane scroll3 = new JScrollPane(UnitsPanelInfo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		

		currentcycle.setBackground(new Color(204,229,255));
		currentcycle.setOpaque(true);
	
		nextcycle.setBackground(new Color(255,153,51));
		nextcycle.setOpaque(true);
		
		noofcasualties.setBackground(new Color(204,229,255));
		noofcasualties.setOpaque(true);
		
		//UnitsPanel.setPreferredSize(new Dimension(300,400));
		UnitsPanel.setBackground(new Color(255,153,51));
		UnitsPanel.setOpaque(true);
		UnitsPanel.setLayout(new GridLayout(5, 1));
		UnitsPanel.setBorder(new LineBorder(Color.black, 2));

		InfoPanel.setBackground(new Color(255,153,51));
		InfoPanel.setOpaque(true);
		InfoPanel.setLayout(new GridLayout(2,1));
		InfoPanel.setPreferredSize(new Dimension(350, 200));
		InfoPanel.setBorder(new LineBorder(Color.black, 2));

		r1.setLayout(new GridLayout(1, 3));
		r1.add(currentcycle);
		r1.add(nextcycle);
		r1.add(noofcasualties);
		
		
		for(int i=0; i<( (CommandCenter) emergencyService).getEmergencyUnits().size();i++) {
			Unit u = ((CommandCenter) emergencyService).getEmergencyUnits().get(i);
			
			if(u instanceof model.units.Ambulance) {
				JButton amb = new JButton();
				amb.setBorderPainted(false);
				amb.setBackground(Color.white);
				amb.setOpaque(true);
				ImageIcon img6 = new ImageIcon("ambulance.png");
				Image z6 = img6.getImage();
				Image newimg6 = z6.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
				img6 = new ImageIcon(newimg6);
				amb.setIcon(img6);
				
				amb.addActionListener(emergencyService);
				Ambulance.add(amb);
				units.add(amb);
				
			}
			if(u instanceof model.units.DiseaseControlUnit) {
				JButton dis = new JButton();
				dis.setBorderPainted(false);
				dis.setBackground(Color.white);
				dis.setOpaque(true);
				ImageIcon img6 = new ImageIcon("disease2.png");
				Image z6 = img6.getImage();
				Image newimg6 = z6.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
				img6 = new ImageIcon(newimg6);
				dis.setIcon(img6);
				dis.addActionListener(emergencyService);
				DiseaseControlUnit.add(dis);
				units.add(dis);
				
			}
			if(u instanceof model.units.FireTruck) {
				JButton fire = new JButton();
				fire.setBorderPainted(false);
				fire.setBackground(Color.white);
				fire.setOpaque(true);
				ImageIcon img6 = new ImageIcon("fire.png");
				Image z6 = img6.getImage();
				Image newimg6 = z6.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
				img6 = new ImageIcon(newimg6);
				fire.setIcon(img6);
				fire.addActionListener(emergencyService);
				FireTruck.add(fire);
				units.add(fire);
				
			}
			if(u instanceof model.units.GasControlUnit) {
				JButton gas = new JButton();
				gas.setBorderPainted(false);
				gas.setBackground(Color.white);
				gas.setOpaque(true);
				ImageIcon img6 = new ImageIcon("gas.png");
				Image z6 = img6.getImage();
				Image newimg6 = z6.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
				img6 = new ImageIcon(newimg6);
				gas.setIcon(img6);
				gas.addActionListener(emergencyService);
				GasControlUnit.add(gas);
				units.add(gas);
				
			}
			if(u instanceof model.units.Evacuator) {
				JButton evac = new JButton();
				evac.setBorderPainted(false);
				evac.setBackground(Color.white);
				evac.setOpaque(true);
				ImageIcon img6 = new ImageIcon("evacuator.png");
				Image z6 = img6.getImage();
				Image newimg6 = z6.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
				img6 = new ImageIcon(newimg6);
				evac.setIcon(img6);
				evac.addActionListener(emergencyService);
				Evacuator.add(evac);
				units.add(evac);
				
			}
		}
		
		
		

		InfoPanel.add(scroll, BorderLayout.SOUTH);
		InfoPanel.add(scroll2, BorderLayout.NORTH);

		Gameplay.setBackground(new Color(255,153,51));
		Gameplay.setFont(new Font("Centaur", Font.ITALIC, 22));
		
		information.setBackground(new Color(255,153,51));
		information.setFont(new Font("Centaur", Font.ITALIC, 22));
		information.setBorder(new LineBorder(Color.black, 2));
	

		UnitsPanelInfo.setBackground(new Color(255,153,51));
		UnitsPanelInfo.setBorder(new LineBorder(Color.black, 2));
		UnitsPanelInfo.setFont(new Font("Centaur", Font.ITALIC, 22));
		UnitsPanelInfo.setForeground(Color.black);
		UnitsPanel2.setPreferredSize(new Dimension(300,100));
		UnitsPanel2.setLayout(new GridLayout(3, 1));
		UnitsPanel2.add(scroll4);
		UnitsPanel2.add(scroll3);
		UnitsPanel2.setBorder(new LineBorder(Color.black, 2));

		UnitsPanel.setBorder(BorderFactory.createTitledBorder("Available Units"));
		UnitsPanelInfo.setBorder(BorderFactory.createTitledBorder("Unit's Information"));
		UnitsPanelInfo.setEditable(false);
		information.setBorder(BorderFactory.createTitledBorder("Disaster and Death log"));
		Gameplay.setBorder(BorderFactory.createTitledBorder("Building/Citizen Information"));

		
		setLayout(new BorderLayout(2, 3));
		add(BorderLayout.NORTH, r1);
		add(BorderLayout.CENTER, RescuePanel);
		add(BorderLayout.WEST, UnitsPanel2);
		add(BorderLayout.EAST, InfoPanel);

		arr = new JButton[10][10];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				JButton a = new JButton();
				a.setBackground(new Color(204,229,255));
				a.setOpaque(true);
				a.setBorderPainted(false);
				a.addActionListener(emergencyService);
				RescuePanel.add(a);
				arr[j][i] = a;

			}
		}
		for(int i=0;i<arr.length;i++)
		{
			for(int k=0;k<arr[i].length;k++) {
			for(int j=0;j<9;j++)
			{
				if(j%2==0)
				{
					if(i%2==0 &&k%2==1)
					{
						arr[k][i].setBackground(Color.white);
						arr[k][i].setOpaque(true);
					}
				}
				else
				{
					if(i%2==1 &&k%2==0)
					{
						arr[k][i].setBackground(Color.white);
						arr[k][i].setOpaque(true);
					}
				}
			}
			}
		}
		
		ImageIcon img9 = new ImageIcon("krusty-removebg.png");
		Image z9 = img9.getImage();
		Image newimg9 = z9.getScaledInstance(120, 90, Image.SCALE_SMOOTH);
		img9 = new ImageIcon(newimg9);
		arr[0][0].setIcon(img9);
		
		hint.setText("HINT");
		hint.setForeground(Color.black);
		hint.addActionListener(emergencyService);
		hint.setBackground(new Color(255,153,51));
	hinttext.setEditable(false);
	
hintpanel2.add(hint);
hintpanel3.add(hinttext);

			hintpanel.add(hintpanel2,BorderLayout.NORTH);
			hintpanel.add(hintpanel3,BorderLayout.SOUTH);
			hintpanel.setBackground(Color.white);
			hintpanel2.setBackground(Color.white);
			hinttext.setBackground(Color.white);
			
			hintpanel3.setBackground(Color.white);
		UnitsPanel2.add(hintpanel);
	
		
		validate();
		setVisible(true);
	}

	public JPanel getUnitsPanel() {
		return UnitsPanel;
	}

	public JPanel getRescuePanel() {
		return RescuePanel;
	}

	public JPanel getInfoPanel() {
		return InfoPanel;
	}

	public JPanel getR1() {
		return r1;
	}

	public JLabel getCurrentcycle() {
		return currentcycle;
	}

	public JLabel getNoofcasualties() {
		return noofcasualties;
	}

	public JPanel getAmbulance() {
		return Ambulance;
	}

	public JPanel getDiseaseControlUnit() {
		return DiseaseControlUnit;
	}

	public JPanel getFireTruck() {
		return FireTruck;
	}

	public JPanel getGasControlUnit() {
		return GasControlUnit;
	}

	public JPanel getEvacuator() {
		return Evacuator;
	}

	public JTextArea getInformation() {
		return information;
	}

	public JPanel getUnitsPanel2() {
		return UnitsPanel2;
	}


	public JScrollPane getScroll4() {
		return scroll4;
	}

	public JPanel getHintpanel() {
		return hintpanel;
	}

	public JPanel getHintpanel2() {
		return hintpanel2;
	}

	public JPanel getHintpanel3() {
		return hintpanel3;
	}

	public JButton getHint() {
		return hint;
	}

	public JTextArea getHinttext() {
		return hinttext;
	}

	public ActionListener getEmergencyService() {
		return emergencyService;
	}

	public ArrayList<JButton> getUnits() {
		return units;
	}

	public JTextArea getGameplay() {
		return Gameplay;
	}

	public void setGameplay(JTextArea gameplay) {
		Gameplay = gameplay;
	}

	public JTextArea getUnitsPanelInfo() {
		return UnitsPanelInfo;
	}

	public JButton getClose() {
		return close;
	}

	public JButton getPlayagain() {
		return playagain;
	}

	public JDialog getGameOver() {
		return GameOver;
	}

	public JLabel getGameo2() {
		return gameo2;
	}

	public void setGameo2(JLabel gameo2) {
		this.gameo2 = gameo2;
	}

	public JButton[][] getArr() {
		return arr;
	}

	public JButton getNextcycle() {
		return nextcycle;
	}

	

	public void updatec(int x) {
		noofcasualties.setText("No. of casualties: " + x);

	}

	public void updatecycle(int x) {
		currentcycle.setText("Current Cycle: " + x);

	}

	public void updateDInfo(String x, String y) {
		information.setText(x + "\n \n" + y);
		information.setForeground(Color.black);
	}
	public void updateBCinfo(String a)
	{
		Gameplay.setText(a);
		Gameplay.setForeground(Color.BLACK);
	}
	public void updateunitinfo(String g)
	{
		UnitsPanelInfo.setText(g);
	}
	public void updatehint(String g) {
		hinttext.setText(g);
	}

}
