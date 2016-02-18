package ca.bcit.infosys.timesheet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;

import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.TableData;

// or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

@Named // or @ManagedBean
@SessionScoped
public class TableSheets implements Serializable, TimesheetCollection {
	Calendar cal = new GregorianCalendar();    
	private boolean editable = true;
	Employee emp = new Employee();
	public static final int DAYS_IN_WEEK = 7;
    private BigDecimal[] hoursForWeek = new BigDecimal[Timesheet.DAYS_IN_WEEK];
    private final List<Timesheet> sheetlist = new ArrayList<Timesheet>(Arrays.asList(
    		new Timesheet(),
    		new Timesheet(),
    		new Timesheet(),
    		new Timesheet(),
    		new Timesheet()
	));
    private static final ArrayList<TimesheetRow> sheets = new ArrayList<TimesheetRow>(Arrays.asList(
    		new TimesheetRow(),
    		new TimesheetRow(),
    		new TimesheetRow(),
    		new TimesheetRow(),
    		new TimesheetRow()
	));
    private int index = -1;
    /**
     * @return
     */
    public ArrayList<TimesheetRow> getSheets(){
    	return sheets;
    }
    /**
     * 
     */
    public void setEmp(){
    	emp = new Employee("wesley", 5, "a00");
    }
    /**
     * @return
     */
    public Employee getEmp(){
    	return emp;
    }
	/* (non-Javadoc)
	 * @see ca.bcit.infosys.timesheet.TimesheetCollection#getTimesheets()
	 */
	@Override
	public List<Timesheet> getTimesheets() {
		return null;
	}
	/**
	 * @return
	 */
	public List<Timesheet> getSheetlist(){
		return sheetlist;
	}
	/* (non-Javadoc)
	 * @see ca.bcit.infosys.timesheet.TimesheetCollection#getTimesheets(ca.bcit.infosys.employee.Employee)
	 */
	@Override
	public List<Timesheet> getTimesheets(Employee e) {
		return null;
	}
	/* (non-Javadoc)
	 * @see ca.bcit.infosys.timesheet.TimesheetCollection#getCurrentTimesheet(ca.bcit.infosys.employee.Employee)
	 */
	@Override
	public Timesheet getCurrentTimesheet(Employee e) {
		return null;
	}
	/* (non-Javadoc)
	 * @see ca.bcit.infosys.timesheet.TimesheetCollection#addTimesheet()
	 */
	@Override
	public String addTimesheet() {
		Timesheet timesheet = new Timesheet();
		sheetlist.add(timesheet);
		return null;
	}
	/**
	 * @param ts
	 * @return
	 */
	public String addRow(int ts){
		sheetlist.get(ts).addRow();
		return null;
	}
	/**
	 * @return
	 */
	public boolean getEditable(){
		return editable;
	}
	/**
	 * 
	 */
	public void setEditable(){
		editable = true;
	}
	/**
	 * @return
	 */
	public String total(){
		String total = "0";
		return total;
	}
	/**
	 * @return
	 * @throws ParseException
	 */
	public String done() throws ParseException{
		String dateInString = new java.text.SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss")
		        .format(cal.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss");
		Date parsedDate = formatter.parse(dateInString);			
		Timesheet sheet = new Timesheet(emp, parsedDate,sheets);
		sheetlist.add(sheet);
		return null;
	}
	/**
	 * @param idx
	 * @return
	 */
	public String returnEdit(int idx){
		setIndex(idx);
		System.out.println("editView sent: "+idx);
		return "editTimesheet";
	}
	/**
	 * @param idx
	 * @return
	 */
	public String returnView(int idx){
		setIndex(idx);
		System.out.println("returnView sent: "+idx);
		return "viewTimesheet";
	}
	/**
	 * @return
	 */
	public int getIndex() {
		System.out.println("Sending index:"+index);
		return index;
	}
	/**
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}