import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
//8.28
public class Player 
{
    private String name;
    private ArrayList<Item> items;
    private Room currentRoom;
    
    private Stack<Room> movements;
    private double weightLimit;
    private int movesRemaining;
    /**
     * Create the game and initialise its internal map.
     */
    public Player(String name, Room location) 
    {
        this.name = name;
        items = new ArrayList<>();
        movements = new Stack<>();
        weightLimit = 4;
        currentRoom = location;
        
        movesRemaining = 5;
    }

    //8.22
     public void addItem(Item it){
        items.add(it);
    }
    
    public void removeItem(String word)
    {
        for(Item umm: items){
            if(umm.getDescription().toLowerCase().contains(word)){
                items.remove(umm);
                return;
            }
        }
    
    }
    
    //8.20
    public Item getItem(String itemName)
    {
    
        for(Item umm: items){
            if(umm.getDescription().toLowerCase().contains(itemName)){
                return umm;
           }
        }
        
        return null;
    }
    
    public String getItemString()
    {
        
        String message = "Items:";
        
        for(Item umm: items){
            message += "\n" + umm.getDescription();
        }
        
        if(items.size() == 0){
            message = name + "has no items";
        }
        return message;
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public String goRoom(String direction) 
    {
        //8.6
        Room nextRoom = currentRoom.getExit(direction);
        String stuff = getItemString();
        
        if(nextRoom == null) {
            return "There is no door";
        }
        
         if(nextRoom.getDescription().contains("dungeon")){
            if(stuff.contains("key")){
                nextRoom.unlocked();
            }
        }
        
        if(nextRoom.getDescription().contains("ballroom ")){
            if(stuff.contains("gloves")){
                nextRoom.unlocked();
            }
        }
        
        if(nextRoom.getDescription().contains("stable")){
            if(stuff.contains("corn")){
                nextRoom.unlocked();
            }
        }
        
        if(nextRoom.getDescription().contains("attic")){
            if(stuff.contains("candle")){
                nextRoom.unlocked();
            }
        }
        
        if(nextRoom.islocked()){
            return("You can't get in this room");
        }
        
        movements.push(currentRoom);
        currentRoom = nextRoom;
        return ("You are " + currentRoom.getLongDescription());
    }
    
    
    /**
     * @return the new currentRoom
     * @ param location the location you want the current room to be
     */public void setCurrentRoom(Room location)
    {
        currentRoom = location;
    }
    /** 
     * @return the long description of the current room
     */
    public String getCurrentRoomDescription()
    {
        return currentRoom.getLongDescription();
    }
    
    public Room getRoom()
    {
        return currentRoom;
    }
    
    /**
     * @return the nextRoom
     * @param direction the direction you want to go
     */
    public Room getNextRoom(String direction)
    {
        return currentRoom.getExit(direction);
    }

    
    public double getWeightLimit()
    {
        return weightLimit;
    }
    
    public void setWeightLimit(double limit)
    {
        weightLimit = limit;
    }
    
    //8.32
    public String itemInventory()
    {
        String things = "Inventory: ";
        double totalWeight = getTotalWeight();
        for(Item item: items){
            things += "\n "+ item.getDescription();  ;
        }
        things += "\n" + "Total Weight: " + totalWeight;
        return things;
        
        
    }
    
    //8.33
    public void increaseWeightLimit(double weightbonus)
    {
        weightLimit += weightbonus; 
    }
    
    public double getTotalWeight()
    {
        double total = 0.0;
        for(Item i : items){
            total += i.getWeight();
        }
        return total;
    }
    
    //8.23
    public String back()
    {
        if(movements.empty()) {
            return "You can't go back any further..."+ "\n" + 
            currentRoom.getLongDescription();
            
        }
        currentRoom = movements.pop();
        return currentRoom.getLongDescription();
    }
    
    public String eatCookie(){
        Item cookie = getItem("cookie");
        if(cookie == null)
        {
            return "Eat what?";
        }
        increaseWeightLimit(5);
        items.remove(cookie);
        return "Congratulations! You can carry 5 more pounds than before.";
    }
    //8.29-8.30
    public String take(String word)
    {
        Item umm = currentRoom.getItem(word);
        if(umm == null){
            return word += " not found";
        }
        
        if(umm.islocked()){
            return "The " + word + " cannot be reached.";
        }
        double itemWeight = umm.getWeight();
        double playerWeight = getTotalWeight();
        //8.31
        if(itemWeight + playerWeight > getWeightLimit()){
            return "you're stash is full. You can't carry anymore items";
        }
        this.addItem(umm);
        currentRoom.removeItem(word);
        
        return word += " taken";
        
        
    }
    //8.29-8.30
    public String drop(String name)
    {
        Item i = getItem(name);
        if(i == null){
            return name += " cannot be dropped";
        }
        items.remove(i);
        currentRoom.addItem(i);
        
        return name += " dropped"; 
        
    }
    
    public String read()
    {
        String currentRoomDesc = getCurrentRoomDescription();
        if(currentRoomDesc.contains("dungeon")){
            return("\"You're first step to the quest involves the most\n" +
            "prominent letter of the alphabet.\"");
        }
        
        if(currentRoomDesc.contains("ballroom")){
            return "\"1-step, 2-step, soon your legs will get crossed like the the letter _.\"";
        }
        
        if(currentRoomDesc.contains("stable")){
            return "\"horses run fast, horses eay hay." + 
            "horses make a sound that rhyme with the letter _.\"";
        }
        
        if(currentRoomDesc.contains("attic")){
            return "\"Watch you're step,4 things get dark. Whether it's a\n" + 
            "murder, mugging, or massacre, the letter _ makes its mark.\"";
        }
        return " ";
    }
    
    /**
     * @return an Image from the current room
     * @see Image
     */
    public String getImage()
    {
        return currentRoom.getImage();
    }

    /**
     * @return an audio clip from the current room
     * @see AudioClip
     */
    public String getAudio()
    {
        return currentRoom.getAudio();
    }

    
}
