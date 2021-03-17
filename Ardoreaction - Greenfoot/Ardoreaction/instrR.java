import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class instrR here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class instrR extends World
{

    /**
     * Constructor for objects of class instrR.
     * 
     */
    public instrR()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 

        prepare();
    }
    public void act()
    {
        Counter.totalCount=0;
        Ardor.vieti=3;
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf1 vf1 = new Vf1();
        addObject(vf1,741,49);
        vf1.setLocation(733,46);
    }
}
