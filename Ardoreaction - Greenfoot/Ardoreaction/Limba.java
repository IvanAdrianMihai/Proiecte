import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Limba here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Limba extends World
{

    /**
     * Constructor for objects of class Limba.
     * 
     */
    static GreenfootSound sound = new GreenfootSound("sunet poveste.mp3");      
    public Limba()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610, 1); 
        prepare();
        sound.playLoop();        
    }
    
   /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        RO ro = new RO();
        addObject(ro,236,413);
        ro.setLocation(232,415);
        EN en = new EN();
        addObject(en,493,424);
        en.setLocation(520,412);
        en.setLocation(522,415);
        ro.setLocation(255,415);
    }
}
