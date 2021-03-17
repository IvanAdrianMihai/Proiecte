import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class god here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class god extends Spir
{
    /**
     * Act - do whatever the god wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
       int x=getX();
       int y=getY();
       move(1);
       if(isTouching(Zid1.class)||isTouching(Zid2.class))
       {
           getWorld().addObject(new gos(),x,y);
           getWorld().removeObject(this);
       }
    }    
}
