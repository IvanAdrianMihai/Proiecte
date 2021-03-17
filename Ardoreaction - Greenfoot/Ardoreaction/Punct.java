import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Punct here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Punct extends Actor
{
    /**
     * Act - do whatever the Punct wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(-1);
        if(getX()==-100)
            getWorld().removeObject(this);
        else
        if(isTouching(Ardor.class))
        {
            if(Volum.vol==1)
            Greenfoot.playSound("Stea.mp3");
            Space space=(Space)getWorld();
            space.removeObject(this);  
            Counter.totalCount++;    
        }
    }    
}
