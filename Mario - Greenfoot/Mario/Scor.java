import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Scor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Scor extends World
{

    /**
     * Constructor for objects of class Scor.
     * 
     */
    public Scor()
    {    
        super(1100, 850, 1); 
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        butb1 butb1 = new butb1();
        addObject(butb1,100,783);
        scormaxim scormaxim = new scormaxim();
        addObject(scormaxim,327,410);
        ultscor ultscor = new ultscor();
        addObject(ultscor,329,538);
        ultscor.setLocation(414,555);
        scormaxim.setLocation(304,414);
    }
}
