import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class butb1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class butb1 extends butoane
{
    /**
     * Act - do whatever the butb1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.playSound("click.wav");
            Greenfoot.setWorld(new Meniu());
        }
    }    
}
