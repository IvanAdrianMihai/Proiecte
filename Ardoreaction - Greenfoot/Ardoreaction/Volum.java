import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Volum here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Volum extends Butoane
{
    /**
     * Act - do whatever the Volum wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int vol=1;
    public void act() 
    {
        
        removeOnClick();
    }    
    public void removeOnClick()
    {
        if (Greenfoot.mouseClicked(this)||vol==0)
        {
        vol=0;
        Limba.sound.setVolume(0);
        Greenfoot.setSpeed(100);
        int x = getX();
        int y = getY();
        World world;
        world = getWorld();
        world.removeObject(this);
        world.addObject(new Mute(),x,y);
        Greenfoot.setSpeed(50);
    }    
}
}
