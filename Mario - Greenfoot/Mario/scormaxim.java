import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class vieti here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class scormaxim extends decor
{
    /**
     * Act - do whatever the vieti wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int max=0;
    public void act() 
    {
        setImage(new GreenfootImage("Scor Maxim: "+max,50,Color.RED,new Color(0,0,0,0)));
    }    
}
