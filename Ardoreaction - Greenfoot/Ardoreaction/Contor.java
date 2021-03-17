import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class Contor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Contor extends Cav
{
    public static int total;
    /**
     * Act - do whatever the Contor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setImage(new GreenfootImage(": " + total, 15, Color.YELLOW,new Color(0,0,0,0)));
    }    
}
