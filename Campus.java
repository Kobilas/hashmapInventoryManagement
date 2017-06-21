package mkobilas.homework.classroomInventory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Campus class is used to create an object of type Campus which is used to store a HashMap object containing all
 *   of the Building objects that are on the campus. The key used to store the Building object values is the String
 *   name of the actual building. This class also contains methods to add, remove, and get the reference to the 
 *   different buildings that can be stored within the HashMap object. This class implements the Serializable interface
 *   which allows the information of the object to be stored in a file upon the exiting of the program.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      SBU ID: 111152838
 *      CSE214-R02
 */
public class Campus implements Serializable{
    private static final long serialVersionUID = 2166906637227106314L;
    private HashMap<String, Building> buildings;
    private int numBuildings;
    /**
     * Empty constructor for objects of the type Campus. The constructor initializes the HashMap object used to store
     *   the Building objects, as well as the int numBuildings variable which is used  to count the number of Building
     *   objects currently stored within the HashMap.
     * @postcondition
     *      Creates an object of type Campus with the HashMap object initialized as well as numBuildings being
     *        initialized to zero.
     */
    public Campus(){
        buildings = new HashMap<String, Building>();
        numBuildings = 0;
    }
    /**
     * Used to add more Building objects to the HashMap object in this Campus object. The Building objects are stored
     *   in the HashMap according to the position assigned by the String name of the buildings.
     * @param buildingName
     *      String buildingName is the key that will be used to decide the location of the Building object in the
     *        HashMap.
     * @param newBuilding
     *      Building newBuilding is the value that the key will be storing in the HashMap.
     * @precondition
     *      String buildingName cannot be null and it cannot already exist as a key within the HashMap.
     * @postcondition
     *      Building newBuilding now exists as a value within the HashMap under the key, which is the String
     *        buildingName.
     * @throws IllegalArgumentException
     *      Throws an exception if String buildingName is null or already exists as a key within the HashMap.
     */
    public void addBuilding(String buildingName, Building newBuilding){
        if(buildingName ==  null)
            throw new IllegalArgumentException("Argument String buildingName cannot be null.");
        if(buildings.containsKey(buildingName))
            throw new IllegalArgumentException("Argument String buildingName already exists as a key within"
              + " the HashMap.");
        buildings.put(buildingName, newBuilding);
        numBuildings++ ;
    }
    /**
     * Accessor method for the Building objects that are held within the HashMap. They are accessed based on the key
     *   that was used to originally insert them into the HashMap, which is the building's name as a String.
     * @param buildingName
     *      String buildingName is the key that was used to insert the Building object into the HashMap originally.
     * @return
     *      Returns a reference to the Building object that has the name of buildingName. If the buildingName does not
     *        yet exist as a key within the HashMap, then the method returns null.
     */
    public Building getBuilding(String buildingName){
        return buildings.get(buildingName);
    }
    /**
     * Used to remove Building objects from the HashMap based on the key used to insert it. The method is called with
     *   the building's name that is to be removed, and that Building object is then removed.
     * @param buildingName
     *      String buildingName is the key used to insert the Building object into the HashMap, as well as the actual
     *        name of the building.
     * @precondition
     *      String buildingName cannot be null, and it must exist as a key within the HashMap.
     * @postcondition
     *      The value that String buildingName was acting as a key for is removed from the HashMap object of this
     *        Campus object.
     * @throws IllegalArgumentException
     *      Throws an exception if String buildingName is null or does not exist as a key within the HashMap.
     */
    public void removeBuilding(String buildingName){
        if(buildingName ==  null)
            throw new IllegalArgumentException("Argument String buildingName cannot be null.");
        if(!(buildings.containsKey(buildingName)))
            throw new IllegalArgumentException("Argument String buildingName does not exist as a key within"
              + " the HashMap.");
        buildings.remove(buildingName);
        numBuildings--;
    }
    /**
     * Accessor method for a String array of the keys that have been used to enter Building objects into the HashMap
     *   object.
     * @return
     *      Returns a String[] containing all of the building names in the HashMap object that are acting as keys for
     *        the Building objects that are stored in the HashMap.
     */
    public String[] getBuildingKeyList(){
        String[] result = Arrays.copyOf(buildings.keySet().toArray(), buildings.keySet().toArray().length,
          String[].class);
        return result;
    }
    /**
     * Accessor method for the number of buildings currently held in the HashMap.
     * @return
     *      Returns the int numBuildings, which is the number of Building objects currently held within this Campus
     *        object.
     */
    public int getNumBuildings(){
        return numBuildings;
    }
    /**
     * Mutator method for the number of buildings currently held in the HashMap. Typically not used, since changing the
     *   value will probably cause certain sections of code not to function properly.
     * @param newNumBuildings
     *      int newNumBuildings is the new number of buildings that will be listed as the number of buildings currently
     *        in this Campus object.
     * @precondition
     *      int newNumBuildings must be greater than or equal to zero.
     * @postcondition
     *      int numBuildings is set to int newNumBuildings.
     * @throws IllegalArgumentException
     *      Throws an exception if int newNumBuildings is negative.
     */
    public void setNumBuildings(int newNumBuildings){
        if(numBuildings < 0)
            throw new IllegalArgumentException("Argument int newNumBuildings must be nonnegative.");
        numBuildings = newNumBuildings;
    }
}
