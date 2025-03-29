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
    private final List <MemberMemento> backup = new ArrayList<>();
    private int currIndex = -1;
   public void saveMemento(MemberMemento memberMemento) {
        while (backup.size() > currIndex + 1) { // for redo
            backup.remove(backup.size() - 1); //Xoa backup o gan nhat
        }
        backup.add(memberMemento);
        currIndex++;
    }

    // undo
    public MemberMemento undo() {
        MemberMemento memento = backup.get(currIndex);
        currIndex--;
        return memento;
    }

    // redo
    public MemberMemento redo() {
            currIndex++;
            return backup.get(currIndex);
    }
}
