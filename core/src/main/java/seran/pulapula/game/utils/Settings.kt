package seran.pulapula.game.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences

public  class Settings(private val preferences: Preferences) {


        var scores: Int
            get() = preferences.getInteger("scores", 0)
            set(value) {
                preferences.putInteger("scores", value)
                preferences.flush()
            }


}