import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class vieti here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class vieti extends decor
{
    /**
     * Act - do whatever the vieti wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int vietim;
    public void act() 
    {
        setImage(new GreenfootImage("Vieti: "+vietim,20,Color.YELLOW,new Color(0,0,0,0)));
    }    
}
