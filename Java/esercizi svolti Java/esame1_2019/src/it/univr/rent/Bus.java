package it.univr.rent;

public class Bus extends AbstractModel{

	public Bus(String name, int pricePerDay) {
		super(name, pricePerDay);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBeDrivenWith(License license) {
        return license == License.valueOf("D");
	}

	@Override
	public boolean equals(Object other) {
		
		if(other instanceof Bus) {
			return getName().equals(((Bus) other).getName()) && pricePerDay() == ((Bus) other).pricePerDay();
		}
		else
			return false;
	}

}
