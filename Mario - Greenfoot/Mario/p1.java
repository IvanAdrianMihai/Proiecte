import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class p1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class p1 extends planta
{
    /**
     * Act - do whatever the p1 wants to do. This method is called whenever
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
            world.addObject(new p2(),x,y); 
            p2.distanta=distanta;
            p2.directie=directie;
            p2.timp2=timp2;
            p2.timp3=timp3;
        }
    }      
}
