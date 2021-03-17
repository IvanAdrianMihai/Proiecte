import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class aripi here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class aripi extends Actor
{
    /**
     * Act - do whatever the aripi wants to do. This method is called whenever
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
                if(this.getClass()==da1.class||this.getClass()==da2.class)
                    world.addObject(new d1(),x,y);
                else
                    world.addObject(new s1(),x,y);
                world.removeObject(this);
                mario.viteza=-10;
            } 
            else
                if(a<x&&(this.getClass()==sa1.class||this.getClass()==sa2.class))
                {
                   directia=1;
                   move(directia*2); 
                   x=this.getX();
                   y=this.getY();              
                   World world=this.getWorld();
                   world.removeObject(this);                    
                   world.addObject(new da1(),x,y);                    
                   da1.distanta=2;
                }  
                else
                if(a>x&&(this.getClass()==da1.class||this.getClass()==da2.class))  
                {
                   directia=-1;
                   move(directia*2);
                   x=this.getX();
                   y=this.getY();
                   World world=this.getWorld();
                   world.removeObject(this);  
                   world.addObject(new sa1(),x,y);                    
                   sa1.distanta=2;
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
