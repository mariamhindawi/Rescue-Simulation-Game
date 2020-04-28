package simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JLabel;

import controller.CommandCenter;
import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.WorldListener;
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
import view.MyWindow;
import view.Patrick;
import view.Spongebob;
import view.Squidward;
import view.coverpage;
//represents the Simulator through which the player controls the Units to rescue Citizens and ResidentialBuilding
public class Simulator implements WorldListener {
	private int currentCycle;
	private ArrayList<ResidentialBuilding> buildings;
	private ArrayList<Citizen> citizens;
	private ArrayList<Unit> emergencyUnits;
	private ArrayList<Disaster> plannedDisasters;
	private ArrayList<Disaster> executedDisasters;
	private Address[][] world;
	private SOSListener emergencyService;
	private MyWindow window;

	public void setWindow(MyWindow window) {
		this.window = window;
	}
	public void setEmergencyService(SOSListener emergency) {
		this.emergencyService = emergency;
	}

	public ArrayList<Unit> getEmergencyUnits() {

		return emergencyUnits;
	}

	public Simulator(SOSListener listener) throws Exception {
		
		emergencyService = listener;
		buildings = new ArrayList<ResidentialBuilding>();
		citizens = new ArrayList<Citizen>();
		emergencyUnits = new ArrayList<Unit>();
		plannedDisasters = new ArrayList<Disaster>();
		executedDisasters = new ArrayList<Disaster>();
		//initializing the world grid that contain the addresses
		world = new Address[10][10];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++)
				world[i][j] = new Address(i, j);
		//loading from csv files
		
		loadUnits("units.csv");
		loadBuildings("buildings.csv");
		loadCitizens("citizens.csv");
		loadDisasters("disasters.csv");
		
