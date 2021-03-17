import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class marest3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class marest3 extends mario
{
    /**
     * Act - do whatever the marest3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
     int x,y;  
     limite();
     atinge(); 
     mario.xx=this.getX();
     mario.yy=this.getY();
     x=this.getX();
     y=this.getY();
     World world=this.getWorld();
     if(this!=null&&this.isTouching(b3.class))
        {
            Greenfoot.playSound("mush.mp3");
            getWorld().removeObject(getOneIntersectingObject(b3.class));
            puncte.punct=puncte.punct+100;
        }
     else
     if(this!=null&&loveste())
        {
            if(this.isTouching(pd1.class))
                 getWorld().removeObject(getOneIntersectingObject(pd1.class));
            else
            if(this.isTouching(ps1.class))
                getWorld().removeObject(getOneIntersectingObject(ps1.class));
            x=this.getX();
            y=this.getY();
            world.removeObject(this);   
            world.addObject(new micst0(),x,y);            
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
     if(this!=null&&!this.isTouching(pereti.class))
        {
            x=this.getX();
            y=this.getY();
            world.removeObject(this);   
            world.addObject(new marest4(),x,y);
        }
     else
     if(this!=null&&Greenfoot.isKeyDown("up"))
        {
            Greenfoot.playSound("jump.mp3");
            x=this.getX();
            y=this.getY();
            mario.viteza=-10;
            world.removeObject(this);   
            world.addObject(new maredr4(),x,y);
        } 
     else
     if(this!=null&&Greenfoot.isKeyDown("left")&&this.isTouching(pereti.class))
     {
       int i; 
       for(i=1;i<=5;i++)
         if(!this.isTouching(peretim.class))
            move(-1);   
       x=this.getX();
       y=this.getY();
       world.removeObject(this);   
       world.addObject(new marest1(),x,y);                     
     }
     else
     if(this!=null&&this.isTouching(pereti.class))
        {
         world.removeObject(this);   
         world.addObject(new maredr0(),x,y);  
        }
    }    
}
