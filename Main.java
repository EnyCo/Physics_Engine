import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
public class Main extends JFrame
{
    public static class Player
    {
        private static Point2D.Double loc = new Point2D.Double(0,0);
        private static double hVelocity = 0;
        private static double vVelocity = 0;
        private static double hAcceleration = 0;
        private static double vAcceleration = 0;
        public Player(Point2D.Double l, double hv,double vv,double ha,double va)
        {
            loc = l;
            hVelocity = hv;
            vVelocity = vv;
            hAcceleration = ha;
            vAcceleration = va;
        }
        //set
        public static void setLoc(Point2D.Double l)
        {
            loc = l;
        }
        public static void setHV(double hv)
        {
            hVelocity = hv;
        }
        public static void setVV(double vv)
        {
            vVelocity = vv;
        }
        public static void setHA(double ha)
        {
            hAcceleration = ha;
        }
        public static void setVA(double va)
        {
            vAcceleration = va;
        }
        //get
        
        public static Point2D.Double getLoc()
        {
            return loc;
        }
        public static double getHV()
        {
            return hVelocity;
        }
        public static double getVV()
        {
            return vVelocity;
        }
        public static double getHA()
        {
            return hAcceleration;
        }
        public static double getVA()
        {
            return vAcceleration;
        }
    }
    
    static String OS = System.getProperty("os.name"); 
    static KeyboardInput keyboard = new KeyboardInput();
    static MouseInput1 mouse; 
    
    static Canvas canvas;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    static int HEIGHT = screenSize.height;
    static int WIDTH = screenSize.width;
    static int exitPopUp = 0;
    static int X = 0;
    static int Y = 0;
    static int speed = 10;
    static int imgSize = 25;
    static int time = 0;
    static boolean answer = false;
    
    
    static Player me = new Player(new Point2D.Double(WIDTH/2,HEIGHT/2), 0,0,0,10);// divide all v by 100 | switch neg and pos signs for v.
    public static void main(String[] args) throws InterruptedException, IOException
    {  
        //openSave();
        if (OS.indexOf("Windows") != -1)
        {
            HEIGHT = HEIGHT - 50;//40
        }
        
        Main program = new Main();
        program.setTitle( "Main" );
        program.setVisible( true );
        program.run();
        System.exit( 0 );
    } 
    
    public Main() 
    {
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas = new Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setSize( WIDTH, HEIGHT );
        add( canvas );
        pack();
        
        // for keyboard using
        addKeyListener( keyboard );
        canvas.addKeyListener( keyboard );
        
        // for mouse using
        mouse = new MouseInput1();
        addMouseListener( mouse );
        addMouseMotionListener( mouse );
        canvas.addMouseListener( mouse );
        canvas.addMouseMotionListener( mouse );
    }
    
    /*public static void openSave() throws IOException
    {
        ArrayList<String> lines = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader("Save.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                lines.add(line);
            }
            String everything = sb.toString();
        } catch (IOException e) {
            System.out.println("File does not exist");
            e.printStackTrace();
        }
    }
    public void save()
    {
        try {
            FileWriter myWriter = new FileWriter("Save.txt");   
            myWriter.write(Player.getLoc().getX()+"\n");
            myWriter.write(Player.getLoc().getY()+"\n"); 
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Saving progress failed");
            e.printStackTrace();
        }
    }*/
    
    public void run() throws InterruptedException
    { 
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage bi = gc.createCompatibleImage( WIDTH, HEIGHT );
        
        Graphics graphics = null;
        Graphics2D draw = null;
        Color background = Color.WHITE;       
        while(answer == false) 
        {
            try { 
                keyboard.poll();
                mouse.poll();
                draw = bi.createGraphics();
                draw.setColor( background );
                draw.fillRect( 0, 0, WIDTH, HEIGHT );
                
                
                draw.setColor( Color.BLACK );
                draw.fillOval((int)me.getLoc().getX()-imgSize/2,(int)me.getLoc().getY()-imgSize/2,imgSize,imgSize);//prints ball
                //draw.setColor( Color.BLACK );
                draw.drawString("("+(int)me.getLoc().getX()/10+","+(int)(HEIGHT-me.getLoc().getY())/10+")", 12,12);//prints coordinates
                if (exitPopUp == 1 && ExitPopUp.input == false)
                {
                    ExitPopUp.main("Do you want to exit the program?");
                    answer = ExitPopUp.answer;
                    exitPopUp = 0;
                    ExitPopUp.input = false;
                }
                else if (exitPopUp == 0)
                {
                    processInput();
                }
                graphics = buffer.getDrawGraphics();
                graphics.drawImage( bi, 0, 0, null );
                if( !buffer.contentsLost() )
                buffer.show();
            
              
                Thread.sleep(10);
            }
                finally 
            {
                if( graphics != null ) 
                  graphics.dispose();
                if( draw != null ) 
                  draw.dispose();
            }
            time+=10;
        }
    }
    public void processInput() throws InterruptedException
    {
        if ((keyboard.keyDown( KeyEvent.VK_D ) || keyboard.keyDown( KeyEvent.VK_RIGHT )))//&& me.getLoc().getX()+imgSize/2 < WIDTH)        
        {
            //me.setLoc(new Point2D.Double((int)(me.getLoc().getX()+speed),(int)me.getLoc().getY())); 
            me.setHV(5);//me.getHV() + speed);
        }
        if ((keyboard.keyDown( KeyEvent.VK_A ) || keyboard.keyDown( KeyEvent.VK_LEFT )))//&& me.getLoc().getX() > 0)        
        {
            //me.setLoc(new Point2D.Double((int)(me.getLoc().getX()-speed),(int)me.getLoc().getY()));   
            me.setHV(-5);//me.getHV() + speed);
        }
        if (keyboard.keyDownOnce( KeyEvent.VK_SPACE ))
        {
            me.setVV(-10);
        }   
        
        //beginning of elastic collisions
        if (me.getLoc().getX()==15)
        {
            me.setHV(10);
        }  
        if (me.getLoc().getX()==WIDTH-15)
        {
            me.setHV(-10);
        }  
        if (HEIGHT-me.getLoc().getY()==20)
        {
            me.setVV(-10);
        } 
        //end of elastic collisions
        if (time%10 == 0)//updates the balls position 100 times a second
        {
            me.setVV(me.getVV() + me.getVA()/100);
            me.setHV(me.getHV() + me.getHA()/100);
            if (me.getLoc().getX()+ me.getHV() <= WIDTH - imgSize/2 && me.getLoc().getX()+ me.getHV() >= 0 + imgSize/2)
            {
                me.setLoc(new Point2D.Double((int)(me.getLoc().getX()+ me.getHV()),(int)(me.getLoc().getY()))); 
            }
            if (me.getLoc().getY() + me.getVV() <= HEIGHT - imgSize/2 && me.getLoc().getY() + me.getVV() >= 0 + imgSize/2)
            {
                me.setLoc(new Point2D.Double((int)(me.getLoc().getX()),(int)(me.getLoc().getY() + me.getVV()))); 
            }
        } 
        
        /*if (mouse.buttonDownOnce( 1 ))
        {
            Point p = MouseInfo.getPointerInfo().getLocation();
            Point c = canvas.getLocationOnScreen();
            int x = p.x-c.x;  
            int y = p.y-c.y;
        }*/
        if (keyboard.keyDownOnce( KeyEvent.VK_ESCAPE ) && exitPopUp == 0)
        {
            exitPopUp = 1;
        }
        else 
        {   exitPopUp = 0;
        }
    } 
}
