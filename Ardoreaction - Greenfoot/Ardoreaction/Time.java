import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Time here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Time extends Cav
{
    public static int timp=240;
    /**
     * Act - do whatever the Time wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setImage(new GreenfootImage(": " + timp, 25, Color.RED,new Color(0,0,0,0)));
        if(timp==0)
        {
            valP.np1=valP.np1+Contor.total;
            if(Peter.lb==1)
                Greenfoot.setWorld(new PR13());
            else
                Greenfoot.setWorld(new PE13());
        };            
    }    
}
