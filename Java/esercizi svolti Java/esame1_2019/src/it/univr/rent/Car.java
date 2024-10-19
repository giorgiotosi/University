package it.univr.rent;

public class Car extends AbstractModel{

	public Car(String name, int pricePerDay) {
		super(name, pricePerDay);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canBeDrivenWith(License license) {
        return license == License.valueOf("B") || license == License.valueOf("C") || license == License.valueOf("D");
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		if(other instanceof Car) {
			return getName().equals(((Car) other).getName()) && pricePerDay() == ((Car) other).pricePerDay();
		}
		else
			return false;
	}

}
