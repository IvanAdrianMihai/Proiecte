import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PP2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PP2 extends World
{

    /**
     * Constructor for objects of class PP2.
     * 
     */
    public PP2()
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
        Vf13 vf13 = new Vf13();
        addObject(vf13,741,560);
        vf13.setLocation(731,561);
    }
}
