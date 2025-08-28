package seran.pulapula.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch

public object AssetsManager {

        lateinit var birdTexture: Texture
        lateinit var background: Texture
        lateinit var foreground: Texture
        lateinit var pipeTop: Texture
        lateinit var pipeDown: Texture
        lateinit var hit: Sound
        lateinit var wing: Sound
        lateinit var die: Sound
        lateinit var score: Sound
        lateinit var fontFile: FileHandle
         

        @JvmStatic
        fun load(){
            birdTexture = Texture(fromPath("ball.png"))
            hit = Gdx.audio.newSound(fromPath("audio/hit.ogg"))
            score = Gdx.audio.newSound(fromPath("audio/point.ogg"))
            die = Gdx.audio.newSound(fromPath("audio/die.ogg"))
            wing = Gdx.audio.newSound(fromPath("audio/wing.ogg"))
            pipeDown = Texture(fromPath("pipeD.png"))
            pipeTop = Texture(fromPath("pipeT.png"))
            foreground = Texture(fromPath("foreg.png"))
            background = Texture(fromPath("background.jpg"))
            fontFile = fromPath("fonts/font.fnt")
            

        }
    @JvmStatic
    fun drawText(batch: SpriteBatch, text: String, x: Float, y: Float, scale: Float ){

    }
        fun fromPath(path: String): FileHandle{
            return Gdx.files.internal(path)
        }
    @JvmStatic
    fun play(sound: Sound, volume: Float){
        Gdx.app.postRunnable {
            sound.play(volume)
        }
    }

        @JvmStatic
        fun dispose(){
            birdTexture.dispose()
            hit.dispose()
            score.dispose()
            die.dispose()
            wing.dispose()
            pipeTop.dispose()
            pipeDown.dispose()
            background.dispose()
            foreground.dispose()
            
        }


}
