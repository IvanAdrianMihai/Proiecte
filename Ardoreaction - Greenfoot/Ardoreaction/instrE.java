import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class instrE here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class instrE extends World
{

    /**
     * Constructor for objects of class instrE.
     * 
     */
    public instrE()
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
        Vf2 vf2 = new Vf2();
        addObject(vf2,731,58);
        vf2.setLocation(731,48);
    }
}
