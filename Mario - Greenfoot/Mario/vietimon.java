import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class vietimon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class vietimon extends decor
{
    /**
     * Act - do whatever the vietimon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int vieti;
    public void act() 
    {
        setImage(new GreenfootImage("Monstru: "+vieti,20,Color.RED,new Color(0,0,0,0)));
    }    
}
