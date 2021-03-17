import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class br1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class brdr1 extends blocuri
{
    /**
     * Act - do whatever the br1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza;
    public void act() 
    {
        move(-1);
        fall();
    }
    public void fall()
    {
        int i,y,x;
        boolean ok=true;
        x=this.getX();
        y=this.getY();
        for(i=y;i<=y+viteza&&ok;i++)
            if(!this.isAtEdge()&&!this.isTouching(m3.class)&&!this.isTouching(m4.class)&&!this.isTouching(t2.class)&&!this.isTouching(m5.class)&&!this.isTouching(t3.class)&&!this.isTouching(t4.class))
                this.setLocation(x,i);
            else
            {   ok=false;
                this.getWorld().removeObject(this);
            }
        viteza=viteza+gravitatie;
    }
}
