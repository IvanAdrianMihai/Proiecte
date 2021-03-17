import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class brake here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class brake extends peretim
{
    /**
     * Act - do whatever the brake wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        int x,y;
        World world=this.getWorld();
        x=this.getX();
        y=this.getY();
        world.removeObject(this);
        world.addObject(new brdr1(),x-4,y+4);
        world.addObject(new brdr2(),x+4,y+4);
        world.addObject(new brst1(),x-6,y-6);
        world.addObject(new brst2(),x+6,y-6);
    }    
}
