import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class c2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class c2 extends Desert
{
    /**
     * Act - do whatever the c2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(-6);
        if(getX()==-50)
            getWorld().removeObject(this);
        if(isTouching(r2.class)||isTouching(r1.class))
        {
            scor1.sc1=0;  
            if(Volum.vol==1)
            Greenfoot.playSound("Lovitura gardiansi cactus.mp3");
            if(pun.ex!=0)
            {
                Actor pun=(Actor)getWorld().getObjects(pun.class).get(0); 
                getWorld().removeObject(pun);
            }
            getWorld().removeObject(this);
        };
    }    
}
