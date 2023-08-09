import java.util.ArrayList;
/**
 * Write a description of class BeginningScenario here.
 *
 * @author Eunice Hassam
 * @version 2022.11.25
 */
public class StoryLine
{
    private String description;
    private ArrayList<String> story;
    /**
    * Constructor for objects of class StoryLine
    * @param description description of the storyline
    */
    public StoryLine(String description)
    {
        this.description = description;
        story = new ArrayList<>();
    }
    
    public void addStoryline(String it)
    {
        story.add(it);
    }
    
    public String getLine(int index)
    {   
        if(story.get(index) == null){
             return null;
         }
        return story.get(index);
    }
    
    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    
    public int getSize()
    {
        return story.size();
    }
    
}