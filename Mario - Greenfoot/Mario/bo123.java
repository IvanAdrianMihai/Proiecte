import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class bo123 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class bo123 extends peretim
{
    /**
     * Act - do whatever the bo123 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp,z;    
    public void act() 
    {
        int x,y;
        limite();
        if(timp==0&&(this.isTouching(micdr4.class)||this.isTouching(micst4.class)||this.isTouching(maredr4.class)||this.isTouching(marest4.class)))
        {
            x=this.getX();
            y=this.getY();
            z=Greenfoot.getRandomNumber(14);
            if(z<2)
               this.getWorld().addObject(new b2(),x,y); 
            else
                if(z<7)
                    this.getWorld().addObject(new b3(),x,y-3);
                else
                    this.getWorld().addObject(new b1(),x,y-3);
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
            world.addObject(new no1(),x,y);
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
            world.addObject(new bo123(),x,y);
        }
        }
    }    
}
