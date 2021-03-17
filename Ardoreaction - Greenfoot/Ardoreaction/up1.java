import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class up1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class up1 extends Peter
{
    /**
     * Act - do whatever the up1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
       
         santinela();per();per1();
        if(Greenfoot.isKeyDown("up")&&(!isTouching(Zid1.class)&&!isTouching(Zid2.class))) 
       {
       setLocation(getX(),getY()-2);
       getWorld().addObject(new up2(),getX(),getY());
       getWorld().removeObject(this);
       } 
       else
       if(isTouching(Zid1.class)||isTouching(Zid2.class))
       {
           setLocation(getX(),getY()+1);
        }
        else 
        if(Greenfoot.isKeyDown("left"))
        {
            getWorld().addObject(new s1(),getX(),getY());
            getWorld().removeObject(this);
        }
        else
        if(Greenfoot.isKeyDown("right"))
        {
            getWorld().addObject(new d1(),getX(),getY());
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
