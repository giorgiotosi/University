package it.univr.rent;

public class Motorbike extends AbstractModel{

	public Motorbike(String name, int pricePerDay) {
		super(name, pricePerDay);

	}

	@Override
	public boolean canBeDrivenWith(License license) {

        return license == License.valueOf("A") || license == License.valueOf("B") || license == License.valueOf("C") || license == License.valueOf("D");
		
	}

	@Override
	public boolean equals(Object other) {

		if(other instanceof Motorbike) {
			return getName().equals(((Motorbike) other).getName()) && pricePerDay() == ((Motorbike) other).pricePerDay();
		}
		else
			return false;
	
	}

}
