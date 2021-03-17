import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE1 extends World
{

    /**
     * Constructor for objects of class PE1.
     * 
     */
    public PE1()
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
        Vf23 vf23 = new Vf23();
        addObject(vf23,732,559);
        vf23.setLocation(729,556);
    }
}
