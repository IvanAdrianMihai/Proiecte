import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class cd here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class cd extends ciperca
{
    /**
     * Act - do whatever the cd wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public void act() 
    {
        trecere();
        int x,y,a,b;
        x=this.getX();
        y=this.getY();
        a=mario.xx;
        b=mario.yy;
        if(this!=null&&this.isTouching(misc.class))
        {
            Greenfoot.playSound("cochilie.mp3");
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new inv1(),x,y);             
        }
        else
        if(this.isTouching(mario.class)&&y-10>b)
        { 
            Greenfoot.playSound("Kick.mp3");   
            World world=this.getWorld();
            puncte.punct=puncte.punct+5;
            world.removeObject(this);
            mario.viteza=-10;
        } 
        if(timp==70)
        {
            timp=0;
            World world=this.getWorld();
            world.removeObject(this); 
            if(ciperca.directia>0)
                world.addObject(new cd1(),x,y); 
            else
                world.addObject(new cs1(),x,y); 
        }
    } 
    public void trecere()
    {
        timp++;
    }
}
