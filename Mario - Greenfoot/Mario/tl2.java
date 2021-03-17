
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class tl2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tl2 extends treceri
{
    /**
     * Act - do whatever the tl2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp=0,x,y;
    public static boolean personaj=false;
    public void act() 
    {
        if(timp!=0)
            timp++;
        if(timp!=0&&timp%4==0)
        {
            x=Greenfoot.getRandomNumber(197);
            y=Greenfoot.getRandomNumber(69);
            this.getWorld().addObject(new et1(),39+x,22+y);
        }
        if(this.isTouching(mario.class))
        {
             timp++;             
        }
        if(timp==80)
        {
            Level2.back.stop();
            Greenfoot.setWorld(new Level3());
        }
    } 
}
