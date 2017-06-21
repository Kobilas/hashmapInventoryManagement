package mkobilas.homework.classroomInventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Building class is used to store a HashMap object which contains the Classroom objects that represent the
 *   classrooms within that building. This class contains methods to add, remove, and get the reference to Classroom
 *   objects within this Building object. This Building object also implements the Serializable interface in order to
 *   allow the user to store information held within the HashMap in this Building object in a file when the user
 *   decides to exit the program.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      SBU ID: 111152838
 *      CSE214-R02
 */
public class Building implements Serializable{
    private static final long serialVersionUID = -7996162474342592672L;
    private HashMap<Integer, Classroom> classrooms;
    private int numClassrooms;
    /**
     * Empty constructor for this Building object which initializes the HashMap which stores all the Classroom objects
     *   as well as the int numClassrooms variable which is used to find the total number of Classroom objects held
     *   within the HashMap in this Building object.
     * @postcondition
     *      Creates a Building object with HashMap initialized to be empty, with the keys being Integer objects and the
     *        values of those keys being Classroom objects. The number of Classroom objects in the HashMap is
     *        initialized to 0.
     */
    public Building(){
        classrooms = new HashMap<Integer, Classroom>();
        numClassrooms = 0;
    }
    /**
     * Adds a Classroom object to the HashMap in this Building object. Takes parameters in the form of the key to be
     *   used in storing the second parameter which is the value.
     * @param roomNumber
     *      int roomNumber is the parameter used to determine where the value gets stored in this Building object's
     *        HashMap.
     * @param newClassroom
     *      Classroom newClassroom is the parameter that is used to specify the classrooms in this building. It is
     *        stored in a position in this Building object's HashMap in accordance with where the key gets assigned a
     *        position.
     * @precondition
     *      Argument int roomNumber must be greater than or equal to zero and cannot already exist as a key in the
     *        HashMap object.
     * @postcondition
     *      HashMap classrooms now has Classroom newClassroom stored in it in a position according to where
     *        int roomNumber gets placed according to a formula.
     * @throws IllegalArgumentException
     *      Throws an exception if int roomNumber is negative or if it is already stored in this Building object's
     *        HashMap.
     */
    public void addClassroom(int roomNumber, Classroom newClassroom){
        if(roomNumber <= -1)
            throw new IllegalArgumentException("Argument int roomNumber must be nonnegative.");
        if(classrooms.containsKey(roomNumber))
            throw new IllegalArgumentException("Argument int roomNumber already exists as a key within the HashMap.");
        classrooms.put(roomNumber, newClassroom);
        numClassrooms++ ;
    }
    /**
     * Accessor method for the Classroom objects that are stored in this Building object's HashMap. Takes the key that
     *   was used to store the Classroom object in the HashMap, which is the int roomNumber of the classroom in the
     *   actual building.
     * @param roomNumber
     *      int roomNumber is the key to search for in this Building object's HashMap.
     * @return
     *      Returns a reference to the Classroom object that is stored according to the int roomNumber used to 
     *        originally store it. If no object with that particular key is found, then the method returns null.
     */
    public Classroom getClassroom(int roomNumber){
        return classrooms.get(roomNumber);
    }
    /**
     * Removes a Classroom object from the HashMap object in this Building object according to the key used to
     *   originally store it which was in the form of an int roomNumber.
     * @param roomNumber
     *      int roomNumber is the key used to store the Classroom object in this Building object's HashMap.
     * @precondition
     *      int roomNumber must be greater than or equal to zero, and must already exist as a key in the HashMap
     *        object.
     * @postcondition
     *      HashMap classrooms now has the Classroom object that was assigned an index with int roomNumber removed
     *        from it.
     * @throws IllegalArgumentException
     *      Throws an exception if the int roomNumber is negative or if it does not exist as a key in the HashMap.
     */
    public void removeClassroom(int roomNumber){
        if(roomNumber <= -1)
            throw new IllegalArgumentException("Argument int roomNumber must be nonnegative.");
        if(!(classrooms.containsKey(roomNumber)))
            throw new IllegalArgumentException("Argument int roomNumber does not exist as a key within the HashMap.");
        classrooms.remove(roomNumber);
        numClassrooms--;
    }
    /**
     * Used to find all of the unique AV equipment in all the classrooms in this building. All objects in the String
     *   array are unique and there are no repeats of the 
     * @return
     *      Returns String[] with the returned object being the sum of all the AV equipment arrays in all the 
     *        Classroom objects in this Building object.
     */
    public String[] getAVEquipment(){
        ArrayList<String> result = new ArrayList<String>();
        Integer[] rooms = Arrays.copyOf(classrooms.keySet().toArray(), classrooms.keySet().toArray().length,
          Integer[].class);
        String[] tempAVEquipment;
        for(int i = 0; i < rooms.length; i++ ){
            tempAVEquipment = getClassroom(rooms[i]).getAVEquipmentList();
            if(tempAVEquipment != null)
                for(int j = 0; j < tempAVEquipment.length; j++ )
                    if(!(result.contains(tempAVEquipment[j])))
                        result.add(tempAVEquipment[j]);
        }
        return Arrays.copyOf(result.toArray(), result.size(), String[].class);
    }
    /**
     * Used to find all of the room numbers of the Classroom objects in this Building object. Used typically to find
     *   any similarities between classrooms or to search for a classroom according to a specification within each
     *   Building object that was created by the user.
     * @return
     *      Returns an Integer array containing all of the keys in the HashMap object in this Building object.
     */
    public Integer[] getClassroomKeyList(){
        Integer[] result = Arrays.copyOf(classrooms.keySet().toArray(), classrooms.keySet().toArray().length,
          Integer[].class);
        return result;
    }
    /**
     * Used to find all of the room numbers of the Classroom objects in this Building object, and formats them
     *   properly with commas between each room number so that it may be printed uniformly if needed. 
     * @return
     *      Returns a String containing all of the room numbers used to store the Classroom objects in the HashMap.
     */
    public String getClassroomKeyListString(){
        StringBuilder result = new StringBuilder();
        Integer[] keySet = getClassroomKeyList();
        for(int i = 0; i < keySet.length; i++ ){
            result.append(keySet[i]);
            if((i+1) < keySet.length)
                result.append(", ");
        }
        return result.toString();
    }
    /**
     * Accessor method for int numClassrooms.
     * @return
     *      Returns int numClassrooms which is the number of Classroom objects that are currently stored in this
     *        Building object's HashMap.
     */
    public int getNumClassrooms(){
        return numClassrooms;
    }
    /**
     * Mutator method for int numClassrooms. It is not typically used since changing it may cause the program to get
     *   interrupted.
     * @param newNumClassrooms
     *      Sets int numClassrooms to int newNumClassrooms.
     * @precondition
     *      int newNumClassrooms must be greater than or equal to zero.
     * @postcondition
     *      int numClassroom is set to int newNumClassrooms.
     * @throws IllegalArgumentException
     *      Throws an exception if int newNumClassrooms is negative.
     */
    public void setNumClassrooms(int newNumClassrooms){
        if(newNumClassrooms < 0)
            throw new IllegalArgumentException("Argument int newNumClassroom must be nonnegative.");
        numClassrooms = newNumClassrooms;
    }
}
