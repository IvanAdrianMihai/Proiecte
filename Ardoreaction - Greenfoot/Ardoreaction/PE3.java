import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE3 extends World
{

    /**
     * Constructor for objects of class PE3.
     * 
     */
    public PE3()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }
    public void act()
    {
        Counter.totalCount=0;
        Ardor.vieti=3;
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf25 vf25 = new Vf25();
        addObject(vf25,740,59);
        vf25.setLocation(732,53);
    }
}
