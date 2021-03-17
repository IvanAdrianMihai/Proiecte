import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class micm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class micm extends mario
{
    /**
     * Act - do whatever the micm wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static boolean ok=true;
    public void act() 
    {
        if(ok&&vieti.vietim>1)
        {
            Greenfoot.playSound("die.mp3");
            ok=false;
        }
        if(vieti.vietim>1)
            this.setLocation(this.getX(),this.getY()+10);
        else
            this.setLocation(this.getX(),this.getY()+40);
        int x,y;
        x=this.getX();
        y=this.getY();        
        World world=this.getWorld();
        if (!this.isAtEdge())
        {
            world.removeObject(this);       
            world.addObject(new micm(),x,y);
            micm.ok=ok;
        }
        else
        {   
             ok=true;
             world.removeObject(this);
             vieti.vietim--;
             if(vieti.vietim==0)
                 Greenfoot.setWorld(new Gameover());
             else
             if(world instanceof Level1 )
             {
                 world.addObject(new micdr0(),38,782);
             }
             else
             if(world instanceof Level2)
             {
                 world.addObject(new micdr0(),64,765);
             }
             else
             if(world instanceof Level3)
             {
                 world.addObject(new micdr0(),517,305);
             }
         }
    }    
}
