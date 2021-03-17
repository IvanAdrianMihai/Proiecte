import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class puncte here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class puncte extends decor
{
    /**
     * Act - do whatever the puncte wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int punct;
    public void act() 
    {
        setImage(new GreenfootImage("Scor: "+punct,20,Color.YELLOW,new Color(0,0,0,0)));
    }    
}
