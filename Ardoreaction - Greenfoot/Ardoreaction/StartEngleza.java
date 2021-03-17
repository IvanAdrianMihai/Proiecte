import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartEngleza extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */

    public StartEngleza()
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
        EN1 en1 = new EN1();
        addObject(en1,403,326);
        EN2 en2 = new EN2();
        addObject(en2,403,399);
        EN3 en3 = new EN3();
        addObject(en3,403,473);
        EN4 en4 = new EN4();
        addObject(en4,403,547);
        en1.actr();
        en2.actr();
        en3.actr();
        en4.actr();
        Volum volum = new Volum();
        addObject(volum,737,561);
    }
}
