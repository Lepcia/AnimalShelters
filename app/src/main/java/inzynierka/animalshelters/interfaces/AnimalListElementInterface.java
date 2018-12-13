package inzynierka.animalshelters.interfaces;

import android.widget.ImageButton;

import inzynierka.animalshelters.models.AnimalDetailsModel;

public interface AnimalListElementInterface {
    void FavoriteBtn_onClick(ImageButton btn, final AnimalDetailsModel animalModel);
}
