import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR2 extends World
{

    /**
     * Constructor for objects of class PR2.
     * 
     */
    public PR2()
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
        Vf4 vf4 = new Vf4();
        addObject(vf4,734,63);
        vf4.setLocation(730,51);
    }
}
