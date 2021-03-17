import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RO3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RO3 extends Butoane
{
    /**
     * Act - do whatever the RO3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
    if (Greenfoot.mouseClicked(this))
        {
            if(Volum.vol==1)
            Greenfoot.playSound("click.mp3");
            Greenfoot.setWorld(new ScorR());
        };
    }    
}
