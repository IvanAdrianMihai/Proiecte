import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PP3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PP3 extends World
{

    /**
     * Constructor for objects of class PP3.
     * 
     */
    public PP3()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(783, 610
        , 1); 
        prepare();
    }
    public void act()
    {
       Contor.total=0;
       Time.timp=240;
       Peter.vietipeter=3;
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Vf17 vf17 = new Vf17();
        addObject(vf17,736,562);
        vf17.setLocation(732,560);
    }
}
