package seran.pulapula.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import seran.pulapula.game.utils.Settings;
import seran.pulapula.game.utils.Utils;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private  boolean  hitPlayed = false;
    String gameOver ="GAME OVER", getReady = "GET READY";

    float width, height,  text_w,  text_h;
   public static float foreg_h;
   private BitmapFont font, font2;
   private GlyphLayout layout;




    private Bird bird;
    private World world;
     public static Settings settings;
    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        settings = new Settings( Gdx.app.getPreferences("My Preferences"));
        
        batch = new SpriteBatch();
        AssetsManager.load();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GoldenTaylor.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(height*0.08f);
        parameter.shadowOffsetX = (int) (parameter.size*0.05f);
        parameter.shadowOffsetY = (int) (parameter.size*0.05f);
        parameter.shadowColor = Color.BLACK;


        font = generator.generateFont(parameter);
        parameter.size = (int)(height*0.03f);
        parameter.shadowOffsetX = (int) (parameter.size*0.05f);
        parameter.shadowOffsetY = (int) (parameter.size*0.05f);
        font2 = generator.generateFont(parameter);


         generator.dispose();
        layout = new GlyphLayout(font, "");

        
       foreg_h = height/6;
       world = new World(width, height);
       bird = new Bird(world);
       restart();
       Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.09f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime();
        batch.begin();
        world.drawBckground(batch );
        world.drawPipes(batch);
        world.drawForeground(batch);

        bird.draw(batch);

        if (bird.isGettingReady()){
            font.setColor(Color.GREEN);
            layout.setText(font, getReady);
            text_w = layout.width;
            text_h = layout.height;
            font.draw(batch, getReady, width/2-text_w/2, height/2 + 4*text_h);
        }
        if (bird.isPlaying() || bird.isGameOver()){
            font.setColor(Color.WHITE);
            layout.setText(font, ""+bird.getScore());
            text_w = layout.width;
            font.draw(batch, ""+bird.getScore(), width/2-text_w/2, height-height*0.1f);
        }
        font2.draw(batch, "Rercord: "+settings.getScores(), width*0.03f,height- width*0.03f);
        if (bird.isGameOver()){
            layout.setText(font, gameOver);
            text_w = layout.width;
            text_h = layout.height;
            font.setColor(Color.RED);
            font.draw(batch, gameOver, width/2-text_w/2, height/2 + 2* text_h);
        }

        batch.end();
         
        if (bird.isPlaying()){
            world.update(delta);
        }
        

        bird.update(delta);



    }

  public void restart() {
    world.reset();
    bird.restart();

    hitPlayed = false;
  }

  @Override
  public void resize(int w, int h) {
    super.resize(w, h);
     
  }

  @Override
  public void dispose() {
    batch.dispose();
    AssetsManager.dispose();
    font.dispose();
    bird.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE && !bird.isGameOver()){
            bird.wing();
        }
        if (keycode== Input.Keys.ENTER && !bird.isPlaying()) {
            if (bird.bounds.y <= foreg_h)restart();
        }




        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
     float click1;
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float click2 = TimeUtils.millis();
        if(bird.isGameOver()){

            if (bird.bounds.y <= foreg_h)restart();

            return true;
        }
        if(!bird.isPlaying()) {
            bird.play();

        }

        if(click2>500){
          bird.wing();
        }
        click1 = click2;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
