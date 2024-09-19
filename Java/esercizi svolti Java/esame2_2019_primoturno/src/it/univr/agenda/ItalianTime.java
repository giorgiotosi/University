package it.univr.agenda;



// Ci sono 3 TODO

public class ItalianTime extends Time {
	private final int hours;
	private final int minutes;
	private final int seconds;
	/**
	 * Costruisce un istante con notazione italiana.
	 * 
	 * @param hours le ore, tra 0 e 23
	 * @param minutes i minuti, tra 0 e 59
	 * @param seconds i secondi, tra 0 e 59
	 * @throws IllegalArgumentException se i parametri non fossero validi
	 */
	public ItalianTime(int hours, int minutes, int seconds) {
		
		super(seconds + minutes*60 + hours*60*60); 
		
		if(seconds<0 || minutes<0 || hours<0 || seconds>59 || minutes>59 || hours>23) {
			throw new IllegalArgumentException();	
		}
		
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		

	}

	/**
	 * @return una stringa nel formato hh:mm:ss (ore, minuti e secondi sempre su due cifre!)
	 */
	@Override
	public String toString() {
		String result;
		result = String.format("%2d:%2d:%2d", hours, minutes, seconds);
		return result; 
	}

	@Override
	public ItalianTime after(int minutes) {

		if (minutes < 0) {
			throw new IllegalArgumentException("minutes non deve essere negativo");
		}
		
		int tmpHours;
		int tmpMinutes;
		int tmpSeconds;
		
		
		tmpHours = this.secondsFromStartOfDay /3600;
		tmpMinutes = (this.secondsFromStartOfDay % 3600)/60;
		tmpSeconds = (this.secondsFromStartOfDay % 3600)%60;
		
		tmpMinutes = tmpMinutes + minutes;
		if(tmpMinutes >=60) {
			tmpHours++;
			tmpMinutes = tmpMinutes-60;			
		}
		
		
		if(tmpHours== 24 ) {
			tmpHours = 0;
			
		}
		if(tmpHours > 24 ) {
			tmpHours = tmpHours-24;
			
		}

			
		return new ItalianTime(tmpHours, tmpMinutes, tmpSeconds);
	}


}
