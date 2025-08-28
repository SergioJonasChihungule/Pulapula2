package seran.pulapula.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3

class World(var width: Float, var height: Float)  {
    var background = AssetsManager.background
    var foreground = AssetsManager.foreground
    var pipeTop = AssetsManager.pipeTop
    var pipeDown = AssetsManager.pipeDown
    lateinit var rtop1: Rectangle
    lateinit var rtop2: Rectangle
    lateinit var rdown1: Rectangle
   lateinit var rdown2: Rectangle
   var ac = -width*0.00046f
   var scored = false
   var gravity = -height*0.0003417f

    var x = width
    var velocity = -width*0.007f
    var GAP = height*0.18f
    var yt = 0F
    var pipe_h = height*0.70f
    var pipe_w = width/4f
    var index = 0
    lateinit var renderer: ShapeRenderer

    init {
        yt = MainGame.foreg_h + height / 2 + (MathUtils.random(-1, 2) * GAP*0.45f)
        rtop1 = Rectangle(width, yt, pipe_w, pipe_h / 8.721f)
        rtop2 = Rectangle(width+ pipe_w*0.094f, yt, pipe_w-(2*pipe_w*0.094f), pipe_h)
        rdown1 = Rectangle(width, rtop1.y-GAP-pipe_h / 8.721f, pipe_w, pipe_h / 8.721f)
        rdown2 = Rectangle(width+ pipe_w*0.094f, rtop1.y-GAP-pipe_h, pipe_w-(2*pipe_w*0.094f), pipe_h)
        renderer = ShapeRenderer()
        renderer.color = Color.YELLOW

    }

    fun drawBckground(batch: Batch){
        batch.draw(background, 0f,0f, width,height)

    }
    fun drawPipes(batch: Batch){
        batch.draw(pipeTop, x, yt, pipe_w, pipe_h)
        batch.draw(pipeDown, x, yt - pipe_h - GAP, pipe_w, pipe_h)
    }
    fun  drawForeground(batch: Batch){
        batch.draw(foreground, 0f, 0f, foreground.getWidth().toFloat(), height / 6)
    }
    fun showCollision(){
        Gdx.gl.glLineWidth(3f)
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.rect(rtop1.x, rtop1.y, rtop1.width, rtop1.height)
        renderer.rect(rtop2.x, rtop2.y, rtop2.width, rtop2.height)
        renderer.rect(rdown1.x, rdown1.y, rdown1.width, rdown1.height)
        renderer.rect(rdown2.x, rdown2.y, rdown2.width, rdown2.height)
        renderer.end()
    }
    fun update(delta: Float){
        x += velocity


        if (x < 0 - pipe_w) {
            yt = MainGame.foreg_h + height / 2 + (MathUtils.random(-1, 2) * GAP*0.35f)
            x = width
            index++
            scored = false
           if (index==10) ac *= 0.5f
           if (index==20) ac *= 0.5f
           
        }
        rtop1.y = yt
        rtop1.x =  x
        rtop2.x = x + pipe_w*0.094f
        rtop2.y = yt
        rdown1.x = x
        rdown1.y = rtop1.y-GAP-pipe_h / 8.721f
        rdown2.x = x + pipe_w*0.094f
        rdown2.y = rtop1.y-GAP-pipe_h
    }
    fun reset(){
        velocity = -width*0.007f
        ac = -width*0.00046f
        x = width
        index = 0
        rtop1.y = yt
        rtop1.x =  x
        rtop2.x = x + pipe_w*0.094f
        rtop2.y = yt
        rdown1.x = x
        rdown1.y = rtop1.y-GAP-pipe_h / 8.721f
        rdown2.x = x + pipe_w*0.094f
        rdown2.y = rtop1.y-GAP-pipe_h
        scored = false
    }
    fun setSize(w: Float, h: Float){
       width = w
       height = h
      initValues()
      reset()
    }
    fun initValues(){
      pipe_h = height*0.70f
      ac = -width*0.00046f
      gravity = -height*0.0003417f
      velocity = -width*0.007f
      GAP = height*0.18f
      pipe_w = width/4f
      rtop1.setSize(pipe_w, pipe_h / 8.721f)
      rtop2.setSize(pipe_w-(2*pipe_w*0.094f), pipe_h)
      rdown1.setSize(pipe_w, pipe_h / 8.721f)
      rdown1.setSize(pipe_w-(2*pipe_w*0.094f), pipe_h)
    }
    fun scoreManager(bird: Bird){
        if (!scored && (x+rtop1.width/2)<bird.bounds.x) {
            bird.score++
            velocity += ac
            AssetsManager.play(AssetsManager.score, 0.1F)
            scored = true
        }
        

    }
    fun  collided(bird: Bird): Boolean{
        if (checkCollision(rtop1, bird.center)) return true
        if (checkCollision(rtop2, bird.center)) return true
        if (checkCollision(rdown1, bird.center)) return true
        if (checkCollision(rdown2, bird.center)) return true
        if (bird.bounds.y < MainGame.foreg_h) return true
        return false
    }
    fun checkCollision(rect: Rectangle, circle: Vector3): Boolean{
        var closedX = clamb(circle.x, rect.x, rect.x+rect.width )
        var closedY = clamb(circle.y, rect.y, rect.y+rect.height )
        var distX = circle.x - closedX
        var distY = circle.y - closedY

        var sqrDist = (distX*distX) + ( distY*distY)
        return sqrDist< (circle.z*circle.z)
    }
    fun  dispose(){
        renderer.dispose()
    }
    fun clamb(value: Float, min: Float, max: Float): Float{
        if (value<min) return min
        if (value>max) return max
        return value
    }
}
