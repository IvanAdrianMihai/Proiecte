import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScorR here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScorR extends World
{

    /**
     * Constructor for objects of class ScorR.
     * 
     */
    public ScorR()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610, 1); 
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf38 vf38 = new Vf38();
        addObject(vf38,62,566);
        vf38.setLocation(51,564);
        valP valp = new valP();
        addObject(valp,411,300);
        valG valg = new valG();
        addObject(valg,422,534);
        valg.setLocation(420,528);
        valg.setLocation(412,526);
        valg.setLocation(416,526);
    }
}
