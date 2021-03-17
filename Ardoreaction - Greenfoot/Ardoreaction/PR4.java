import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR4 extends World
{

    /**
     * Constructor for objects of class PR4.
     * 
     */
    public PR4()
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
        Vf7 vf7 = new Vf7();
        addObject(vf7,723,68);
        vf7.setLocation(719,58);
    }
}
