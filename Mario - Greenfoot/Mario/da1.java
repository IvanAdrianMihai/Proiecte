import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class da1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class da1 extends aripi
{
    /**
     * Act - do whatever the da1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final int gravitatie=1;
    private int viteza,direc=1,z;
    public static int distanta=0;
    public void act() 
    {
        int x,y;
        limite();
        if(this!=null&&this.isTouching(peretim.class))
        {
            this.setLocation(this.getX()-2,this.getY());
            distanta=0;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new sa1(),x,y);
            sa1.distanta=distanta;
        } 
        else{
        if(this!=null&&this.isTouching(pereti.class)&&!this.isTouching(peretim.class))
            z=Greenfoot.getRandomNumber(110);
        if(z<=5)
        {
            z=12;
            viteza=-10;
            fall();
        }
        if(this!=null&&this.isTouching(misc.class))
        {
            Greenfoot.playSound("cochilie.mp3");
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new inv2(),x,y);             
        }
        else
        if(this!=null&&this.isAtEdge())
        {
            World world=this.getWorld();
            world.removeObject(this);
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
            world.addObject(new da2(),x,y);
            da2.distanta=distanta;
        }
        else
        if(this!=null&&this.isTouching(pereti.class)&&distanta+1==200)
        {
            aripi.directia=aripi.directia*(-1);
            distanta=0;
            x=this.getX();
            y=this.getY();
            World world=this.getWorld();
            world.removeObject(this);   
            world.addObject(new sa1(),x,y);
            sa1.distanta=0;
        }
        else
        if(this!=null&&!this.isTouching(pereti.class))
        {
            fall();
            distanta++;
            move(direc);
        }
        
    }  
}
    public void fall()
    {
        int i,x,y;
        boolean ok=true;       
        x=this.getX();
        y=this.getY();
        viteza=viteza+gravitatie;        
        if(viteza<0&&this.isTouching(pereti.class))
        {
            for(i=y;i>=y+viteza&&ok;i--)
                if(!this.isTouching(peretim.class))
                    this.setLocation(x,i);
        }
        else
        if(viteza>0)
            for(i=y;i<=y+viteza&&ok;i++)
                if(!this.isTouching(pereti.class)&&!this.isTouching(peretim.class))
                    this.setLocation(x,i);
                 else
                    ok=false;
        else
        if(viteza<0)
            for(i=y;i>=y+viteza&&ok;i--)
                if(!this.isTouching(pereti.class)&&!this.isTouching(peretim.class))
                    this.setLocation(x,i);
                else
                    ok=false; 
        if(this.isTouching(peretim.class))
        {
            this.setLocation(this.getX(),this.getY()+2);
            viteza=0;
        }
    } 
}
