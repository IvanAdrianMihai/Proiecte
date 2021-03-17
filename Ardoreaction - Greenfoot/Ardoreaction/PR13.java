import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PR13 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PR13 extends World
{

    /**
     * Constructor for objects of class PR13.
     * 
     */
    private int u=0;
    public PR13()
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
            Greenfoot.playSound("SfarsitJoc.mp3");
            u++;
        };   
        if(valP.np1>valP.nr1)
            valP.nr1=valP.np1;
        Greenfoot.setSpeed(50);
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf36 vf36 = new Vf36();
        addObject(vf36,723,559);
        vf36.setLocation(734,559);
    }
}