		for (int i = 0; i < buildings.size(); i++) {
			ResidentialBuilding building = buildings.get(i);
			for (int j = 0; j < citizens.size(); j++) {
				Citizen citizen = citizens.get(j);
				if (citizen.getLocation() == building.getLocation())
					building.getOccupants().add(citizen);
			}
		}
	}
	//reading from units csv
	private void loadUnits(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			String id = info[1];
			int steps = Integer.parseInt(info[2]);
			switch (info[0]) {
			case "AMB": {
				Ambulance a = new Ambulance(id, world[0][0], steps, this);
				emergencyUnits.add(a);

			}
				break;
			case "DCU": {
				DiseaseControlUnit d = new DiseaseControlUnit(id, world[0][0],
						steps, this);
				emergencyUnits.add(d);
			}
				break;
			case "EVC": {
				Evacuator e = new Evacuator(id, world[0][0], steps, this,
						Integer.parseInt(info[3]));
				emergencyUnits.add(e);
			}
				break;
			case "FTK": {
				FireTruck f = new FireTruck(id, world[0][0], steps, this);
				emergencyUnits.add(f);
			}
				break;
			case "GCU": {
				GasControlUnit g = new GasControlUnit(id, world[0][0], steps,
						this);
				emergencyUnits.add(g);
			}
				break;

			}
			line = br.readLine();
		}
		br.close();

	}
	//reading from buildings csv
	private void loadBuildings(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			ResidentialBuilding b = new ResidentialBuilding(world[x][y]);
			b.setEmergencyService(emergencyService);
			buildings.add(b);
			line = br.readLine();
		}
		br.close();
	}
	//reading from citizens csv
	private void loadCitizens(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int x = Integer.parseInt(info[0]);
			int y = Integer.parseInt(info[1]);
			String id = info[2];
			String name = info[3];
			int age = Integer.parseInt(info[4]);
			Citizen c = new Citizen(world[x][y], id, name, age, this);
			c.setEmergencyService(emergencyService);
			citizens.add(c);
			line = br.readLine();
		}
		br.close();
	}
	//reading from disasters csv
	private void loadDisasters(String path) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			int startCycle = Integer.parseInt(info[0]);
			ResidentialBuilding building = null;
			Citizen citizen = null;
			if (info.length == 3)
				citizen = getCitizenByID(info[2]);
			else {
				int x = Integer.parseInt(info[2]);
				int y = Integer.parseInt(info[3]);
				building = getBuildingByLocation(world[x][y]);
			}
			switch (info[1]) {
			case "INJ":
				plannedDisasters.add(new Injury(startCycle, citizen));
				break;
			case "INF":
				plannedDisasters.add(new Infection(startCycle, citizen));
				break;
			case "FIR":
				plannedDisasters.add(new Fire(startCycle, building));
				break;
			case "GLK":
				plannedDisasters.add(new GasLeak(startCycle, building));
				break;
			}
			line = br.readLine();
		}
		br.close();
	}
	//If a citizen exists at the specified location return the citizen and null otherwise
	private Citizen getCitizenByID(String id) {
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getNationalID().equals(id))
				return citizens.get(i);
		}
		return null;
	}
	//If a building exists at the specified location return the building and null otherwise
	private ResidentialBuilding getBuildingByLocation(Address location) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).getLocation() == location)
				return buildings.get(i);
		}
		return null;
	}

	@Override
	//sets the address(location) of a citizen or a unit to a specified location 
	public void assignAddress(Simulatable s, int x, int y) {
		if (s instanceof Citizen)
			((Citizen) s).setLocation(world[x][y]);
		else
			((Unit) s).setLocation(world[x][y]);

	}



	public void nextCycle() {

		currentCycle++;
		
		//if the game has ended
		if(checkGameOver()) {
			//if player was spongebob
			if(window instanceof Spongebob)
			{ 	//provide window with number of casualties
				Spongebob s= (Spongebob) window;
				s.getGameo2().setText("NUMBER OF SPONGEBOBS YOU KILLED: " + calculateCasualties());
				s.getGameOver().setVisible(true);
			}
			//if player was patrick
			if(window instanceof Patrick)
			{	//provide window with number of casualties
				Patrick p= (Patrick)window;
				p.getGameo2().setText("NUMBER OF PATRICKS YOU KILLED: " + calculateCasualties());
				p.getGameOver().setVisible(true);
			}
			//if player was squidward
			if(window instanceof Squidward)
			{	//provide window with number of casualties
				Squidward sq= (Squidward)window;
				sq.getGameo2().setText("NUMBER OF SQUIDWARDS YOU KILLED: " + calculateCasualties());
				sq.getGameOver().setVisible(true);
			}
		}

		for (int i = 0; i < plannedDisasters.size(); i++) {
			Disaster d = plannedDisasters.get(i);
			//if it is time for a planned disaster
			if (d.getStartCycle() == currentCycle) {
				//remove from planned disasters
				plannedDisasters.remove(d);
				i--;
				if (d instanceof Fire)
					handleFire(d);
				else if (d instanceof GasLeak)
					handleGas(d);
				else {
					try {
						d.strike();
						executedDisasters.add(d);
					} catch (CitizenAlreadyDeadException e)
					{
					
					}
					catch(  BuildingAlreadyCollapsedException e)
					{
						
					}
				}
			}
		}
		//for each building check if fire damage is 100 or more than a collapse disaster should occur
		for (int i = 0; i < buildings.size(); i++) {
			ResidentialBuilding b = buildings.get(i);
			if (b.getFireDamage() >= 100) {
				b.getDisaster().setActive(false);
				b.setFireDamage(0);
				Collapse c = new Collapse(currentCycle, b);
				try {
					c.strike();
					executedDisasters.add(c);
				}  catch (CitizenAlreadyDeadException e)
				{
					
				}
				catch(  BuildingAlreadyCollapsedException e)
				{
					
				}
				
			}
		}

		for (int i = 0; i < emergencyUnits.size(); i++) {
			if(emergencyUnits.get(i) instanceof Evacuator && emergencyUnits.get(i).getLocation().getX()==0 && emergencyUnits.get(i).getLocation().getY()==0) {
				Evacuator e =(Evacuator)emergencyUnits.get(i); 
				for(int j=0; j<e.getPassengers().size();j++) {
				((CommandCenter)emergencyService).getArrived().add(e.getPassengers().get(j));
				}
			}
			emergencyUnits.get(i).cycleStep();
		}

		for (int i = 0; i < executedDisasters.size(); i++) {
			Disaster d = executedDisasters.get(i);
			if (d.getStartCycle() < currentCycle && d.isActive())
				d.cycleStep();
		}

		for (int i = 0; i < buildings.size(); i++) {
			buildings.get(i).cycleStep();
		}

		for (int i = 0; i < citizens.size(); i++) {
			citizens.get(i).cycleStep();
		}
		
		if(window instanceof Spongebob) {
			
		Spongebob s=(Spongebob)window;
		s.updatec(calculateCasualties());
		
		
		s.updatecycle(currentCycle);
		
		((CommandCenter) emergencyService).addtogrid();
		
		s.updateBCinfo("");
		s.updateunitinfo("");
		
		s.updateDInfo(this.DisasterInfo(), this.DeadCitizens());
		s.updatehint("");
		}
		
		
		
		if(window instanceof Patrick) {
			
			Patrick p=(Patrick)window;
			p.updatec(calculateCasualties());
			
			
			p.updatecycle(currentCycle);
			
			((CommandCenter) emergencyService).addtogrid();
			
			p.updateBCinfo("");
			p.updateunitinfo("");
	
			
			p.updateDInfo(this.DisasterInfo(), this.DeadCitizens());
			p.updatehint("");
			}
		
		
		if(window instanceof Squidward) {
			
			Squidward w=(Squidward)window;
			w.updatec(calculateCasualties());
			
			
			w.updatecycle(currentCycle);
			
			((CommandCenter) emergencyService).addtogrid();
			
			w.updateBCinfo("");
			w.updateunitinfo("");
			
			w.updateDInfo(this.DisasterInfo(), this.DeadCitizens());
			w.updatehint("");
			}
		
		
		
		
		

	}
	//if a citizen is dead change their state and print location
	public String DeadCitizens() {
		String s ="Dead Citizens:";
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getState() == CitizenState.DECEASED) {
				String t ="\nCitizen's Location: " + citizens.get(i).getLocation().getX() +" ," + citizens.get(i).getLocation().getY();
				s=s+ t;
			}
				
		}
		return s;
	}
	
	//print info about the disaster
	public String DisasterInfo() {
		String s="Disasters:";
		for(int i =0;i<executedDisasters.size();i++) {
			String d="";
			if(executedDisasters.get(i) instanceof Collapse) {
				d="Collapse";
			}
			else if(executedDisasters.get(i) instanceof Fire) {
				d="Fire";
			}
			else if(executedDisasters.get(i) instanceof GasLeak) {
				d="Gas Leak";
			}
			else if(executedDisasters.get(i) instanceof Infection) {
				d="Infection";
			}
			else if(executedDisasters.get(i) instanceof Injury) {
				d="Injury";
			}
			
			String t ="";
			if(executedDisasters.get(i).getTarget() instanceof ResidentialBuilding) {
				t= "Building";
			}
			else {
				Citizen c = (Citizen) executedDisasters.get(i).getTarget();
				t=c.getName();
			}
			
			s=s+"\nType Of Disaster: "+d+ "\n" + "Target: " + t;
		}
		return s;
	}

	private void handleGas(Disaster d) {
		ResidentialBuilding b = (ResidentialBuilding) d.getTarget();
		if (b.getFireDamage() != 0) {
			b.setFireDamage(0);
			Collapse c = new Collapse(currentCycle, b);
			try {
				c.strike();
				executedDisasters.add(c);
			}catch (CitizenAlreadyDeadException e) {
				
			}
			catch (BuildingAlreadyCollapsedException e) {
		
			}
			
			
		} else {
			try {
				d.strike();
				executedDisasters.add(d);
			} catch (CitizenAlreadyDeadException e) {
				
			}
			catch (BuildingAlreadyCollapsedException e) {
				
			}
			
			
		}
	}

	private void handleFire(Disaster d)  {
		ResidentialBuilding b = (ResidentialBuilding) d.getTarget();
		if (b.getGasLevel() == 0) {
			try {
				d.strike();
				executedDisasters.add(d);
			} 
			catch (CitizenAlreadyDeadException e) {
				
			}
			catch (BuildingAlreadyCollapsedException e) {
		
			}
			
		} else if (b.getGasLevel() < 70) {
			b.setFireDamage(0);
			Collapse c = new Collapse(currentCycle, b);
			try {
				c.strike();
				executedDisasters.add(c);
			} catch (CitizenAlreadyDeadException e) {
				
			}
			catch (BuildingAlreadyCollapsedException e) {
			}
			
			
		} else
			b.setStructuralIntegrity(0);

	}

	public boolean checkGameOver() {
		//if their are still any planned disasters the game is not over
		if (plannedDisasters.size() != 0)
			return false;

		for (int i = 0; i < executedDisasters.size(); i++) {
			if (executedDisasters.get(i).isActive()) {
				Disaster d = executedDisasters.get(i);
				Rescuable r = d.getTarget();
				//if he  target of a certain executed disaster is a citizen and is not dead then the game is not over
				if (r instanceof Citizen) {
					Citizen c = (Citizen) r;
					if (c.getState() != CitizenState.DECEASED)
						return false;
				} 
				//if the target o a certain disaster is a building and has not collapsed then the game is not over
				else {
					ResidentialBuilding b = (ResidentialBuilding) r;
					if (b.getStructuralIntegrity() != 0)
						return false;
				}

			}

		}
		//if there is an emergency unit that is not idle then the game is not over
		for (int i = 0; i < emergencyUnits.size(); i++) {
			if (emergencyUnits.get(i).getState() != UnitState.IDLE)
				return false;
		}

		return true;
	}
	//check how many citizen are dead by looking at the state of all citizens 
	public int calculateCasualties() {
		int count = 0;
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getState() == CitizenState.DECEASED)
				count++;
		}
		return count;

	}


}
