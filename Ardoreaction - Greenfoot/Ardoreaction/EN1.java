import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EN1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EN1 extends Butoane
{
    /**
     * Act - do whatever the EN1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            if(Volum.vol==1)
            Greenfoot.playSound("click.mp3");
            Greenfoot.setWorld(new PE1());
        };
    }    
}
