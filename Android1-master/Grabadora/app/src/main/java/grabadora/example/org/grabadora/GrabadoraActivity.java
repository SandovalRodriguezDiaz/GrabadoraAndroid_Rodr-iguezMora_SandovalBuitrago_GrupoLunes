package grabadora.example.org.grabadora;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import java.io.IOException;
import android.app.Activity;
import android.media.MediaPlayer; //Esta clase sera usada para reproducir archivos de audio
import android.media.MediaRecorder; //Esta clase sera usada para grabar archivos de audio
import java.io.File;
import android.media.MediaPlayer.OnCompletionListener; //Called when the end of a media source is reached during playback
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

//Se deben dar los permisos anteriormente para utilizar los diferentes dispostivos del celular (reproductor, microfono) en el manifest

//Creación de la clase para los activities
public class GrabadoraActivity extends Activity implements OnCompletionListener { //Called when the end of a media source is reached during playback

    //Declarando variables
    TextView tv1; //textview como 'tv1'
    MediaRecorder recorder; //clase MediaRecorder oomo 'recorder'
    MediaPlayer player; //clase MediaPlayer como 'player'
    File archivo; //file como 'archivo'
    Button b1, b2, b3; //botones como 'b1, b2, b3'



    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //se inicia la aplicacion pero aún no es visible para el usuario
        //llamando al activity
        setContentView(R.layout.activity_grabadora);

        //Designando a los variables los botones y/o elementos del activity
        tv1 = (TextView) this.findViewById(R.id.textView1);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
    }
    //Función que abre la ventana por una variable booleana
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_grabadora, menu);
        return true;
    }
    //Función de grabar
    public void grabar(View v) {
        recorder = new MediaRecorder(); //clase que importamos anteriormente
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //la clase MediaRecorder ingresa al microfono del dispositivo
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//la clase MediaRecorder generara el archivo de audio en formato 3gp
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory() //se establece la direccion donde se genarara el audio
                .getPath());
        try {

            archivo = File.createTempFile("temporal", ".3gp", path); //se genera un archivo 'temporal' de audio en formato 3gp mediante la clase File
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        try {
            recorder.prepare();//se prepara la clase MediaRecorder para implementarse
        } catch (IOException e) {
        }
        recorder.start(); //Se inica la clase MediaRecorder - se inicia la grabacion
        tv1.setText("Grabando... "); //saldrá este texto mientras se esta grabando
        b1.setEnabled(false);//se inhabilitara el boton de grabar mientras se este grabando
        b2.setEnabled(true);//se habilitara el boton de detener grabacion mientras se este grabando
    }
    //Función para parar de grabar
    public void detener(View v) {
        recorder.stop(); //se detiene la grabacion
        recorder.release();//se renuncia a la clase MediaRecorder al soltar (release) el boton de grabar
        player = new MediaPlayer(); //se genera la clase que repodrucira el archivo grabado
        player.setOnCompletionListener(this); //Called when the end of a media source is reached during playback.
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        try {
            player.prepare();//se prepara la clase MediaPlayer para implementarse
        } catch (IOException e) {
        }
        b1.setEnabled(true);//se habilitara el boton de grabar cuando finalice la grabación
        b2.setEnabled(false);//se inhabilitara el boton de detener
        b3.setEnabled(true);//se habilitara el boton de reproducir
        tv1.setText("Listo para reproducir"); //saldrá este texto después de haber grabado
    }
    //Función para reproducir
    public void reproducir(View v) {
        player.start();//la clase MediaPlayer se incia
        b1.setEnabled(false);//se inhabilitara todos los botones miestra se este reproduciendo
        b2.setEnabled(false);
        b3.setEnabled(false);
        tv1.setText("Reproduciendo... ");//saldrá este texto mientras se este reproduciendo el archivo grabado
    }

    public void onCompletion(MediaPlayer mp) { //Called when the end of a media source is reached during playback.
        //despues de haber reproducido el archivo grabado se habilitaran todos los botones de nuevo
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        tv1.setText("¡Listo!");//saldrá este texto despues de haber reproducideo el archivo grabado
    }
}
