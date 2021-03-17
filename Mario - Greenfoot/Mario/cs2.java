import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class cs2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class cs2 extends ciperca
{
    /**
     * Act - do whatever the cs2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza,direc=-1;
    public static int distanta;
    public void act() 
    {
        int x,y;
        limite();
        if(this!=null&&(this.isTouching(misc.class)||this.isAtEdge()))
        {
            if(this.isTouching(misc.class))
                Greenfoot.playSound("cochilie.mp3");
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new inv1(),x,y);             
        }
        else
        if(this!=null&&this.isTouching(peretim.class))
        {
            this.setLocation(this.getX()+2,this.getY());
            distanta=0;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new cd1(),x,y);
            cd1.distanta=distanta;
        } 
        else
        if(this!=null&&this.isTouching(mario.class)&&!this.isTouching(micm.class))
            loveste();
        else
        if(this!=null&&this.isTouching(pereti.class)&&distanta+1!=200)
        {   
            viteza=0;
            move(direc);
            distanta++;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new cs1(),x,y);
            cs1.distanta=distanta;
        }
        else
        if(this!=null&&this.isTouching(pereti.class)&&distanta+1==200)
        {
            ciperca.directia=ciperca.directia*(-1);
            distanta=0;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new cd1(),x,y);
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
