import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class maredr4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class maredr4 extends mario
{
    /**
     * Act - do whatever the maredr4 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
     int x,y;
     limite();
     atinge(); 
     mario.xx=this.getX();
     mario.yy=this.getY();
     World world=this.getWorld();
     fall();
     if(this!=null&&this.isTouching(b3.class))
        {
            Greenfoot.playSound("mush.mp3");
            getWorld().removeObject(getOneIntersectingObject(b3.class));
            puncte.punct=puncte.punct+100;
        }
     else
     if(this.isTouching(pd1.class))
     {
            getWorld().removeObject(getOneIntersectingObject(pd1.class));
            x=this.getX();
            y=this.getY();
            world.removeObject(this);   
            world.addObject(new micdr0(),x,y); 
     }
     else
     if(this.isTouching(ps1.class))
     {
            getWorld().removeObject(getOneIntersectingObject(ps1.class));
            x=this.getX();
            y=this.getY();
            world.removeObject(this);   
            world.addObject(new micdr0(),x,y); 
        }
     else
     if(this!=null&&this.isTouching(pereti.class))
     {
         x=getX();
         y=getY(); 
         mario.viteza=0;
         world.addObject(new maredr0(),x,y);
         world.removeObject(this);
     }
     else
     if(this!=null&&(this.isAtEdge()||this.isTouching(p2.class)||this.isTouching(p4.class)||this.isTouching(p6.class)||this.isTouching(misc.class)))
        {
            x=this.getX();
            y=this.getY();
            world.removeObject(this);   
            world.addObject(new micdr0(),x,y);             
        }
     else
     if(this!=null&&(this.isTouching(tl1.class)||this.isTouching(tl2.class)))
            this.getWorld().removeObject(this);
     else
     if(this!=null&&Greenfoot.isKeyDown("right"))
     {
       int i; 
       for(i=1;i<=5;i++)
            if(!this.isTouching(peretim.class))
                move(1);       
     }
     else
     if(this!=null&&Greenfoot.isKeyDown("left"))
     {
         x=getX();
         y=getY();
         world.addObject(new marest4(),x,y);
         world.removeObject(this); 
     }
    }    
}
