import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class pun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class pun extends Desert
{
    /**
     * Act - do whatever the pun wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int ex=1;
    public void act() 
    {
        move(-6);
        if(isTouching(r1.class)||isTouching(r2.class))
        {
            if(Volum.vol==1)
            Greenfoot.playSound("Stea.mp3");
            scor1.sc1++;
            ex=0;
            getWorld().removeObject(this);
        };
    }    
}
