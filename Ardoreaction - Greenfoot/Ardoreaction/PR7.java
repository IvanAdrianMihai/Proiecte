import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR7 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR7 extends World
{

    /**
     * Constructor for objects of class PR7.
     * 
     */
    public PR7()
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
        Vf10 vf10 = new Vf10();
        addObject(vf10,730,60);
        vf10.setLocation(729,53);
    }
}
