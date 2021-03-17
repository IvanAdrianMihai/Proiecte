import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PP5 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PP5 extends World
{

    /**
     * Constructor for objects of class PP5.
     * 
     */
    public PP5()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }
    public void act()
    {
        Greenfoot.setSpeed(50);
    }   
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf6 vf6 = new Vf6();
        addObject(vf6,725,558);
        vf6.setLocation(719,555);
    }
}
