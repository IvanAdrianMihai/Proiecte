import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScorE here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScorE extends World
{

    /**
     * Constructor for objects of class ScorE.
     * 
     */
    public ScorE()
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
        Vf39 vf39 = new Vf39();
        addObject(vf39,56,571);
        vf39.setLocation(48,562);
        valP valp2 = new valP();
        addObject(valp2,402,316);
        valp2.setLocation(395,304);
        valp2.setLocation(402,304);
        valG valg2 = new valG();
        addObject(valg2,414,534);
        valg2.setLocation(407,529);
    }
}
