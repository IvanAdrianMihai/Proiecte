import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instructiuni here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instructiuni extends World
{

    /**
     * Constructor for objects of class Instructiuni.
     * 
     */
    public Instructiuni()
    {    
        super(1100, 850, 1); 
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        butb1 butb1 = new butb1();
        addObject(butb1,78,785);
    }
}
