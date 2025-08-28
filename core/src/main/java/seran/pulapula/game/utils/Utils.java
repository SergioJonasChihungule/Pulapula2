package seran.pulapula.game.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Utils {
    
    // Calcular a escala necessária para obter um tamanho específico em pixels
    public static float calculateScale(BitmapFont fonteOriginal, int tamanhoDesejado) {
        // Se sua fonte original é de 64px, a escala é tamanhoDesejado/64
        return tamanhoDesejado / fonteOriginal.getCapHeight();
    }
    
    // Método para desenhar texto com tamanho específico
    public static void drawText(SpriteBatch batch, BitmapFont font, String texto, float x, float y, int tamanhoPx) {
        float escala = calculateScale(font, tamanhoPx);
        font.getData().setScale(escala);
        font.draw(batch, texto, x, y);
    }
}