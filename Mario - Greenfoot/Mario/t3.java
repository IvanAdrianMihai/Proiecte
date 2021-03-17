import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class t3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class t3 extends peretim
{
    /**
     * Act - do whatever the t3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        int x,y;
        limite();
        x=this.getX();
        y=this.getY();
        World world=this.getWorld();
        world.removeObject(this);   
        world.addObject(new t3(),x,y);
    }    
}
