package seran.pulapula.game.ai

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Logger

class NeuralNetwork (val inSize: Int){
    var weightIn = FloatArray(inSize)
    val bias = 0.5f
    init {
        for (item in 0.rangeTo(inSize-1)){
            weightIn[item] = MathUtils.random(-10f, 10f)
        }

    }

    fun activation(sum: Float): Boolean{
        return (sum >= 0)
    }
    fun sum(data: FloatArray): Float {
        var index =0
        var sum = 0f
        for (item in data) {
            sum += item*weightIn[index]
        }
        return sum + bias
    }
    fun train(){
        var index = 0
        for (item in weightIn){
            weightIn[index++] += MathUtils.random(-2f, 2f)
        }
    }
    fun allow(data: FloatArray): Boolean{

        return activation(sum(data))
    }
}
