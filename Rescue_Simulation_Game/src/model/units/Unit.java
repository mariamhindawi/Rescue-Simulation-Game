	package model.units;
	
	import exceptions.CannotTreatException;
	import java.lang.Math;
	import exceptions.IncompatibleTargetException;
	import model.disasters.*;
	import model.events.SOSResponder;
	import model.events.WorldListener;
	import model.infrastructure.ResidentialBuilding;
	import model.people.Citizen;
	import model.people.CitizenState;
	import simulation.Address;
	import simulation.Rescuable;
	import simulation.Simulatable;
	//represents one Unit of the available rescue units in the game
	
	public abstract class Unit implements Simulatable, SOSResponder {
		private String unitID;
		private UnitState state;
		private Address location;
		private Rescuable target;
		private int distanceToTarget;
		private int stepsPerCycle;
		private WorldListener worldListener;
	
		public Unit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {
			this.unitID = unitID;
			this.location = location;
			this.stepsPerCycle = stepsPerCycle;
			this.state = UnitState.IDLE;
			this.worldListener = worldListener;
		}
	
		public void setWorldListener(WorldListener listener) {
			this.worldListener = listener;
		}
	
		public WorldListener getWorldListener() {
			return worldListener;
		}
	
		public UnitState getState() {
			return state;
		}
	
		public void setState(UnitState state) {
			this.state = state;
		}
	
		public Address getLocation() {
			return location;
		}
	
		public void setLocation(Address location) {
			this.location = location;
		}
	
		public String getUnitID() {
			return unitID;
		}
	
		public Rescuable getTarget() {
			return target;
		}
	
		public int getStepsPerCycle() {
			return stepsPerCycle;
		}
	
		public void setDistanceToTarget(int distanceToTarget) {
			this.distanceToTarget = distanceToTarget;
		}
	
		@Override
		public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException{
			if( r!= null && r instanceof Citizen) {
				if(!(this instanceof DiseaseControlUnit) && !(this instanceof Ambulance)) {
					throw new IncompatibleTargetException(this, r, "You Cannot Send "+this+" to "+r);
				}
			}
			else if(r!= null && r instanceof ResidentialBuilding) {
				if((this instanceof DiseaseControlUnit) || (this instanceof Ambulance)) {
					throw new IncompatibleTargetException(this, r, "You Cannot Send "+this+" to "+r);
				}
				
				
				
			
			}
			if(r!= null && r.getDisaster()==null) {
				throw new CannotTreatException(this, r, "There is no disaster affecting " + r);
			}
			else if (this instanceof DiseaseControlUnit) {
					Citizen c =(Citizen)r;
					
					if(c!=null && (c.getState()==CitizenState.DECEASED || !(c.getDisaster() instanceof Infection))) {
						throw new CannotTreatException(this, r, "You Cannot Send "+this+" to "+r);
					}
				}
			else if (this instanceof Ambulance) {
				Citizen c =(Citizen)r;
				
				if(c!=null && (c.getState()==CitizenState.DECEASED || !(c.getDisaster() instanceof Injury))) {
					throw new CannotTreatException(this, r, "You Cannot Send "+this+" to "+r);
				}
			}
			else if (this instanceof FireTruck) {
				ResidentialBuilding b =(ResidentialBuilding)r;
				
				if(b!=null && (b.getFireDamage()==0 || !(b.getDisaster() instanceof Fire))) {
					throw new CannotTreatException(this, r, "You Cannot Send "+this+" to "+r);
				}
			}
			else if (this instanceof GasControlUnit) {
				ResidentialBuilding b =(ResidentialBuilding)r;
				
				if(b!=null && (b.getGasLevel()==0 || !(b.getDisaster() instanceof GasLeak))) {
					throw new CannotTreatException(this, r, "You Cannot Send "+this+" to "+r);
				}
			}
			else if (this instanceof Evacuator) {
				ResidentialBuilding b =(ResidentialBuilding)r;
				
				if(b!=null && (b.getDisaster()==null || !(b.getDisaster() instanceof Collapse)|| !(b.getDisaster().isActive())||b.getStructuralIntegrity()==0)) {
					throw new CannotTreatException(this, r, "You Cannot Send "+this+" to "+r);
				}
			}
	
		
		}
		
	
		public void reactivateDisaster() {
			Disaster curr = target.getDisaster();
			curr.setActive(true);
		}
	
		public void finishRespond(Rescuable r) {
			if(r!=null) {
			target = r;
			state = UnitState.RESPONDING;
			Address t = r.getLocation();
			distanceToTarget = Math.abs(t.getX() - location.getX())
					+ Math.abs(t.getY() - location.getY());
			}
		}
	
		public abstract void treat();
	
		public void cycleStep() {
			if (state == UnitState.IDLE)
				return;
			if (distanceToTarget > 0) {
				distanceToTarget = distanceToTarget - stepsPerCycle;
				if (distanceToTarget <= 0) {
					distanceToTarget = 0;
					Address t = target.getLocation();
					worldListener.assignAddress(this, t.getX(), t.getY());
				}
			} else {
				state = UnitState.TREATING;
				treat();
			}
		}
	
		public void jobsDone() {
			target = null;
			state = UnitState.IDLE;
	
		}
	
		
		
		public int calculate()
		{
			int fin=distanceToTarget/stepsPerCycle;
			if(distanceToTarget%stepsPerCycle!=0)
			{
				int x=(int)Math.ceil(fin);
				return x;
			}
			
					
			return fin;
		}
	}
