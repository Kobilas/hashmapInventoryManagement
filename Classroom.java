package mkobilas.homework.classroomInventory;

import java.io.Serializable;

/**
 * The Classroom class is used to hold the information regarding the inventory of the classrooms within the buildings
 *   in which these objects are held. The information held in the Classroom consists of the number of seats in the
 *   classroom, whether or not it has a whiteboard, whether or not it has a chalkboard, as well as the AV equipment
 *   that can be found in the classroom. The class also contains methods for getting and setting each of these values
 *   if the class is an object in another class. This class implements the Serializable interface in order to be able
 *   to store the information held within this class in a file should the user choose to shut down the program.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      SBU ID: 111152838
 *      CSE214-R02
 */
public class Classroom implements Serializable{
    private static final long serialVersionUID = -2557295052783549111L;
    private boolean hasWhiteboard;
    private boolean hasChalkboard;
    private int numSeats;
    private String[] AVEquipmentList;
    /**
     * This is an empty constructor for objects of type Classroom which initializes the boolean values regarding
     *   whether or not this classroom has a chalkboard and whiteboard to false. It also initializes the number of
     *   seats to 0, and the AV equipment list to null.
     * @postcondition
     *      Creates an object of type Classroom with all variables initialized to either false, null, or 0.
     */
    public Classroom(){
        hasWhiteboard = false;
        hasChalkboard = false;
        numSeats = 0;
        AVEquipmentList = null;
    }
    /**
     * This is a constructor for objects of type Classroom which contains parameters for all the variables held within
     *   this class. The variables in this Classroom are initialized to the user input.
     * @param initNumSeats
     *      int initNumSeats is the initial number of seats in the classroom, which is what int numSeats is
     *        initialized to.
     * @param initAVEquipmentList
     *      String[] initAVEquipmentList is the list of AV equipment that is in the classroom in the form of an array,
     *        with each individual item in its own index. This is what String[] AVEquipmentList will be initialized to.
     * @param initHasWhiteboard
     *      boolean initHasWhiteboard is true if the classroom has a whiteboard, and false otherwise. This is the value
     *        which hasWhiteboard will be initialized to.
     * @param initHasChalkboard
     *      boolean initHasChalkboard is true if the classroom has a chalkboard, and false otherwise. This is the value
     *        which hasChalkboard will be initialized to.
     * @precondition
     *      initNumSeats must be greater than or equal to zero.
     * @postcondition
     *      Creates an object of type Classroom with the variables held within initialized to user input for the
     *        parameters in this constructor method.
     * @throws IllegalArgumentException
     *      Throws an exception if int initNumSeats is negative.
     */
    public Classroom(int initNumSeats, String[] initAVEquipmentList, boolean initHasWhiteboard,
      boolean initHasChalkboard){
        if(initNumSeats < 0)
            throw new IllegalArgumentException("Argument int initNumSeats must be nonnegative.");
        hasWhiteboard = initHasWhiteboard;
        hasChalkboard = initHasChalkboard;
        numSeats = initNumSeats;
        AVEquipmentList = new String[initAVEquipmentList.length];
        for(int i = 0; i < AVEquipmentList.length; i++ )
            AVEquipmentList[i] = initAVEquipmentList[i];
    }
    /**
     * Accessor method for boolean hasWhiteboard.
     * @return
     *      Returns true if this Classroom has a whiteboard, and false otherwise.
     */
    public boolean getHasWhiteboard(){
        return hasWhiteboard;
    }
    /**
     * Mutator method for boolean hasWhiteboard.
     * @param newHasWhiteboard
     *      boolean newHasWhiteboard is the value to set boolean hasWhiteboard to.
     * @postcondition
     *      Sets boolean hasWhiteboard to boolean newHasWhiteboard.
     */
    public void setHasWhiteboard(boolean newHasWhiteboard){
        hasWhiteboard = newHasWhiteboard;
    }
    /**
     * Accessor method for boolean hasChalkboard.
     * @return
     *      Returns true if this Classroom has a chalkboard, and false otherwise.
     */
    public boolean getHasChalkboard(){
        return hasChalkboard;
    }
    /**
     * Mutator method for boolean hasChalkboard.
     * @param newHasChalkboard
     *      boolean newHasChalkboard is the value to set boolean hasChalkboard to.
     * @postcondition
     *      Sets boolean hasChalkboard to boolean newHasChalkboard.
     */
    public void setHasChalkboard(boolean newHasChalkboard){
        hasChalkboard = newHasChalkboard;
    }
    /**
     * Accessor method for int numSeats.
     * @return
     *      Returns int numSeats, which is the number of seats in this Classroom.
     */
    public int getNumSeats(){
        return numSeats;
    }
    /**
     * Mutator method for int numSeats.
     * @param newNumSeats
     *      int newNumSeats is the new number of seats to set int numSeats to.
     * @precondition
     *      int newNumSeats must be greater than or equal to zero.
     * @postcondition
     *      Sets int numSeats to int newNumSeats.
     * @throws IllegalArgumentException
     *      Throws an exception if int newNumSeats is negative.
     */
    public void setNumSeats(int newNumSeats){
        if(newNumSeats < 0)
            throw new IllegalArgumentException("Argument int newNumSeats must be nonnegative.");
        numSeats = newNumSeats;
    }
    /**
     * Accessor method for String[] AVEquipmentList.
     * @return
     *      Returns the array containing the AV equipment in this classroom, or null if String[] AVEquipmentList has
     *        not yet been initialized.
     */
    public String[] getAVEquipmentList(){
        if(AVEquipmentList ==  null)
            return null;
        else
            return AVEquipmentList;
    }
    /**
     * Returns a formatted String of the contents of String[] AVEquipmentList separated by commas.
     * @return
     *      Returns a String of the contents of String[] AVEquipmentList, with each item in each index separated by a
     *        comma in order to be printed if need be.
     */
    public String getAVEquipmentString(){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < AVEquipmentList.length; i++ ){
            result.append(AVEquipmentList[i]);
            if(!((i+1) ==  AVEquipmentList.length))
                result.append(", ");
        }
        return result.toString();
    }
    /**
     * Accessor method for String[] AVEquipmentList.
     * @param newAVEquipmentList
     *      String[] AVEquipmentList is set to String[] newAVEquipmentList. If the parameter is null, then there is no
     *        AV equipment in this Classroom.
     * @postcondition
     *      Sets String[] AVEquipmentList to String[] newAVEquipmentList.
     */
    public void setAVEquipmentList(String[] newAVEquipmentList){
        AVEquipmentList = newAVEquipmentList;
    }
}
