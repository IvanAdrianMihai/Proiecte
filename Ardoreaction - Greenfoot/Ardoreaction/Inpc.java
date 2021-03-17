import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Inpc here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inpc extends Cav
{
    /**
     * Act - do whatever the Inpc wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(isTouching(j1.class)||isTouching(j2.class)||isTouching(s1.class)||isTouching(s2.class)||isTouching(d1.class)||isTouching(d2.class)||isTouching(up1.class)||isTouching(up2.class))
            if(Peter.vietipeter<3)
            {
                if(Peter.vietipeter==2)
                {
                    Greenfoot.playSound("Viata.mp3");
                    Peter.vietipeter++;
                    Actor inima1=(Actor)getWorld().getObjects(In4.class).get(0);      
                    World world;
                    world = getWorld();
                    world.addObject(new In1(),inima1.getX(),inima1.getY());
                    world.removeObject(inima1);
                    world.removeObject(this);
                }
                else
                if(Peter.vietipeter==1)
                {
                    Greenfoot.playSound("Viata.mp3");
                    Peter.vietipeter++;
                    Actor inima1=(Actor)getWorld().getObjects(In5.class).get(0);      
                    World world;
                    world = getWorld();
                    world.addObject(new In2(),inima1.getX(),inima1.getY());
                    world.removeObject(inima1);
                    world.removeObject(this);
                }
            }
    }    
}
