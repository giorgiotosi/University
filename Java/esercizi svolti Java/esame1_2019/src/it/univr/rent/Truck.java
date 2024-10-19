package it.univr.rent;

public class Truck extends AbstractModel{

	public Truck(String name, int pricePerDay) {
		super(name, pricePerDay);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBeDrivenWith(License license) {
        return license == License.valueOf("C");
	}

	@Override
	public boolean equals(Object other) {
		
		if(other instanceof Truck) {
			return getName().equals(((Truck) other).getName()) && pricePerDay() == ((Truck) other).pricePerDay();
		}
		else
			return false;
		
	}

}
