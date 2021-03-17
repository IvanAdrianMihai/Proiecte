import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ciperca here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ciperca extends Actor
{
    /**
     * Act - do whatever the ciperca wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */    
    public static int directia,sus,jos,stanga,dreapta;
    public void act() 
    {       
         
    }       
    public void loveste()
    {
        int x,y,a,b;
        x=this.getX();
        y=this.getY();
        if(this.isTouching(mario.class))
        {            
            a=mario.xx;
            b=mario.yy;
            if(y-10>b)
            { 
                Greenfoot.playSound("Kick.mp3");
                World world=this.getWorld();
                world.removeObject(this);   
                world.addObject(new cd(),x,y); 
                mario.viteza=-10;
            } 
            else
                if(a<x&&(this.getClass()==cs1.class||this.getClass()==cs2.class))
                {
                   directia=1;
                   move(directia*2); 
                   x=this.getX();
                   y=this.getY();              
                   World world=this.getWorld();
                   world.removeObject(this);                    
                   world.addObject(new cd1(),x,y);                    
                   cd1.distanta=2;
                }  
                else
                if(a>x&&(this.getClass()==cd1.class||this.getClass()==cd2.class))  
                {
                   directia=-1;
                   move(directia*2);
                   x=this.getX();
                   y=this.getY();
                   World world=this.getWorld();
                   world.removeObject(this);  
                   world.addObject(new cs1(),x,y);                    
                   cs1.distanta=2;
                }
                else
                {
                   getWorld().removeObjects(getWorld().getObjects(mario.class));
                   getWorld().addObject(new micm(),a,b);
                }
        }        
    }  
     public void limite()
    {
        if(this.isTouching(peretim.class))
        {
            sus=this.getY()-this.getImage().getHeight()/2;
            jos=this.getY()+this.getImage().getHeight()/2;
            stanga=this.getX()-this.getImage().getWidth()/2;
            dreapta=this.getX()+this.getImage().getWidth()/2;
        }            
    }
}
