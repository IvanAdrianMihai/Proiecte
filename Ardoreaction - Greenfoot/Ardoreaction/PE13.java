import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PE13 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PE13 extends World
{

    /**
     * Constructor for objects of class PE13.
     * 
     */
    private int u=0;
    public PE13()
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
        Vf37 vf37 = new Vf37();
        addObject(vf37,747,563);
        vf37.setLocation(730,560);
    }
}
