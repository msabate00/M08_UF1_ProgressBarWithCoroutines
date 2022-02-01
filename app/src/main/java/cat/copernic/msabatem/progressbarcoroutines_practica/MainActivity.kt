package cat.copernic.msabatem.progressbarcoroutines_practica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val scope = MainScope()

    private var estado_1 = SIN_INICIAR;
    private var estado_2 = SIN_INICIAR;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar_1: ProgressBar = findViewById(R.id.progressbar_1)
        val progressBar_2: ProgressBar = findViewById(R.id.progressbar_2)


        //PRIMERA PROGRESS BAR
        findViewById<Button>(R.id.iniciar_1).setOnClickListener {
            if (estado_1 != DETENIDA) {
                estado_1 = CORRIENDO;
                it.isEnabled = false;
                findViewById<Button>(R.id.pausar_1).isEnabled = true;
                findViewById<Button>(R.id.detener_1).isEnabled = true;
            }

        }
        findViewById<Button>(R.id.pausar_1).setOnClickListener {
            if (estado_1 != DETENIDA) {
                if (estado_1 == CORRIENDO) {
                    estado_1 = PAUSADA;
                    findViewById<Button>(R.id.pausar_1).text = "REANUDAR";
                } else {
                    estado_1 = CORRIENDO;
                    findViewById<Button>(R.id.pausar_1).text = "PAUSAR";
                }
            }
        }
        findViewById<Button>(R.id.detener_1).setOnClickListener {
            estado_1 = DETENIDA;
            findViewById<Button>(R.id.pausar_1).isEnabled = false;
            it.isEnabled = false;
        }

        //SEGUNDA PROGRESS BAR
        findViewById<Button>(R.id.iniciar_2).setOnClickListener {
            if (estado_2 != DETENIDA) {
                estado_2 = CORRIENDO;
                it.isEnabled = false;
                findViewById<Button>(R.id.pausar_2).isEnabled = true;
                findViewById<Button>(R.id.detener_2).isEnabled = true;
            }

        }
        findViewById<Button>(R.id.pausar_2).setOnClickListener {
            if (estado_2 != DETENIDA) {
                if (estado_2 == CORRIENDO) {
                    estado_2 = PAUSADA;
                    findViewById<Button>(R.id.pausar_2).text = "REANUDAR";
                } else {
                    estado_2 = CORRIENDO;
                    findViewById<Button>(R.id.pausar_2).text = "PAUSAR";
                }
            }
        }
        findViewById<Button>(R.id.detener_2).setOnClickListener {
            estado_2 = DETENIDA;
            findViewById<Button>(R.id.pausar_2).isEnabled = false;
            it.isEnabled = false;
        }


        // Indicar determinado
        scope.launch {
            while (true) {

                when (estado_1) {
                    SIN_INICIAR -> {}
                    CORRIENDO -> {
                        progress(progressBar_1);
                    }
                    PAUSADA -> {}
                    DETENIDA -> {}
                }

                when (estado_2) {
                    SIN_INICIAR -> {}
                    CORRIENDO -> {
                        progress(progressBar_2);
                    }
                    PAUSADA -> {}
                    DETENIDA -> {}
                }

                //progress(progressBar_1)
            delay(300);
            }

        }
    }

    private suspend fun progress(progressBar: ProgressBar) {

        progressBar.incrementProgressBy(PROGRESS_INCREMENT)
        if(progressBar.progress >= progressBar.max){
            progressBar.progress = 0
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() // Destruimos el alcance de la corrutina
    }

    companion object {
        const val PROGRESS_INCREMENT = 5

        const val SIN_INICIAR = 0;
        const val CORRIENDO = 1;
        const val PAUSADA = 2;
        const val DETENIDA = 3;

    }
}