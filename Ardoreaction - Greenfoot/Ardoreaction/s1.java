import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class s1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class s1 extends Peter
{
    /**
     * Act - do whatever the s1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
         santinela();per();per1();
        if(Greenfoot.isKeyDown("left")&&(!isTouching(Zid1.class)&&!isTouching(Zid2.class))) 
       {
       move(-2);
       getWorld().addObject(new s2(),getX(),getY());
       getWorld().removeObject(this);
       }
        else
       if(isTouching(Zid1.class)||isTouching(Zid2.class))
       {
           move(1);
        }
        else      
        if(Greenfoot.isKeyDown("right"))
        {
            getWorld().addObject(new d1(),getX(),getY());
            getWorld().removeObject(this);
        }
        else
        if(Greenfoot.isKeyDown("up"))
        {
            getWorld().addObject(new up1(),getX(),getY());
            getWorld().removeObject(this);
        }
        else
        if(Greenfoot.isKeyDown("down"))
        {
            getWorld().addObject(new j1(),getX(),getY());
            getWorld().removeObject(this);
        }
    }    
}
