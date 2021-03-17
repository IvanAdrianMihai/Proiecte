import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class monstru here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class monstru extends Actor
{
    /**
     * Act - do whatever the monstru wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static int directia,jos,sus,stanga,dreapta;
    public void act() 
    {       
         
    }       
    public void loveste()
    {
        int x,y,a,b,c;
        x=this.getX();
        y=this.getY();
        if(this.isTouching(mario.class))
        {            
            a=mario.xx;
            b=mario.yy;
            if(b<sus)
            {   
                Greenfoot.playSound("roar.mp3");
                vietimon.vieti--;
                World world=this.getWorld();
                world.removeObject(this);   
                if(vietimon.vieti!=0)
                {
                    if(x>520)
                     {   world.addObject(new md1(),431,547);
                         md1.distanta=0;
                     }
                    else
                    {
                        world.addObject(new ms1(),623,547);
                        ms1.distanta=0;
                    }
                }
                else
                world.addObject(new printesa(),525,412);
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
            sus=this.getY()-this.getImage().getHeight()/2;
            jos=this.getY()+this.getImage().getHeight()/2;
            stanga=this.getX()-this.getImage().getWidth()/2;
            dreapta=this.getX()+this.getImage().getWidth()/2;        
    }
}
