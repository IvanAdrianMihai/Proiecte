import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR11 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR11 extends World
{

    /**
     * Constructor for objects of class PR11.
     * 
     */
    public PR11()
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
        Vf16 vf16 = new Vf16();
        addObject(vf16,725,59);
        vf16.setLocation(726,51);
    }
}
