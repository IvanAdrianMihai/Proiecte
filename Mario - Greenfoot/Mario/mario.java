import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class mario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class mario extends Actor
{
    /**
     * Act - do whatever the mario wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public final int gravitatie=1;
    public static int viteza;
    public static int xx,yy,sus,jos,stanga,dreapta;
    public void act() 
    {
        // Add your action code here.
    } 
    public void fall()
    {
        int i,x,y;
        boolean ok=true;
        atinge();        
        x=this.getX();
        y=this.getY();
        viteza=viteza+gravitatie;        
        if(viteza<0&&(loveste()||this.isTouching(pereti.class)))
        {
            for(i=y;i>=y+viteza&&ok;i--)
                if(!this.isTouching(peretim.class))
                    this.setLocation(x,i);
        }
        else
        if(viteza>0)
            for(i=y;i<=y+viteza&&ok;i++)
                if(!loveste()&&!this.isTouching(pereti.class)&&!this.isTouching(peretim.class)&&!this.isTouching(monstru.class))
                    this.setLocation(x,i);
                 else
                    ok=false;
        else
        if(viteza<0)
            for(i=y;i>=y+viteza&&ok;i--)
                if(!loveste()&&!this.isTouching(pereti.class)&&!this.isTouching(peretim.class))
                    this.setLocation(x,i);
                else
                    ok=false;        
    }
    public boolean loveste()
    {
        if(this.isTouching(monstru.class)||this.isTouching(aripi.class)||this.isTouching(cs1.class)||this.isTouching(cd1.class)||this.isTouching(cd2.class)||this.isTouching(cs2.class)||this.isTouching(d1.class)||this.isTouching(d2.class)||this.isTouching(s1.class)||this.isTouching(s2.class))
            return true;
        else
            return false;
    }
    public void limite()
    {
        if(this.isTouching(peretim.class))
        {
            sus=this.getY()-this.getImage().getHeight()/2;
            jos=this.getY()+this.getImage().getHeight()/2;
            stanga=this.getX()-this.getImage().getWidth()/2;
            dreapta=this.getX()+this.getImage().getWidth()/2;
        }       
        if(this.isTouching(tl1.class))
            if(this.getClass()==maredr0.class||this.getClass()==maredr1.class||this.getClass()==maredr2.class||this.getClass()==maredr3.class||this.getClass()==maredr4.class||this.getClass()==marest0.class||this.getClass()==marest1.class||this.getClass()==marest2.class||this.getClass()==marest3.class||this.getClass()==marest4.class)
                tl1.personaj=true;
        if(this.isTouching(tl2.class))
            if(this.getClass()==maredr0.class||this.getClass()==maredr1.class||this.getClass()==maredr2.class||this.getClass()==maredr3.class||this.getClass()==maredr4.class||this.getClass()==marest0.class||this.getClass()==marest1.class||this.getClass()==marest2.class||this.getClass()==marest3.class||this.getClass()==marest4.class)
                tl2.personaj=true;
    }
    public void atinge()
    {
        if(this.isTouching(peretim.class))
        {
            if(stanga+5>peretim.dreapta)
            {
               this.setLocation(this.getX()+5,this.getY());
            }
            else
            if(sus+5>peretim.jos)
            {
                this.setLocation(this.getX(),this.getY()+2);
                mario.viteza=0;
            }
            else            
            if(dreapta-5<peretim.stanga)
            {
                this.setLocation(this.getX()-5,this.getY());            
            }
        } 
    }
}
