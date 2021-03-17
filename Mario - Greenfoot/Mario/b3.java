import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class b3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class b3 extends bonus
{
    /**
     * Act - do whatever the b3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */     
    private final int gravitatie=1;
    private int viteza,direc=1,timp;
    private boolean ok=true;
    public void act() 
    {
        timp++;
        if(this.isTouching(peretim.class)&&ok==true)
            this.setLocation(this.getX(),this.getY()-1);
        else            
        {
            ok=false;
            move(direc);
            limite();
            if(this!=null&&this.isTouching(peretim.class))
            {
                if(stanga+5>peretim.dreaptab)
                {
                    this.setLocation(this.getX()+2,this.getY());
                    direc=direc*(-1);
                }
                else           
                if(dreapta+5<peretim.stangab)
                {
                    this.setLocation(this.getX()-2,this.getY());
                    direc=direc*(-1);
                }
            }
            if(this!=null&&!this.isTouching(pereti.class))
                fall();
        }
        if(timp==500||this.isAtEdge())
            this.getWorld().removeObject(this);
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
}
