package ca.bcit.infosys.employee;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
// or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
@Named ("tableData")// or @ManagedBean
@SessionScoped
public class TableData implements Serializable {
	/**
	 * 
	 */
	static int counter=1;
	private double saturday;
	private String newEmpName ="";
	private String newEmpUser ="";
	private String newEmpPass ="";
	private String changedPassword = "";
	private boolean validLogin = true;
	private boolean validPassChange = true;
	private boolean canAddEmployees = true;
	private boolean superUser = false;
	private Employee editableEmp;
	@Inject private Credentials credentials;
	
	   /**
	 * @return
	 */
	public String getNewEmpName() {
		   return newEmpName;
	   }
		/**
		 * @param newEmpName
		 */
		public void setNewEmpName(String newEmpName) {
			this.newEmpName = newEmpName;
		}
	   /**
	 * @return
	 */
	public String getChangedPassword() {
		   return newEmpName;
	   }
		/**
		 * @param pass
		 */
		public void setChangedPassword(String pass) {
			this.changedPassword = pass;
		}
		/**
		 * @return
		 */
		public boolean getValidLogin() {
			return validLogin;
		}
		/**
		 * @param validLogin
		 */
		public void setValidLogin(boolean validLogin) {
			this.validLogin = validLogin;
		}
		/**
		 * @return
		 */
		public boolean isCanAddEmployees() {
			return canAddEmployees;
		}
		/**
		 * @param canAddEmployees
		 */
		public void setCanAddEmployees(boolean canAddEmployees) {
			this.canAddEmployees = canAddEmployees;
		}
		/**
		 * @return
		 */
		public String getNewEmpUser() {
			return newEmpUser;
		}
		/**
		 * @param newEmpUser
		 */
		public void setNewEmpUser(String newEmpUser) {
			this.newEmpUser = newEmpUser;
		}
		/**
		 * @return
		 */
		public String getNewEmpPass() {
			return newEmpPass;
		}
		/**
		 * @param newEmpPass
		 */
		public void setNewEmpPass(String newEmpPass) {
			this.newEmpPass = newEmpPass;
		}
		/**
		 * @return
		 */
		public boolean isSuperUser() {
			return superUser;
		}
		/**
		 * @param isSuperUser
		 */
		public void setSuperUser(boolean isSuperUser) {
			this.superUser = isSuperUser;
		}
		/**
		 * @return
		 */
		public Employee getEditableEmp() {
			return editableEmp;
		}
		/**
		 * @param editableEmp
		 */
		public void setEditableEmp(Employee editableEmp) {
			this.editableEmp = editableEmp;
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
			this.saturday = saturday;
		}
	/**
	 * @return
	 */
	public Credentials getCredentials() {
		return credentials;
	}
	/**
	 * @param credentials
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	/**
	 * We use this HashMap to store our usernames and passwords
	 */
	private static final HashMap<String, String> users = new HashMap<String, String>();
	
