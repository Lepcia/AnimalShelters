package inzynierka.animalshelters.interfaces;

import android.app.AlertDialog;
import android.widget.ImageButton;

public interface AdminListElementInterface {

    void EditBtn_onClick(ImageButton btn, final int id);
    void DeleteBtn_onClick(ImageButton btn);
    AlertDialog ConfirmDelete();

}
