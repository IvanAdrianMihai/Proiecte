import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ba3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ba3 extends bani
{
    /**
     * Act - do whatever the ba3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public void act() 
    {
        int x,y; 
        timp++;
        if(timp==3&&this!=null&&(!this.isTouching(mario.class)||this.isTouching(micm.class)))
        {
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);
            world.addObject(new ba4(),x,y);
        }
        else
        if(this!=null&&this.isTouching(mario.class)&&!this.isTouching(micm.class))
        {
            Greenfoot.playSound("coin.mp3");
            World world=this.getWorld();
            puncte.punct++;
            world.removeObject(this);
        }
    }    
}
