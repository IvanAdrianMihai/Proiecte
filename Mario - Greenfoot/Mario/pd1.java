import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class pd1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class pd1 extends monstru
{
    /**
     * Act - do whatever the pd1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int dist=0;
    private boolean ok=true;
    public void act() 
    {
        if(ok)
        {
            Greenfoot.playSound("foc.mp3");
            ok=false;
        }
        dist++;
        move(2);
        if(dist==70||this.isTouching(peretim.class))
            this.getWorld().removeObject(this);
    }    
}
