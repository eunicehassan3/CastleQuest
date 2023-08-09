import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private String imageName;
    private String audioName;
    
    private HashMap<String, Room> roomMap;
    private ArrayList<Item> items;
    private boolean locked;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        roomMap = new HashMap<>();
        items = new ArrayList<>();
        locked = false;
    }

    //8.8
    public void setExit(String direction, Room destination)
    {
        roomMap.put(direction, destination);
    }
    //8.8
    public Room getExit(String direction)
    {
        return roomMap.get(direction);
    }
    //8.8
    public String getExitString()
    {
        String message = "Exits:";
        
        for(String directions: roomMap.keySet()){
            message += " " + directions;
        }
    
        return message;
    }
    
    
    //8.22
     public void addItem(Item it){
        items.add(it);
    }

    public void removeItem(int index)
    {
        items.remove(index);
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
    
    public Item getItem(String itemName)
    {
    
        for(Item umm: items){
            if(umm.getDescription().toLowerCase().contains(itemName)){
                return umm;
           }
        }
        
        return null;
    }
    
    public void locked()
    {
        locked = true;
    }
    
    public void unlocked()
    {
        locked = false;
    }
    
    public boolean islocked(){
        return locked;
    }
    
    public String getItemString()
    {
        
        String message = "Items in the room:";
        
        for(Item umm: items){
            message += "\n" + umm.getDescription();
        }
        
        if(items.size() == 0){
            message = "There are no items in this room";
        }
        return message;
    }
    
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    //8.8
     /**
     * @return The description of the room.
     */
    public String getLongDescription()
    {
        return description + "\n" + getExitString() + "\n" + getItemString();
    }
    
    /*************************************************************
     * added by William H. Hooper, 2006-11-28
    *************************************************************/
    /**
     * @return a String, which hopeful/ly contains the file name
     * of an Image file.
     */
    public String getImage()
    {
        return imageName;
    }
    
    /**
     * associate an image with this room
     * @param filename a String containing a file.
     * The file <b>must</b> reside in the media directory, 
     * and must be in a format viewable in the Java AWT.
     * Readable formats include: 
     * PNG, JPG (RGB color scheme only), GIF
     */
    public void setImage(String filename)
    {
        imageName = "media/" + filename;
    }
    
    /**
     * @return a String, which hopefully contains the file name
     * of an audio file.
     */
    public String getAudio()
    {
        return audioName;
    }
    
    /**
     * associate an audio clip with this room
     * @param filename a String containing a file.
     * The file <b>must</b> reside in the media directory, 
     * and must be in a format palyable in the Java AWT.
     * Readable formats include: 
     * WAV, AU.
     */
    public void setAudio(String filename)
    {
        audioName = "media/" + filename;
    }
}
