import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gameover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gameover extends World
{

    /**
     * Constructor for objects of class Gameover.
     * 
     */
    private int timp=0;
    public Gameover()
    {    
        super(1100, 850, 1);
        Level1.back.stop();
        Level2.back.stop();
        Level3.back2.stop();
        Greenfoot.playSound("end.mp3");
        if(puncte.punct>scormaxim.max)
            scormaxim.max=puncte.punct;
        ultscor.ult=puncte.punct;        
    }
    public void act()
    {
        timp++;
        if(timp==100)
            Greenfoot.setWorld(new Meniu());
    }
}
