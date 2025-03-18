import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.*;
public class slots_p extends JPanel {
    slot_B[] slotArr = new slot_B[64];
    public slot_B selected_slot = null;
    public String turn = "Black";
    public slot_B target = null;
    public void clearAllSelection()
    {
        target = null;
        boolean even2 = true;

        int x2 = 1;
        int y2 = 1;
        //
        for (int i =0 ; i<slotArr.length;i++)
        {
            slotArr[i].setAvailable(false);
            slotArr[i].attacked = false;
            if (even2)
            {
                if ( (x2 & 1) == 0 ) {slotArr[i].setBackground(new Color(174, 174, 174));} else {slotArr[i].setBackground(new Color(100, 100, 100));}
            }
            else
            {
                if ( (x2 & 1) != 0 ) {slotArr[i].setBackground(new Color(174, 174, 174));} else {slotArr[i].setBackground(new Color(100, 100, 100));}
            }
            if (x2==8)
            {
                x2 = 1;
                y2++;
                even2 = !even2;
            }
            else {
                x2++;
            }
        }
    }
    public void createTeam(int slotIndex, String team, String figure)
    {
        slotArr[slotIndex].occupied = true;
        slotArr[slotIndex].setText(figure);
        slotArr[slotIndex].figure = figure;
        slotArr[slotIndex].team = team;
    }
    public void Move(int[] Pos)
    {
        System.out.println("Attempting to move...");
        if (selected_slot != null)
        {
            System.out.println("Selected slot is not null");

            // For each slot, check if it matches the position.
            for (int i = 0; i < slotArr.length; i++)
            {
                if(slotArr[i].getPos()[0] == Pos[0] && slotArr[i].getPos()[1] == Pos[1])
                {


                    // Check if the selected slot's background color equals the attacking color.
                    if (target != null)
                    {
                        System.out.println(Arrays.toString(slotArr[i].getPos()));
                        System.out.println(Arrays.toString(target.getPos()));
                        if (slotArr[i].attacked == true)
                        {
                            target.team = "";
                            target.occupied = false;
                            target.available = false;
                            target.setText("");
                            target.figure = "";
                        }
                    }

                    System.out.println("Move successful");

                    // Move the piece by updating the team and figure.
                    selected_slot.available = false;
                    selected_slot.occupied = false;
                    slotArr[i].team = selected_slot.team;
                    selected_slot.team = "";
                    slotArr[i].figure = selected_slot.figure;
                    selected_slot.figure = "";
                    selected_slot.setText("");
                    if(slotArr[i].team == "Black" && slotArr[i].getY() == 1)
                    {
                        slotArr[i].figure = "!O!";
                    }
                    if(slotArr[i].team == "Black" && slotArr[i].getY() == 0)
                    {
                        slotArr[i].figure = "!O!";
                    }

                    if(slotArr[i].team == "White" && slotArr[i].getY() == 8)
                    {
                        slotArr[i].figure = "!X!";
                    }
                    if(slotArr[i].team == "White" && slotArr[i].getY() == 9)
                    {
                        slotArr[i].figure = "!X!";
                    }

                    if (target != null)
                    {
                        // You can add any logic for additional actions when a target is present.
                    }

                    // Update the moved slot
                    slotArr[i].setText(slotArr[i].figure);
                    slotArr[i].setOccupied(true);
                    System.out.println("POS X: "+Integer.toString(slotArr[i].getX()));
                    System.out.println("POS Y: "+Integer.toString(slotArr[i].getY()));
                }
            }

            System.out.println("Clearing selection");

            // Switch turns after the move
            if (turn.equals("White"))
            {
                turn = "Black";
            }
            else
            {
                turn = "White";
            }

            System.out.println(turn);

            // Update the slot teams and clear any selections.
            updateSlotTeams();
            clearAllSelection();
        }
    }
    public void updateSlotTeams()
    {
        for (int i = 0; i < slotArr.length ; i++)
        {
            if (slotArr[i].team.equals("White"))
            {
                slotArr[i].setForeground(new Color(255,255,255));
            }
            else
            {
                slotArr[i].setForeground(new Color(0,0,0));
            }
        }
    }
    public void findMoveBlack(int[] Pos) {
        clearAllSelection(); // Clear previous selections

        for (int i = 0; i < slotArr.length; i++) {
            if (slotArr[i].getPos()[0] == Pos[0] && slotArr[i].getPos()[1] == Pos[1]) {
                slotArr[i].setBackground(new Color(255, 92, 0)); // Highlight the selected piece
                selected_slot = slotArr[i];
            }

            // Check diagonal move (up-left) for black piece
            if (slotArr[i].getPos()[0] == Pos[0] - 1 && slotArr[i].getPos()[1] == Pos[1] - 1) {
                if (slotArr[i].team != turn && !slotArr[i].team.isEmpty()) { // Attack move
                    target = slotArr[i]; // Set target
                    slot_B moveSlot = findSlotByPosition(new int[]{slotArr[i].getPos()[0] - 1, slotArr[i].getPos()[1] - 1});
                    moveSlot.setAvailable(true);
                    moveSlot.setBackground(new Color(255, 0, 0)); // Attack slot
                    moveSlot.attacked = true;
                } else { // Regular move (empty or own team slot)
                    if (slotArr[i].getPos()[0] > 1) { // Ensure black doesn't move off the board
                        slotArr[i].setBackground(new Color(64, 255, 0)); // Highlight move slot
                        slotArr[i].setAvailable(true);
                    }
                }
            }

            // Check diagonal move (up-right) for black piece
            if (slotArr[i].getPos()[0] == Pos[0] + 1 && slotArr[i].getPos()[1] == Pos[1] - 1) {
                if (slotArr[i].team != turn && !slotArr[i].team.isEmpty()) { // Attack move
                    target = slotArr[i]; // Set target
                    slot_B moveSlot = findSlotByPosition(new int[]{slotArr[i].getPos()[0] + 1, slotArr[i].getPos()[1] - 1});
                    if (moveSlot == null)
                    {
                        System.out.println("!-! Looking for position: [" + (slotArr[i].getPos()[0] + 1) + ", " + (slotArr[i].getPos()[1] - 1) + "]");
                        return;
                    }
                    moveSlot.setAvailable(true);
                    moveSlot.setBackground(new Color(255, 0, 0)); // Attack slot
                    moveSlot.attacked = true;
                } else { // Regular move (empty or own team slot)
                    if (slotArr[i].getPos()[0] < 8) { // Ensure black doesn't move off the board
                        slotArr[i].setBackground(new Color(64, 255, 0)); // Highlight move slot
                        slotArr[i].setAvailable(true);
                    }
                }
            }
        }
    }

