package model;


public class Date {
	
	private int day;
	private int month;
	private int year;
	
	public Date(int day, int month, int year){
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	//compares date by year month and day in that order
	public int compareTo(Date otherDate){
		if(this.year == otherDate.year){
			if(this.month == otherDate.month){
				if(this.day == otherDate.day){
					return 0; //dates are equal
				}
				if(this.day > otherDate.day){
					return 1;
				}
				return -1;
			}
			if(this.month > otherDate.month){
				return 1;
			}
			return -1;
		}
		if(this.year > otherDate.year){
			return 1;
		}
		return -1;
	}

	@Override
	public String toString() {
		return day + "/" + month + "/" + year;
	}
	
	/**
	 * Parses date if it matches format: dd/mm/yyyy
	 * @param text
	 * @return The parsed date
	 * @throws Exception
	 */
	public static Date parseDate(String text) throws Exception{
		String parts[] = text.split("/");
		int d = Integer.parseInt(parts[0]);
		int m = Integer.parseInt(parts[1]);
		int y = Integer.parseInt(parts[2]);
		return new Date(d, m, y);	
	}
	
	
	
}
