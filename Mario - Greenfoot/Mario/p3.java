import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class p3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class p3 extends planta
{
    /**
     * Act - do whatever the p3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int timp;
    public static int distanta=0,directie=1,timp2,timp3;
    public void act() 
    {
        int x,y;
        timp++;        
        x=this.getX();
        y=this.getY();
        if(distanta!=42)
        {            
            distanta++;
            this.setLocation(this.getX(),this.getY()-directie);  
        }
        else
        if(directie>0&&distanta==42&&timp2!=30)
            timp2++;
        else
        if(directie<0&&distanta==42&&timp3!=50)
            timp3++;
        else
            {
                timp2=0;
                timp3=0;
                distanta=0;
                directie=directie*(-1);
            };
        if(timp==3)
        {
            timp=0;
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new p4(),x,y); 
            p4.distanta=distanta;
            p4.directie=directie;
            p4.timp2=timp2;
            p4.timp3=timp3;
        }
    }   
}
