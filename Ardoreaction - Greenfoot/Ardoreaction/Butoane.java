import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Butoane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Butoane extends Actor
{
    /**
     * Act - do whatever the Butoane wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
     public void actr()
    {
        GreenfootImage image = getImage();
        image.scale(300, 70);
        setImage(image);
    }
    public void act() 
    {
    }    
}
