import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PP8 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PP8 extends World
{

    /**
     * Constructor for objects of class PP8.
     * 
     */
    private int u=0;
    public PP8()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }
    public void act()
    {
        if(u==0)
        if(Volum.vol==1)
        {
            Greenfoot.playSound("Laser intreg.mp3");
            u++;
        };
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf20 vf20 = new Vf20();
        addObject(vf20,734,562);
    }
}
