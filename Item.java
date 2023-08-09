//8.20
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private String description;
    private double weight;
    private String name; 
    private boolean locked;
    
    public Item(String description, double weight, String name)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
        locked = false;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    public String getName()
    {
        return name; 
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
    
}
