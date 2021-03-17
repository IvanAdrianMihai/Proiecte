import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ms3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ms3 extends monstru
{
    /**
     * Act - do whatever the ms3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza,direc=-1;
    public static int distanta=0;
    public void act() 
    {
        int x,y;
        x=this.getX();
        limite();
        if(this!=null&&this.isTouching(mario.class)&&!this.isTouching(micm.class))
            loveste();
        else
        if(this!=null&&x!=431)
        {   
            viteza=0;
            move(direc);
            distanta++;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new ms4(),x,y);
            ms4.distanta=distanta;
        }
        else
        if(this!=null&&x==431)
        {
            monstru.directia=monstru.directia*(-1);
            distanta=0;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new md1(),x,y);
            md1.distanta=0;
        }
        else
        if(this!=null&&!this.isTouching(pereti.class))
        {
            fall();
            move(direc);
        }
    } 
    public void fall()
    {
        int i,y,x;
        boolean ok=true;
        x=this.getX();
        y=this.getY();
        for(i=y;i<=y+viteza&&ok;i++)
            if(!this.isTouching(pereti.class))
                this.setLocation(x,i);
            else 
                ok=false;
        viteza=viteza+gravitatie;
    }   
}
