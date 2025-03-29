/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MemberMemento;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author minh9
 */
public class MemberRestore {
    private final List <MemberMemento> backup = new ArrayList<>();  // 0 1 2
    private int currIndex = -1;
   public void saveMemento(MemberMemento memberMemento) {  //  rong  
        while (backup.size() > currIndex + 1) { // for redo
            backup.remove(backup.size() - 1); //Xoa backup o gan nhat
        }
        backup.add(memberMemento);
        currIndex++; //0
    }

    // undo
    public MemberMemento undo() {
        if(canUndo()) {
        MemberMemento memento = backup.get(currIndex);
        currIndex--; //-1 
        return memento;
        } return null;
    }

    // redo
    public MemberMemento redo() {
        if(canRedo()) {
            currIndex++;
            return backup.get(currIndex);
        } return null;
    }
    
    public boolean canUndo()
    {
       boolean check =  currIndex >= 0 && !backup.isEmpty() ?  true :  false; // 
       return check;
    }
    
    
        public boolean canRedo()
    {
       boolean check =  currIndex < backup.size() - 1 ?  true :  false; // 
       return check;
    }
    
    
    
}
