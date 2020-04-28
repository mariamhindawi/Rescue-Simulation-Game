	package simulation;
	
	import model.disasters.Disaster;
	// contains the methods available for all Rescuable objects.
	
	public interface Rescuable {
		
	public void struckBy (Disaster d);
	
	public Address getLocation();
	
	public Disaster getDisaster();
	
	}
