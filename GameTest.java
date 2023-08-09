import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

/**
 * GameTest - a class to test the zuul game engine.
 *
 * @author  William H. Hooper
 * @version 2018-11-19
 */
public class GameTest
{
    private Game game1;
    private Console console1; 

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        System.out.println("\f");
        game1 = new Game();
        //game1 =  new Game(new Random(5323));
        console1 = new Console(game1);
        String message = game1.readMessages();
        System.out.print(message + "> ");
    }

    @Test
    public void start()
    {
        assertEquals(false, game1.finished());
    }

    @Test
    public void map()
    {
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("take gloves", "gloves taken"));
        assertEquals(true, console1.testCommand("go north", "bedroom"));
        assertEquals(true, console1.testCommand("take candle", "candle taken"));
        assertEquals(true, console1.testCommand("back", "dungeon"));
        assertEquals(true, console1.testCommand("back", "kitchen"));
        assertEquals(true, console1.testCommand("go north", "entrance"));
        assertEquals(true, console1.testCommand("go north", "ballroom"));
        assertEquals(true, console1.testCommand("go up", "parlor"));
        assertEquals(true, console1.testCommand("go down", "ballroom"));
        assertEquals(true, console1.testCommand("go east", "attic"));
        assertEquals(true, console1.testCommand("go up", "cage"));
    }
    
    @Test
    public void items()
    {
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "knife"));
        assertEquals(true, console1.testCommand("go west", "gloves"));
        assertEquals(true, console1.testCommand("take gloves", "gloves taken"));
        assertEquals(true, console1.testCommand("go north", "mirror"));
        assertEquals(true, console1.testCommand("take candle", "candle taken"));
        assertEquals(true, console1.testCommand("back", "dungeon"));
        assertEquals(true, console1.testCommand("back", "corn"));
        assertEquals(true, console1.testCommand("go north", "flag"));
        assertEquals(true, console1.testCommand("go north", "ballroom"));
        assertEquals(true, console1.testCommand("go up", "tea bags"));
        assertEquals(true, console1.testCommand("go down", "no items"));
        assertEquals(true, console1.testCommand("go east", "paper"));
        assertEquals(true, console1.testCommand("go up", "pen"));

    }

    @Test
    public void noDoor()
    {
        assertEquals(true, console1.testCommand("go east", "no door"));
        assertEquals(true, console1.testCommand("go west", "no door"));
    }

    @Test
    public void back()
    {
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("go east", "kitchen"));
        assertEquals(true, console1.testCommand("take corn", "corn taken"));
        assertEquals(true, console1.testCommand("go east", "stable"));

        assertEquals(true, console1.testCommand("back", "kitchen"));
        assertEquals(true, console1.testCommand("back", "dungeon"));
        assertEquals(true, console1.testCommand("back", "kitchen"));
        assertEquals(true, console1.testCommand("back", "entrance"));
        assertEquals(true, console1.testCommand("back", "entrance"));
        
    }
    
    @Test
    public void quit()
    {
        console1.testCommand("quit");
        assertEquals(true, game1.finished());
        assertEquals(false, console1.testCommand("go North", "doorway"));
        assertEquals(true, console1.testCommand("anything", "game is over"));
    }

    @Test
    public void help()
    {
        String string1 = console1.getResponse("help");
        assertNotNull(string1);
        assertEquals(true, string1.contains("go"));
        assertEquals(true, string1.contains("quit"));
        assertEquals(true, string1.contains("help"));
        assertEquals(true, string1.contains("next"));
        assertEquals(true, string1.contains("back"));
        assertEquals(true, string1.contains("look"));
    }
    
    @Test
    public void takeKnife()
    {
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("take knife", "knife taken"));
        assertEquals(true, console1.testCommand("look", "Total Weight: 0.5"));

    }
    
    @Test
    public void take()
    {
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("take knife", "knife taken"));
        assertEquals(true, console1.testCommand("look", "Total Weight: 0.5"));
        assertEquals(true, console1.testCommand("take apron", "apron taken"));
        assertEquals(true, console1.testCommand("look ", "Total Weight: 1.0"));
        
        assertEquals(true, console1.testCommand("take" , "take what?")); 
        assertEquals(false, console1.testCommand("take crowbar" , "crowbar taken"));
    }
    
    @Test
    public void dropKnife()
    {
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("take knife", "knife taken"));
        assertEquals(true, console1.testCommand("drop knife", "knife dropped"));
        assertEquals(true, console1.testCommand("look", "Total Weight: 0"));

    }
    
    @Test 
    public void next()
    {
        assertEquals(true, console1.testCommand("start" , "It's only been thirty minutes"));
        assertEquals(true, console1.testCommand("next" , "So as to prevent your brain from bursting"));
        assertEquals(true, console1.testCommand("next" , "As you wander in the hallways"));
        assertEquals(true, console1.testCommand("next" , "There appears to be a bright"));
        assertEquals(true, console1.testCommand("next" , "Unfortunately, you were so"));
        assertEquals(true, console1.testCommand("next" , "When you open the door"));
        assertEquals(true, console1.testCommand("next" , "Disappointed, you try"));
        assertEquals(true, console1.testCommand("next" , "You're freaking out"));
        assertEquals(true, console1.testCommand("next" , "You're next thought "));
        assertEquals(true, console1.testCommand("next" , "It turns out that the closet"));
        assertEquals(true, console1.testCommand("next" , "You figure exploring "));
        assertEquals(true, console1.testCommand("next" , "help"));
    }
    
    @Test 
    public void takeLimit()
    {
        assertEquals(true, console1.testCommand("take key" , "key taken"));
        assertEquals(true, console1.testCommand("take rubble" , "rubble taken"));
        assertEquals(true, console1.testCommand("take flag" , "flag taken"));
        assertEquals(true, console1.testCommand("look" , "Total Weight: 2.05"));
        assertEquals(true, console1.testCommand("go south" , "kitchen"));
        assertEquals(true, console1.testCommand("take corn" , "corn taken"));
        assertEquals(true, console1.testCommand("go east" , "stable"));
        assertEquals(true, console1.testCommand("take bucket" , "stash is full"));
    }
    


    
    @Test 
    public void look()
    {
        assertEquals(true, console1.testCommand("take key" , "key taken"));
        assertEquals(true, console1.testCommand("take rubble" , "rubble taken"));
        assertEquals(true, console1.testCommand("take flag" , "flag taken"));
        
        String string1 = console1.getResponse("look");
        assertNotNull(string1);
        assertEquals(true, string1.contains("key"));
        assertEquals(true, string1.contains("rubble"));
        assertEquals(true, string1.contains("flag"));
        assertEquals(false, string1.contains("knife"));
       
    }
    
    @Test 
    public void eatCookie()
    {
        
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("take gloves", "gloves taken"));
        assertEquals(true, console1.testCommand("go north", "bedroom"));
        assertEquals(true, console1.testCommand("take candle", "candle taken"));
        assertEquals(true, console1.testCommand("back", "dungeon"));
        assertEquals(true, console1.testCommand("back", "kitchen"));
        assertEquals(true, console1.testCommand("go north", "entrance"));
        assertEquals(true, console1.testCommand("go north", "ballroom"));
        assertEquals(true, console1.testCommand("go up", "tea parlor"));
        assertEquals(true, console1.testCommand("take cookie" , "cookie taken"));
        assertEquals(true, console1.testCommand("eat cookie" , "Congratulations!"));
        assertEquals(false, console1.testCommand("eat cookie" , "Congratulations!"));
    }
    
    @Test 
    public void locked()
    {
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(false, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("go west", "can't get in"));
        assertEquals(true, console1.testCommand("go north", "entrance"));
        
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("go east", "kitchen"));
        assertEquals(true, console1.testCommand("take corn", "corn taken"));
        assertEquals(true, console1.testCommand("go east", "stable"));
    }
    
    @Test 
    public void win()
    {
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("read", "You're first step"));
        assertEquals(true, console1.testCommand("take gloves", "gloves taken"));
        assertEquals(true, console1.testCommand("go north", "bedroom"));
        assertEquals(true, console1.testCommand("take candle", "candle taken"));
        assertEquals(true, console1.testCommand("back", "dungeon"));
        assertEquals(true, console1.testCommand("back", "kitchen"));
        assertEquals(true, console1.testCommand("go north", "entrance"));
        assertEquals(true, console1.testCommand("go north", "ballroom"));
        assertEquals(true, console1.testCommand("go east", "attic"));
        assertEquals(true, console1.testCommand("go up", "cage"));
        assertEquals(true, console1.testCommand("Code: EXAM", "Nice! You got in"));
        assertEquals(true, console1.testCommand("take pen", "pen taken"));
      
    }
    
    @Test
    public void read()
    {
        assertEquals(true, console1.testCommand("take key", "key taken"));
        assertEquals(true, console1.testCommand("go south", "kitchen"));
        assertEquals(true, console1.testCommand("go west", "dungeon"));
        assertEquals(true, console1.testCommand("read", "You're first step"));
        assertEquals(true, console1.testCommand("take gloves", "gloves taken"));
        assertEquals(true, console1.testCommand("go north", "bedroom"));
        assertEquals(true, console1.testCommand("take candle", "candle taken"));
        assertEquals(true, console1.testCommand("back", "dungeon"));
        assertEquals(true, console1.testCommand("back", "kitchen"));
        assertEquals(true, console1.testCommand("go north", "entrance"));
        assertEquals(true, console1.testCommand("go north", "ballroom"));
        assertEquals(true, console1.testCommand("read", "1-step, 2-step,"));
        assertEquals(true, console1.testCommand("go east", "attic"));
        assertEquals(true, console1.testCommand("read", "Watch you're step,4 things get dark."));
        assertEquals(true, console1.testCommand("go up", "cage"));
    }
}






