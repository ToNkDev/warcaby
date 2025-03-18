import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class slot_B extends JButton implements ActionListener {
    protected int posX = 0;
    protected int posY = 0;
    public boolean attacked = false;
    protected boolean debug = false;
    public String figure = "";
    public String team = "";
    protected boolean occupied =false;
    protected boolean available = false;
    public void setAvailable(boolean val)
    {
        available = val;
    }
    public boolean isOccupied()
    {
        return occupied;
    }
    public void setOccupied(boolean val)
    {
        occupied = val;
    }
    public int[] getPos()
    {
        return new int[]{posX,posY};
    }
    public void setPos(int[] Position)
    {
        posX = Position[0];
        posY = Position[1];
        repaint();
    }

    public slot_B(int index,int x,int y,slots_p board)
    {

        super();
        posX = x;
        posY = y;
        String posX_String = Integer.toString(posX);
        String posY_String = Integer.toString(posY);
       if (debug){ setText(posX_String + ";" + posY_String);}
        addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (occupied == true )
                {
                    if(board.turn == team)
                    {
                       if (team == "Black")
                       {
                           board.findMoveBlack(getPos());
                       }
                       else { board.findMoveWhite(getPos());}
                    }
                }
                else if(available == true)
                {
                    if (board.turn == board.selected_slot.team)
                    {
                        board.Move(getPos());
                    }
                }
                else
                {
                    board.clearAllSelection();
                }

            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
