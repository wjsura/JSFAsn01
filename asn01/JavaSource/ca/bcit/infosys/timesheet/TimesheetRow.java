package ca.bcit.infosys.timesheet;

import java.math.BigDecimal;

/**
 * A class representing a single row of a Timesheet.
 *
 * @author Bruce Link
 */

public class TimesheetRow implements java.io.Serializable {
    private double saturday;
    private double sunday;
    private double monday;
    private double tuesday;
    private double wednesday;
    private double thursday;
    private double friday;
    private double total;
    /** Version number. */
    private static final long serialVersionUID = 2L;
    private boolean editable;
    /** Timesheet row index for Saturday. */
    public static final int SAT = 0;
    /** Timesheet row index for Sunday. */
    public static final int SUN = 1;
    /** Timesheet row index for Monday. */
    public static final int MON = 2;
    /** Timesheet row index for Tuesday. */
    public static final int TUE = 3;
    /** Timesheet row index for Wednesday. */
    public static final int WED = 4;
    /** Timesheet row index for Thursday. */
    public static final int THU = 5;
    /** Timesheet row index for Friday. */
    public static final int FRI = 6;

    /** The projectID. */
    private int projectID;
    /** The WorkPackage. Must be a unique for a given projectID. */
    private String workPackage;

    /**
     * An array holding all the hours charged for each day of the week. Day 0 is
     * Saturday, ... day 6 is Friday
     */
    private BigDecimal[] hoursForWeek = new BigDecimal[Timesheet.DAYS_IN_WEEK];
    /** Any notes added to the end of a row. */
    private String notes;

    /**
     * Creates a TimesheetDetails object and sets the editable state to true.
     */
    public TimesheetRow() {
    }

    /**
     * Creates a TimesheetDetails object with all fields set. Used to create
     * sample data.
     * @param id project id
     * @param wp work package number (alphanumeric)
     * @param hours number of hours charged for each day of week.
     *      null represents ZERO
     * @param comments any notes with respect to this work package charges
     */
    public TimesheetRow(final int id, final String wp,
            final BigDecimal[] hours, final String comments) {
        setProjectID(id);
        setWorkPackage(wp);
        setHoursForWeek(hours);
        setNotes(comments);
    }

    /**
     * @return the projectID
     */
    public int getProjectID() {
        return projectID;
    }

    public boolean getEditable(){
    	return editable;
    }
    
    /**
     * @param id the projectID to set
     */
    public void setProjectID(final int id) {
        this.projectID = id;
    }

    /**
     * @return the workPackage
     */
    public String getWorkPackage() {
        return workPackage;
    }

    /**
     * @param wp the workPackage to set
     */
    public void setWorkPackage(final String wp) {
        this.workPackage = wp;
    }

    /**
     * @return the hours charged for each day
     */
    public BigDecimal[] getHoursForWeek() {
        return hoursForWeek;
    }

    /**
     * @param hours the hours charged for each day
     */
    public void setHoursForWeek(final BigDecimal[] hours) {
        checkHoursForWeek(hours);
        this.hoursForWeek = hours;
    }

    /**
     * @param day The day of week to return charges for
     * @return charges in hours of specific day in week
     */
    public BigDecimal getHour(final int day) {
        return hoursForWeek[day];
    }

    /**
    * @param day The day of week to set the hour
    * @param hour The number of hours worked for that day
    */
   public void setHour(final int day, final BigDecimal hour) {
       checkHour(hour);
       hoursForWeek[day] = hour;
   }
   /**
   * @param day The day of week to set the hour
   * @param hour The number of hours worked for that day
   */
  public void setHour(final int day, final double hour) {
      BigDecimal bdHour = null;
      if (hour != 0.0) {
          bdHour = new BigDecimal(hour).setScale(1, BigDecimal.ROUND_HALF_UP);
      }
      checkHour(bdHour);
      hoursForWeek[day] = bdHour;
  }

    /**
     * Checks if hour value is out of the valid
     * bounds of 0.0 to 24.0, or has more than one decimal digit.
     * 
     *
     *@param hour the value to check
     */
    private void checkHour(final BigDecimal hour) {
        if (hour != null) {
            if (hour.compareTo(Timesheet.HOURS_IN_DAY) > 0.0
                    || hour.compareTo(BigDecimal.ZERO) < 0.0) {
                throw new IllegalArgumentException(
                       "out of range: should be between 0 and 24");
            }
            if (hour.scale() > 1) {
                throw new IllegalArgumentException(
                        "too many decimal digits: should be at most 1");
            }
        }
    }

    /**
     * Checks if any hour value in any day of the week is out of the valid
     * bounds of 0.0 to 24.0, or has more than one decimal digit.
     *
     * @param hours array of hours charged for each day in a week
     */
    private void checkHoursForWeek(final BigDecimal[] hours) {
        if (hours.length != Timesheet.DAYS_IN_WEEK) {
            throw new IllegalArgumentException(
                    "wrong week length: should be 7");
        }
        for (BigDecimal next : hours) {
            checkHour(next);
        }
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param comments the notes to set
     */
    public void setNotes(final String comments) {
        this.notes = comments;
    }

    /**
     * @return the weekly hours
     */
    public BigDecimal getSum() {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal next : hoursForWeek) {
            if (next != null) {
                sum = sum.add(next);
            }
        }
        return sum;
    }

	/**
	 * @param b
	 */
	public void setEditable(boolean b) {
		editable = b;
		
	}

	/**
	 * @return
	 */
	public double getSaturday() {
		return saturday;
	}

	/**
	 * @param saturday
	 */
	public void setSaturday(double saturday) {
		if(saturday<=24){
			this.saturday = saturday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getSunday() {
		return sunday;
	}

	/**
	 * @param sunday
	 */
	public void setSunday(double sunday) {
		if(sunday<=24){
			this.sunday = sunday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getMonday() {
		return monday;
	}

	/**
	 * @param monday
	 */
	public void setMonday(double monday) {
		if(monday<=24){
			this.monday = monday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getTuesday() {
		return tuesday;
	}

	/**
	 * @param tuesday
	 */
	public void setTuesday(double tuesday) {
		if(tuesday<=24){
			this.tuesday = tuesday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getWednesday() {
		return wednesday;
	}

	/**
	 * @param wednesday
	 */
	public void setWednesday(double wednesday) {
		if(wednesday<=24){
			this.wednesday = wednesday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getThursday() {
		return thursday;
	}

	/**
	 * @param thursday
	 */
	public void setThursday(double thursday) {
		if(thursday<=24){
			this.thursday = thursday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getFriday() {
		return friday;
	}

	/**
	 * @param friday
	 */
	public void setFriday(double friday) {
		if(friday<=24){
			this.friday = friday;
		} else {
			throw new IllegalArgumentException("There isn't more than 24 hours in a day!");
		}
	}

	/**
	 * @return
	 */
	public double getTotal() {
		return total+getSaturday()+getSunday()+getMonday()+getTuesday()+getWednesday()+getThursday()+getFriday();
	}

	/**
	 * @param total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

}
