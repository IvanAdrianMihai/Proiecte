import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color.*;
/**
 * Write a description of class vieti here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ultscor extends decor
{
    /**
     * Act - do whatever the vieti wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int ult=0;
    public void act() 
    {
        setImage(new GreenfootImage("Ultimul Scor: "+ult,50,Color.WHITE,new Color(0,0,0,0)));
    }    
}
