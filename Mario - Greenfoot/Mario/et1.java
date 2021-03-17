import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class et1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class et1 extends artificii
{
    /**
     * Act - do whatever the et1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public void act() 
    {
        int x,y;
        timp++;
        if(timp==5)
        {
            Greenfoot.playSound("artificii.mp3");
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this); 
            world.addObject(new et2(),x,y);
        }
    }    
}
