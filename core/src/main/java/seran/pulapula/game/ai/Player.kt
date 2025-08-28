package seran.pulapula.game.ai

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import seran.pulapula.game.Bird

class Player(var bird: Bird) {
     var cx = bird.center.x
     var cy = bird.center.y
     var wcx = bird.world.x + bird.world.pipe_w/2f
     var wcy = bird.world.rtop1.y - bird.world.GAP/2F
     var velocity = bird.world.velocity
    lateinit var renderer: ShapeRenderer
    var data = FloatArray(3)
     lateinit var network: NeuralNetwork


    init {
        renderer = ShapeRenderer()
        renderer.color = Color.YELLOW
        network = NeuralNetwork(4)
    }
    fun drawCenter(){

        velocity = bird.world.velocity
        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.circle(wcx, wcy, 16f)
        renderer.circle(cx, cy, 16f)
        renderer.end()

    }
    fun draw(batch: Batch){
        bird.draw(batch)
    }
    fun dispose(){
        bird.dispose()
        renderer.dispose()
    }
    fun start(){
        bird.play()
    }
    fun play(delta: Float){
        wcx = bird.world.x + bird.world.pipe_w/2f
        wcy = bird.world.rtop1.y - bird.world.GAP/2F
        cx = bird.center.x
        cy = bird.center.y
        var dx = wcx - cx
        var dy = wcy - cy
        data = floatArrayOf(dx, dy, bird.world.velocity)
        bird.update(delta)
        if (bird.isPlaying && network.allow(data)) {
            bird.wing()
        }
    }
    fun restart(){
        network = NeuralNetwork(3)
        bird.restart()
    }
    fun train(){
        network.train()
    }



}
