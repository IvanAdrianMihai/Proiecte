import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE4 extends World
{

    /**
     * Constructor for objects of class PE4.
     * 
     */
    public PE4()
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
        Vf26 vf26 = new Vf26();
        addObject(vf26,731,69);
        vf26.setLocation(724,60);
    }
}
