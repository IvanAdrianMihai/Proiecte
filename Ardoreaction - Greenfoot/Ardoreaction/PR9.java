import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR9 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR9 extends World
{

    /**
     * Constructor for objects of class PR9.
     * 
     */
    public PR9()
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
        Vf34 vf34 = new Vf34();
        addObject(vf34,730,56);
        vf34.setLocation(736,50);
    }
}