    public slot_B findSlotByPosition(int[] Pos) {
        slot_B toReturn = null;
        if (Pos[1] == 0)
        {
            Pos[1] = 1;
        }
        if (Pos[0] == 0)
        {
            Pos[0] = 1;
        }
        // Loop through each slot in slotArr
        for (int i = 0; i < slotArr.length; i++) {
            // Ensure that slotArr[i] is not null before accessing its getPos method
            if (slotArr[i] != null) {
                // Debugging output to check the values being compared
                System.out.println("slotArr[i].getPos(): " + Arrays.toString(slotArr[i].getPos()));
                System.out.println("Pos: " + Arrays.toString(Pos));

                // Check if the current slot's position matches the given position
                if (Arrays.equals(slotArr[i].getPos(), Pos)) {
                    toReturn = slotArr[i];
                    break; // Exit the loop once we find the matching slot
                }
            } else {
                // Log if there are any null slots in the array (this shouldn't happen in most cases)
                System.out.println("Warning: slotArr[" + i + "] is null!");
            }
        }

        // Return the slot if found, or null if not found
        return toReturn;
    }

    public void findMoveWhite(int[] Pos)
    {

        clearAllSelection();
        for (int i = 0; i < slotArr.length ; i++)
        {
            if(slotArr[i].getPos()[0] == Pos[0] && slotArr[i].getPos()[1]==Pos[1])
            {
                slotArr[i].setBackground(new Color(255, 92, 0));
                selected_slot = slotArr[i];
            }
            if(slotArr[i].getPos()[0] == Pos[0]+1 && slotArr[i].getPos()[1]==Pos[1]+1 )
            {
                if (slotArr[i].team != turn && slotArr[i].team != "")
                {
                    target = slotArr[i];
                    slot_B moveSlot;
                    moveSlot = findSlotByPosition(new int[]{slotArr[i].getPos()[0]+1,slotArr[i].getPos()[1]+1});
                    moveSlot.setAvailable(true);
                    moveSlot.setBackground(new Color(255,0,0));
                    moveSlot.attacked = true;
                }
                else {
                    slotArr[i].setBackground(new Color(64, 255,0));
                    slotArr[i].setAvailable(true);
                }
            }
            if(slotArr[i].getPos()[0] == Pos[0]-1 && slotArr[i].getPos()[1]==Pos[1]+1 )
            {
                if (slotArr[i].team != turn && slotArr[i].team != "")
                {
                    target = slotArr[i];
                    slot_B moveSlot;
                    moveSlot = findSlotByPosition(new int[]{slotArr[i].getPos()[0]-1,slotArr[i].getPos()[1]+1});
                    moveSlot.setAvailable(true);
                    moveSlot.setBackground(new Color(255,0,0));
                    moveSlot.attacked = true;
                }
                else {
                    slotArr[i].setBackground(new Color(64, 255,0));
                    slotArr[i].setAvailable(true);
                }
            }
        }
    }
    public slots_p() {
        super();
        boolean even = true;

        int x = 0;
        int y = 1;

        setPreferredSize(new Dimension(500, 500));
        setLayout(new GridLayout(8, 8));

        // Ensure y starts at 1 and x stays within 1 to 8 range
        for (int i = 0; i < 64; i++) {
            slotArr[i] = new slot_B(i, x, y, this);
            add(slotArr[i]);

            // Check if we are using even or odd logic to set the background color
            if (even) {
                if ((x & 1) == 0) {
                    slotArr[i].setBackground(new Color(174, 174, 174));
                } else {
                    slotArr[i].setBackground(new Color(100, 100, 100));
                }
            } else {
                if ((x & 1) != 0) {
                    slotArr[i].setBackground(new Color(174, 174, 174));
                } else {
                    slotArr[i].setBackground(new Color(100, 100, 100));
                }
            }

            // Debugging output
            System.out.println("!-! Iteration " + i + ": x = " + x + ", y = " + y);

            // Ensure that x stays within bounds
            if (x == 8) {
                x = 1;  // Reset x to 1 after it reaches 8
                y++;    // Increment y
                if (y > 8) {
                    System.out.println("!-! Warning: y exceeded the maximum value of 8!");
                    y = 8; // Prevent y from going beyond 8 (if this is not allowed)
                }
                even = !even;  // Toggle even/odd row logic
            } else {
                x++;  // Increment x
            }

            // Ensure that y never equals 0
            if (y == 0) {
                System.out.println("!-! Warning: y is 0, which should never happen!");
            }

            // Additional check to prevent x from exceeding 8
            if (x > 8) {
                System.out.println("!-! Error: x exceeded 8! Resetting x.");
                x = 1;
            }
            System.out.println("CREATING: "+Integer.toString(x)+" / "+Integer.toString(y));
        }

        // Creating teams
        createTeam(49, "Black", "O");
        createTeam(51, "Black", "O");
        createTeam(53, "Black", "O");
        createTeam(55, "Black", "O");
        createTeam(56, "Black", "O");
        createTeam(58, "Black", "O");
        createTeam(60, "Black", "O");
        createTeam(62, "Black", "O");

        createTeam(1, "White", "X");
        createTeam(3, "White", "X");
        createTeam(5, "White", "X");
        createTeam(7, "White", "X");
        createTeam(8, "White", "X");
        createTeam(10, "White", "X");
        createTeam(12, "White", "X");
        createTeam(14, "White", "X");

        updateSlotTeams();
    }

}
