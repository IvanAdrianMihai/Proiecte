import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class et2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class et2 extends artificii
{
    /**
     * Act - do whatever the et2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public void act() 
    {
        timp++;
        if(timp==10)
        {
            World world=this.getWorld();
            world.removeObject(this); 
        }
    }    
}
