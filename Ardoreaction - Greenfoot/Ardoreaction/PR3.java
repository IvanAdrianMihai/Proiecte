import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR3 extends World
{

    /**
     * Constructor for objects of class PR3.
     * 
     */
    public PR3()
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
    private void prepare()
    {
        Vf5 vf5 = new Vf5();
        addObject(vf5,734,58);
        vf5.setLocation(734,56);
    }
}
