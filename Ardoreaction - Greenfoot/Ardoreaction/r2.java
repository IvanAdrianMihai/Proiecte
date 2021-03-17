import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class r2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class r2 extends Elz
{
    /**
     * Act - do whatever the r2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private static int ti=0;
    public void act() 
    {
        sar();
        if(isTouching(p1.class))
        {
            setLocation(getX(),484);
        };
         if(isTouching(p1.class))
         {
             ti++;
             if(ti==10)
             {
             ti=0;
             getWorld().addObject(new r1(),getX(),getY());
             getWorld().removeObject(this);
             };
         };
    }    
}
