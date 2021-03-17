import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pct here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pct extends Cav
{
    /**
     * Act - do whatever the Pct wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(isTouching(j1.class)||isTouching(j2.class)||isTouching(s1.class)||isTouching(s2.class)||isTouching(d1.class)||isTouching(d2.class)||isTouching(up1.class)||isTouching(up2.class))
        {

            GreenfootSound sound = new GreenfootSound("Stea.mp3");
            if(Volum.vol==1)
            sound.play();
            getWorld().removeObject(this);  
            Contor.total++;    
        }
        
    }    
}
