import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class inv1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class inv1 extends Actor
{
    /**
     * Act - do whatever the inv1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza;
    public void act() 
    {
        fall();
    } 
    public void fall()
    {
        int i,y,x;
        boolean ok=true;
        x=this.getX();
        y=this.getY();
        for(i=y;i<=y+viteza&&ok;i++)
            if(!this.isAtEdge())
                this.setLocation(x,i);
            else 
                ok=false;
        if(this.isAtEdge())
        {
            puncte.punct++;
            this.getWorld().removeObject(this);
        };
        viteza=viteza+gravitatie;
    }
}
