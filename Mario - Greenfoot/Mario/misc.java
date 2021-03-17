import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class td2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class misc extends testoasa
{
    /**
     * Act - do whatever the td2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza,direc=1,timp;
    public void act() 
    {
        int i; 
        for(i=1;i<=5;i++)
        if(!this.isTouching(peretim.class))
            move(direc);
        trecere();
        limite();
        if(this!=null&&this.isTouching(peretim.class))
        {
            if(direc<0)
            {
               this.setLocation(this.getX()+5,this.getY());
               direc=direc*(-1);
            }
            else
            {
                this.setLocation(this.getX()-5,this.getY());
                direc=direc*(-1);
            }
        }
        if(this!=null&&!this.isTouching(pereti.class))
            fall();  
        if(timp==70||this.isAtEdge())
        {
            timp=0;
            World world=this.getWorld();
            world.removeObject(this); 
        }
    }    
    public void fall()
    {
        int i,y,x;
        x=this.getX();
        y=this.getY();
        for(i=y;i<=y+viteza;i++)
            if(!this.isTouching(pereti.class))
                this.setLocation(x,i);
        viteza=viteza+gravitatie;
    }
    public void trecere()
    {
        timp++;
    }
}
