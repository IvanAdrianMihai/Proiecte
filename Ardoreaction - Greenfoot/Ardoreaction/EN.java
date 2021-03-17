import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EN here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EN extends Actor
{
    /**
     * Act - do whatever the EN wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
         if (Greenfoot.mouseClicked(this))
        {
            Peter.lb=2;   
            Greenfoot.playSound("click.mp3");
            Greenfoot.setWorld(new StartEngleza());
        };
    }    
}
