import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE9 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE9 extends World
{

    /**
     * Constructor for objects of class PE9.
     * 
     */
    public PE9()
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
        Vf35 vf35 = new Vf35();
        addObject(vf35,736,66);
        vf35.setLocation(734,49);
    }
}
