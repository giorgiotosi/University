package it.univr.agenda;

// Ci sono 3 TODO

public class AmericanTime extends Time {
	public enum Period {
		AM, PM
	}
	
	private final int hours;
	private final int minutes;
	private final int seconds;
	private final Period period;

	/**
	 * Costruisce un istante con notazione americana.
	 * 
	 * @param hours le ore, tra 1 e 12
	 * @param minutes i minuti, tra 0 e 59
	 * @param seconds i secondi, tra 0 e 59
	 * @param period il periodo del giorno
	 * @throws IllegalArgumentException se i parametri non fossero validi
	 */
	public AmericanTime(int hours, int minutes, int seconds, Period period) {
		super( seconds + minutes*60 + ( period==Period.PM && hours<12?((hours*60*60) +12*60*60 ):hours*60*60) );
		if(hours == 12 && period == Period.AM) {
			secondsFromStartOfDay = secondsFromStartOfDay - 12*60*60;
		}

		if(seconds<0 || minutes<0 || hours<1 || seconds>59 || minutes>59 || hours>12) {
			throw new IllegalArgumentException();	
		}

		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;		
		this.period = period;

	}

	/**
	 * @return una stringa nel formato hh:mm:ssPP, dove PP e' il periodo
	 * e quindi puo' essere AM oppure PM (ore, minuti e secondi sempre su due cifre!)
	 */
	@Override
	public String toString() {
		String result;
		result = String.format("%02d:%02d:%02d%s", hours, minutes, seconds, period);
		return result; 
	}

	@Override
	public AmericanTime after(int minutes) {

		if (minutes < 0) {
			throw new IllegalArgumentException("minutes non deve essere negativo");
		}
		boolean changePeriod=false;
		
		int tmpHours;
		int tmpMinutes;
		int tmpSeconds;
		Period tmpPeriod = period;
		
		tmpHours = this.secondsFromStartOfDay /3600;
		tmpMinutes = (this.secondsFromStartOfDay % 3600)/60;
		tmpSeconds = (this.secondsFromStartOfDay % 3600)%60;
		
		tmpMinutes = tmpMinutes + minutes;
		if(tmpMinutes >=60) {
			tmpHours++;
			tmpMinutes = tmpMinutes-60;			
		}
		
		if(hours==11 && tmpHours-12 ==12) {
			changePeriod=true;
		}
		
		if(changePeriod) {
			if(period== Period.AM)
				tmpPeriod = Period.PM;
			if(period== Period.PM)
				tmpPeriod = Period.AM;
		}
		
			return new AmericanTime(tmpHours-12, tmpMinutes, tmpSeconds, tmpPeriod);
	}
}