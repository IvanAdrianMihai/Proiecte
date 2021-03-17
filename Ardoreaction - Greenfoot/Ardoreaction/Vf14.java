import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Vf14 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vf14 extends Sageti
{
    /**
     * Act - do whatever the Vf14 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            if(Volum.vol==1)
            Greenfoot.playSound("click.mp3");
            if(Peter.lb==1)
                Greenfoot.setWorld(new PR10());
            else
                Greenfoot.setWorld(new PE10());                
        };
    }    
}