	/**
	 * Our ArrayList of employees
	 */
	public static final ArrayList<Employee> employees = new ArrayList<Employee>(Arrays.asList(
		       new Employee("Bruce Link", counter++, "blink"),	
		       new Employee("Test User", counter++, "test")
	));
    public ArrayList<Employee> getEmployees() {
        return employees;
     }
	/**
	 * Constructor
	 */
	public TableData(){
		users.put("blink", "blink");
		users.put("test", "pass");
	}
	/**
	 * Returns a specific employee
	 */
	public Employee getOneEmp(String emp){
		for(int i=0;i<employees.size();i++){
			if(emp.equals(employees.get(i).getUserName())){
				return employees.get(i);
			}
		}
		return null;
	}
	/**
	 * Return the employee name*/
	public String getEmpName(){
		Employee tmp = getCurrentEmp();
		return tmp.getName();
	}
	/**Return the employee number
	 * */
	public int getEmpNum(){
		Employee tmp = getCurrentEmp();
		return tmp.getEmpNumber();
	}
	/**
	 * Returns the super user name to compare.
	 * @return
	 */
	public Employee getSuperUser(){
		String username ="";
		Properties property = new Properties();
		try{
			property.load(TableData.class.getResourceAsStream("superUser.properties"));
		} catch(Exception e){
			System.out.println("Something went wrong with the properties file!");
			return null;
		}
		username = property.getProperty("superUsername");
		return getOneEmp(username);
	}
	/**
	 * Checks if the person trying to login matches anything in our list of employees
	 * @param credential
	 * @return
	 */
	@AssertTrue
	public boolean verifyUser(Credentials credential){
		if(!(users.containsKey(credentials.getUserName()))){
			return false;
		}
		if(!(users.get(credential.getUserName()).equals(credential.getPassword()))){
			return false;
		}
		return true;
	}
	/**
	 * @return
	 */
	public String resetPass(){
		changePassword(getCurrentEmp());
		return confirmChange();
	}
	/**
	 * @param emp
	 * @return
	 */
	public String changePassword(Employee emp){
		setEditableEmp(emp);
		//go to change password xhtml page
		return null;
	}
	/**
	 * @return
	 */
	public String confirmChange(){
		if(newEmpPass.length() == 0 || changedPassword.length() == 0){
			setValidPassChange(false);
			return "resetPassword";
		}
		if(newEmpPass.equals(changedPassword)){
			users.replace(editableEmp.getUserName(), newEmpPass);
			newEmpPass="";
			changedPassword="";
			setValidPassChange(true);
			//employee xhtml page
			return "welcome";
		}
		//goes to employee xhtml page anyways, but doesn't change pass
		setValidPassChange(false);
		return "resetPassword";
	}
	
	/**
	 * Resets status of person who logged in and goes back to login page
	 * @return
	 */
	public String logout(){
		setSuperUser(false);
		setCanAddEmployees(true);
		setValidLogin(true);
		return "login";
	}
	/**
	 * @return
	 */
	public Employee getCurrentEmp(){
		String current = credentials.getUserName();
		Employee emp = getOneEmp(current);
		return emp;
	}
	/**
	 * makes the ArrayList of employees outputText
	 */
    public String save() {
       for (Employee name : employees) name.setEditable(false);
       return null;
    }

    /**
     * @param employee
     * @return
     */
    public String deleteEmp(Employee employee){
	   employees.remove(employee);
	   users.remove(employee.getUserName());
	   return null;
    }
    /**
     * @param guy
     * @return
     */
    public String addEmp(Employee guy){
	   if(guy != null){
		   employees.add(guy);
	   }
	   return null;
    }
   /**
    * Add an employee to our ArrayList of Employees
    * @return
    */
   public String addNewEmployee(){
	   String name = getNewEmpName();
	   String username = getNewEmpUser();
	   String password = getNewEmpPass();
	   int id = counter++;
	   //make sure they have each part of Employee filled
	   if(!(name.equals("")||name.equals(null))&& !(username.equals("")||username.equals(null))){
		   employees.add(new Employee(name, id, username));
		   //make sure they have a password
		   if(!(password.equals("")||password.equals(null))){
			   users.put(username, password);
		   }
	   }
	   setNewEmpName("");
	   setNewEmpUser("");
	   setNewEmpPass("");
	   return null;
   }
   /**
 * @return
 */
public String whichType(){
	   if(superUser){
		   //superuser xhtml page
		   return null;
	   } else{
		   setCanAddEmployees(false);
		   //normal xhtml page
		   return null;
	   }
   }
   /**
 * @return
 */
public String validateLogin(){
	   if(verifyUser(this.credentials)){
		   validLogin=true;
		   Employee superUserEmp = getSuperUser();
		   if(superUserEmp.getUserName().equals(credentials.getUserName())){
			   superUser = true;
		   }
		   //go to welcome xhtml page because they passed
		   return "welcome";
	   }
	   validLogin=false;
	   //wherever they go when they fail to log in
	   return "login";
   }
/**
 * @return
 */
public boolean isValidPassChange() {
	return validPassChange;
}
/**
 * @param validPassChange
 */
public void setValidPassChange(boolean validPassChange) {
	this.validPassChange = validPassChange;
}

}