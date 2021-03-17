import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR12 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR12 extends World
{

    /**
     * Constructor for objects of class PR12.
     * 
     */
    public PR12()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }
    public void act()
    {
        if(valP.np1>valP.nr1)
            valP.nr1=valP.np1;
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf22 vf22 = new Vf22();
        addObject(vf22,738,566);
        vf22.setLocation(736,561);
    }
}
