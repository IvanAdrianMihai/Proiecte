import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class b2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class b2 extends bonus
{
    /**
     * Act - do whatever the b2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza=-7;
    private boolean ok=true;
    public void act() 
    {
        if(ok)
        {
            GreenfootSound up =new GreenfootSound("1up.mp3");
            up.play();
            ok=false;
        }
        fall();
        if(viteza>0&&this.isTouching(peretim.class))
        { 
            this.getWorld().removeObject(this);
            vieti.vietim++;
        }
    }    
    public void fall()
    {
        int i,x,y;
        boolean ok=true;  
        x=this.getX();
        y=this.getY();        
        if(viteza>0)
            for(i=y;i<=y+viteza;i++)
                 this.setLocation(x,i);
        else
        if(viteza<0)
            for(i=y;i>=y+viteza;i--)
                this.setLocation(x,i);   
        viteza=viteza+gravitatie; 
    }    
}
