import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class but3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class but3 extends butoane
{
    /**
     * Act - do whatever the but3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.playSound("click.wav");
            Greenfoot.setWorld(new Instructiuni());
        }
    }    
}
