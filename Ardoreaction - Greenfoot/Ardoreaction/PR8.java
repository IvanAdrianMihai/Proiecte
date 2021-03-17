import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR8 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR8 extends World
{

    /**
     * Constructor for objects of class PR8.
     * 
     */
    public PR8()
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
        Vf11 vf11 = new Vf11();
        addObject(vf11,731,559);
        vf11.setLocation(733,563);
    }
}
