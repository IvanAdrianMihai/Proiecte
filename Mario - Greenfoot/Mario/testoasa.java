import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class testoasa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class testoasa extends Actor
{
    /**
     * Act - do whatever the testoasa wants to do. This method is called whenever
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
                world.addObject(new td1(),x,y); 
                mario.viteza=-10;
            } 
            else
                if(a<x&&(this.getClass()==s1.class||this.getClass()==s2.class))
                {
                   directia=1;
                   move(directia*2); 
                   x=this.getX();
                   y=this.getY();              
                   World world=this.getWorld();
                   world.removeObject(this);                    
                   world.addObject(new d1(),x,y);                    
                   d1.distanta=2;
                }  
                else
                if(a>x&&(this.getClass()==d1.class||this.getClass()==d2.class))  
                {
                   directia=-1;
                   move(directia*2);
                   x=this.getX();
                   y=this.getY();
                   World world=this.getWorld();
                   world.removeObject(this);  
                   world.addObject(new s1(),x,y);                    
                   s1.distanta=2;
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
