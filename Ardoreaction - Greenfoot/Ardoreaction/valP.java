import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class valP here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class valP extends Actor
{
    /**
     * Act - do whatever the valP wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int nr1=0,np1=0;
    public void act() 
    {
        setImage(new GreenfootImage(""+nr1, 50, Color.RED,new Color(0,0,0,0)));
    }     
}
