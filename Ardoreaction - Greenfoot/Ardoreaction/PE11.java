import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE11 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE11 extends World
{

    /**
     * Constructor for objects of class PE11.
     * 
     */
    public PE11()
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
        Vf32 vf32 = new Vf32();
        addObject(vf32,730,63);
        vf32.setLocation(733,55);
    }
}
