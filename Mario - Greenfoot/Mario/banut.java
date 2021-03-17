import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class banut here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class banut extends Actor
{
    /**
     * Act - do whatever the banut wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza=-7;
    private boolean ko=true;
    public void act() 
    {        
        if(ko)
        {
            Greenfoot.playSound("coin.mp3");
            ko=false;
        }
        fall();
        if(viteza>0&&this.isTouching(peretim.class))
        {
            this.getWorld().removeObject(this);
            puncte.punct=puncte.punct+5;
        }
    }    
    public void fall()
    {
        int i,x,y;
        boolean ok=true;  
        x=this.getX();
        y=this.getY();   
        if(viteza>0){
            for(i=y;i<=y+viteza;i++)
                 this.setLocation(x,i);}
        else
        if(viteza<0)
            for(i=y;i>=y+viteza;i--)
                this.setLocation(x,i);   
        viteza=viteza+gravitatie; 
    }  
}
