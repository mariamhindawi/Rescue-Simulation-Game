	package simulation;
	
	//represents a single cell location in the grid by its x and y coordinates
	public class Address {
	private int x;
	private int y;
	
	public Address(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
		public String toString() {
			return x + " " + y;
		}
	}
