import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class but1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class but1 extends butoane
{
    /**
     * Act - do whatever the but1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.playSound("here.mp3");
            Meniu.remix.stop();
            Greenfoot.setWorld(new Level1());
        }
    }    
}
