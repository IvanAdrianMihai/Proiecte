import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PP1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PP1 extends World
{

    /**
     * Constructor for objects of class PP1.
     * 
     */
    public PP1()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }
    public void act()
    {
       scor1.sc1=0;
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf12 vf12 = new Vf12();
        addObject(vf12,736,566);
        vf12.setLocation(733,564);
    }
}
