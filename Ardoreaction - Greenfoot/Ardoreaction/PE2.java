import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE2 extends World
{

    /**
     * Constructor for objects of class PE2.
     * 
     */
    public PE2()
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
        Vf24 vf24 = new Vf24();
        addObject(vf24,731,66);
        vf24.setLocation(731,56);
    }
}
