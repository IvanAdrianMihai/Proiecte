import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE10 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE10 extends World
{

    /**
     * Constructor for objects of class PE10.
     * 
     */
    public PE10()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf31 vf31 = new Vf31();
        addObject(vf31,737,563);
        vf31.setLocation(735,557);
    }
}
