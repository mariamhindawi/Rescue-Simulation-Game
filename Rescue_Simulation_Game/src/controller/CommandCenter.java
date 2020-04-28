package controller;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.BoldAction;


import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.MyWindow;
import view.Patrick;
import view.Spongebob;
import view.Squidward;
import view.coverpage;

//represents the controller responsible for the communication between the model and the view

public class CommandCenter implements SOSListener, ActionListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<ResidentialBuilding> visibleBuildings1;
	private ArrayList<Citizen> visibleCitizens1;
	private MyWindow window;
	private boolean uclick = false;
	private boolean bcclick = false;
	private int x;
	private int y;
	private int see;
	private ArrayList<Boolean>blist;
	private ArrayList<Rescuable> check;
	private ArrayList<Integer> compare;
	private int min;
	private int index;
	private String sa;
	

	private ArrayList<Citizen> arrived;

	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		engine = new Simulator(this);

		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		visibleBuildings1 = new ArrayList<ResidentialBuilding>();
		visibleCitizens1 = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		window = new coverpage(this);
		engine.setWindow(window);

		arrived = new ArrayList<Citizen>();
		check = new ArrayList<Rescuable>();
		compare=new ArrayList<Integer>();
		
	}
	
	public void updates()
	{
		for(int i=0;i<visibleBuildings.size();i++)
		{
	visibleBuildings1.add(visibleBuildings.get(i));
		}
		for(int i=0;i<visibleCitizens.size();i++)
		{
	visibleCitizens1.add(visibleCitizens.get(i));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public void updatecheckandcompare()
	{
		//updates();
		if (check.size()>0)
		{
			check.clear();
			compare.clear();
		}
		
		for(int i=0;i<visibleBuildings1.size();i++)
		{
			if(visibleBuildings1.get(i).getStructuralIntegrity()>0) {
			check.add(visibleBuildings1.get(i));
			compare.add(visibleBuildings1.get(i).getStructuralIntegrity());
		}
		}
		for(int i=0;i<visibleCitizens1.size();i++)
		{
			if(visibleCitizens1.get(i).getHp()>0)
			{
			check.add(visibleCitizens1.get(i));
			compare.add(visibleCitizens1.get(i).getHp());
		}
		}
		
	}
	public void worth(int mm)
	{
		if(mm<check.size())
		{
		
	
			if(check.get(mm)instanceof Citizen)
			{
				Citizen c=(Citizen)check.get(mm);
				if (c.getDisaster()!=null&&c.getDisaster()instanceof Injury)
				{
					
					for(int j=0;j<emergencyUnits.size();j++)
					{boolean x;
						Unit u=(Unit)emergencyUnits.get(j);
						if(u instanceof Ambulance)
						{
							if(u.getState()==UnitState.IDLE)
							{
								x=true;
							}
							else {
								x=false;
							}
						}
						
						
						if(x=true) {
							int cycles=u.calculate();
							int myhp=0;
							int bl=c.getBloodLoss();
							for(int a=0;a<cycles;a++)
							{
								if (bl>0&&bl<30)
								{
									bl=bl+10;
									myhp=myhp+5;
								}
								if (bl>=30&&bl<70)
								{
									bl=bl+10;
									myhp=myhp+10;
								}
								if (bl>70)
								{
									bl=bl+10;
									myhp=myhp+15;
								}
							}
							if((c.getHp()-myhp)>0)
							{
								sa="Send an Ambulance to Citizen at location: "+c.getLocation().getX()+","+c.getLocation().getY();
								
							}
							else {
								if(check.size()>=mm) {
								check.remove(mm);
								compare.remove(mm);
								
								worth(minimum());
								}
							}
						}
						else {
							if(check.size()>=mm) {
							check.remove(mm);
							compare.remove(mm);
							
							worth(minimum());
							}
						}
					}
				}
				if (c.getDisaster()!=null&&c.getDisaster()instanceof Infection)
				{
					
					for(int j=0;j<emergencyUnits.size();j++)
					{boolean x;
						Unit u=(Unit)emergencyUnits.get(j);
						if(u instanceof DiseaseControlUnit)
						{
					if(u.getState()==UnitState.IDLE)
					{
						x=true;
					}
					else {
						x=false;
					}
						}
						
						if(x=true) {
							int cycles=u.calculate();
							int myhp=0;
							int bl=c.getToxicity();
							for(int a=0;a<cycles;a++)
							{
								if (bl>0&&bl<30)
								{
									bl=bl+10;
									myhp=myhp+5;
								}
								if (bl>=30&&bl<70)
								{
									bl=bl+10;
									myhp=myhp+10;
								}
								if (bl>70)
								{
									bl=bl+10;
									myhp=myhp+15;
								}
							}
							if((c.getHp()-myhp)>0)
							{
								sa="Send a Disease Control Unit to Citizen at location: "+c.getLocation().getX()+","+c.getLocation().getY();
							}
							else {
								if(check.size()>=mm) {
								check.remove(mm);
								compare.remove(mm);
								
								worth(minimum());
								
								}
								
							}
						}
						else {
							if(check.size()>=mm) {
							check.remove(mm);
							compare.remove(mm);
							
							worth(minimum());
							}
						}
					}
				}
				
			}
			else if(check.get(mm)instanceof ResidentialBuilding)
			{
				ResidentialBuilding b=(ResidentialBuilding)check.get(mm);
				
				
				
				if (b.getDisaster()!=null&&b.getDisaster()instanceof Fire)
				{
					
					for(int j=0;j<emergencyUnits.size();j++)
					{boolean x;
						Unit u=(Unit)emergencyUnits.get(j);
						if(u instanceof FireTruck)

						{
							if(u.getState()==UnitState.IDLE)
							{
								x=true;
							}
							else
							{
								x=false;
							}
						}
						
						if(x=true) {
							int cycles=u.calculate();
							int mysi=0;
							int bl=b.getFireDamage();
							for(int a=0;a<cycles;a++)
							{
								if (bl>0&&bl<30)
								{
									bl=bl+10;
									mysi=mysi+3;
								}
								if (bl>=30&&bl<70)
								{
									bl=bl+10;
									mysi=mysi+5;
								}
								if (bl>70)
								{
									bl=bl+10;
									mysi=mysi+7;
								}
							}
							if((b.getStructuralIntegrity()-mysi)>0)
							{
								sa="Send a Fire Truck to Building at location: "+b.getLocation().getX()+","+b.getLocation().getY();
							}
							else {
								if(check.size()>=mm) {
								check.remove(mm);
								compare.remove(mm);
								
								worth(minimum());
								}
							}
							
						}
						else {
							if(check.size()>=mm) {
							check.remove(mm);
							compare.remove(mm);
							
							worth(minimum());
							}
						}
					}
				}
				
				
				if (b.getDisaster()!=null&&b.getDisaster()instanceof GasLeak)
				{
					
					for(int j=0;j<emergencyUnits.size();j++)
					{boolean x;
						Unit u=(Unit)emergencyUnits.get(j);
						if(u instanceof GasControlUnit)
							
						{
							if(u.getState()==UnitState.IDLE)
							{
								x=true;
								
							}
							else {
								x=false;
							}
						}
						
						if(x=true) {
							int cycles=u.calculate();
							int g=b.getGasLevel();
							int total=(cycles*15)+g;
							if(g<100)
							{
								sa="Send a Gas Control Unit to Building at location: "+b.getLocation().getX()+","+b.getLocation().getY();
							}
							else {
								
								if(check.size()>=mm) {
								
								check.remove(mm);
								compare.remove(mm);
								
								worth(minimum());
								
							}
							}
							
							
						}
						else {
							if(check.size()>=mm) {
							check.remove(mm);
							compare.remove(mm);
							
							worth(minimum());
							
						}
						}
					}
				}
				if (b.getDisaster()!=null&&b.getDisaster()instanceof Collapse)
				{
					
					for(int j=0;j<emergencyUnits.size();j++)
					{boolean x;
						Unit u=(Unit)emergencyUnits.get(j);
						if(u instanceof Evacuator)
						{
							if(u.getState()==UnitState.IDLE)
							{
								x=true;
							}
							else { x=false;

							}
						}
						
						if(x=true) {
							int cycles=u.calculate();
							int g=b.getFoundationDamage();
							int total=(cycles*15)+g;
							if(g<100)
							{
								sa="Send an Evacuator to Building at location: "+b.getLocation().getX()+","+b.getLocation().getY();	
							}
							else {
								
								if(check.size()>=mm) {
								check.remove(mm);
								compare.remove(mm);
								
								worth(minimum());
								}
							}
							
							
						}
						else {
							if(check.size()>=mm) {
							check.remove(mm);
							compare.remove(mm);
							
							worth(minimum());
							
						}
						}
					}
				}
				
				
			}
		
	}
	}
	
	
	
	
	public void last()
	{
		updates();
		updatecheckandcompare();
		worth(minimum());
		
	}
	
	public int minimum()
	{int hh=100;
	int o=0;
		for(int i=0;i<compare.size();i++)
		{
			if(compare.get(i)<hh)
			{
				hh=compare.get(i);
				o=i;
			}
		}
		return o;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*public void hint() {
		
		for (int i = 0; i < visibleBuildings.size(); i++) {
			ResidentialBuilding b = (ResidentialBuilding) visibleBuildings.get(i);
			
			if ( b.getStructuralIntegrity() > 0 && b.getStructuralIntegrity() < 100) {
				check.add(b);
				compare.add(b.getStructuralIntegrity());
			}

		}
		for (int i = 0; i < visibleCitizens.size(); i++) {
			Citizen c = (Citizen) visibleCitizens.get(i);
			if (c.getHp() > 0 && c.getHp() < 100) {
				check.add(c);
				compare.add(c.getHp());
			}
		}

	}

	public int getmin()  {
		int index=0;
		int min = compare.get(0);
		for (int i = 0; i < compare.size(); i++) {
			if (compare.get(i) < min) {
				min = compare.get(i);
				index = i;
			}

		}

		return index;
	}

	public boolean checckifidle( ){
	if(index<check.size()) {
		if ( check.get(index) instanceof Citizen){
			Citizen cit=(Citizen)check.get(index);
			
			if(cit.getDisaster() instanceof Injury)
			{
			
				for(int i=0;i<emergencyUnits.size();i++) {
				
					Unit u=(Unit)emergencyUnits.get(i);
				
					if(u instanceof Ambulance && u.getState()==UnitState.IDLE) {
						int t=cit.getBloodLoss();
						int c=compare.get(index);
						int fin=u.calculate();
						for(int l=0;l<fin;l++) {
							if(t>0&&t<30) {
								c=c-5;
								t=t+10;}
							else if(t>=30&&t<70) {
								t=t+10;
								c=c-10;
							}
							else if(t>=70) {
								t=t+10;
								c=c-15;
							}
						}
							
						if(c>0) {
							return true;
						}
						}
						}	
						}
			
			else if(cit.getDisaster() instanceof Infection)
			{
			
				for(int i=0;i<emergencyUnits.size();i++) {
				
					Unit u=(Unit)emergencyUnits.get(i);
				
					if(u instanceof DiseaseControlUnit && u.getState()==UnitState.IDLE) {
						int t=cit.getToxicity();
						int c=compare.get(index);
						int fin=u.calculate();
						for(int l=0;l<fin;l++) {
							if(t>0&&t<30) {
								t=t+15;
								c=c-5;
								}
							else if(t>=30&&t<70) {
								t=t+15;
								c=c-10;
							}
							else if(t>=70) {
								t=t+15;
								c=c-15;
							}
						}
							
						if(c>0) {
							
							return true;
						}
						}
						}	
						}
		}	
							
							
			
			
		else if (check.get(index) instanceof ResidentialBuilding) {
			 ResidentialBuilding res=(ResidentialBuilding)check.get(index);
			
		 if(res.getDisaster() instanceof Fire)
			{
			
				for(int i=0;i<emergencyUnits.size();i++)
				{
					Unit u=(Unit)emergencyUnits.get(i);
				
					if(u instanceof FireTruck && u.getState()==UnitState.IDLE)
					{
						int c=compare.get(index);
						int t=res.getFireDamage();
						int fin=u.calculate();
						for(int l=0;l<fin;l++)
						{
							if(t>0&&t<30) {
								t=t+10;
								c=c-3;
							}
							else if(t>=30&&t<70) {
								t=t+10;
								c=c-5;}
							else if(t>=70) {
								t=t+10;
								c=c-7;}
						}
						if(c>0) {
							
						return true;
						}
						
					}
				}
			}
			
			
			
			
			
		else	if(res.getDisaster() instanceof GasLeak)
			{
				for(int i=0;i<emergencyUnits.size();i++)
				{Unit u=(Unit)emergencyUnits.get(i);
				
					if(u instanceof GasControlUnit && u.getState()==UnitState.IDLE)
					{
						ResidentialBuilding b=(ResidentialBuilding)check.get(index);
						int y=b.getGasLevel();
						int c=(u.calculate())*15;
						int total=y+c;
						if(total<100) {
				
							return true;
						}
					}
				}
			}
			
		else if(res.getDisaster() instanceof Collapse)
			{ 
				for(int i=0;i<emergencyUnits.size();i++)
				{Unit u=(Unit)emergencyUnits.get(i);
				
					if(u instanceof Evacuator && u.getState()==UnitState.IDLE)
					{
						ResidentialBuilding b=(ResidentialBuilding)check.get(index);
						int y=b.getFoundationDamage();
						int c=(u.calculate())*10;
						int total=y+c;
						if(total<100) {
							
						return true;
						}
					}
				}
			}
			
		}

	
		
	
	}
	return false;
	}
	
	
	
	public String recommend(int b) {

		String s = "";
		if(checckifidle()) {
		if (check.get(b) instanceof Citizen) {
			if (check.get(b).getDisaster() instanceof Injury) {
				s = s + "Send Ambulance to Citizen at location:  " + check.get(b).getLocation().getX() + ","
						+ check.get(b).getLocation().getY();
				return s;
			} else if (check.get(b).getDisaster() instanceof Infection) {
				s = s + "Send Disease Control Unit to Citizen at location:  " + check.get(b).getLocation().getX() + ","
						+ check.get(b).getLocation().getY();
				return s;
			}
		} else if (check.get(b) instanceof ResidentialBuilding) {
			if (check.get(b).getDisaster() instanceof Fire) {
				s = s + "Send Fire Truck to building at location:  " + check.get(b).getLocation().getX() + ","
						+ check.get(b).getLocation().getY();
				return s;
			}
			if (check.get(b).getDisaster() instanceof GasLeak) {
				s = s + "Send Gas Control Unit to building at location:  " + check.get(b).getLocation().getX() + ","
						+ check.get(b).getLocation().getY();
				return s;
			}
			if (check.get(b).getDisaster() instanceof Collapse) {
				s = s + "Send Evacuator to building at location:  " + check.get(b).getLocation().getX() + ","
						+ check.get(b).getLocation().getY();
				return s;
			}
		}
		}
		

		return s;

	}
	
	
	
	
	
	
	
	public void updatecheck(){
		
		for(int i=check.size()-1;i>check.size();i--) {
			check.remove(i);
		}
		
		for(int i=0;i<visibleBuildings.size();i++)
		{
			ResidentialBuilding b=(ResidentialBuilding)visibleBuildings.get(i);
		if(b.getStructuralIntegrity()>0&&b.getStructuralIntegrity()<100) {
			check.add(b);
		}
			
		}
		for(int i=0;i<visibleCitizens.size();i++)
		{
			Citizen b=(Citizen)visibleCitizens.get(i);
			if(b.getHp()>0&&b.getHp()<100) {
				check.add(b);
			}
		}
	
		
	}
	
	
	
	
	
	
	
	
	
	
public void updatecompare(){
	for(int i=compare.size()-1;i>compare.size();i--) {
		compare.remove(i);
	}
	
	
	for(int  i=0;i<check.size();i++) {
		if(check.get(i)instanceof Citizen)
		{
			Citizen cit=(Citizen)check.get(i);
			compare.add(cit.getHp());
			
		}
		else if(check.get(i)instanceof ResidentialBuilding)
		{
			ResidentialBuilding res=(ResidentialBuilding)check.get(i);
			compare.add(res.getStructuralIntegrity());
			
		}	}
	
	}


	
	
	public void minimum() {
		int m=100;
		for(int i=0;i<compare.size();i++)
		{
			if(compare.get(i)<m)
			{
				m=compare.get(i);
			}
			
		}
		min=m;

		
	}
	public void findindex()
	{
		for(int i=0;i<compare.size();i++)
{ if (compare.get(i)==min)
	index=i;
	
}
	}
	
	
	public String last() {
		updatecheck();
		updatecompare();
		minimum();
		findindex();
		recommend(index);
		return (recommend(index));
		
	
	}
	
	
	
	
	
	
	
	*/

	public String checkcitizen() {
		String m = "";
		for (int i = 0; i < arrived.size(); i++) {
			Citizen c = (Citizen) arrived.get(i);
			m = m + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: " + c.getLocation().getX()
					+ "," + c.getLocation().getY() + "\nAge: " + c.getAge() + "\nHP: " + c.getHp() + "\nBlood Loss: "
					+ c.getBloodLoss() + "\nToxicity: " + c.getToxicity() + "\nState: " + c.getState();
		}

		return m;

	}
	
	
	
	
	
	public ArrayList<Citizen> getArrived() {
		return arrived;
	}

	public void setArrived(ArrayList<Citizen> arrived) {
		this.arrived = arrived;
	}

	public void addtogrid() {

		if (window instanceof Spongebob) {
			Spongebob s = (Spongebob) window;
			for (int i = 0; i < visibleBuildings.size(); i++) {

				ResidentialBuilding b = visibleBuildings.get(i);
				JButton x = s.getArr()[b.getLocation().getX()][b.getLocation().getY()];
				if (b.getStructuralIntegrity() == 0) {
					ImageIcon img5 = new ImageIcon("spongecollapse.png");
					Image z5 = img5.getImage();
					Image newimg5 = z5.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
					img5 = new ImageIcon(newimg5);
					x.setIcon(img5);
				} else {
					ImageIcon img5 = new ImageIcon("Spongebob's_House.png");
					Image z5 = img5.getImage();
					Image newimg5 = z5.getScaledInstance(60, 65, Image.SCALE_SMOOTH);
					img5 = new ImageIcon(newimg5);
					x.setIcon(img5);

				}

			}
			for (int i = 0; i < visibleCitizens.size(); i++) {
				Citizen c = visibleCitizens.get(i);
				JButton x = s.getArr()[c.getLocation().getX()][c.getLocation().getY()];
				if (c.getState() == CitizenState.DECEASED) {
					ImageIcon img6 = new ImageIcon("deadspongee.png");
					Image z6 = img6.getImage();
					Image newimg6 = z6.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
					img6 = new ImageIcon(newimg6);
					x.setIcon(img6);

				} else {
					ImageIcon img6 = new ImageIcon("spongee.png");
					Image z6 = img6.getImage();
					Image newimg6 = z6.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
					img6 = new ImageIcon(newimg6);
					x.setIcon(img6);
				}
			}
		}
		if (window instanceof Patrick) {
			Patrick p = (Patrick) window;
			for (int i = 0; i < visibleBuildings.size(); i++) {

				ResidentialBuilding b = visibleBuildings.get(i);
				JButton x = p.getArr()[b.getLocation().getX()][b.getLocation().getY()];
				if (b.getStructuralIntegrity() == 0) {
					ImageIcon img5 = new ImageIcon("patrickcollapse.png");
					Image z5 = img5.getImage();
					Image newimg5 = z5.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
					img5 = new ImageIcon(newimg5);
					x.setIcon(img5);
				} else {
					ImageIcon img5 = new ImageIcon("image (1).png");
					Image z5 = img5.getImage();
					Image newimg5 = z5.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
					img5 = new ImageIcon(newimg5);

					x.setIcon(img5);
				}
			}
			for (int i = 0; i < visibleCitizens.size(); i++) {
				Citizen c = visibleCitizens.get(i);
				JButton x = p.getArr()[c.getLocation().getX()][c.getLocation().getY()];
				if (c.getState() == CitizenState.DECEASED) {
					ImageIcon img6 = new ImageIcon("deadpa.png");
					Image z6 = img6.getImage();
					Image newimg6 = z6.getScaledInstance(80, 60, Image.SCALE_SMOOTH);
					img6 = new ImageIcon(newimg6);
					x.setIcon(img6);

				} else {
					ImageIcon img6 = new ImageIcon("patrick.png");
					Image z6 = img6.getImage();
					Image newimg6 = z6.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
					img6 = new ImageIcon(newimg6);
					x.setIcon(img6);
				}
			}
		}

		if (window instanceof Squidward) {
			Squidward w = (Squidward) window;
			for (int i = 0; i < visibleBuildings.size(); i++) {

				ResidentialBuilding b = visibleBuildings.get(i);
				JButton x = w.getArr()[b.getLocation().getX()][b.getLocation().getY()];
				if (b.getStructuralIntegrity() == 0) {
					ImageIcon img5 = new ImageIcon("squidcollapse.png");
					Image z5 = img5.getImage();
					Image newimg5 = z5.getScaledInstance(130, 70, Image.SCALE_SMOOTH);
					img5 = new ImageIcon(newimg5);
					x.setIcon(img5);
				} else {
					ImageIcon img5 = new ImageIcon("squidwardshome.png");
					Image z5 = img5.getImage();
					Image newimg5 = z5.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
					img5 = new ImageIcon(newimg5);
					x.setIcon(img5);
				}
			}
			for (int i = 0; i < visibleCitizens.size(); i++) {
				Citizen c = visibleCitizens.get(i);
				JButton x = w.getArr()[c.getLocation().getX()][c.getLocation().getY()];
				if (c.getState() == CitizenState.DECEASED) {
					ImageIcon img6 = new ImageIcon("deadsquid.png");
					Image z6 = img6.getImage();
					Image newimg6 = z6.getScaledInstance(120, 70, Image.SCALE_SMOOTH);
					img6 = new ImageIcon(newimg6);
					x.setIcon(img6);

				} else {
					ImageIcon img6 = new ImageIcon("squid.png");
					Image z6 = img6.getImage();
					Image newimg6 = z6.getScaledInstance(120, 60, Image.SCALE_SMOOTH);
					img6 = new ImageIcon(newimg6);
					x.setIcon(img6);
				}
			}
		}

	}

	@Override
	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);

		} else {

			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}

	public Rescuable gettheobject(int a, int b) {
		for (int i = 0; i < visibleBuildings.size(); i++) {
			if (visibleBuildings.get(i).getLocation().getX() == a
					&& visibleBuildings.get(i).getLocation().getY() == b) {
				ResidentialBuilding r = (ResidentialBuilding) visibleBuildings.get(i);

				return r;

			}

		}
		for (int k = 0; k < visibleCitizens.size(); k++) {
			if (visibleCitizens.get(k).getLocation().getX() == a && visibleCitizens.get(k).getLocation().getY() == b) {
				Citizen c = (Citizen) visibleCitizens.get(k);

				return c;
			}
		}

		return null;

	}

	public String find(int x, int y) {
		String s = "";
		for (int i = 0; i < visibleBuildings.size(); i++) {
			if (visibleBuildings.get(i).getLocation().getX() == x
					&& visibleBuildings.get(i).getLocation().getY() == y) {
				ResidentialBuilding r = (ResidentialBuilding) visibleBuildings.get(i);
				String d = "";
				if (r.getDisaster() instanceof Collapse) {
					d = "Collapse";
				} else if (r.getDisaster() instanceof Fire) {
					d = "Fire";
				} else if (r.getDisaster() instanceof GasLeak) {
					d = "Gas Leak";
				} else if (r.getDisaster() instanceof Infection) {
					d = "Infection";
				} else if (r.getDisaster() instanceof Injury) {
					d = "Injury";
				}
				s = s + "Location: " + r.getLocation().getX() + "," + r.getLocation().getY() + "\nStructural Integrity:"
						+ r.getStructuralIntegrity() + "\nFoundation Damage: " + r.getFoundationDamage()
						+ "\nFire Damage: " + r.getFireDamage() + "\nGas Level: " + r.getGasLevel()
						+ "\nNo. of occupants: " + r.getOccupants().size() + "\nDisaster's Type: " + d
						+ "\nOccupants:\n";
				for (int j = 0; j < r.getOccupants().size(); j++) {
					Citizen c = (Citizen) r.getOccupants().get(j);
					s = s + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
							+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge() + "\nHP: "
							+ c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: " + c.getToxicity()
							+ "\nState: " + c.getState();
				}

			}

		}
		for (int k = 0; k < visibleCitizens.size(); k++) {
			if (visibleCitizens.get(k).getLocation().getX() == x && visibleCitizens.get(k).getLocation().getY() == y) {
				for (int j = 0; j < visibleCitizens.size(); j++) {
					Citizen c = (Citizen) visibleCitizens.get(j);
					String d = "";
					if (c.getDisaster() instanceof Infection) {
						d = "Infection";
					} else if (c.getDisaster() instanceof Injury) {
						d = "Injury";
					}
					s = s + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
							+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge() + "\nHP: "
							+ c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: " + c.getToxicity()
							+ "\nState: " + c.getState() + "\nDisaster's Type: " + d;
				}

			}
		}
		return s;
	}

	public static void main(String[] args) throws Exception {
		new CommandCenter();
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton a = (JButton) e.getSource();

		if (window instanceof coverpage) {
			coverpage c = (coverpage) window;

			if (a == c.getBack2()) {
				c.setContentPane(c.getCover());
				c.revalidate();
				c.repaint();
			}
			if (a == c.getStart()) {

				c.setContentPane(c.getChoose());
				c.revalidate();
				c.repaint();

			}
			if (a == c.getStory()) {
				c.setContentPane(c.getS());
				c.revalidate();
				c.repaint();

			}
			if (a == c.getExit()) {
				c.dispose();

			}
			
			if (a == c.getSpongebob()) {
				c.dispose();
				window = new Spongebob(this);
				engine.setWindow(window);
			}
			if (a == c.getPatrick()) {
				c.dispose();
				window = new Patrick(this);
				engine.setWindow(window);
			}
			if (a == c.getSquidward()) {
				c.dispose();
				window = new Squidward(this);
				engine.setWindow(window);
			}
			if (a == c.getBack()) {
				c.setContentPane(c.getCover());
				c.revalidate();
				c.repaint();
			}
		}

		if (window instanceof Spongebob) {
			Spongebob s = (Spongebob) window;

			
			if(a==s.getHint())
			{
					
					last();
					s.updatehint(sa);
					
					
			}
			
			
			for (int i = 0; i < s.getArr().length; i++) {
				for (int j = 0; j < s.getArr()[i].length; j++) {
					if (a == s.getArr()[i][j]) {
						for (int k = 0; k < emergencyUnits.size(); k++) {
							if (emergencyUnits.get(k).getLocation().getX() == i
									&& emergencyUnits.get(k).getLocation().getY() == j) {
								Unit u = emergencyUnits.get(k);
								String g = "";
								if (u instanceof Ambulance) {
									Citizen c = (Citizen) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (c != null) {
										g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: "
												+ c.getLocation().getX() + "," + c.getLocation().getY();
									}
								}
								if (u instanceof DiseaseControlUnit) {
									Citizen c = (Citizen) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (c != null) {
										g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: "
												+ c.getLocation().getX() + "," + c.getLocation().getY();
									}
								}
								if (u instanceof FireTruck) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}

								if (u instanceof GasControlUnit) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Gas control Unit" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}
								if (u instanceof Evacuator) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState()
											+ "\nNo.of Passengers: " + ((Evacuator) u).getPassengers().size()
											+ "\nPassengers: ";
									for (int l = 0; l < ((Evacuator) u).getPassengers().size(); l++) {
										Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
										g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
												+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: "
												+ c.getAge() + "\nHP: " + c.getHp() + "\nBlood Loss: "
												+ c.getBloodLoss() + "\nToxicity: " + c.getToxicity() + "\nState: "
												+ c.getState();
									}
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}
								s.updateunitinfo(g);
							}
						}
					}
				}
			}

			for (int i = 0; i < s.getArr().length; i++) {
				for (int j = 0; j < s.getArr()[i].length; j++) {
					if (a == s.getArr()[i][j]) {
						bcclick = true;
						x = i;
						y = j;
						gettheobject(i, j);
						s.updateBCinfo(find(i, j));
					}
				}
			}

			for (int w = 0; w < s.getUnits().size(); w++) {
				if (a == s.getUnits().get(w)) {
					Unit u = (Unit) emergencyUnits.get(w);

					if (u instanceof Ambulance) {
						Citizen c = (Citizen) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (c != null) {
							g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX() + ","
									+ c.getLocation().getY();
						}

						s.updateunitinfo(g);

						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {

								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send an Ambulance to a Pineapple", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Spongebob with an Ambulance",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof DiseaseControlUnit) {
						Citizen c = (Citizen) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (c != null) {
							g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX() + ","
									+ c.getLocation().getY();
						}

						s.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send a Disease Control Unit to a Pineapple",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Sponebob with a Disease Control Unit",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}
					}
					if (u instanceof FireTruck) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}

						s.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);
								JLabel l = new JLabel("You cannot send a Fire Truck to a Spongebob", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);
							} catch (CannotTreatException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Pineapple with a Fire Truck",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof GasControlUnit) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Gas control Unit" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}

						s.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send a Gas Control Unit to a Spongebob",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);
							} catch (CannotTreatException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Pineapple with a Gas Control Unit",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof Evacuator) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\nNo.of Passengers: "
								+ ((Evacuator) u).getPassengers().size() + "\nPassengers: ";
						for (int j = 0; j < ((Evacuator) u).getPassengers().size(); j++) {
							Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
							g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
									+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge()
									+ "\nHP: " + c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: "
									+ c.getToxicity() + "\nState: " + c.getState();
						}
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}
						s.updateunitinfo(g);

						uclick = true;
						if (bcclick = true) {

							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send an Evacuator to a Spongebob", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(s);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Pineapple with an Evacuator",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}

						}
					}

				}
			}

			if (a == s.getNextcycle()) {
				engine.nextCycle();

			} else if (a == s.getClose()) {
				s.dispose();
			} else if (a == s.getPlayagain()) {
				s.dispose();
				try {
					new CommandCenter();
				} catch (Exception e1) {

				}
			}

			if (a == s.getArr()[0][0]) {

				s.updateBCinfo(checkcitizen());
				// System.out.println("gg");
				String g = "";
				for (int i = 0; i < emergencyUnits.size(); i++) {
					Unit u = (Unit) emergencyUnits.get(i);
					if (emergencyUnits.get(i).getLocation().getX() == 0
							&& emergencyUnits.get(i).getLocation().getY() == 0) {
						if (u instanceof Ambulance) {

							Citizen c = (Citizen) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (c != null) {
								g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX()
										+ "," + c.getLocation().getY() + "\n";
							}

						}
						if (u instanceof DiseaseControlUnit) {
							Citizen c = (Citizen) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (c != null) {
								g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX()
										+ "," + c.getLocation().getY() + "\n";
							}

						}
						if (u instanceof FireTruck) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY() + "\n";
							}

						}
						if (u instanceof GasControlUnit) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Gas Control Unit" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY() + "\n";
							}

						}
						if (u instanceof Evacuator) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							String m = "";
							g = g + "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\nNo.of Passengers: "
									+ ((Evacuator) u).getPassengers().size() + "\nPassengers: ";
							for (int j = 0; j < ((Evacuator) u).getPassengers().size(); j++) {
								Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
								g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
										+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge()
										+ "\nHP: " + c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: "
										+ c.getToxicity() + "\nState: " + c.getState() + "\n";
							}

							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY();
							}

						}
					}

				}
				s.updateunitinfo(g);
			}
		}

		if (window instanceof Patrick) {
			Patrick p = (Patrick) window;

			

				
				if(a==p.getHint())
				{
						
						last();
						p.updatehint(sa);
						
						
				}
				
					
			
			for (int i = 0; i < p.getArr().length; i++) {
				for (int j = 0; j < p.getArr()[i].length; j++) {
					if (a == p.getArr()[i][j]) {
						for (int k = 0; k < emergencyUnits.size(); k++) {
							if (emergencyUnits.get(k).getLocation().getX() == i
									&& emergencyUnits.get(k).getLocation().getY() == j) {
								Unit u = emergencyUnits.get(k);
								String g = "";
								if (u instanceof Ambulance) {
									Citizen c = (Citizen) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (c != null) {
										g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: "
												+ c.getLocation().getX() + "," + c.getLocation().getY();
									}
								}
								if (u instanceof DiseaseControlUnit) {
									Citizen c = (Citizen) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (c != null) {
										g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: "
												+ c.getLocation().getX() + "," + c.getLocation().getY();
									}
								}
								if (u instanceof FireTruck) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}

								if (u instanceof GasControlUnit) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Gas Control Unit" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}
								if (u instanceof Evacuator) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState()
											+ "\nNo.of Passengers: " + ((Evacuator) u).getPassengers().size()
											+ "\nPassengers: ";
									for (int l = 0; l < ((Evacuator) u).getPassengers().size(); l++) {
										Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
										g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
												+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: "
												+ c.getAge() + "\nHP: " + c.getHp() + "\nBlood Loss: "
												+ c.getBloodLoss() + "\nToxicity: " + c.getToxicity() + "\nState: "
												+ c.getState();
									}
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}
								p.updateunitinfo(g);
							}
						}
					}
				}
			}

			for (int i = 0; i < p.getArr().length; i++) {
				for (int j = 0; j < p.getArr()[i].length; j++) {
					if (a == p.getArr()[i][j]) {
						bcclick = true;
						x = i;
						y = j;
						gettheobject(i, j);
						p.updateBCinfo(find(i, j));
					}
				}
			}

			for (int w = 0; w < p.getUnits().size(); w++) {
				if (a == p.getUnits().get(w)) {
					Unit u = (Unit) emergencyUnits.get(w);

					if (u instanceof Ambulance) {
						Citizen c = (Citizen) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (c != null) {
							g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX() + ","
									+ c.getLocation().getY();
						}

						p.updateunitinfo(g);

						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {

								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send an Ambulance to Patrick's house", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Patrick with an Ambulance", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof DiseaseControlUnit) {
						Citizen c = (Citizen) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (c != null) {
							g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX() + ","
									+ c.getLocation().getY();
						}

						p.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send a Disease Control Unit to Patrick's house",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Patrick with a Disease Control Unit",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}
					}
					if (u instanceof FireTruck) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}

						p.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);
								JLabel l = new JLabel("You cannot send a Fire Truck to a Patrick", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);
							} catch (CannotTreatException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Patrick's house with a Fire Truck",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof GasControlUnit) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Gas control Unit" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}

						p.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send a Gas Control Unit to a Patrick", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);
							} catch (CannotTreatException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Patrick's house with a Gas Control Unit",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof Evacuator) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\nNo.of Passengers: "
								+ ((Evacuator) u).getPassengers().size() + "\nPassengers: ";
						for (int j = 0; j < ((Evacuator) u).getPassengers().size(); j++) {
							Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
							g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
									+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge()
									+ "\nHP: " + c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: "
									+ c.getToxicity() + "\nState: " + c.getState();
						}
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}
						p.updateunitinfo(g);

						uclick = true;
						if (bcclick = true) {

							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send an Evacuator to a Patrick", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(p);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Patrick's house with an Evacuator",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}

						}
					}

				}
			}

			if (a == p.getNextcycle()) {
				engine.nextCycle();

			} else if (a == p.getClose()) {
				p.dispose();
			} else if (a == p.getPlayagain()) {
				p.dispose();
				try {
					new CommandCenter();
				} catch (Exception e1) {

				}
			}

			if (a == p.getArr()[0][0]) {

				p.updateBCinfo(checkcitizen());
				// System.out.println("gg");
				String g = "";
				for (int i = 0; i < emergencyUnits.size(); i++) {
					Unit u = (Unit) emergencyUnits.get(i);
					if (emergencyUnits.get(i).getLocation().getX() == 0
							&& emergencyUnits.get(i).getLocation().getY() == 0) {
						if (u instanceof Ambulance) {

							Citizen c = (Citizen) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (c != null) {
								g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX()
										+ "," + c.getLocation().getY() + "\n";
							}

						}
						if (u instanceof DiseaseControlUnit) {
							Citizen c = (Citizen) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Disease Coontrol Unit" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (c != null) {
								g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX()
										+ "," + c.getLocation().getY() + "\n";
							}

						}
						if (u instanceof FireTruck) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY() + "\n";
							}

						}
						if (u instanceof GasControlUnit) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Gas Control Unit" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY() + "\n";
							}

						}
						if (u instanceof Evacuator) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							String m = "";
							g = g + "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\nNo.of Passengers: "
									+ ((Evacuator) u).getPassengers().size() + "\nPassengers: ";
							for (int j = 0; j < ((Evacuator) u).getPassengers().size(); j++) {
								Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
								g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
										+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge()
										+ "\nHP: " + c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: "
										+ c.getToxicity() + "\nState: " + c.getState() + "\n";
							}

							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY();
							}

						}
					}

				}
				p.updateunitinfo(g);
			}
		}

		if (window instanceof Squidward) {
			Squidward sw = (Squidward) window;
			
			if(a==sw.getHint())
			{
					
					last();
					sw.updatehint(sa);
					
					
			}
			

			for (int i = 0; i < sw.getArr().length; i++) {
				for (int j = 0; j < sw.getArr()[i].length; j++) {
					if (a == sw.getArr()[i][j]) {
						for (int k = 0; k < emergencyUnits.size(); k++) {
							if (emergencyUnits.get(k).getLocation().getX() == i
									&& emergencyUnits.get(k).getLocation().getY() == j) {
								Unit u = emergencyUnits.get(k);
								String g = "";
								if (u instanceof Ambulance) {
									Citizen c = (Citizen) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (c != null) {
										g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: "
												+ c.getLocation().getX() + "," + c.getLocation().getY();
									}
								}
								if (u instanceof DiseaseControlUnit) {
									Citizen c = (Citizen) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (c != null) {
										g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: "
												+ c.getLocation().getX() + "," + c.getLocation().getY();
									}
								}
								if (u instanceof FireTruck) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}

								if (u instanceof GasControlUnit) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Gas control Unit" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState();
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}
								if (u instanceof Evacuator) {
									ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
									g = g + "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
											+ u.getLocation().getX() + "," + u.getLocation().getY()
											+ "\nSteps Per Cycle: " + u.getStepsPerCycle() + "\nState: " + u.getState()
											+ "\nNo.of Passengers: " + ((Evacuator) u).getPassengers().size()
											+ "\nPassengers: ";
									for (int l = 0; l < ((Evacuator) u).getPassengers().size(); l++) {
										Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
										g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
												+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: "
												+ c.getAge() + "\nHP: " + c.getHp() + "\nBlood Loss: "
												+ c.getBloodLoss() + "\nToxicity: " + c.getToxicity() + "\nState: "
												+ c.getState();
									}
									if (b != null) {
										g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
												+ b.getLocation().getY();
									}
								}
								sw.updateunitinfo(g);
							}
						}
					}
				}
			}

			for (int i = 0; i < sw.getArr().length; i++) {
				for (int j = 0; j < sw.getArr()[i].length; j++) {
					if (a == sw.getArr()[i][j]) {
						bcclick = true;
						x = i;
						y = j;
						gettheobject(i, j);
						sw.updateBCinfo(find(i, j));
					}
				}
			}

			for (int w = 0; w < sw.getUnits().size(); w++) {
				if (a == sw.getUnits().get(w)) {
					Unit u = (Unit) emergencyUnits.get(w);

					if (u instanceof Ambulance) {
						Citizen c = (Citizen) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (c != null) {
							g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX() + ","
									+ c.getLocation().getY();
						}

						sw.updateunitinfo(g);

						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {

								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send an Ambulance to Squidward's house",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Squidward's house with an Ambulance",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof DiseaseControlUnit) {
						Citizen c = (Citizen) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Disease Control Unit" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (c != null) {
							g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX() + ","
									+ c.getLocation().getY();
						}

						sw.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send a Disease Control Unit to  Squidward's house",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Squidward with a Disease Control Unit",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}
					}
					if (u instanceof FireTruck) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}

						sw.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);
								JLabel l = new JLabel("You cannot send a Fire Truck to a Squidward", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);
							} catch (CannotTreatException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Squidward's house with a Fire Truck",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof GasControlUnit) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Gas control Unit" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState();
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}

						sw.updateunitinfo(g);
						uclick = true;
						if (bcclick = true) {
							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send a Gas Control Unit to a Squidward",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);
							} catch (CannotTreatException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Squidward's house with a Gas Control Unit",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}
						}

					}

					if (u instanceof Evacuator) {
						ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
						String g = "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
								+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
								+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\nNo.of Passengers: "
								+ ((Evacuator) u).getPassengers().size() + "\nPassengers: ";
						for (int j = 0; j < ((Evacuator) u).getPassengers().size(); j++) {
							Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
							g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
									+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge()
									+ "\nHP: " + c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: "
									+ c.getToxicity() + "\nState: " + c.getState();
						}
						if (b != null) {
							g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
									+ b.getLocation().getY();
						}
						sw.updateunitinfo(g);

						uclick = true;
						if (bcclick = true) {

							try {
								u.respond(gettheobject(x, y));
								uclick = false;
								bcclick = false;

							} catch (IncompatibleTargetException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot send an Evacuator to a Squidward", JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							} catch (CannotTreatException h) {
								JDialog d = new JDialog(sw);
								d.setTitle("ERROR!!");
								d.setSize(800, 200);
								d.setLocation(300, 250);

								JLabel l = new JLabel("You cannot help this Squidward's house with an Evacuator",
										JLabel.CENTER);
								l.setFont(new Font("Centaur", Font.BOLD, 32));
								l.setForeground(Color.RED);
								l.setBackground(Color.BLACK);
								l.setOpaque(true);
								d.add(l);
								d.setVisible(true);

							}

						}
					}

				}
			}

			if (a == sw.getNextcycle()) {

				engine.nextCycle();

			} else if (a == sw.getClose()) {
				sw.dispose();
			} else if (a == sw.getPlayagain()) {
				sw.dispose();
				try {
					new CommandCenter();
				} catch (Exception e1) {

				}
			}

			if (a == sw.getArr()[0][0]) {

				sw.updateBCinfo(checkcitizen());
				// System.out.println("gg");
				String g = "";
				for (int i = 0; i < emergencyUnits.size(); i++) {
					Unit u = (Unit) emergencyUnits.get(i);
					if (emergencyUnits.get(i).getLocation().getX() == 0
							&& emergencyUnits.get(i).getLocation().getY() == 0) {
						if (u instanceof Ambulance) {

							Citizen c = (Citizen) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Ambulance" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (c != null) {
								g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX()
										+ "," + c.getLocation().getY() + "\n";
							}

						}
						if (u instanceof DiseaseControlUnit) {
							Citizen c = (Citizen) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Disease Coontrol Unit" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (c != null) {
								g = g + "\nTarget: " + c.getName() + "\nTarget's Loation: " + c.getLocation().getX()
										+ "," + c.getLocation().getY() + "\n";
							}

						}
						if (u instanceof FireTruck) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Fire Truck" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY() + "\n";
							}

						}
						if (u instanceof GasControlUnit) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							g = g + "ID: " + u.getUnitID() + "\nType: Gas Control Unit" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\n";
							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY() + "\n";
							}

						}
						if (u instanceof Evacuator) {
							ResidentialBuilding b = (ResidentialBuilding) u.getTarget();
							String m = "";
							g = g + "ID: " + u.getUnitID() + "\nType: Evacuator" + "\nLocation: "
									+ u.getLocation().getX() + "," + u.getLocation().getY() + "\nSteps Per Cycle: "
									+ u.getStepsPerCycle() + "\nState: " + u.getState() + "\nNo.of Passengers: "
									+ ((Evacuator) u).getPassengers().size() + "\nPassengers: ";
							for (int j = 0; j < ((Evacuator) u).getPassengers().size(); j++) {
								Citizen c = (Citizen) ((Evacuator) u).getPassengers().get(j);
								g = g + "\nName: " + c.getName() + "\nID: " + c.getNationalID() + "\nLoction: "
										+ c.getLocation().getX() + "," + c.getLocation().getY() + "\nAge: " + c.getAge()
										+ "\nHP: " + c.getHp() + "\nBlood Loss: " + c.getBloodLoss() + "\nToxicity: "
										+ c.getToxicity() + "\nState: " + c.getState() + "\n";
							}

							if (b != null) {
								g = g + "\nTarget: Building\nTarget's Location: " + b.getLocation().getX() + ","
										+ b.getLocation().getY();
							}

						}
					}

				}
				sw.updateunitinfo(g);
			}
		}

	}

	public ArrayList<ResidentialBuilding> getVisibleBuildings() {
		return visibleBuildings;
	}

	public ArrayList<Citizen> getVisibleCitizens() {
		return visibleCitizens;
	}

	public ArrayList<Unit> getEmergencyUnits() {
		return emergencyUnits;
	}

}
