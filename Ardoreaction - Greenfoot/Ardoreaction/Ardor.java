import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ardor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ardor extends PlaneteMeteoriti
{
    public static int vieti=3;
    /**
     * Act - do whatever the Ardor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        GreenfootImage image = getImage();
        Greenfoot.setSpeed(55);
        int y1=getY();
        int x1=getX();
        int x2=0,y2=0;        
        if (vieti==2&&getWorld().getObjects(Inima1.class).size() != 0) 
        { 
        Actor inima1=(Actor)getWorld().getObjects(Inima1.class).get(0);      
        World world;
        world = getWorld();
        world.addObject(new Inima4(),inima1.getX(),inima1.getY());
        world.removeObject(inima1);
        }
        if(vieti==1&&getWorld().getObjects(Inima2.class).size() != 0)
        {            
        Actor inima2=(Actor)getWorld().getObjects(Inima2.class).get(0);
        World world;
        world = getWorld();
        world.addObject(new Inima5(), inima2.getX(),inima2.getY());
        world.removeObject(inima2);
        }
        if(vieti==0&&getWorld().getObjects(Inima3.class).size() != 0)
        {
        valP.np1=valP.np1+Counter.totalCount;
        vieti=3;      
        Actor inima3=(Actor)getWorld().getObjects(Inima3.class).get(0);
        World world;        
        world = getWorld();
        world.addObject(new Inima6(),inima3.getX(),inima3.getY());
        world.removeObject(inima3);
        if(Peter.lb==1)
            Greenfoot.setWorld(new PR13());
        else
            Greenfoot.setWorld(new PE13());
        }
        
        if(mouse!=null)
        {
            y2=mouse.getY(); 
            x2=mouse.getX();
        }
        if(mouse != null&&y1!=y2)
        {
            if(y1<y2)
            {     
                setLocation(getX(),getY()+1);
            };
            if(y1>y2)
            {
                setLocation(getX(),getY()-1);
            };
        }
        if(mouse != null&&x1!=x2)
        {
            if(x1<x2)
            {     
                setLocation(getX()+1,getY());
            };
            if(x1>x2)
            {
                setLocation(getX()-1,getY());
            };
        }       
    }  
   
}
