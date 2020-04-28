	package model.disasters;
	import exceptions.BuildingAlreadyCollapsedException;
	import exceptions.CitizenAlreadyDeadException;
	import model.infrastructure.ResidentialBuilding;
	import model.people.Citizen;
	import model.people.CitizenState;
	import simulation.Rescuable;
	import simulation.Simulatable;
	//represents a disaster that can take place at a ResidentialBuilding or occur to a Citizen
	public abstract class Disaster implements Simulatable {
		
		private int startCycle;
		private Rescuable target;
		private boolean active;
		
		public Disaster(int startCycle, Rescuable target) {
			this.startCycle = startCycle;
			this.target = target;
		}
		public boolean isActive() {
			return active;
		}
		public void setActive(boolean active) {
			this.active = active;
		}
		public int getStartCycle() {
			return startCycle;
		}
		public Rescuable getTarget() {
			return target;
		}
		public void strike() throws CitizenAlreadyDeadException, BuildingAlreadyCollapsedException {
			if(target instanceof Citizen) {
				Citizen c = (Citizen) target;
				if(c.getState()==CitizenState.DECEASED) {
					throw new CitizenAlreadyDeadException(this);
				}
			}
			else if(target instanceof ResidentialBuilding) {
				ResidentialBuilding b = (ResidentialBuilding) target;
				if(b.getStructuralIntegrity()==0) 
					throw new BuildingAlreadyCollapsedException(this);
				
			}
			target.struckBy(this);
			active=true;
		}
	}
