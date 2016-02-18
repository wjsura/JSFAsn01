package ca.bcit.infosys.employee;

import java.io.Serializable;

public class Name implements Serializable {
   private String first;
   private String last;
   private boolean editable;

   public Name(String first, String last) {
      this.first = first;
      this.last = last;
   }

   /**
 * @param newValue
 */
public void setFirst(String newValue) { first = newValue; }
   /**
 * @return
 */
public String getFirst() { return first; }

   /**
 * @param newValue
 */
public void setLast(String newValue) { last = newValue; }     
   /**
 * @return
 */
public String getLast() { return last; }
   
   /**
 * @return
 */
public boolean isEditable() { return editable; }
   /**
 * @param newValue
 */
public void setEditable(boolean newValue) { editable = newValue; }
}