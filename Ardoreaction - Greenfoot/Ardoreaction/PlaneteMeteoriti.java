import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlaneteMeteoriti here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlaneteMeteoriti extends Actor
{
    /**
     * Act - do whatever the PlaneteMeteoriti wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void Touch()
    {
              
         if(getX()==-200)
            getWorld().removeObject(this);
            else
        {
        Actor ardor=(Actor)getWorld().getObjects(Ardor.class).get(0);
        if(isTouching(Ardor.class))
        {
            Ardor.vieti--;
            if(Ardor.vieti!=0)
                if(Volum.vol==1)
                Greenfoot.playSound("lovitura.mp3");
            getWorld().removeObject(this);
        };    
        }
    } 
    public void Move()
    {
        move(-1);
    }
    public void act() 
    {
       
    }    
}
