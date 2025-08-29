package seran.pulapula.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3

class Bird(val world: World) {
    lateinit var bounds: Rectangle
    var score = 0;
    var velocity = 0F
    val width = world.width
    val height = world.height
    val gravity = world.gravity
    val animGravity = height*0.0000433f
    val animWingRate = height*0.005190f
    var isPlaying = false
    val wingRate = height*0.007353f
    lateinit var texture: Texture
    lateinit var hit: Sound
    lateinit var wing: Sound
    var volume = 0.1f
    lateinit var center: Vector3
    var isGameOver = false
    private var hitSoundPlayed = false
    var collided = false
    lateinit var renderer: ShapeRenderer
    var isGettingReady = true


    fun fromPath(path: String): FileHandle{
        return Gdx.files.internal(path)
    }

   init {
       texture = AssetsManager.birdTexture
       hit = AssetsManager.hit
       wing = AssetsManager.wing
       renderer = ShapeRenderer()
       renderer.color = Color.RED

      restart()
   }
    fun draw(batch: Batch){
        bounds.width =height*0.06f
       bounds.height =height*0.06f
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height)
    }
    fun showCollision() {
        Gdx.gl.glLineWidth(3f)
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.circle(center.x, center.y, bounds.width/2)
        renderer.end()
    }
    fun update(delta: Float){
      
        if (!collided){
            velocity += gravity
            bounds.y += velocity
        }
        if (isPlaying){
            world.scoreManager(this)
        }


        if (isGettingReady)animate()

        if (bounds.y < MainGame.foreg_h) collided = true

        if (world.collided(this)) collide()
        center.y = bounds.y + bounds.height/2

    }
    fun wing(){
        play()
        if (bounds.y < height + height*0.1f){
            velocity = wingRate
            play(wing)
        }

    }
    fun collide(){
        if (!hitSoundPlayed) play(hit)
        if (bounds.y> MainGame.foreg_h && !hitSoundPlayed)play(AssetsManager.die)
        gameOver()
        hitSoundPlayed = true
    }
    fun restart(){
        bounds = Rectangle(width*0.15f, MainGame.foreg_h*3, height*0.06f, height*0.06f)
        center = Vector3(bounds.x+bounds.width/2, bounds.y + bounds.height/2, bounds.width/2)
        velocity = 0f
        hitSoundPlayed = false
        center.y = bounds.y + bounds.height/2
        score = 0;
        collided = false
        isPlaying = false
        isGameOver = false
        isGettingReady = true
    }
    fun gameOver(){
        if (score > MainGame.settings.scores) MainGame.settings.scores = score
        isGameOver = true;
        isPlaying = false;
    }
    fun play(){
        isPlaying = true
        isGettingReady = false
    }
    fun animate(){
        velocity +=animGravity
        bounds.y += velocity
        if (bounds.y < MainGame.foreg_h*3) velocity = animWingRate

    }
    fun play(sound: Sound){
        Gdx.app.postRunnable {
            sound.play(volume)
        }
    }
    
   
    fun dispose(){
         renderer.dispose()
    }



}
