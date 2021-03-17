import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class micst0 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class micst0 extends mario
{
    /**
     * Act - do whatever the micst0 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
     int x,y;    
     limite();
     atinge(); 
     mario.xx=this.getX();
     mario.yy=this.getY();
     if(this!=null&&this.isTouching(b3.class))
        {
            Greenfoot.playSound("mush.mp3");
            getWorld().removeObject(getOneIntersectingObject(b3.class));
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new marest0(),x,y-8); 
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
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new micm(),x,y);          
        }
     else        
     if(this!=null&&(this.isAtEdge()||this.isTouching(p2.class)||this.isTouching(p4.class)||this.isTouching(p6.class)||this.isTouching(misc.class)))
        {
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new micm(),x,y);             
        }
      else
     if(this!=null&&(this.isTouching(tl1.class)||this.isTouching(tl2.class)))
            this.getWorld().removeObject(this);
     else
     if(this!=null&&!this.isTouching(pereti.class))
        {
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new micst4(),x,y);
        }
     else
     if(this!=null&&Greenfoot.isKeyDown("up"))
        {
            Greenfoot.playSound("jump.mp3");
            x=this.getX();
            y=this.getY();
            mario.viteza=-10;
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new micst4(),x,y);
        } 
     else
     if(this!=null&&Greenfoot.isKeyDown("left")&&this.isTouching(pereti.class))
        {
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new micst1(),x,y);
        }
     else
     if(this!=null&&Greenfoot.isKeyDown("right")&&this.isTouching(pereti.class))
        {
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new micdr0(),x,y);       
        }
    }    
}
