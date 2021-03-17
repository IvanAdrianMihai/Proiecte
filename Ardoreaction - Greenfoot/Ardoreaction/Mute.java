import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mute here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mute extends Butoane
{
    /**
     * Act - do whatever the Mute wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        removeOnClick();
    }    
    public void removeOnClick()
    {
        if (Greenfoot.mouseClicked(this)||Volum.vol==1) 
        {        
        Volum.vol=1;
        Limba.sound.setVolume(100);
        Greenfoot.setSpeed(100);
        int x = getX();
        int y = getY();
        World world;
        world = getWorld();
        world.removeObject(this);
        world.addObject(new Volum(),x,y); 
        Greenfoot.setSpeed(50);
        }
    }   
}
