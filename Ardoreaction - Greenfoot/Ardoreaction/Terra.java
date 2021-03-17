import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Terra here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Terra extends PlaneteMeteoriti
{
    /**
     * Act - do whatever the Terra wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        Move();
        Actor ardor=(Actor)getWorld().getObjects(Ardor.class).get(0);
        if(getX()<=783)
        {
            if(getX()-ardor.getX()<=300)  
            {
                valP.np1=valP.np1+Counter.totalCount;
                Greenfoot.setWorld(new PP5());              
            };
        }
    }    
}
