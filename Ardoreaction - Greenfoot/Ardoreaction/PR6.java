import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR6 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR6 extends World
{

    /**
     * Constructor for objects of class PR6.
     * 
     */
    public PR6()
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
        Vf9 vf9 = new Vf9();
        addObject(vf9,727,566);
        vf9.setLocation(731,564);
    }
}
