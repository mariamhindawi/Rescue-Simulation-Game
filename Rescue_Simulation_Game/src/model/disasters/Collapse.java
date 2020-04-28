package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.infrastructure.ResidentialBuilding;


public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	public void strike() throws CitizenAlreadyDeadException, BuildingAlreadyCollapsedException 
	{
		ResidentialBuilding b= (ResidentialBuilding)getTarget();	
		
		
		
			if(b.getStructuralIntegrity()==0) {
				throw new BuildingAlreadyCollapsedException(this);
			}
			else {
				
		b.setFoundationDamage(b.getFoundationDamage()+10);
		super.strike();
	}
	}
	public void cycleStep()
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}

}
