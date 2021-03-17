import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class sim here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class sim extends peretim
{
    /**
     * Act - do whatever the sim wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public void act() 
    {
        int x,y;
        limite();
        if(this.isTouching(maredr4.class)||this.isTouching(marest4.class))
        {
            Greenfoot.playSound("zid.mp3");
            this.setLocation(this.getX(),this.getY()-2);
            getWorld().removeObject(getOneIntersectingObject(zid.class));
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);          
            world.addObject(new brake(),x,y);
        }
        else
        if(timp==0&&(this.isTouching(micdr4.class)||this.isTouching(micst4.class)))
        {
            this.setLocation(this.getX(),this.getY()-3);                  
            timp++;
        }
        else
        if(timp==3)
        {    
            timp=0;
            World world=this.getWorld();
            this.setLocation(this.getX(),this.getY()+3);
            x=this.getX();
            y=this.getY();
            world.removeObject(this);          
            world.addObject(new sim(),x,y);            
        }
        else
        {
        if(timp>0)
            timp++;
        else
        {
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);          
            world.addObject(new sim(),x,y);
        }
        }
    } 
    
}
