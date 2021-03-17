import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartRomana extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */

    public StartRomana()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783,610, 1);
        prepare();
    }
    public void act()
    {
        valP.np1=0;
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        RO1 ro1 = new RO1();
        addObject(ro1,403,326);
        RO2 ro2 = new RO2();
        addObject(ro2,403,399);
        RO3 ro3 = new RO3();
        addObject(ro3,403,473);
        RO4 ro4 = new RO4();
        addObject(ro4,403,547);
        ro1.actr();
        ro2.actr();
        ro3.actr();
        ro4.actr();
        Volum volum = new Volum();
        addObject(volum,737,561);
    }
}
