import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PP9 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PP9 extends World
{

    /**
     * Constructor for objects of class PP9.
     * 
     */
    private int u=0;
    public PP9()
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
            Greenfoot.playSound("PartiLaser.mp3");
            u++;
        };
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf19 vf19 = new Vf19();
        addObject(vf19,741,564);
        vf19.setLocation(735,562);
    }
}
