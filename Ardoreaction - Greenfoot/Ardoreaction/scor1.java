import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class scor1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class scor1 extends Desert
{
    /**
     * Act - do whatever the scor1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int sc1=0;
    public void act() 
    {
        setImage(new GreenfootImage(": " + sc1, 30, Color.YELLOW,new Color(0,0,0,0)));
    }    
}
