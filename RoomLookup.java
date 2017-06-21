package mkobilas.homework.classroomInventory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Driver class that is used to manipulate the other object classes created throughout the program. This class features
 *   several menus that are used to manipulate the information held within the different objects that are created
 *   such as the Campus, its Buildings, and the Buildings' Classrooms. These different objects are held within HashMaps
 *   which serve as the system for organization in order to more easily access the different objects, and then be able
 *   to manipulate them.
 * @author Matthew Kobilas
 *      matthew.kobilas@stonybrook.edu
 *      SBU ID: 111152838
 *      CSE214-R02
 */
public class RoomLookup implements Serializable{
    private static final long serialVersionUID = -4413530464743215484L;
    //Used to read user input in response to the menus displayed.
    private static Scanner input;
    //Serve as temporary values for the user-input values so that the program knows which parts of the code to access.
    private static String answer, selectedBuilding, tempAVEquipment;
    private static int tempRoom, tempSeats;
    //Used to access different if-then statements according to past user input.
    private static boolean invalidAnswer = false, tempWhiteboard, tempChalkboard, printedSomething = false;
    //Used to temporarily hold the building names that are found in the Campus object's HashMap.
    private static String[] buildings;
    //Used to temporarily hold the classroom numbers that are found in a Building object's HashMap.
    private static Integer[] classrooms;
    //Used to write to String objects since regular String objects are more inefficient while in a loop.
    private static StringBuilder toPrint;
    //Main object that is being manipulate which stores the different Building objects which store the different
      //Classroom objects.
    private static Campus campus;
    //FileOutputStream/FileInputStream used to save/load files which ObjectOutputStream/ObjectInputStream write/read as
      //Objects.
    private static FileOutputStream fileOut;
    private static ObjectOutputStream outStream;
    private static FileInputStream fileIn;
    private static ObjectInputStream inStream;
    /**
     * Contains the main loop from which the menu is displayed and which will continuously ask for input unless the
     *   user specifies that the program should quit and save/not save. There are loops held within the main loop which
     *   are used to continuously ask for input in the case of the displaying of other menus. There are other methods
     *   used in conjunction with this main method in order to display other menus and do specific functions such as
     *   save and load information.
     * @param args
     *      String[] args is not typically used, and is mostly just there to classify this method as an actual main 
     *        Java method.
     */
    public static void main(String[] args){
        System.out.println("Welcome to SBGetARoom, Stony Brook's premium room lookup system.\n");
        System.out.println("Load saved data? (Y/N)");
        //Initializes the method of input from the user.
        input = new Scanner(System.in);
        //Prompts the user for whether or not they would like to load a previous save file, or to start anew.
        loadLayerLoop: while(true){
            if(invalidAnswer){
                System.out.print("\nPlease select a valid option: ");
                invalidAnswer = false;
            }
            else
                System.out.print("Please select an option: ");
            answer = input.nextLine();
            System.out.println("");
            switch(answer.toLowerCase()){
                case("y"):{
                    if(!(bootSequence()))
                        campus = new Campus();
                    break loadLayerLoop;
                }
                case("n"):{
                    System.out.println("File not loaded. Creating an empty campus.");
                    campus = new Campus();
                    break loadLayerLoop;
                }
                default:{
                    invalidAnswer = true;
                    continue loadLayerLoop;
                }
            }
        }
        //Initializes the method for holding Strings in a loop since a regular String is more inefficient.
        toPrint = new StringBuilder();
        //The main loop which will run until the program is terminated or the user quits.
        topLayerLoop: while(true){
            //Determines if the user entered something that did not function as an actual option.
            if(invalidAnswer){
                System.out.print("Please select a valid option: ");
                invalidAnswer = false;
            }
            else{
                printMainMenu();
                System.out.print("Please select an option: " );
            }
            answer = input.nextLine();
            if(answer.length() != 1)
                invalidAnswer = true;
            System.out.println();
            //Switch-case block to run different functions according to what the user inputs.
            switch(answer.toLowerCase()){
                //Adds a Building object to the Campus object.
                case("a"):{
                    System.out.print("Please enter a building name: ");
                    answer = input.nextLine();
                    System.out.println("");
                    try{
                        campus.addBuilding(answer, new Building());
                    }
                    catch(IllegalArgumentException err){
                        System.out.println("Building " + answer + " already exists or value entered was null.");
                        continue topLayerLoop;
                    }
                    System.out.println("Building " + answer + " added.");
                    continue topLayerLoop;
                }
                //Removes a Building object from the Campus object.
                case("d"):{
                    System.out.print("Please enter a building name: ");
                    answer = input.nextLine();
                    System.out.println("");
                    try{
                        campus.removeBuilding(answer);
                    }
                    catch(IllegalArgumentException err){
                        System.out.println("Building " + answer + " does not exist or value entered was null.");
                        continue topLayerLoop;
                    }
                    System.out.println("Building " + answer + " removed.");
                    continue topLayerLoop;
                }
                //Edits a Building object in the Campus object, to either add more Classroom objects, remove them, or 
                  //edit the Classroom objects themselves.
                case("e"):{
                    System.out.print("Please enter a building name: ");
                    selectedBuilding = input.nextLine();
                    System.out.println("");
                    if(campus.getBuilding(selectedBuilding) ==  null){
                        System.out.println("Building " + selectedBuilding + " does not exist.");
                        continue topLayerLoop;
                    }
                    System.out.println("Building " + selectedBuilding + " selected: ");
                    printEditBuildingMenu();
                    //Loop used to edit the selected Building object. Loops until either a function is successfully
                      //executed or until the user quits or terminates the program.
                    editLayerLoop: while(true){
                        if(invalidAnswer){
                            System.out.print("Please select a valid option: ");
                            invalidAnswer = false;
                        }
                        else{
                            System.out.print("Please select an option: ");
                        }
                        answer = input.nextLine();
                        System.out.println("");
                        //Switch-case block for the different options that can be found in the editing menu.
                        switch(answer.toLowerCase()){
                            //Adds a Classroom object to the given Building object.
                            case("a"):{
                                System.out.print("Please enter room number or press enter to cancel: ");
                                answer = input.nextLine();
                                if(answer.isEmpty()){
                                    System.out.println("\nMoving back to editing menu.");
                                    continue editLayerLoop;
                                }
                                try{
                                    tempRoom = Integer.parseInt(answer);
                                    if(tempRoom < 0){
                                        System.out.println("\nValue entered was not a nonnegative integer.");
                                        continue editLayerLoop;
                                    }
                                    if(campus.getBuilding(selectedBuilding).getClassroom(tempRoom) != null){
                                        System.out.println("\nClassroom " + selectedBuilding + " " + tempRoom
                                          + " already exists.");
                                    }
                                    System.out.print("\nPlease enter number of seats: ");
                                    tempSeats = Integer.parseInt(input.nextLine());
                                    if(tempSeats < 0){
                                        System.out.println("\nValue entered was not a nonnegative integer.");
                                        continue editLayerLoop;
                                    }
                                }
                                catch(NumberFormatException err){
                                    System.out.println("\nValue entered was not a nonnegative integer.");
                                    continue editLayerLoop;
                                }
                                System.out.print("\nPlease enter AV Equipment (separated by commas): ");
                                tempAVEquipment = input.nextLine();
                                System.out.print("\nDoes it have a whiteboard? (Y/N): ");
                                answer = input.nextLine();
                                if(answer.toLowerCase().equals("y"))
                                    tempWhiteboard = true;
                                else
                                    if(answer.toLowerCase().equals("n"))
                                        tempWhiteboard = false;
                                    else{
                                        System.out.println("\nInput must be in the form (Y/N) (not case-sensitive).");
                                        continue editLayerLoop;
                                    }
                                System.out.print("\nDoes it have a chalkboard? (Y/N): ");
                                answer = input.nextLine();
                                if(answer.toLowerCase().equals("y"))
                                    tempChalkboard = true;
                                else
                                    if(answer.toLowerCase().equals("n"))
                                        tempChalkboard = false;
                                    else{
                                        System.out.println("\nInput must be in the form (Y/N) (not case-sensitive).");
                                        continue editLayerLoop;
                                    }
                                System.out.println("");
                                campus.getBuilding(selectedBuilding).addClassroom(tempRoom,
                                  new Classroom(tempSeats, tempAVEquipment.split(","), tempWhiteboard, tempChalkboard));
                                System.out.println("Room " + selectedBuilding + " " + tempRoom + " added.");
                                continue topLayerLoop;
                            }
                            //Removes a Classroom object from the given Building object.
                            case("d"):{
                                System.out.print("Please enter room number or press enter to cancel: ");
                                answer = input.nextLine();
                                if(answer.isEmpty()){
                                    System.out.println("\nMoving back to editing menu.");
                                    continue editLayerLoop;
                                }
                                try{
                                    tempRoom = Integer.parseInt(answer);
                                    if(tempRoom < 0){
                                        System.out.println("\nValue entered was not a nonnegative integer.");
                                        continue editLayerLoop;
                                    }
                                }
                                catch(NumberFormatException err){
                                    System.out.println("\nValue entered was not a nonnegative integer.");
                                    continue editLayerLoop;
                                }
                                System.out.println("");
                                try{
                                    campus.getBuilding(selectedBuilding).removeClassroom(tempRoom);
                                }
                                catch(IllegalArgumentException err){
                                    System.out.println("Classroom not found.");
                                    continue editLayerLoop;
                                }
                                System.out.println("Classroom " + tempRoom + " removed.");
                                continue topLayerLoop;
                            }
                            //Edits a Classroom object that is in the given Building object.
                            case("e"):{
                                System.out.print("Please enter room number or press enter to cancel: ");
                                answer = input.nextLine();
                                if(answer.isEmpty()){
                                    System.out.println("\nMoving back to editing menu.");
                                    continue editLayerLoop;
                                }
                                try{
                                    tempRoom = Integer.parseInt(answer);
                                    if(campus.getBuilding(selectedBuilding).getClassroom(tempRoom) ==  null){
                                        System.out.println("\nClassroom " + selectedBuilding + " " + tempRoom
                                          + " does not exist.");
                                        continue editLayerLoop;
                                    }
                                    System.out.println("\nOld number of seats: "
                                      + campus.getBuilding(selectedBuilding).getClassroom(tempRoom).getNumSeats());
                                    System.out.print("Please enter new number of seats or press enter to skip: ");
                                    answer = input.nextLine();
                                    if(!(answer.isEmpty())){
                                        tempSeats = Integer.parseInt(answer);
                                        if(tempSeats < 0){
                                            System.out.println("\nValue entered was not a nonnegative integer.");
                                            continue editLayerLoop;
                                        }
                                        campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                          .setNumSeats(tempSeats);
                                        System.out.print("\nNumber of seats updated to " + tempSeats + ".");
                                    }
                                }
                                catch(NumberFormatException err){
                                    System.out.println("\nValue entered was not a nonnegative integer.");
                                    continue editLayerLoop;
                                }
                                System.out.println("\nOld AV Equipment: "
                                  + campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                  .getAVEquipmentString());
                                System.out.print("Please enter new AV Equipment (separated by commas) or press " 
                                  + "enter to skip: ");
                                answer = input.nextLine();
                                if(!(answer.isEmpty())){
                                    tempAVEquipment = answer;
                                    campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                      .setAVEquipmentList(tempAVEquipment.split(","));
                                    System.out.print("\nAV Equipment updated to " + tempAVEquipment + ".");
                                }
                                System.out.print("\nDoes it have a whiteboard? (Y/N) or press enter to skip: ");
                                answer = input.nextLine();
                                if(!(answer.isEmpty())){
                                    if(answer.toLowerCase().equals("y"))
                                        campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                          .setHasWhiteboard(true);
                                    else
                                        if(answer.toLowerCase().equals("n"))
                                            campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                              .setHasWhiteboard(false);
                                        else{
                                            System.out.println("\nInput must be in the form (Y/N)"
                                              + "(not case-sensitive).");
                                            continue editLayerLoop;
                                        }
                                }
                                System.out.print("\nDoes it have a chalkboard? (Y/N) or press enter to skip: ");
                                answer = input.nextLine();
                                if(!(answer.isEmpty())){
                                    if(answer.toLowerCase().equals("y"))
                                        campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                          .setHasChalkboard(true);
                                    else
                                        if(answer.toLowerCase().equals("n"))
                                            campus.getBuilding(selectedBuilding).getClassroom(tempRoom)
                                              .setHasChalkboard(false);
                                        else{
                                            System.out.println("\nInput must be in the form (Y/N)"
                                              + "(not case-sensitive).");
                                            continue editLayerLoop;
                                        }
                                }
                                System.out.println(selectedBuilding + " " + tempRoom + " updated.");
                                continue topLayerLoop;
                            }
                            //Quits the editing menu and moves back to the main menu.
                            case("q"):{
                                System.out.println("Moving back to main menu.");
                                continue topLayerLoop;
                            }
                            //Indicates that the user did not enter a valid option.
                            default:{
                                invalidAnswer = true;
                                continue editLayerLoop;
                            }
                        }
                    }
                }
                //Prints the information of a Classroom object found in a Building object.
                case("f"):{
                    System.out.print("Please enter a building name followed by a space and a room number: ");
                    answer = input.nextLine();
                    try{
                        printRoomDetails(answer.split(" ")[0], Integer.parseInt(answer.split(" ")[1]));
                    }
                    catch(NullPointerException err){
                        System.out.println("\nArgument entered was formatted incorrectly or the given "
                          + "building/room does not exist.");
                        continue topLayerLoop;
                    }
                    catch(ArrayIndexOutOfBoundsException err){
                        System.out.println("\nArgument entered was formatted incorrectly.");
                        continue topLayerLoop;
                    }
                    continue topLayerLoop;
                }
                //Searches for a according to user-input specifications.
                case("s"):{
                    printSearchRoomsMenu();
                    buildings = campus.getBuildingKeyList();
                    //Loop used for continuously reading input for searching for rooms and does not break the loop
                      //until the user terminates or quits the menu, or a function successfully completes.
                    searchLayerLoop: while(true){
                        if(invalidAnswer){
                            System.out.print("Please select a valid option: ");
                            invalidAnswer = false;
                        }
                        else{
                            System.out.print("Please select an option: ");
                        }
                        answer = input.nextLine();
                        toPrint.setLength(0);
                        //Switch-case block for the different search menu options.
                        switch(answer.toLowerCase()){
                            //Search and display any Classrooms that have Chalkboards.
                            case("c"):{
                                for(int i = 0; i < buildings.length; i++ ){
                                    toPrint.setLength(0);
                                    printedSomething = false;
                                    classrooms = campus.getBuilding(buildings[i]).getClassroomKeyList();
                                    toPrint.append("\n    " + buildings[i] + ": ");
                                    for(int j = 0; j < classrooms.length; j++ ){
                                        if(campus.getBuilding(buildings[i]).getClassroom(classrooms[j])
                                          .getHasChalkboard()){
                                            toPrint.append(classrooms[j].toString());
                                            printedSomething = true;
                                        }
                                        if((j+1) < classrooms.length)
                                            if((campus.getBuilding(buildings[i]).getClassroom(classrooms[j+1])
                                                    .getHasChalkboard()) && (printedSomething))
                                                toPrint.append(", ");
                                    }
                                    if(printedSomething)
                                        System.out.println(toPrint.toString());
                                }
                                continue topLayerLoop;
                            }
                            //Search and display any Classrooms that have Whiteboards.
                            case("w"):{
                                for(int i = 0; i < buildings.length; i++ ){
                                    toPrint.setLength(0);
                                    printedSomething = false;
                                    classrooms = campus.getBuilding(buildings[i]).getClassroomKeyList();
                                    toPrint.append("\n    " + buildings[i] + ": ");
                                    for(int j = 0; j < classrooms.length; j++ ){
                                        if(campus.getBuilding(buildings[i]).getClassroom(classrooms[j])
                                          .getHasWhiteboard()){
                                            toPrint.append(classrooms[j].toString());
                                            printedSomething = true;
                                        }
                                        if((j+1) < classrooms.length)
                                            if((campus.getBuilding(buildings[i]).getClassroom(classrooms[j+1])
                                                    .getHasWhiteboard()) && (printedSomething))
                                                toPrint.append(", ");
                                    }
                                    if(printedSomething)
                                        System.out.println(toPrint.toString());
                                }
                                continue topLayerLoop;
                            }
                            //Search and display any Classrooms that have a user-input type of AV equipment.
                            case("a"):{
                                System.out.print("\nPlease enter a keyword: ");
                                answer = input.nextLine();
                                for(int i = 0; i < buildings.length; i++ ){
                                    toPrint.setLength(0);
                                    printedSomething = false;
                                    classrooms = campus.getBuilding(buildings[i]).getClassroomKeyList();
                                    toPrint.append("\n    " + buildings[i] + ": ");
                                    for(int j = 0; j < classrooms.length; j++ ){
                                        if(campus.getBuilding(buildings[i]).getClassroom(classrooms[j])
                                          .getAVEquipmentString().toLowerCase().contains(answer.toLowerCase())){
                                            toPrint.append(classrooms[j].toString());
                                            printedSomething = true;
                                        }
                                        if((j+1) < classrooms.length)
                                            if((campus.getBuilding(buildings[i]).getClassroom(classrooms[j+1])
                                                    .getAVEquipmentString().toLowerCase()
                                                    .contains(answer.toLowerCase())) && (printedSomething))
                                                toPrint.append(", ");
                                    }
                                    if(printedSomething)
                                        System.out.println(toPrint.toString());
                                }
                                continue topLayerLoop;
                            }
                            //Moves the user back to the main menu
                            case("q"):{
                                System.out.println("Moving back to the main menu");
                                continue topLayerLoop;
                            }
                            //Used to indicate that the user input something incorrectly.
                            default:{
                                invalidAnswer = true;
                                continue searchLayerLoop;
                            }
                        }
                    }
                }
                //Prints the list of Building objects in this Campus object as well as any Classroom objects that they
                  //have.
                case("c"):{
                    buildings = campus.getBuildingKeyList();
                    for(int i = 0; i < buildings.length; i++ ){
                        if(i ==  0)
                            System.out.print("    " + buildings[i] + ": ");
                        else
                            System.out.print("\n    " + buildings[i] + ": ");
                        classrooms = campus.getBuilding(buildings[i]).getClassroomKeyList();
                        if(classrooms.length ==  0)
                            System.out.print("empty");
                        else
                            for(int j = 0; j < classrooms.length; j++ ){
                                System.out.print(classrooms[j]);
                                if(j+1 < classrooms.length)
                                    System.out.print(", ");
                            }
                        System.out.println("");
                    }
                    continue topLayerLoop;
                }
                //Prints the information of a Building object including any information about the Classroom objects
                  //held within, such as the total number of seats.
                case("l"):{
                    System.out.print("Please enter a building name: ");
                    answer = input.nextLine();
                    System.out.println("");
                    try{
                        printBuildingDetails(answer);
                    }
                    catch(NullPointerException err){
                        System.out.println("Value entered is null or Building " + answer + " does not exist.");
                        continue topLayerLoop;
                    }
                    catch(IllegalArgumentException err){
                        System.out.println("Building " + answer + " does not exist or value is null.");
                        continue topLayerLoop;
                    }
                    continue topLayerLoop;
                }
                //Quits the program completely, and prompts the user for whether or not they would like to save the
                  //information created this session.
                case("q"):{
                    System.out.println("    S) Quit and DO save information (will overwrite any previous saves)");
                    System.out.println("    D) Quit and DO NOT save information");
                    System.out.println("    C) Cancel quitting and go back to main menu");
                    invalidAnswer = false;
                    quitLayerLoop: while(true){
                        if(invalidAnswer){
                            System.out.println("\nSelect a valid option: ");
                            invalidAnswer = false;
                        }
                        else
                            System.out.print("Select an option: ");
                        answer = input.nextLine();
                        switch(answer.toLowerCase()){
                            case("s"):{
                                exitSequence(true);
                            }
                            case("d"):{
                                exitSequence(false);
                            }
                            case("c"):{
                                continue topLayerLoop;
                            }
                            default:{
                                invalidAnswer = true;
                                continue quitLayerLoop;
                            }
                        }
                    }
                }
                //Indicates that the user input something incorrectly.
                default:{
                    invalidAnswer = true;
                    continue topLayerLoop;
                }
            }
        }
    }
    /**
     * Used to print the main menu containing the means to quit the program properly as well as the way to search for
     *   Classrooms, list all Buildings, and to add and remove Buildings.
     */
    public static void printMainMenu(){
        System.out.println("\nMain Menu:");
        System.out.println("A) Add a building");
        System.out.println("D) Delete a building");
        System.out.println("E) Edit a building");
        System.out.println("F) Find a room");
        System.out.println("S) Search for rooms");
        System.out.println("C) List all buildings on campus");
        System.out.println("L) List building details");
        System.out.println("Q) Quit");
    }
    /**
     * Used to print the menu that is displayed when the user indicates that they would like to edit a Building object.
     *   Contains a menu which gives options for adding, removing, and editing Classrooms. Also allows the user to
     *   exit the editing menu and return to the main menu.
     */
    public static void printEditBuildingMenu(){
        System.out.println("\nOptions:");
        System.out.println("    A) Add room");
        System.out.println("    D) Delete room");
        System.out.println("    E) Edit room");
        System.out.println("    Q) Go back to main menu");
    }
    /**
     * Prints the room details that are displayed if the user inputs "f" or "F".
     * @param buildingName
     *      String buildingName is the name of the Building that the Classroom is in.
     * @param roomNumber
     *      int roomNumber is the room number of the Classroom that the details are being asked for.
     */
    public static void printRoomDetails(String buildingName, int roomNumber){
        toPrint.setLength(0);
        toPrint.append("\nRoom Details:");
        toPrint.append("\n    Seats: " + campus.getBuilding(buildingName).getClassroom(roomNumber)
          .getNumSeats());
        if(campus.getBuilding(buildingName).getClassroom(roomNumber).getHasWhiteboard())
            toPrint.append("\n    Has whiteboard");
        else
            toPrint.append("\n    Doesn't have whiteboard");
        if(campus.getBuilding(buildingName).getClassroom(roomNumber).getHasChalkboard())
            toPrint.append("\n    Has chalkboard");
        else
            toPrint.append("\n    Doesn't have chalkboard");
        toPrint.append("\n    AV Equipment: " + campus.getBuilding(buildingName).getClassroom(roomNumber)
          .getAVEquipmentString());
        System.out.print(toPrint.toString());
    }
    /**
     * Used to print the menu that is displayed when the user indicates that they would like to search for a room
     *   somewhere on the Campus according to specifications that are displayed with the menu. The specifications that
     *   can be searched for are whether or not the Classroom has a chalkboard, whiteboard, or a certain piece of 
     *   AV equipment.
     */
    public static void printSearchRoomsMenu(){
        System.out.println("\nOptions:");
        System.out.println("    C) Chalkboard");
        System.out.println("    W) Whiteboard");
        System.out.println("    A) AV Equipment");
        System.out.println("    Q) Go back to main menu");
    }
    /**
     * Prints the details of any Building in the Campus object. These details include the total number of seats, the
     *   percentage of rooms with whiteboard and chalkboards, and the sum of all AV equipment throughout the building.
     * @param buildingName
     *      String buildingName is the name of the Building that details are being asked for.
     */
    public static void printBuildingDetails(String buildingName){
        int temp;
        double tempDouble = 0.0, numWhiteboard = 0.0, numChalkboard = 0.0;
        Integer[] roomKeys = campus.getBuilding(buildingName).getClassroomKeyList();
        String[] AVEquipment = campus.getBuilding(buildingName).getAVEquipment();
        double numRooms = (double) roomKeys.length;
        System.out.println("Details:");
        if(campus.getBuilding(buildingName).getNumClassrooms() ==  0)
            System.out.println("    Rooms: none");
        else
            System.out.println("    Rooms: " + campus.getBuilding(buildingName).getClassroomKeyListString());
        temp = 0;
        for(int i = 0; i < roomKeys.length; i++ ){
            temp += campus.getBuilding(buildingName).getClassroom(roomKeys[i]).getNumSeats();
        }
        System.out.println("    Total seats: " + temp);
        temp = 0;
        for(int i = 0; i < roomKeys.length; i++ ){
            if(campus.getBuilding(buildingName).getClassroom(roomKeys[i]).getHasWhiteboard())
                numWhiteboard++ ;
        }
        try{
            tempDouble = (numWhiteboard/numRooms);
        }
        catch(ArithmeticException err){
            tempDouble = 0.0;
        }
        if(campus.getBuilding(buildingName).getClassroomKeyList().length ==  0)
            System.out.println("    0% of rooms have whiteboards.");
        else{
            System.out.printf("%s%3.0f%s", "    ", (tempDouble*100), "% of rooms have whiteboards.");
            System.out.println("");
        }
        temp = 0;
        for(int i = 0; i < roomKeys.length; i++ ){
            if(campus.getBuilding(buildingName).getClassroom(roomKeys[i]).getHasChalkboard())
                numChalkboard++ ;
        }
        try{
            tempDouble = (numChalkboard/numRooms);
        }
        catch(ArithmeticException err){
            tempDouble = 0.0;
        }
        if(campus.getBuilding(buildingName).getClassroomKeyList().length ==  0)
            System.out.println("    0% of rooms have chalkboards.");
        else
            System.out.printf("%s%3.0f%s", "    ", (tempDouble*100), "% of rooms have chalkboards.");
        if(campus.getBuilding(buildingName).getClassroomKeyList().length ==  0)
            System.out.println("    AV Equipment present: none");
        else{
            System.out.print("\n    AV Equipment present: ");
            for(int i = 0; i < AVEquipment.length; i++ ){
                System.out.print(AVEquipment[i]);
                if((i+1) < AVEquipment.length)
                    System.out.print(", ");
            }
        }
    }
    /**
     * Runs the shut down sequence for this program, and saves according to input from the user.
     * @param toSave
     *      boolean toSave is true if the user would like to save any information gathered this session of the program,
     *        and false if the user would like to quit without saving.
     */
    public static void exitSequence(boolean toSave){
        input.close();
        if(!toSave){
            System.out.println("\nExiting SBGetARoom without saving data....  ");
            System.exit(0);
        }
        else{
            System.out.println("Saving SBGetARoom data...   ");
            try{
                fileOut = new FileOutputStream("campusData.obj");
            }
            catch(FileNotFoundException err){
                System.out.println("FATAL ERROR: Problem occurred while writing data to file in attempt to save.");
                System.out.println("Data will not be saved. I apologize.");
                System.exit(1);
            }
            try{
                outStream = new ObjectOutputStream(fileOut);
                outStream.writeObject(campus);
            }
            catch(IOException err){
                System.out.println("FATAL ERROR: Problem occurred while saving data to computer.");
                System.out.println("Data will not be saved. I apologize.");
                System.exit(1);
            }
            try{
                outStream.close();
            }
            catch(IOException err){
                System.out.println("Error: Problem occurred while attempting to close output stream for object.");
                System.out.println("Data was still saved successfully.");
                System.exit(-1);
            }
            System.out.println("SBGetARoom data saved successfully!\nShutting down...   ");
            System.exit(0);
        }
    }
    /**
     * Runs the booting sequence which determines whether or not information from a past save was loaded properly.
     * @return
     *      Returns true if information from a past session was loaded properly, and false otherwise.
     */
    public static boolean bootSequence(){
        try{
            fileIn = new FileInputStream("campusData.obj");
        }
        catch(FileNotFoundException err){
            System.out.println("No save file found. Creating an empty campus....  ");
            return false;
        }
        try{
            inStream = new ObjectInputStream(fileIn);
        }
        catch (IOException err){
            System.out.println("FATAL ERROR: Problem occurred while reading data from file.");
            System.out.println("I apologize. Creating an empty campus....  ");
            return false;
        }
        try{
            campus = (Campus) inStream.readObject();
        }
        catch(ClassNotFoundException err){
            System.out.println("FATAL ERROR: Problem occured while retrieving object's class from file.");
            System.out.println("Data cannot be read succesfully. I apologize.");
            System.out.println("Creating an empty campus....  ");
            return false;
        }
        catch(IOException err){
            System.out.println("FATAL ERROR: Problem occurred while reading data to initialize campus object.");
            System.out.println("I apologize. Creating an empty campus....  ");
            return false;
        }
        try{
            inStream.close();
        }
        catch(IOException err){
            System.out.println("Error: Problem occurred while trying to close file reader stream.");
            System.out.println("Data was still loaded successfully.");
            return true;
        }
        System.out.println("Saved file loaded...   ");
        return true;
    }
}
