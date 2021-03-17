import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class b1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class b1 extends bonus
{
    /**
     * Act - do whatever the b1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    private boolean ok=true;
    public void act() 
    {
        if(ok)
        {
            Greenfoot.playSound("floare.mp3");
            ok=false;
        }
        if(this.isTouching(peretim.class))
            this.setLocation(this.getX(),this.getY()-1);
        else
        {
            timp++;
            if(timp==5)
            { 
                puncte.punct=puncte.punct+100; 
                this.getWorld().removeObject(this);
            }
        }
    }    
}
