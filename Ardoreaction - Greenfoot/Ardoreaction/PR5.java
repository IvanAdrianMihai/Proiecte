import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR5 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR5 extends World
{

    /**
     * Constructor for objects of class PR5.
     * 
     */
    public PR5()
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
        Vf8 vf8 = new Vf8();
        addObject(vf8,739,573);
        vf8.setLocation(735,565);
    }
}
