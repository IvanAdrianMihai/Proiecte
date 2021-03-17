import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Meniu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Meniu extends World
{

    /**
     * Constructor for objects of class Meniu.
     * 
     */
    private boolean ok=true;
    public static GreenfootSound remix =new GreenfootSound("remix.mp3");
    public Meniu()
    {    
        super(1100, 850, 1);
        puncte.punct=0;
        vieti.vietim=3;
        vietimon.vieti=3;
        tl1.personaj=false;
        tl2.personaj=false;
        prepare();
        Greenfoot.start();
    }
    public void act()
    {
        if(ok&&!remix.isPlaying())
        { 
          remix.playLoop();
          ok=false;
        }
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        but1 but1 = new but1();
        addObject(but1,496,411);
        but2 but2 = new but2();
        addObject(but2,496,530);
        but3 but3 = new but3();
        addObject(but3,496,657);
    }
}
