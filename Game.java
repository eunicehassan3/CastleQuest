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
 * @author  Eunice Hassan
 * @version 2022.04.12
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    private StoryLine story;
    private int storyIncrement;
    private StoryLine dragonStory;
    private Player player;
    private Player monster;
    private Stack<Room> movements;
    private int movesLeft;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createStoryline();
        movements = new Stack<>();
        parser = new Parser();
        createRooms();
        storyIncrement = 1;
        movesLeft = 5;

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        
        Room entrance, kitchen, dungeon, stable, bedroom, cage, parlor, attic, ballroom, classroom, wallPainting
        ;
        //8.4 - create the rooms
        entrance = new Room("outside the main entrance of the castle");
        kitchen = new Room("inside the kitchen of the castle");
        dungeon = new Room("inside the dungeon of the castle");
        stable = new Room("inside the horse stable");
        bedroom = new Room("inside someone's bedroom");
        cage = new Room("inside a dragon's cage");
        parlor = new Room("inside the castle's tea parlor");
        attic = new Room("inside the attic of the castle");
        ballroom = new Room("inside the ballroom of the castle");
        classroom = new Room("inside the empty classroom");
        
        classroom.setExit("west", entrance);
        entrance.setExit("north", ballroom);
        entrance.setExit("south", kitchen);
        kitchen.setExit("north", entrance);
        kitchen.setExit("west", dungeon);
        kitchen.setExit("east", stable);
        stable.setExit("west",kitchen);
        dungeon.setExit("east", kitchen);
        dungeon.setExit("north", bedroom);
        bedroom.setExit("south", dungeon);
        ballroom.setExit("east", attic);
        ballroom.setExit("south", entrance);
        ballroom.setExit("up", parlor);
        parlor.setExit("down", ballroom);
        attic.setExit("up", cage);
        attic.setExit("west", ballroom);
        cage.setExit("down", attic);
        
        Item key = new Item("A rusty key", 0.3, "key");
        Item rubble = new Item("A piece of rubble", 1.0, "rubble");
        Item flag = new Item("A red flag", 0.75, "flag");
        entrance.addItem(key); // going to need in the dungeon
        entrance.addItem(rubble);
        entrance.addItem(flag);
        
        Item knife = new Item("A knife" , 0.5, "knife");
        Item corn = new Item("A sachet of corn" , 1.5, "corn");
        Item apron = new Item("An black apron" , 0.5, "apron");
        kitchen.addItem(knife);
        kitchen.addItem(corn); //going to need in the horse stable
        kitchen.addItem(apron); //may need in the ballroom
        
        
        Item hay = new Item("a stack of hay" , 2,"hay"); 
        Item bucket = new Item("a water bucket", 2, "bucket"); // may need in the attic
        //stable.addItem(china);
        stable.addItem(hay); 
        stable.addItem(bucket);
        
        Item mirror = new Item("A mirror", 2.2, "mirror");
        Item candle = new Item("A lighted candle", .5, "candle");
        bedroom.addItem(mirror);
        bedroom.addItem(candle);
        
        Item paper = new Item("old sheets of paper", 1, "paper");
        attic.addItem(paper);
        
        Item gloves = new Item("worn-down gloves", .75, "gloves"); // may need for ballroom
        dungeon.addItem(gloves);
        
        Item china = new Item("a china plate", .8,"plate"); // may need for ballroom
        parlor.addItem(china);
        Item tea = new Item("a couple of tea bags", .05, "tea bags"); // may need for ballroom
        parlor.addItem(tea);
        //8.33
        Item cookie = new Item("a special cookie", 0.2,"cookie");
        parlor.addItem(cookie);
        
        Item pen = new Item("glowing pen", 0.1, "pen");
        cage.addItem(pen);
        
        dungeon.locked();
        ballroom.locked();
        stable.locked();
        attic.locked();
        
        pen.locked();
        
        // assign images
        // outside.setImage("deakinsign.jpg");
        // theater.setImage("lecture-hall.jpg");
        // pub.setImage("cozy-little-pub.jpg");
        // lab.setImage("computer-lab.jpg");
        // office.setImage("cluttered-office.jpg");
        
        //assign sounds
        // office.setAudio("cricket.mp3");

        player = new Player("Student", entrance);
        currentRoom = player.getRoom();
        monster = new Player("dragon", cage);
        
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        println();
        println("Welcome to Castle Quest!");
        println("Type 'start' to begin.");
        println("Type 'help' if you need help.");
        println("Type 'skip' to skip the story.");


    }
    
    /**
     * creates my Storylines
     */
    private void createStoryline()
    {
        
        story = new StoryLine("Introduction");
        story.addStoryline("It's only been thirty minutes and you're already about to die\n of boredom." + 
        "You thought 'the History of Wall Painting' would\n be an easy A, but all it turned out to be was a snoozer.");
        story.addStoryline("So as to prevent your brain from bursting,"
        + "you rush out of\nthe class in a desperate attempt to be anywhere but there");
        story.addStoryline("As you wander in the hallways, you spot\n" +
        "a particular classroom that intrigues you");
        story.addStoryline("There appears to be a bright green light glowing inside the\n" +
        "classroom. Your curiosity gets the best of you, and you rush inside.");
        story.addStoryline("Unfortunately, you were so curious that you didn't that see the\n" +
        "DO NOT ENTER Sign at the top of the doorpost.");
        story.addStoryline("When you open the door, you've come to realized that glowing object" 
        +"\nwas just a glow in the dark pencil that someone probably left from the last class.");
        story.addStoryline("Disappointed, you try to leave, but when you\n" +
        "push the door, it doesn't open!");
        story.addStoryline("You're freaking out!!! In a state of panic, you scream for\n" +
        "help and pound the door but to no avail.");
        story.addStoryline("You're next thought is to look around the room for something you can use to crack the door open.\n"
        + "You spot a closet, and think there might be something useful in there that could help you");
        story.addStoryline("It turns out that the closet isn't an actually closet!\n" +
        "The closet is actually a doorway to... a Castle?!");
        story.addStoryline("You figure exploring a hidden Castle is better than being stuck in this room,\n" +
        "so you decide to enter, and from there the adventure begins!\ntype help to see instructions. ");
        
        
        dragonStory = new StoryLine("Dragon's Story");
        dragonStory.addStoryline("");
        dragonStory.addStoryline("Ahh, there's a dragon in this room! But it appears to be sleeping.\n" +
        "You see in his cage though,something that appears to be glowing.\nSomething small and cyclindrical...like...a pen!"+
        "It's the same pen you saw in the empty classroom. .\n" +
        "However, in order to get inside the cage, you have to enter a code, that only allows three tries.");
        dragonStory.addStoryline("Enter the code:____");
        
    }
    

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private void processCommand(Command command) 
    {
        if(command.isUnknown()) {
            println("I don't know what you mean...");
            return;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("skip")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            quit(command);
        }
        //8.14
        else if(commandWord.equals("look")){
            look(command);
        }
         //8.14
        else if(commandWord.equals("next")){
            if(storyIncrement <= 10){
                next(command);
                storyIncrement ++;
            }
            else{
                
                println("type help to see instructions.");
                return;
            }
        }
        
        else if(commandWord.equals("start")){
            start(command);
        }
         //8.14
        else if(commandWord.equals("back")){
            back(command);
        }
        else if(commandWord.equals("take")){
            take(command);
        }
        else if(commandWord.equals("drop")){
            drop(command);
        }
        //8.33
        else if(commandWord.equals("eat")){
            eatCookie(command);
        }
        //8.33
        else if(commandWord.equals("read")){
            read(command);
        }
        else if(commandWord.equals("Code:")){
            code(command);
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        println("Your objective is to find your way through the castle and\n" +
        "somehow make it back to your wall painting class before anyone gets suspicious.");
        println();
        //8.16
        println("Your command words are:");
        println(parser.showCommands());
        println();
        
        println("You are " + player.getCurrentRoomDescription());
        
        //println("You are " + currentRoom.getLongDescription());
        println();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            println("Go where?");
            return;
        }
        
        String direction = command.getSecondWord();
        println(player.goRoom(direction));
        
    
        
        String currentRoomDesc = player.getCurrentRoomDescription();
        if(currentRoomDesc.contains("cage")){
            println(player.goRoom(direction));
            println();
            println(dragonStory.getLine(1));
            println();
            println(dragonStory.getLine(2));
            println("(Type [Code: 'Your Answer'] for your code to be accepted)");
        }
        
        if(currentRoomDesc.contains("dungeon")){
            println("it turn's out the rusty key opened the dungeon.\n" + 
            "Look! There's a note on one of the cells.");
        }
        
        if(currentRoomDesc.contains("ballroom")){
            println("\nApparently, only people with the special black gloves you found\n " +
            "are allowed to enter the ballroom. Great find!\nAnd see what else we've found. A letter!");
        }
        
        if(currentRoomDesc.contains("attic")){
            println("\nWoah, it's really dark in here! Good thing you brought\n"+
            "a candle...and look what we have here, another letter!");
        }
        
        if(currentRoomDesc.contains("stable")){
            println("\nHaha the way the world works! The stable boy saw the corn\n"+ 
            "you were carrying and thought you were bringing food for the horses.\n" +
            "He hands you what looks like a reciept, but as you look closer, you\n realize its  a letter!");
        }
        
    }
    
    // Commands
    
    private void start(Command command)
    {
        if(story != null){
            println();
            println(story.getLine(0) + "\n" + nextDescription());
        }
    }
    //8.15
    private void next(Command command)
    {
        if(currentRoom.getLongDescription().contains("entrance")){
            println(story.getLine(storyIncrement));
        }
        
    }
    
    private String nextDescription()
    {
        return "(Type next to see what happens next)";
    }
    
     //8.14
    private void look(Command command)
    {
        println();
        println(player.getCurrentRoomDescription());
        println();
        println(player.itemInventory());
    }
    
    //8.23
    private void back(Command command)
    {
        println(player.back());
    }
    //8.29-8.30
    private void take(Command command)
    {
        if(!command.hasSecondWord()){
            println("take what?");
            return;
        }        
        String item = command.getSecondWord();
        println(player.take(item));
        
        if(player.itemInventory().contains("pen")){
            println("Congratulations, you've won the game! When you clicked the pen, it teleported you back the room, but this time,\n"+ 
            "the door was propped open. While that was a lot better than sitting through your class, maybe next time, stick\nto killing time in the bathroom.");
            wantToQuit = true;
        }
    }
    //8.29-8.30
    private void drop(Command command)
    {
        if(!command.hasSecondWord()){
            println("drop what?");
            return;
        }
        String item = command.getSecondWord();
        println(player.drop(item));
    }
    
    private void read(Command command)
    {
        println(player.read());
    }
    
    //8.23
    private void code(Command command)
    {
        if(player.getCurrentRoomDescription().contains("cage")){
            String guess = command.getSecondWord();
            if(guess.toUpperCase().equals("EXAM") ){
            println("Nice! You got in. Now lets take the pen.");
            
                Item pen = player.getRoom().getItem("pen");
                pen.unlocked();
            } 
        }
        else{
            println("Not quite. Try again");
            movesLeft--;
        }
        
    } 
    
    private void eatCookie(Command command)
    {
        println(player.eatCookie());
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void quit(Command command) 
    {
        if(command.hasSecondWord()) {
            println("Quit what?");
            return;
        }
        
        wantToQuit = true;  // signal that we want to quit
    }
    
    /****************************************************************
     * If you want to launch an Applet
     ****************************************************************/
    
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
    
    /****************************************************************
     * Variables & Methods added 2018-04-16 by William H. Hooper
     ****************************************************************/
    
    private String messages;
    private boolean wantToQuit;
    
    /**
     * Initialize the new variables and begin the game.
     */
    private void start()
    {
        messages = "";
        wantToQuit = false;
        printWelcome();
    }
    
    /**
     * process commands or queries to the game
     * @param input user-supplied input
     */
    public void processInput(String input)
    {
        if(finished()) {
            println("This game is over.  Please go away.");
            return;
        }
        
        Command command = parser.getCommand(input);
        processCommand(command);
    }
    
    /**
     * clear and return the output messages
     * @return current contents of the messages.
     */
    public String readMessages()
    {
        if(messages == null) {
            start();
        }
        String oldMessages = messages;
        messages = "";
        return oldMessages;
    }
    
    /**
     * @return true when the game is over.
     */
    public boolean finished()
    {
        return wantToQuit || movesLeft == 0;
    }

    /**
     * add a message to the output list.
     * @param message the string to be displayed
     */
    private void print(String message)
    {
        messages += message;
    }
    
    /**
     * add a message to the output list, 
     * followed by newline.
     * @param message the string to be displayed
     * @see readMessages
     */
    private void println(String message)
    {
        print(message + "\n");
    }
    
    /**
     * add a blank line to the output list.
     */
    private void println()
    {
        println("");
    }
}
