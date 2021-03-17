import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class gou here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gou extends Spir
{
    /**
     * Act - do whatever the gou wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        int y=getY();
        int x=getX();
        setLocation(x,y-1);
        if(isTouching(Zid1.class)||isTouching(Zid2.class))
       {
           getWorld().addObject(new godo(),x,y);
           getWorld().removeObject(this);
       }
    }    
}
