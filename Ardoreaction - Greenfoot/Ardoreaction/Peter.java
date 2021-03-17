import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Peter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Peter extends Cav
{
    public static int vietipeter=3;
    public static int lb=1;
    /**
     * Act - do whatever the Peter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
    }  
    public void per1()
    {
       if(isTouching(Perg1.class))
       {
       if(lb==1)
       {
       String response = Greenfoot.ask("Care este tara cu cea mai mare suprafata? (raspunsul va fi format din litere mici si nu va contine spatii)");
       if (response != null && "rusia".equals(response))
       {
          valP.np1=valP.np1+Contor.total;
          if(Volum.vol==1)
          Greenfoot.playSound("Raspuns corect.mp3");
          Greenfoot.setWorld(new PP4());
        }
        else
        {
            if(Volum.vol==1)
            Greenfoot.playSound("Raspuns gresit.mp3");
            setLocation(741,459);           
        }}
       if(lb==2)
       {
           String response = Greenfoot.ask("What is the country with the largest surface? (the answer will be in small letters and will not contain spaces)");
       if (response != null && "russia".equals(response))
       {
           valP.np1=valP.np1+Contor.total;
           if(Volum.vol==1)
           Greenfoot.playSound("Raspuns corect.mp3");
          Greenfoot.setWorld(new PP4());
        }
        else
        {
            if(Volum.vol==1)
            Greenfoot.playSound("Raspuns gresit.mp3");
            setLocation(741,459);           
        }
       }
    }
    }
    public void per()
    {
       if(lb==1)
       {
       if(isTouching(Perg2.class))
       {
       String response = Greenfoot.ask("Care este formula chimica a apei? (raspunsul va fi format din litere mici si numere si nu va contine spatii)");
       if (response != null && "h2o".equals(response))
       {
           valP.np1=valP.np1+Contor.total;
           if(Volum.vol==1)
           Greenfoot.playSound("Raspuns corect.mp3");
          Greenfoot.setWorld(new PP9());
        }
        else
        {
            if(Volum.vol==1)
            Greenfoot.playSound("Raspuns gresit.mp3");
            setLocation(745,536);           
        }
       }
       }
       if(lb==2)
       {
          if(isTouching(Perg2.class))
       {
       String response = Greenfoot.ask("What is the chemical formula of water? (the answer will be in small letters and numbers and will not contain spaces)");
       if (response != null && "h2o".equals(response))
       {
           valP.np1=valP.np1+Contor.total;
           if(Volum.vol==1)
           Greenfoot.playSound("Raspuns corect.mp3");
          Greenfoot.setWorld(new PP9());
        }
        else
        {
            if(Volum.vol==1)
            Greenfoot.playSound("Raspuns gresit.mp3");
            setLocation(745,536);           
        }
       } 
       }
    }
    public void santinela()
    {
        if(isTouching(gos.class)||isTouching(gou.class)||isTouching(godo.class)||isTouching(god.class))
        {  
            vietipeter--;            
        if(vietipeter==2)
        {
        setLocation(34,35);
        if(Volum.vol==1)
        Greenfoot.playSound("Lovitura gardiansi cactus.mp3");
        Actor inima1=(Actor)getWorld().getObjects(In1.class).get(0);      
        World world;
        world = getWorld();
        world.addObject(new In4(),inima1.getX(),inima1.getY());
        world.removeObject(inima1);
        }
        else
        if(vietipeter==1)
        {
        setLocation(34,35);
        if(Volum.vol==1)
        Greenfoot.playSound("Lovitura gardiansi cactus.mp3");
        Actor inima1=(Actor)getWorld().getObjects(In2.class).get(0);      
        World world;
        world = getWorld();
        world.addObject(new In5(),inima1.getX(),inima1.getY());
        world.removeObject(inima1);
        }
        else
        if(vietipeter==0)
        {
        valP.np1=valP.np1+Contor.total;
        Actor inima1=(Actor)getWorld().getObjects(In3.class).get(0);      
        World world;
        world = getWorld();
        world.addObject(new In6(),inima1.getX(),inima1.getY());
        world.removeObject(inima1);
        vietipeter=3;
        if(Peter.lb==1)
            Greenfoot.setWorld(new PR13());
        else
            Greenfoot.setWorld(new PE13());
        }
        
        }
    }    
}
